package com.project.enotes_api_service.repository;

import com.project.enotes_api_service.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository  extends JpaRepository<FileDetails, Integer> {

    Boolean existsByOriginalFileName(String fileName);

}
