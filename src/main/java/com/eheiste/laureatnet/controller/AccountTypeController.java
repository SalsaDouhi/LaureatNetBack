package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.model.AccountType;
import com.eheiste.laureatnet.service.AccountTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@AllArgsConstructor
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @GetMapping
    public List<AccountType> getAccountTypes() {
        return accountTypeService.getAllAccountTypes();
    }
}
