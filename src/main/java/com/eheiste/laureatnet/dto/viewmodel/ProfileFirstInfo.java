package com.eheiste.laureatnet.dto.viewmodel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileFirstInfo {
    private String currentGrade;
    private String location;
    private String firstName;
    private String lastName;
    private String birthDate;
}
