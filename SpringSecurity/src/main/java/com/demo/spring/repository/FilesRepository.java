package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.model.UserFiles;


@Repository
public interface FilesRepository extends CrudRepository<UserFiles, Long> {

	
	@Query("select f from UserFiles as f where f.slider.id=?1")
	List<UserFiles> findFilesBySliderId(Long userId);
	
	@Modifying
	@Query("delete from UserFiles as f where f.slider.id=?1 and f.modifiedFileName in (?2)")
	void deleteFilesBySliderUdAndImageNames(Long id, List<String> removeImages);
	
	@Modifying
	@Query("delete from UserFiles as f where f.slider.id=?1")
	void deleteFilesBySliderId(Long userId);
	@Query("select f from UserFiles as f where f.fileExtension=?1")
	List<UserFiles> findFilesByExtension(String extension);

}
