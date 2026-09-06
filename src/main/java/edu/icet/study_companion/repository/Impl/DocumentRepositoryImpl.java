package edu.icet.study_companion.repository.Impl;

import edu.icet.study_companion.entity.Document;
import edu.icet.study_companion.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DocumentRepositoryImpl implements DocumentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Document save(Document document) {
        String sql = "INSERT INTO documents (user_id, file_name, file_path, file_size, file_type, upload_status, uploaded_at) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, document.getUser_id());
            ps.setString(2, document.getFile_name());
            ps.setString(3, document.getFile_path());
            ps.setString(4, document.getFile_size());
            ps.setString(5, document.getFile_type());
            ps.setString(6, document.getUpload_status());
            ps.setTimestamp(7, Timestamp.valueOf(document.getUpload_at()));
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        document.setId(generatedId);

        return document;
    }

    @Override
    public List<Document> findByUserId(Integer userId) {
        String sql = "SELECT * FROM documents WHERE user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Document document = new Document();
            document.setId(rs.getLong("id"));
            document.setUser_id(rs.getInt("user_id"));
            document.setFile_name(rs.getString("file_name"));
            document.setFile_path(rs.getString("file_path"));
            document.setFile_size(rs.getString("file_size"));
            document.setFile_type(rs.getString("file_type"));
            document.setUpload_status(rs.getString("upload_status"));
            document.setUpload_at(rs.getTimestamp("uploaded_at").toLocalDateTime());
            return document;
        }, userId);
    }

    @Override
    public Document findById(Long id) {
        String sql = "SELECT * FROM documents WHERE id = ?";

        List<Document> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Document document = new Document();
            document.setId(rs.getLong("id"));
            document.setUser_id(rs.getInt("user_id"));
            document.setFile_name(rs.getString("file_name"));
            document.setFile_path(rs.getString("file_path"));
            document.setFile_size(rs.getString("file_size"));
            document.setFile_type(rs.getString("file_type"));
            document.setUpload_status(rs.getString("upload_status"));
            document.setUpload_at(rs.getTimestamp("uploaded_at").toLocalDateTime());
            return document;
        }, id);

        return result.isEmpty() ? null : result.get(0);
    }
}
