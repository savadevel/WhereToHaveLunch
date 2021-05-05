package ru.savadevel.wthl;

import ru.savadevel.wthl.model.Role;
import ru.savadevel.wthl.model.User;

public class UserTestData {
    public static final User user1 = new User("user1", "password", Role.USER);
    public static final User user2 = new User("user2", "password", Role.USER);
    public static final User user3 = new User("user3", "password", Role.USER);
    public static final User user4 = new User("user4", "password", Role.USER);
    public static final User admin = new User("admin", "admin", Role.ADMIN);

    public static User getNewWithUserRole() {
        return new User("NewUser", "password", Role.USER);
    }

    public static User getNewWithAdminRole() {
        return new User("NewAdmin", "password", Role.ADMIN);
    }
}
