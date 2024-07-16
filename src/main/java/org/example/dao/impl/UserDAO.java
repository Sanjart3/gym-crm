package org.example.dao.impl;

import org.example.dao.BaseDao;
import org.example.entities.User;
import org.example.utils.DataSource;
import org.example.utils.PasswordGenerator;
import org.example.utils.UserNameGenerator;
import org.example.utils.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class UserDAO implements BaseDao<User> {

    private DataSource dataSource;
    private PasswordGenerator passwordGenerator;
    private UserNameGenerator userNameGenerator;
    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }
    @Autowired
    public void setUserNameGenerator(UserNameGenerator userNameGenerator) {
        this.userNameGenerator = userNameGenerator;
    }
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> readAll() {
        Map<Long, User> userMap = dataSource.getUsers();
        List<User> users = new ArrayList<>(userMap.values());
        return users;
    }

    @Override
    public User readById(Long id) {
        Map<Long, User> userMap = dataSource.getUsers();
        User user = userMap.get(id);
        return user;
    }

    @Override
    public User create(User user) {
        Long id = getLastId()+1;
        user.setId(id);
        String username = userNameGenerator.generate(user.getFirstName(), user.getLastName());
        String password = passwordGenerator.generatePassword();
        user.setUsername(username);
        user.setPassword(password);
        Map<Long, User> userMap = dataSource.getUsers();
        userMap.put(id, user);
        return user;
    }

    @Override
    public User update(User user) {
        Long id = user.getId();
        Map<Long, User> userMap = dataSource.getUsers();
        if (userMap.containsKey(id)) {
            userMap.put(id, user);
            return user;
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public Boolean existById(Long id) {
        Map<Long, User> userMap = dataSource.getUsers();
        return userMap.containsKey(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        Map<Long, User> userMap = dataSource.getUsers();
        if (userMap.containsKey(id)) {
            userMap.remove(id);
            return true;
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public Long getLastId() {
        TreeMap<Long, User> userMap = (TreeMap<Long, User>) dataSource.getUsers();
        Long lastId = userMap.lastKey();
        return lastId;
    }

}
