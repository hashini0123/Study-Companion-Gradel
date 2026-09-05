package edu.icet.study_companion.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    private Long id;
    private Integer user_id;
    private String file_name;
    private String file_path;
    private String file_Size;
    private String file_type;
    private String upload_status;
    private LocalDateTime upload_at;

}
