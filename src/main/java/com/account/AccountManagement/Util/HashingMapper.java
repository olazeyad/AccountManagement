package com.account.AccountManagement.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.security.MessageDigest.getInstance;

/**
 * Helper class that provide hashing function
 * @author ola zeyad
 * 12-7-2021
 * @version 1.0
 */
@Service
public class HashingMapper {
    private static final Logger logger = LoggerFactory.getLogger(HashingMapper.class);

    private MessageDigest md;

    /**
     * Function to hash string values based on SHA3-256 algorithm
     * @param value string to be hashed
     * @return hashed bytes
     */
    public byte[] encoder(String value){
        byte[] hashedValue = new byte[0];
        try {
            md=getInstance("SHA3-256");
            hashedValue = md.digest(value.getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException: "+e.getStackTrace());
            e.printStackTrace();
        }
        return hashedValue;
    }

}
