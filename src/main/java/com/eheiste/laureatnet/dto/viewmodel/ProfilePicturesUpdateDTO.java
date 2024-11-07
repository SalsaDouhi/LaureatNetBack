package com.eheiste.laureatnet.dto.viewmodel;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePicturesUpdateDTO {
    private MultipartFile picture;
    private MultipartFile banner;
}
