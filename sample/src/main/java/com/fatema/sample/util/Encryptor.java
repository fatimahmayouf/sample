package com.fatema.sample.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Encryptor {


    public static String encryptAccountNumber(String accountNumber) {
        String encryptedNumber = BCrypt.hashpw(accountNumber, BCrypt.gensalt());
        return encryptedNumber;
    }
}
