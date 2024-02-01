package com.fatema.sample.repository;

import com.fatema.sample.entity.Account;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface AccountRepository extends JpaRepository<Account,Integer> {

//    Optional<Account> getAccountByAccountNumber(String accountNumber);
}
