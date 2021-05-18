package ru.savadevel.wthl.web.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.savadevel.wthl.model.AbstractBaseEntity;
import ru.savadevel.wthl.to.BaseTo;
import ru.savadevel.wthl.util.exception.NotFoundException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtil {
    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(BaseTo baseTo, int id) {
        if (baseTo.isNew()) {
            baseTo.setId(id);
        } else if (baseTo.id() != id) {
            throw new IllegalArgumentException(baseTo + " must be with id=" + id);
        }
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}