package com.eheiste.laureatnet.config;

import com.eheiste.laureatnet.model.*;
import com.eheiste.laureatnet.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer {
    private final AttachmentRepository attachmentRepository;
    private final ScientificArticleRepository scientificArticleRepository;
    private final EntrepriseRepository entrepriseRepository;
    private UserAccountRepository accountRepository;
    private AccountTypeRepository accountTypeRepository;
    private UserProfileRepository userProfileRepository;
    private UserSettingsRepository userSettingsRepository;
    private SectionTypeRepository sectionTypeRepository;

    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        AccountType userType = AccountType.builder().id(1L).title("User").roles("USER").build();
        AccountType laureatType = AccountType.builder().id(2L).title("Laureat").roles("USER, LAUREAT").build();
        AccountType docType = AccountType.builder().id(4L).title("Doc").roles("USER, LAUREAT, DOC").build();
        AccountType adminType = AccountType.builder().id(3L).title("Admin").roles("USER, LAUREAT, DOC, ADMIN").build();

        if (accountTypeRepository.findAll().size() < 4) {
            userType = accountTypeRepository.save(userType);
            laureatType = accountTypeRepository.save(laureatType);
            docType = accountTypeRepository.save(docType);
            adminType = accountTypeRepository.save(adminType);
        }

        if (sectionTypeRepository.findAll().size() != 3) {
            SectionType experience = SectionType.builder().title("Experiences").build();
            sectionTypeRepository.save(experience);
            SectionType education = SectionType.builder().title("Education").build();
            sectionTypeRepository.save(education);
            SectionType projet = SectionType.builder().title("Projets").build();
            sectionTypeRepository.save(projet);
        }

        if (accountRepository.findAll().size() < 4) {
            UserProfile userProfile = UserProfile.builder()
                    .firstName("salsabil")
                    .lastName("douhi")
                    .banner("https://picsum.photos/1400/400")
                    .picture("https://picsum.photos/200/200")
                    .build();
            //userProfileRepository.save(userProfile4);

            UserSetting userSetting = UserSetting.builder()
                    //.userAccount(userAccount4)
            		.acceptMsgs(false)
            		.administrativePosts(false)
            		.privateMsgs(false)
            		.connectionRequest(true)
            		.build();
            //userSettingsRepository.save(userSetting4);
            UserAccount userAccount = UserAccount.builder()
                    .email("salsabil@gmail.com")
                    .password(passwordEncoder.encode("test"))
                    .isEnabled(true)
                    .accountType(docType)
                    .userProfile(userProfile4)
                    .userSettings(userSetting4)
                    .build();
            userAccount4 = accountRepository.save(userAccount4);

        }

        // migrate from old paths to new paths
        List<Attachment> attachments = attachmentRepository.findAll();
        attachments.forEach(a -> {
            if (a.getPath().contains("http://localhost:8080/api/v1/files/")) {
                a.setPath(a.getPath().replace("http://localhost:8080/api/v1/files/", ""));
                attachmentRepository.save(a);
            }
        });


    }
}
