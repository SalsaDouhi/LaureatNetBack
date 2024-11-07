package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.dto.ApiResponse;
import com.eheiste.laureatnet.dto.ProfileDTO;
import com.eheiste.laureatnet.dto.viewmodel.UserCreationDTO;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserAccountService {

    Optional<UserAccount> getAccountById(Long id);

    Optional<UserAccount> createAccount(String email, String password);

    Collection<UserAccount> loadUserAccountsWithoutBlockedUsers(Long id);

    Optional<UserAccount> createAccount(UserAccount userAccount);

    Optional<UserAccount> createAccountWithType(String email, String password, Long l);

    Optional<UserAccount> loadByEmail(String email);

    List<UserAccount> getAllUsers();

    Optional<UserAccount> loadById(Long id);

    ProfileDTO getProfileInfo(Long id);

    String importAccounts(List<UserCreationDTO> userCreationDTOS);

    // related to chat
    ResponseEntity<ApiResponse> findAllUsers();

    ResponseEntity<ApiResponse> findUserByEmail(String email);

    ResponseEntity<ApiResponse> findFriends(Long userId);

    ResponseEntity<ApiResponse> findConversationIdByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    void deleteUser(Long id);
}
