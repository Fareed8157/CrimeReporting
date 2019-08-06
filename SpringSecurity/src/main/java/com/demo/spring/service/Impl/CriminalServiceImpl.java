package com.demo.spring.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.demo.spring.domain.CriminalResponse;
import com.demo.spring.domain.EventResponse;
import com.demo.spring.domain.ImagesByCriminal;
import com.demo.spring.domain.ImagesByEvent;
import com.demo.spring.model.Criminal;
import com.demo.spring.model.CriminalFiles;
import com.demo.spring.model.Slider;
import com.demo.spring.model.UserFiles;
import com.demo.spring.repository.CriminalFileRepository;
import com.demo.spring.repository.CriminalRepository;
import com.demo.spring.service.CriminalService;
import com.demo.spring.service.UploadPathService;

@Service
public class CriminalServiceImpl implements CriminalService {

	@Autowired CriminalRepository criminalRepository;
	@Autowired UploadPathService uploadPathService;
	@Autowired CriminalFileRepository criminalFileRepository;
	@Autowired ServletContext context;
	private FileInputStream fileInputStream;

	@Transactional
	@Override
	public Criminal save(Criminal criminal,MultipartHttpServletRequest httpfile) {
		//criminalRepository.save(criminal);
		MultipartFile mpf=null;
		List<MultipartFile> criminalFiles=new ArrayList<>();
		Iterator<String> files=httpfile.getFileNames();
		while(files.hasNext()) {
			String f=files.next();
			mpf=httpfile.getFile(f);
			criminalFiles.add(mpf);
		}
		for(MultipartFile f: criminalFiles) {
			System.out.println("File names"+f.getOriginalFilename());
		}
		criminal.setFiles(criminalFiles);
		
		Criminal dbCriminal=criminalRepository.save(criminal);
		//System.out.println("User files"+slider.getFiles()+",Size ="+slider.getFiles().size()+",Files="+slider.getFiles().equals(""));
		if(criminal!=null && criminal.getRemoveImages()!=null && criminal.getRemoveImages().size()>0) {
			System.out.println("Inside removeImages");
			criminalFileRepository.deleteFilesByCriminalUdAndImageNames(criminal.getId(),criminal.getRemoveImages());
			for(String file : criminal.getRemoveImages()) {
				File dbFile=new File(context.getRealPath("/images/"+File.separator+file));
				if(dbFile.exists()) {
					dbFile.delete();
				}
			}
		}
		System.out.println(dbCriminal+","+dbCriminal.getFiles()+","+dbCriminal.getFiles().size());
		if(dbCriminal!=null && criminal.getFiles()!=null && criminal.getFiles().size()>0) {
			for (MultipartFile file : criminal.getFiles()) {
				String fileName=file.getOriginalFilename();
				System.out.println("original name"+fileName);
				String modifiedFileName=FilenameUtils.getBaseName(fileName)+"_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(fileName);
				File storeFile=uploadPathService.getFilePath(modifiedFileName,"images");
				if(storeFile!=null) {
					try {
						
						FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				CriminalFiles crimFiles=new CriminalFiles();
				crimFiles.setFileExtension(FilenameUtils.getExtension(fileName));
				crimFiles.setFileName(fileName);
				crimFiles.setModifiedFileName(modifiedFileName);
				crimFiles.setCriminals(dbCriminal);
				criminalFileRepository.save(crimFiles);
			}
		}
		return dbCriminal;
	}

	@Override
	public List<CriminalFiles> findFilesByCriminalId(Long criminalId) {
		// TODO Auto-generated method stub
		return criminalFileRepository.findFilesByCriminalId(criminalId);
	}
	
	@Override
	public CriminalResponse findCriminals() {
		List<Criminal> criminals=(List<Criminal>)criminalRepository.findAll();
		//System.out.println("Criminals are "+criminals.size());
		List<String> imageNames=new ArrayList<>();
		List<ImagesByCriminal> listOfCriminals=new ArrayList<>();
		for(Criminal criminal: criminals) {
			List<String> images=new ArrayList<>();
			ImagesByCriminal ibc=new ImagesByCriminal();
			ibc.setCriminal(criminal);
			//System.out.println("Slider"+criminal);
			List<CriminalFiles> criminalFiles=this.findFilesByCriminalId(criminal.getId());
			if(criminalFiles!=null && criminalFiles.size()>0) {
				for(CriminalFiles dbFile : criminalFiles) {
					File dbStoreFile=new File(context.getRealPath("/images/"+File.separator+dbFile.getModifiedFileName()));
					if(dbStoreFile.exists()) {
						if(!dbStoreFile.isDirectory()) {
							String encodeBase64=null;
							try {
								String extension=FilenameUtils.getExtension(dbStoreFile.getName());
								String fileName=dbStoreFile.getName();
								fileInputStream = new FileInputStream(dbStoreFile);
								byte[] bytes=new byte[(int)dbStoreFile.length()];
								fileInputStream.read(bytes);
								encodeBase64=Base64.getEncoder().encodeToString(bytes);
								imageNames.add(fileName);
								if(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png"))
									images.add("data:image/"+extension.toLowerCase()+";base64,"+encodeBase64);
								else {
									images.add("fileName:"+fileName+":extension:"+extension+"");
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				}
				ibc.setImages(images);
			}
			listOfCriminals.add(ibc);
		}
		CriminalResponse res=new CriminalResponse();
		res.setListOfCriminals(listOfCriminals);
		res.setFileName(imageNames);
		return res;
	}

	
	public void deleteFilesByCriminalId(Long criminalId) {
		List<CriminalFiles> criminalFiles=criminalFileRepository.findFilesByCriminalId(criminalId);
		if(criminalFiles!=null && criminalFiles.size()>0) {
			for(CriminalFiles dbFile : criminalFiles) {
				File dbStoreFile=new File(context.getRealPath("/images/"+File.separator+dbFile.getModifiedFileName()));
				if(dbStoreFile.exists()) {
					dbStoreFile.delete();
				}
			}
		}
		criminalFileRepository.deleteFilesByCriminalId(criminalId);
	}

	@Transactional
	@Override
	public void deleteCriminalById(Long criminalId) {
		this.deleteFilesByCriminalId(criminalId);
		criminalRepository.deleteById(criminalId);
	}

	@Override
	public ImagesByCriminal findByCriminalId(Long criminalId) {
		List<String> imageNames=new ArrayList<>();

		List<String> images=new ArrayList<>();
		ImagesByCriminal ibc=new ImagesByCriminal();
		Criminal criminal=criminalRepository.findById(criminalId).orElse(new Criminal());
		ibc.setCriminal(criminal);
		List<CriminalFiles> criminalFiles=this.findFilesByCriminalId(criminal.getId());
		if(criminalFiles!=null && criminalFiles.size()>0) {
			for(CriminalFiles dbFile : criminalFiles) {
				File dbStoreFile=new File(context.getRealPath("/images/"+File.separator+dbFile.getModifiedFileName()));
				if(dbStoreFile.exists()) {
					if(!dbStoreFile.isDirectory()) {
						String encodeBase64=null;
						try {
							String extension=FilenameUtils.getExtension(dbStoreFile.getName());
							String fileName=dbStoreFile.getName();
							fileInputStream = new FileInputStream(dbStoreFile);
							byte[] bytes=new byte[(int)dbStoreFile.length()];
							fileInputStream.read(bytes);
							imageNames.add(fileName);
							encodeBase64=Base64.getEncoder().encodeToString(bytes);
							System.out.println("File names with extension"+fileName+"=="+extension);
							if(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png"))
								images.add("data:image/"+extension.toLowerCase()+";base64,"+encodeBase64);
							else {
								images.add("fileName:"+fileName+":extension:"+extension+"");
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
			ibc.setImages(images);
		}
		ibc.setFileName(imageNames);		
		return ibc;
	}
	
	

}
