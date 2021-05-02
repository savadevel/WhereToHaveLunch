package ru.savadevel.wthl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.savadevel.wthl.TestMatcher;
import ru.savadevel.wthl.model.AbstractBaseEntity;
import ru.savadevel.wthl.to.BaseTo;
import ru.savadevel.wthl.web.json.JsonUtil;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Function;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.savadevel.wthl.TestUtil.readFromJson;
import static ru.savadevel.wthl.util.voteday.ProduceVoteDay.getVoteDay;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
public class AbstractControllerTest {
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();
    private final static LocalDate LOCAL_DATE = LocalDate.of(2021, 1, 1);

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
//                .addFilter(CHARACTER_ENCODING_FILTER)
//                .apply(springSecurity())
                .build();
        getVoteDay().setClock(Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault()));
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @SafeVarargs
    protected final <T> void checkGet(URI uri, TestMatcher<T> matcher, T... expected) throws Exception {
        perform(MockMvcRequestBuilders.get(uri))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher.contentJson(expected));
    }

    protected <T> ResultActions getResultActionsPost(URI uri, T content) throws Exception {
        return perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(content)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    protected <T extends AbstractBaseEntity> void checkPost(URI uri,
                                                            TestMatcher<T> matcher,
                                                            T newEntity,
                                                            Class<T> clazz,
                                                            JpaRepository<T, Integer> repository) throws Exception {
        ResultActions action = getResultActionsPost(uri, newEntity);
        T created = readFromJson(action, clazz);
        newEntity.setId(created.id());
        action.andExpect(header().stringValues(LOCATION, hasItem(endsWith(uri.getPath() + created.id()))));
        matcher.assertMatch(created, newEntity);
        matcher.assertMatch(repository.findById(created.id()).orElse(null), newEntity);
    }

    protected <T extends BaseTo, S extends AbstractBaseEntity> void checkPostTo(URI uri,
                                                                                TestMatcher<T> matcher,
                                                                                S newEntity,
                                                                                Class<S> clazz,
                                                                                Function<S, T> asTo,
                                                                                JpaRepository<S, Integer> repository) throws Exception {
        T newTo = asTo.apply(newEntity);
        ResultActions action = getResultActionsPost(uri, newTo);
        S created = readFromJson(action, clazz);
        newTo.setId(created.id());
        matcher.assertMatch(asTo.apply(created), newTo);
        matcher.assertMatch(asTo.apply(repository.findById(created.id()).orElse(null)), newTo);
    }
}
