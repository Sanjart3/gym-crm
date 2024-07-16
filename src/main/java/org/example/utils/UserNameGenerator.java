package org.example.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserNameGenerator {
    private static List<String> usernames = new ArrayList<>();
    public String generate(String firstName, String lastName) {
        String username = firstName+"."+lastName;
        String finalUsername;
        long id = usernames.stream()
                .filter(object->object.startsWith(username))
                .count()+1;
        finalUsername = username+id;
        usernames.add(finalUsername);
        return username+id;
    }
}
