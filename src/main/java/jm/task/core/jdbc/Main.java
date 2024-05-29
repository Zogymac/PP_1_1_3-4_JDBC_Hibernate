package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;



public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Maksim", "Filatov", (byte) 23);
        System.out.println("User с именем - Maxim добавлен в базу данных");

        userService.saveUser("Aleksandr", "Kornilov", (byte) 40);
        System.out.println("User с именем - Aleksandr добавлен в базу данных");

        userService.saveUser("Nikita", "Belov", (byte) 23);
        System.out.println("User с именем - Nikita добавлен в базу данных");

        userService.saveUser("Aleksandr", "Ponomarev", (byte) 19);
        System.out.println("User с именем - Aleksandr добавлен в базу данных");

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }

}
