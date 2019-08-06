package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.spring.model.FirFiles;
import com.demo.spring.model.UserFiles;

public interface FirFileRepository extends CrudRepository<FirFiles, Long> {

	@Query("select f from FirFiles as f where f.fir.id=?1")
	List<UserFiles> findFilesByFirId(Long userId);
	
	@Modifying
	@Query("delete from FirFiles as f where f.fir.id=?1 and f.modifiedFileName in (?2)")
	void deleteFilesByFirUdAndImageNames(Long id, List<String> removeImages);
	
	@Modifying
	@Query("delete from FirFiles as f where f.fir.id=?1")
	void deleteFilesByFirId(Long userId);
	@Query("select f from FirFiles as f where f.fileExtension=?1")
	List<UserFiles> findFilesByExtension(String extension);
}
