package ru.savadevel.wthl.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.savadevel.wthl.model.AbstractBaseEntity;

import java.net.URI;

import static ru.savadevel.wthl.util.ValidationUtil.checkNew;
import static ru.savadevel.wthl.util.ValidationUtil.checkNotFoundWithId;

public class WebUtils {
    public static final String PART_REST_URL_DISHES = "/dishes";
    public static final String PART_REST_URL_RESTAURANTS = "/restaurants";
    public static final String PART_REST_URL_MENUS = "/menus";
    public static final String PART_REST_URL_VOTES = "/votes";

    private WebUtils() {
    }

    public static <T extends AbstractBaseEntity> T getById(Integer id, JpaRepository<T, Integer> repository) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public static <T extends AbstractBaseEntity> ResponseEntity<T> add(T entity, String path, JpaRepository<T, Integer> repository) {
        checkNew(entity);
        T created = repository.save(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }
}
