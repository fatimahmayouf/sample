package com.fatema.sample.service;
import com.fatema.sample.entity.Account;
import com.fatema.sample.mapper.GeneralMapper;
import com.fatema.sample.repository.AccountRepository;
import com.fatema.sample.web.api.v1.dto.request.AccountRequestDto;
import com.fatema.sample.web.api.v1.dto.response.AccountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private GeneralMapper mapper;


    @InjectMocks
    private AccountService accountService;

    @Test
    void testGetAll() {
        // Arrange
        Account account1 = new Account();
        account1.setId(1);
        account1.setAccountNumber("1234567890");

        Account account2 = new Account();
        account2.setId(2);
        account2.setAccountNumber("0987654321");

        List<Account> accountList = Arrays.asList(account1, account2);

        when(accountRepository.findAll()).thenReturn(accountList);

        // Act
        List<AccountResponse> result = accountService.getAll();

        // Assert
        assertEquals(accountList.size(), result.size());
        assertEquals(account1.getAccountNumber(), result.get(0).getAccountNumber());
        assertEquals(account2.getAccountNumber(), result.get(1).getAccountNumber());
    }

    @Test
    void testGetAccountById_ValidId() {
        // Arrange
        Integer accountId = 1;
        Account account = new Account();
        account.setId(accountId);
        account.setAccountNumber("1234567890");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        AccountResponse result = accountService.getAccountById(accountId);

        // Assert
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
    }

    @Test
    void testGetAccountById_InvalidId() {
        // Arrange
        Integer invalidId = 100;

        when(accountRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> accountService.getAccountById(invalidId));
    }

    @Test
    void testSave() {
        // Arrange
        AccountRequestDto accountDTO = new AccountRequestDto();
        accountDTO.setAccountNumber("1234567890");

        Account account = new Account();
        account.setAccountNumber(accountDTO.getAccountNumber());

        when(mapper.map(accountDTO, Account.class)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        AccountResponse result = accountService.save(accountDTO);

        // Assert
        assertEquals(accountDTO.getAccountNumber(), result.getAccountNumber());
    }

    @Test
    void testDelete() {
        // Arrange
        Integer accountId = 1;

        // Act
        accountService.delete(accountId);

        // Assert
        verify(accountRepository, times(1)).deleteById(accountId);
    }
}
