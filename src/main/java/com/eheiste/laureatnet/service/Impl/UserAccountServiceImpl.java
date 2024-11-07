package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.dto.ApiResponse;
import com.eheiste.laureatnet.dto.ProfileDTO;
import com.eheiste.laureatnet.dto.SectionDTO;
import com.eheiste.laureatnet.dto.modelview.UniversalPostDTO;
import com.eheiste.laureatnet.dto.viewmodel.UserCreationDTO;
import com.eheiste.laureatnet.exception.exceptions.ApiRequestException;
import com.eheiste.laureatnet.mapper.UniversalPostMapper;
import com.eheiste.laureatnet.mapper.UserAccountMapper;
import com.eheiste.laureatnet.mapper.UserCreationMapper;
import com.eheiste.laureatnet.model.*;
import com.eheiste.laureatnet.repository.*;
import com.eheiste.laureatnet.service.EmailService;
import com.eheiste.laureatnet.service.UserAccountService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final SectionTypeRepository sectionTypeRepository;
    private final PostRepository postRepository;
    private final SectionItemRepository sectionItemRepository;
    private final ConversationRepository conversationRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ConnectionRepository connectionRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserSettingsRepository userSettingsRepository;

    private PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final UniversalPostMapper postUniversalMapper;
    private final UserAccountMapper userAccountMapper;
    private final UserCreationMapper userCreationMapper;

    @Lazy
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserAccount> getAccountById(Long id) {
        UserAccount account = userAccountRepository.findById(id).orElseThrow();
        return Optional.of(account);
    }

    @Override
    @Transactional
    public Optional<UserAccount> createAccount(String email, String password) {
        AccountType accountType = accountTypeRepository.findById(1L).orElseThrow();

        UserAccount userAccount = UserAccount.builder().email(email).password(password).accountType(accountType).build();
        userAccount = userAccountRepository.save(userAccount);

        return Optional.of(userAccount);
    }

    public Optional<UserAccount> createAccountWithType(String email, String password, Long type) {
        AccountType accountType = accountTypeRepository.findById(type).orElseThrow();

        UserAccount userAccount = UserAccount.builder().email(email).password(password).accountType(accountType).build();
        userAccount = userAccountRepository.save(userAccount);
        System.out.println("laaaaaaast" + userAccount);
        return Optional.of(userAccount);
    }

    @Override
    public Collection<UserAccount> loadUserAccountsWithoutBlockedUsers(Long id) {
        System.out.println("test1");
        Collection<UserAccount> userAccounts = userAccountRepository.findAllWithoutUsersBlocked(id);
        return userAccounts;
    }


    @Override
    public Optional<UserAccount> loadByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }


    @Override
    public Optional<UserAccount> loadById(Long id) {
        return userAccountRepository.findById(id);
    }

    @Override
    public ProfileDTO getProfileInfo(Long id) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (id == null) id = user.getId();

        UserAccount profileUser = userAccountRepository.findById(id).orElseThrow(() ->
                new ApiRequestException("Invalid profile id.")
        );

        if (profileUser.getUserProfile() == null) {
            UserProfile userProfile = UserProfile.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .currentGrade("1")
                    .major("GI")
                    .userAccount(profileUser)
                    .build();

            userProfile = userProfileRepository.save(userProfile);
            profileUser.setUserProfile(userProfile);
        }

        List<UniversalPostDTO> userPosts = postRepository.findByPosterIdOrderByCreatedAtDesc(profileUser.getId())
                .stream()
                .map(postUniversalMapper::toDTO)
                .collect(Collectors.toList());

        List<SectionItem> userSectionItems = sectionItemRepository.findByUserAccount_IdOrderByEndDateDesc(profileUser.getId());

        List<SectionDTO> sections = userSectionItems.stream()
                .collect(Collectors.groupingBy(item -> Map.entry(item.getSectionType().getId(), item.getSectionType().getTitle())))
                .entrySet()
                .stream()
                .map(e -> new SectionDTO(e.getKey().getKey(), e.getKey().getValue(), e.getValue()))
                .collect(Collectors.toList());

        List<SectionType> sectionTypes = sectionTypeRepository.findAll();
        sectionTypes.forEach(sectionType -> {
            boolean isContained = sections.stream()
                    .anyMatch(section -> section.getId().equals(sectionType.getId()));

            if (!isContained) {
                SectionDTO missingSection = SectionDTO.builder()
                        .title(sectionType.getTitle())
                        .id(sectionType.getId())
                        .items(new ArrayList<>())
                        .build();
                sections.add(missingSection);
            }
        });

        List<Connection> connections = connectionRepository.findBySenderIdOrReceiverId(profileUser.getId());
        List<UserAccount> friends = connections.stream()
                .flatMap(connection -> Stream.of(connection.getSender(), connection.getReceiver()))
                .filter(userAccount -> !userAccount.getId().equals(profileUser.getId()))
                .toList();

        List<ProfileDTO> friendProfiles = friends.stream()
                .map(userAccountMapper::toProfileDTO)
                .toList();

        List<Like> likes = likeRepository.findLikeByLiker_Id(profileUser.getId());
        List<Comment> comments = commentRepository.findByCommentor_IdOrderByCreatedAtDesc(profileUser.getId());

        ProfileDTO profileDTO = ProfileDTO.builder()
                .userId(profileUser.getId())
                .email((profileUser.getEmail()))
                .userProfile(profileUser.getUserProfile())
                .isOwner(Objects.equals(user.getId(), id))
                .posts(userPosts)
                .friends(friendProfiles)
                .comments(comments)
                .likes(likes)
                .sections(sections)
                .build();

        return profileDTO;
    }

    @Override
    public String importAccounts(List<UserCreationDTO> userCreationDTOList) {
        System.out.println("Starting user import with " + userCreationDTOList.size() + " users.");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserCreationDTO>> violations;
        for (UserCreationDTO userCreationDTO : userCreationDTOList) {
            System.out.println("Processing user: " + userCreationDTO);

            violations = validator.validate(userCreationDTO);
            if (!violations.isEmpty()) {
                // S'il y a des violations de contraintes, retournez une erreur
                StringBuilder errorMessage = new StringBuilder();
                for (ConstraintViolation<UserCreationDTO> violation : violations) {
                    errorMessage.append(violation.getMessage()).append("\n");
                }
                throw new ApiRequestException(errorMessage.toString());
            }
            try {
                // getting user role
                Optional<AccountType> optionalAccountType;

                if (userCreationDTO.getRoleId() != null)
                    optionalAccountType = accountTypeRepository.findById(userCreationDTO.getRoleId());
                else
                    optionalAccountType = accountTypeRepository.findByTitle(userCreationDTO.getRole());

                if (optionalAccountType.isEmpty())
                    throw new ApiRequestException("Unknown account type");
                AccountType accountType = optionalAccountType.get();

                // generating a password
                String password = generateUniquePassword();
                String encodedPassword = passwordEncoder.encode(password);

                // creating the account
                UserAccount userAccount = userCreationMapper.toUserAccount(userCreationDTO);
                userAccount.setPassword(encodedPassword);
                userAccount.setAccountType(accountType);
                userAccount.setId(null);
                UserAccount newUserAccount = userAccountRepository.save(userAccount); // saving

                // creating user Profile
                UserProfile userProfile = userCreationMapper.toUserProfile(userCreationDTO);
                userProfile.setUserAccount(newUserAccount);
                userProfileRepository.save(userProfile); // saving

                // creating user Settings
                UserSetting userSetting = UserSetting.builder().userAccount(newUserAccount).build();
                userSetting.setUserAccount(newUserAccount);
                userSettingsRepository.save(userSetting); // saving

                // sending the password in an email
                String subject = "Your LaureatNet Account Password";
                String text = "Here is your password: " + password + "\nYour login is your email: " + userAccount.getEmail();
                emailService.sendPasswordEmail(userAccount.getEmail(), subject, text);
            } catch (Exception e) {
                // Handle any exceptions during mapping or user creation (e.g., logging, notifying admin)
                throw new ApiRequestException("Error processing user: " + userCreationDTO);
                // System.err.println("Error processing user: " + userCreationDTO + ", Exception: " + e.getMessage());
            }
        }

        return userCreationDTOList.size() == 1 ? "User created successfully." : "Import completed successfully.";
    }


    @Override
    public Optional<UserAccount> createAccount(UserAccount userAccount) {
        userAccount = userAccountRepository.save(userAccount);
        return Optional.of(userAccount);
    }

    public List<UserAccount> getAllUsers() {
        return userAccountRepository.findAll();
    }

    public ResponseEntity<ApiResponse> findAllUsers() {
        List<UserAccount> list = userAccountRepository.findAll();
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .statusCode(200)
                        .status("Success")
                        .reason("OK")
                        .data(list)
                        .build(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<ApiResponse> findUserByEmail(String email) {
        UserAccount user = userAccountRepository.findByEmail(email).orElseThrow();
        if (user != null) {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .statusCode(200)
                            .status("Success")
                            .reason("OK")
                            .data(user)
                            .build(),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .statusCode(200)
                            .status("Failed")
                            .reason("User not found")
                            .data(null)
                            .build(),
                    HttpStatus.OK
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> findFriends(Long userId) {
        // TODO change to get friends only
        List<UserAccount> userAccounts = userAccountRepository.findAllUsersExceptThisUserId(userId);
        List<ProfileDTO> list = new ArrayList<>();

        for (UserAccount account : userAccounts) {
            if (account.getUserProfile() == null) continue;
            ProfileDTO p = ProfileDTO.builder()
                    .userId(account.getId())
                    .email(account.getEmail())
                    .userProfile(
                            UserProfile.builder()
                                    .firstName(account.getUserProfile().getFirstName())
                                    .lastName(account.getUserProfile().getLastName())
                                    .build()
                    )
                    .build();

            list.add(p);
        }

        return new ResponseEntity<>(
                ApiResponse.builder()
                        .statusCode(200)
                        .status("Success")
                        .reason("OK")
                        .data(list)
                        .build(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<ApiResponse> findConversationIdByUser1IdAndUser2Id(Long user1Id, Long user2Id) {
        Long conversationId;
        Optional<UserAccount> user1 = userAccountRepository.findById(user1Id);
        Optional<UserAccount> user2 = userAccountRepository.findById(user2Id);
        if (user1.isEmpty() || user2.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .statusCode(200)
                            .status("Failed")
                            .reason("User not found")
                            .data(null)
                            .build(),
                    HttpStatus.OK
            );
        }

        Optional<Conversation> existingConversation = conversationRepository.findConversationByUsers(user1.get(), user2.get());
        if (existingConversation.isPresent()) {
            conversationId = existingConversation.get().getId();
        } else {
            Conversation newConversation = new Conversation();
            newConversation.setUser1(user1.get());
            newConversation.setUser2(user2.get());
            Conversation savedConversation = conversationRepository.save(newConversation);
            conversationId = savedConversation.getId();
        }
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .statusCode(200)
                        .status("Success")
                        .reason("OK")
                        .data(conversationId)
                        .build(),
                HttpStatus.OK
        );
    }
    public void deleteUser(Long id) {
        userAccountRepository.deleteById(id);
    }

    private String generateUniquePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
