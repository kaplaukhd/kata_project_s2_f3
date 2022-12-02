package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("ivan", "ivanov", (byte) 12);
        userService.saveUser("sergey", "sergeyev", (byte) 18);
        userService.saveUser("nikolay", "nikolayev", (byte) 24);
        userService.saveUser("konstantin", "konstantinov", (byte) 30);
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
