package com.fatema.sample.web.api.v1.controller;


import com.fatema.sample.service.AccountService;
import com.fatema.sample.web.api.v1.dto.request.AccountRequestDto;
import com.fatema.sample.web.api.v1.dto.response.AccountResponse;
import com.fatema.sample.web.api.v1.dto.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
@Configuration
@Log4j2
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllAccounts() {

        List<AccountResponse> accounts = accountService.getAll();
        return BaseResponse.responseBuilder("SUCCESS",HttpStatus.OK,accounts);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id) {
        AccountResponse account = accountService.getAccountById(id);
        return BaseResponse.responseBuilder(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK,account);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveAccount(@RequestBody AccountRequestDto accountDTO) {
        AccountResponse savedAccount = accountService.save(accountDTO);
        return BaseResponse.responseBuilder(HttpStatus.CREATED.getReasonPhrase(),HttpStatus.CREATED,savedAccount);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer id) {
        accountService.delete(id);
        return BaseResponse.responseBuilder(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK,ResponseEntity.noContent().build());
    }
}
