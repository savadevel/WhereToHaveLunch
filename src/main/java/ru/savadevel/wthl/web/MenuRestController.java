package ru.savadevel.wthl.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.repository.MenuRepository;
import ru.savadevel.wthl.security.SecurityUtil;
import ru.savadevel.wthl.to.MenuTo;
import ru.savadevel.wthl.util.MenuUtil;

import javax.validation.Valid;
import java.util.List;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;
import static ru.savadevel.wthl.web.WebUtil.add;
import static ru.savadevel.wthl.web.WebUtil.delete;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    public static final String REST_URL = "/rest/menus";

    private final MenuRepository menuRepository;

    @Cacheable(value = "menus")
    @GetMapping
    public List<Menu> getMenus() {
        log.info("getMenusOnCurrentDate for user '{}'", SecurityUtil.authUserId());
        return menuRepository.getAllByDateOrderByRestaurantNameAscDishNameAsc(getVotingDay().getNowDate());
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable Integer id) {
        log.info("getMenuByIs for menuId {} and user '{}'", id, SecurityUtil.authUserId());
        return menuRepository.getById(id);
    }

    @CacheEvict(value = {"menus", "votes"}, allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> addMenu(@Valid @RequestBody MenuTo menuTo) {
        log.info("addMenu for MenuTo {} and user '{}'", menuTo, SecurityUtil.authUserId());
        return add(MenuUtil.createNewFromTo(menuTo), REST_URL, menuRepository::save);
    }

    @CacheEvict(value = {"menus", "votes"}, allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteMenu(@PathVariable int id) {
        log.info("deleteMenu for id {} and user '{}'", id, SecurityUtil.authUserId());
        delete(id, menuRepository::delete);
    }
}
