package ru.savadevel.wthl.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.savadevel.wthl.model.AbstractBaseEntity;

import java.net.URI;
import java.util.function.Function;

import static ru.savadevel.wthl.web.validation.ValidationUtil.checkNew;
import static ru.savadevel.wthl.web.validation.ValidationUtil.checkNotFoundWithId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebUtil {
    public static <T extends AbstractBaseEntity> ResponseEntity<T> add(T entity, String path, Function<T, T> save) {
        checkNew(entity);
        T created = save.apply(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    public static void delete(int id, Function<Integer, Integer> deleteById) {
        checkNotFoundWithId(deleteById.apply(id) != 0, id);
    }
}
