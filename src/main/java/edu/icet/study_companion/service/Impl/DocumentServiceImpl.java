package edu.icet.study_companion.service.Impl;

import edu.icet.study_companion.dto.DocumentDTO;
import edu.icet.study_companion.dto.DocumentDetailsDTO;
import edu.icet.study_companion.dto.DocumentListItemDTO;
import edu.icet.study_companion.entity.Document;
import edu.icet.study_companion.repository.DocumentRepository;
import edu.icet.study_companion.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public DocumentDTO uploadDocument(Integer userId, MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Document document = new Document();
            document.setUser_id(userId);
            document.setFile_name(originalFileName);
            document.setFile_path(filePath.toString());
            document.setFile_size(String.valueOf(file.getSize()));
            document.setFile_type(file.getContentType());
            document.setUpload_status("UPLOADED");
            document.setUpload_at(LocalDateTime.now());

            Document savedDocument = documentRepository.save(document);

            DocumentDTO responseDTO = new DocumentDTO();
            responseDTO.setId(savedDocument.getId());
            responseDTO.setFile_name(savedDocument.getFile_name());
            responseDTO.setUpload_at(savedDocument.getUpload_at());

            return responseDTO;

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed: " + e.getMessage());
        }
    }

    @Override
    public List<DocumentListItemDTO> getDocumentsByUserId(Integer userId) {
        List<Document> documents = documentRepository.findByUserId(userId);

        return documents.stream()
                .map(doc -> {
                    DocumentListItemDTO dto = new DocumentListItemDTO();
                    dto.setId(doc.getId());
                    dto.setFile_name(doc.getFile_name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public DocumentDetailsDTO getDocumentById(Long id, Integer userId) {
        Document document = documentRepository.findById(id);
        if (document == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        }
        if (!document.getUser_id().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        }

        DocumentDetailsDTO dto = new DocumentDetailsDTO();
        dto.setId(document.getId());
        dto.setUser_id(document.getUser_id());
        dto.setFile_name(document.getFile_name());
        dto.setFile_type(document.getFile_type());
        dto.setFile_size(document.getFile_size());
        dto.setUpload_status(document.getUpload_status());
        dto.setUpload_at(document.getUpload_at());

        return dto;
    }
}