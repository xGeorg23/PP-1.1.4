package jm.task.core.jdbc;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Коля","простоКоля",(byte) 20);
        userService.saveUser("Антон","Идиотикус",(byte) 21);
        userService.saveUser("Леша","Мудаликус",(byte) 22);
        userService.saveUser("Гоша","Кидаликус",(byte) 23);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable(); // очищаем таблицу
        userService.dropUsersTable(); // удаление таблицы
    }
}
