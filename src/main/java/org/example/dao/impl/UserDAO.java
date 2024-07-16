package org.example.dao.impl;

import org.example.dao.BaseDao;
import org.example.entities.User;
import org.example.utils.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class UserDAO implements BaseDao<User> {

    private DataSource dataSource;
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
        Map<Long, User> userMap = dataSource.getUsers();
        userMap.put(id, user);
        return user;
    }

    @Override
    public User update(User user) {
        Long id = user.getId();
        Map<Long, User> userMap = dataSource.getUsers();
        userMap.put(id, user);
        return user;
    }

    @Override
    public Boolean existById(Long id) {
        Map<Long, User> userMap = dataSource.getUsers();
        return userMap.containsKey(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        Map<Long, User> userMap = dataSource.getUsers();
        userMap.remove(id);
        return true;
    }

    @Override
    public Long getLastId() {
        TreeMap<Long, User> users = (TreeMap<Long, User>) dataSource.getUsers();
        Long lastId = users.lastKey();
        return lastId;
    }
}
