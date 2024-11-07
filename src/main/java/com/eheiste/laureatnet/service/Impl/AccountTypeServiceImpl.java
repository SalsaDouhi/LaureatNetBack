package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.AccountType;
import com.eheiste.laureatnet.repository.AccountTypeRepository;
import com.eheiste.laureatnet.service.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Override
    public List<AccountType> getAllAccountTypes() {
        return accountTypeRepository.findAll();
    }

    @Override
    public Optional<AccountType> findTypeByTitle(String title) {
        return accountTypeRepository.findByTitle(title);
    }

}
