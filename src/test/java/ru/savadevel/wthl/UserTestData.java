package ru.savadevel.wthl;

import ru.savadevel.wthl.model.Role;
import ru.savadevel.wthl.model.User;

import static ru.savadevel.wthl.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER1_ID = START_SEQ;

    public static final User user1 = new User(USER1_ID, "user1", "password", Role.USER);
    public static final User user2 = new User(USER1_ID + 1, "user2", "password", Role.USER);
    public static final User user4 = new User(USER1_ID + 3, "user4", "password", Role.USER);
    public static final User admin = new User(USER1_ID + 4, "admin", "admin", Role.ADMIN);

    public static User getNew(Role role) {
        return new User(null, "NewUser", "password", role);
    }
}
