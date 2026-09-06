package edu.icet.study_companion.controller;

import edu.icet.study_companion.dto.DocumentDTO;
import edu.icet.study_companion.dto.DocumentListItemDTO;
import edu.icet.study_companion.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/documents")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<DocumentDTO> uploadDocument(
            @RequestParam("user_id") Integer userId,
            @RequestParam("file") MultipartFile file) {

        DocumentDTO response = documentService.uploadDocument(userId, file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DocumentListItemDTO>> getDocumentsByUSerId(@RequestParam("user_id") Integer userId){
        List<DocumentListItemDTO> document = documentService.getDocumentsByUserId(userId);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

}
