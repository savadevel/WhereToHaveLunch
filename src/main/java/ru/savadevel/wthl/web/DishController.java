package ru.savadevel.wthl.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.repository.DishRepository;

import java.util.List;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    public static final String REST_URL = "/rest/dishes";

    private final DishRepository dishRepository;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public List<Dish> getAll() {
        // TODO add a field to json "new": false
        // TODO called twice at startup
        return dishRepository.findAll();
    }
}
