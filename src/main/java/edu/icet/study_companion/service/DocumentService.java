package edu.icet.study_companion.service;

import edu.icet.study_companion.dto.DocumentDTO;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    DocumentDTO uploadDocument(Integer userId, MultipartFile file);


}
