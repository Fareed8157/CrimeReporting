package com.demo.spring.service;

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

import com.demo.spring.domain.EventResponse;
import com.demo.spring.domain.ImagesByEvent;
import com.demo.spring.domain.Response;
import com.demo.spring.model.Slider;
import com.demo.spring.model.UserFiles;
import com.demo.spring.repository.FilesRepository;
import com.demo.spring.repository.SliderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SliderServiceImpl implements SliderService{

	@Autowired SliderRepository sliderRepository;
	@Autowired UploadPathService uploadPathService;
	@Autowired FilesRepository userFileRepository;
	@Autowired ServletContext context;
	private FileInputStream fileInputStream;
	
	
	@Transactional
	@Override
	public Slider save(Slider slider,MultipartHttpServletRequest httpfile) {
		MultipartFile mpf=null;
		List<MultipartFile> sliderFiles=new ArrayList<>();
		Iterator<String> files=httpfile.getFileNames();
		while(files.hasNext()) {
			String f=files.next();
			mpf=httpfile.getFile(f);
			sliderFiles.add(mpf);
		}
		for(MultipartFile f: sliderFiles) {
			System.out.println("File names"+f.getOriginalFilename());
		}
		slider.setFiles(sliderFiles);
		slider.setEnabled(true);
		
		Slider dbSlider=sliderRepository.save(slider);
		System.out.println("User files"+slider.getFiles()+",Size ="+slider.getFiles().size()+",Files="+slider.getFiles().equals(""));
		if(slider!=null && slider.getRemoveImages()!=null && slider.getRemoveImages().size()>0) {
			System.out.println("Inside removeImages");
			userFileRepository.deleteFilesBySliderUdAndImageNames(slider.getId(),slider.getRemoveImages());
			for(String file : slider.getRemoveImages()) {
				File dbFile=new File(context.getRealPath("/images/"+File.separator+file));
				if(dbFile.exists()) {
					dbFile.delete();
				}
			}
		}
		
		if(dbSlider!=null && slider.getFiles()!=null && slider.getFiles().size()>0) {
			for (MultipartFile file : slider.getFiles()) {
				String fileName=file.getOriginalFilename();
				String modifiedFileName=FilenameUtils.getBaseName(fileName)+"_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(fileName);
				File storeFile=uploadPathService.getFilePath(modifiedFileName,"images");
				if(storeFile!=null) {
					try {
						
						FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				UserFiles userFiles=new UserFiles();
				userFiles.setFileExtension(FilenameUtils.getExtension(fileName));
				userFiles.setFileName(fileName);
				userFiles.setModifiedFileName(modifiedFileName);
				userFiles.setSlider(dbSlider);
				userFileRepository.save(userFiles);
			}
		}
		return dbSlider;
	}
	@Override
	public List<UserFiles> findFilesBySliderId(Long userId) {
		// TODO Auto-generated method stub
		return userFileRepository.findFilesBySliderId(userId);
	}
	
	@Override
	public EventResponse findFilesByStatus(boolean status) {
		List<Slider> sliders=sliderRepository.findSliderByStatus(true);
		List<String> imageNames=new ArrayList<>();

		List<ImagesByEvent> listOfEvents=new ArrayList<>();
		for(Slider slider: sliders) {
			List<String> images=new ArrayList<>();
			ImagesByEvent ibe=new ImagesByEvent();
			ibe.setSlider(slider);
			System.out.println("Slider"+slider);
			List<UserFiles> userFiles=this.findFilesBySliderId(slider.getId());
			if(userFiles!=null && userFiles.size()>0) {
				for(UserFiles dbFile : userFiles) {
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
				ibe.setImages(images);
			}
			listOfEvents.add(ibe);
		}
		//System.out.println(sliders);
		EventResponse res=new EventResponse();
		res.setListOfEvents(listOfEvents);
		res.setFileName(imageNames);
		return res;
	}
	
	public void deleteFilesBySliderId(Long eventId) {
		List<UserFiles> userFiles=userFileRepository.findFilesBySliderId(eventId);
		if(userFiles!=null && userFiles.size()>0) {
			for(UserFiles dbFile : userFiles) {
				File dbStoreFile=new File(context.getRealPath("/images/"+File.separator+dbFile.getModifiedFileName()));
				if(dbStoreFile.exists()) {
					dbStoreFile.delete();
				}
			}
		}
		userFileRepository.deleteFilesBySliderId(eventId);
	}

	@Transactional
	@Override
	public void deleteImagesByEvent(Long eventId) {
		this.deleteFilesBySliderId(eventId);
		sliderRepository.deleteById(eventId);
	}
	
	
	public ImagesByEvent findByEventId(Long eventId) {
		List<String> imageNames=new ArrayList<>();

		List<String> images=new ArrayList<>();
		ImagesByEvent ibe=new ImagesByEvent();
		Slider slider=sliderRepository.findById(eventId).orElse(new Slider());
		ibe.setSlider(slider);
		List<UserFiles> userFiles=this.findFilesBySliderId(slider.getId());
		if(userFiles!=null && userFiles.size()>0) {
			for(UserFiles dbFile : userFiles) {
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
			ibe.setImages(images);
			
		}
		ibe.setFileName(imageNames);		
		return ibe;
	}

}
