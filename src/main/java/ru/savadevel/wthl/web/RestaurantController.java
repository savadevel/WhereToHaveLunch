package ru.savadevel.wthl.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.repository.MenuRepository;

import java.util.List;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_MENUS;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    public static final String REST_URL = "/rest";

    private final MenuRepository menuRepository;

    @Cacheable(value = "menus")
    @GetMapping(PART_REST_URL_MENUS)
    public List<Menu> getMenus() {
        log.info("getMenusOnCurrentDate for user '{}'", SecurityUtil.authUserId());
        return menuRepository.getAllByDateOrderByRestaurantNameAscDishNameAsc(getVotingDay().getNowDate());
    }

    @GetMapping(PART_REST_URL_MENUS + "/{menuId}")
    public Menu getMenuById(@PathVariable Integer menuId) {
        log.info("getMenuByIs for menuId {} and user '{}'", menuId, SecurityUtil.authUserId());
        return menuRepository.getById(menuId);
    }
}
