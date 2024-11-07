package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.dto.ProfileDTO;
import com.eheiste.laureatnet.model.AccountType;
import com.eheiste.laureatnet.model.UserAccount;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

public interface AccountTypeService {
    List<AccountType> getAllAccountTypes();
    Optional<AccountType> findTypeByTitle(String title);
}
