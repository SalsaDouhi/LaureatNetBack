package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.ApiResponse;
import com.eheiste.laureatnet.dto.ProfileDTO;
import com.eheiste.laureatnet.dto.UserAccountDTO;
import com.eheiste.laureatnet.dto.viewmodel.UserCreationDTO;
import com.eheiste.laureatnet.mapper.UserAccountMapper;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.model.UserProfile;
import com.eheiste.laureatnet.repository.UserAccountRepository;
import com.eheiste.laureatnet.repository.UserProfileRepository;
import com.eheiste.laureatnet.service.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;


    @GetMapping("/profiles/{id}")
    public ResponseEntity<ProfileDTO> getUserProfile(@PathVariable long id) {
        ProfileDTO userProfile = userAccountService.getProfileInfo(id);

        return ResponseEntity.ok(userProfile);
    }

    @GetMapping("/profiles")
    public ResponseEntity<Collection<ProfileDTO>> getAllUserAccounts(@RequestParam("id") Long id) {
        Collection<UserAccount> userAccounts = userAccountService.loadUserAccountsWithoutBlockedUsers(id);
        if (userAccounts != null && !userAccounts.isEmpty()) {
            Collection<ProfileDTO> userAccountDTOS = new ArrayList<>();
            for (UserAccount userAccount : userAccounts) {
                ProfileDTO userAccountDTO = UserAccountMapper.mapUserAccountToDTO(userAccount);
                userAccountDTOS.add(userAccountDTO);
            }
            return ResponseEntity.ok(userAccountDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users")
    public List<UserAccountDTO> getAllUsers() {
        List<UserAccount> accounts = userAccountService.getAllUsers();
        for (UserAccount userAccount : accounts) {
            if (userAccount.getUserProfile() == null) {
                UserProfile userProfile = UserProfile.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .currentGrade("1")
                        .major("GI")
                        .userAccount(userAccount)
                        .build();

                userProfile = userProfileRepository.save(userProfile);
                userAccount.setUserProfile(userProfile);
            }
        }

        List<UserProfile> userProfiles = userAccountService.getAllUsers()
                .stream()
                .filter(userAccount -> userAccount.getUserProfile() != null)
                .map(UserAccount::getUserProfile)
                .toList();


        return userProfiles.stream()
                .map(userAccountMapper::mapToUserAccountDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/import")
    public ResponseEntity<String> importUsers(@RequestBody List<UserCreationDTO> userCreationDTOList) {
        userAccountService.importAccounts(userCreationDTOList);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // get a list of all users
    @GetMapping()
    public ResponseEntity<ApiResponse> findAllUsers() {
        return userAccountService.findAllUsers();
    }

    // get a list of friends of a user
    @GetMapping("/friends/{userId}")
    public ResponseEntity<ApiResponse> findFriends(@PathVariable Long userId) {
        return userAccountService.findFriends(userId);
    }

    // find or create a conversation id of two users
    @GetMapping("/conversation")
    public ResponseEntity<ApiResponse> findConversationIdByUser1IdAndUser2Id(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return userAccountService.findConversationIdByUser1IdAndUser2Id(user1Id, user2Id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userAccountService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
