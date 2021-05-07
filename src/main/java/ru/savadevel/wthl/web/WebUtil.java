package ru.savadevel.wthl.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.savadevel.wthl.model.AbstractBaseEntity;

import java.net.URI;
import java.util.function.Function;

import static ru.savadevel.wthl.web.validation.ValidationUtil.checkNew;

public class WebUtil {
    public static final String PART_REST_URL_DISHES = "/dishes";
    public static final String PART_REST_URL_RESTAURANTS = "/restaurants";
    public static final String PART_REST_URL_MENUS = "/menus";
    public static final String PART_REST_URL_VOTES = "/votes";
    public static final String PART_REST_URL_VOTE = "/vote";

    private WebUtil() {
    }

    public static <T extends AbstractBaseEntity> ResponseEntity<T> add(T entity, String path, Function<T, T> save) {
        checkNew(entity);
        T created = save.apply(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }
}
