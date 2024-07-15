package org.example.utils;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class PasswordGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Set<String> existingPasswords = new HashSet<>();

    public String generatePassword() {
        StringBuilder password = new StringBuilder(10);

        while (true) {
            for (int i = 0; i < 10; i++) {
                password.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            }

            String newPassword = password.toString();
            if (!existingPasswords.contains(newPassword)) {
                existingPasswords.add(newPassword);
                return newPassword;
            }

            password.setLength(0);
        }
    }
}
