package edu.icet.study_companion.repository;

import edu.icet.study_companion.entity.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DocumentRepository {

    Document save(Document document);

    List<Document> findByUserId(Integer userId);
}
