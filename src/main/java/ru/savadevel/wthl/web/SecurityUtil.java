package ru.savadevel.wthl.web;

import ru.savadevel.wthl.model.AbstractBaseEntity;

public class SecurityUtil {
    private static int id = AbstractBaseEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }
}
