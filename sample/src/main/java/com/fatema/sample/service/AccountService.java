package com.fatema.sample.service;

import com.fatema.sample.entity.Account;
import com.fatema.sample.mapper.GeneralMapper;
import com.fatema.sample.repository.AccountRepository;
import com.fatema.sample.util.Encryptor;
import com.fatema.sample.web.api.v1.dto.request.AccountRequestDto;
import com.fatema.sample.web.api.v1.dto.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Log4j2
@Slf4j
public class AccountService{

    private final AccountRepository accountRepository;
    private final GeneralMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);


    public List<AccountResponse> getAll() {


        List<Account> accountList = accountRepository.findAll();
        accountList.forEach(account ->{
            // Encrypt the account number
            String encryptedAccountNumber = Encryptor.encryptAccountNumber(account.getAccountNumber());
            account.setAccountNumber(encryptedAccountNumber);
        });

        return mapper.mapList(accountList, AccountResponse.class);
    }

    public AccountResponse getAccountById(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Account not found for id:{}",id);

                    return new NoSuchElementException("Account not found");
                });

        return mapper.map(account, AccountResponse.class);
    }

    public AccountResponse save(AccountRequestDto accountDTO) {

        Account account = accountRepository.save(mapper.map(accountDTO, Account.class));
        logger.info("Request: {}", accountDTO + " Response: " + account);

        return mapper.map(account, AccountResponse.class);
    }

    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }

}
