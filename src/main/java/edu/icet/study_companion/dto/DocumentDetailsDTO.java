package edu.icet.study_companion.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentDetailsDTO {
    private Long id;
    private Integer user_id;
    private String file_name;
    private String file_type;
    private String file_size;
    private String upload_status;
    private LocalDateTime upload_at;
}
