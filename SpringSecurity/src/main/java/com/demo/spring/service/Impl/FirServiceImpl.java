package com.demo.spring.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.demo.spring.model.Fir;
import com.demo.spring.model.FirFiles;
import com.demo.spring.model.Slider;
import com.demo.spring.model.UserFiles;
import com.demo.spring.repository.FirFileRepository;
import com.demo.spring.repository.FirRepository;
import com.demo.spring.service.FirService;
import com.demo.spring.service.UploadPathService;

@Service
public class FirServiceImpl implements FirService{

	@Autowired UploadPathService uploadPathService;
	@Autowired FirRepository firRepository;
	@Autowired FirFileRepository firFileRepository;
	@Override
	public void saveFir(Fir fir, MultipartHttpServletRequest httpfile) {
		fir.setFirSubmittedDate(new Date());
		MultipartFile mpf=null;
		List<MultipartFile> firFiles=new ArrayList<>();
		Iterator<String> files=httpfile.getFileNames();
		while(files.hasNext()) {
			String f=files.next();
			mpf=httpfile.getFile(f);
			firFiles.add(mpf);
		}
		for(MultipartFile f: firFiles) {
			System.out.println("Fir File names"+f.getOriginalFilename());
		}
		fir.setFiles(firFiles);
		fir.setStatus(false);
		//System.out.println("Fir files "+firFiles.get(0));
		//firRepository.save(fir);
		Fir dbFir=firRepository.save(fir);
		if(dbFir!=null && fir.getFiles()!=null && fir.getFiles().size()>0) {
			for (MultipartFile file : fir.getFiles()) {
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
				FirFiles firProofs=new FirFiles();
				firProofs.setFileExtension(FilenameUtils.getExtension(fileName));
				firProofs.setFileName(fileName);
				firProofs.setModifiedFileName(modifiedFileName);
				firProofs.setFir(dbFir);
				firFileRepository.save(firProofs);
			}
		}
	}

}
