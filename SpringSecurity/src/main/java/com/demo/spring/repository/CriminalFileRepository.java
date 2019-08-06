package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.spring.model.CriminalFiles;
import com.demo.spring.model.UserFiles;

@Repository
public interface CriminalFileRepository extends CrudRepository<CriminalFiles, Long> {

	@Query("select f from CriminalFiles as f where f.criminals.id=?1")
	List<CriminalFiles> findFilesByCriminalId(Long userId);
	
	@Modifying
	@Query("delete from CriminalFiles as f where f.criminals.id=?1 and f.modifiedFileName in (?2)")
	void deleteFilesByCriminalUdAndImageNames(Long id, List<String> removeImages);
	
	@Modifying
	@Query("delete from CriminalFiles as f where f.criminals.id=?1")
	void deleteFilesByCriminalId(Long userId);
	@Query("select f from CriminalFiles as f where f.fileExtension=?1")
	List<UserFiles> findFilesByExtension(String extension);
}
