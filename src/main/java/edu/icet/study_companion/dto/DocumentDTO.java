package edu.icet.study_companion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class DocumentDTO {

    private Long id;
    private Integer user_id;
    private String file_name;
    private LocalDateTime upload_at;

}
