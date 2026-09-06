package edu.icet.study_companion.service;

import edu.icet.study_companion.dto.DocumentDTO;
import edu.icet.study_companion.dto.DocumentListItemDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    DocumentDTO uploadDocument(Integer userId, MultipartFile file);

    List<DocumentListItemDTO> getDocumentsByUserId(Integer userId);
}
