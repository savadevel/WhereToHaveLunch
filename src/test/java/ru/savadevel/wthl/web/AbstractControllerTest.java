package ru.savadevel.wthl.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.savadevel.wthl.TestMatcher;
import ru.savadevel.wthl.model.AbstractBaseEntity;
import ru.savadevel.wthl.model.User;
import ru.savadevel.wthl.to.BaseTo;
import ru.savadevel.wthl.util.exception.NotFoundException;
import ru.savadevel.wthl.web.json.JsonUtil;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.function.Function;
import java.util.function.IntFunction;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.savadevel.wthl.TestUtil.readFromJson;
import static ru.savadevel.wthl.TestUtil.userHttpBasic;
import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class AbstractControllerTest {
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();
    private final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2021, 1, 1, 10, 0, 0, 0);

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
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    private void init() {
        setDateTime(LOCAL_DATE_TIME);
    }

    protected void setDateTime(LocalDateTime dateTime) {
        ZoneId systemZone = ZoneId.systemDefault();
        ZoneOffset systemZoneOffset = systemZone.getRules().getOffset(dateTime);
        getVotingDay().setClock(Clock.fixed(dateTime.toInstant(systemZoneOffset), ZoneId.systemDefault()));
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @SafeVarargs
    protected final <T> void checkGet(URI uri, User user, TestMatcher<T> matcher, T... expected) throws Exception {
        perform(MockMvcRequestBuilders.get(uri).with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher.contentJson(expected));
    }

    protected <T> ResultActions getResultActionsPost(URI uri, User user, T content) throws Exception {
        return perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(content))
                .with(userHttpBasic(user)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    protected <T extends AbstractBaseEntity> void checkPost(URI uri,
                                                            User user,
                                                            TestMatcher<T>matcher,
                                                            T newEntity,
                                                            Class<T> clazz,
                                                            IntFunction<T> getById) throws Exception {
        ResultActions action = getResultActionsPost(uri, user, newEntity);
        T created = readFromJson(action, clazz);
        newEntity.setId(created.id());
        action.andExpect(header().stringValues(LOCATION, hasItem(endsWith(uri.getPath() + created.id()))));
        matcher.assertMatch(created, newEntity);
        matcher.assertMatch(getById.apply(created.id()), newEntity);
    }

    protected <T extends BaseTo, S extends AbstractBaseEntity> void checkPostTo(URI uri,
                                                                                User user,
                                                                                TestMatcher<T> matcher,
                                                                                S newEntity,
                                                                                Class<S> clazz,
                                                                                Function<S, T> asTo,
                                                                                IntFunction<S> getById) throws Exception {
        T newTo = asTo.apply(newEntity);
        ResultActions action = getResultActionsPost(uri, user, newTo);
        S created = readFromJson(action, clazz);
        newTo.setId(created.id());
        matcher.assertMatch(asTo.apply(created), newTo);
        matcher.assertMatch(asTo.apply(getById.apply(created.id())), newTo);
    }

    protected final void checkDelete(URI uri, User user, Executable deleteById) throws Exception {
        perform(MockMvcRequestBuilders.delete(uri)
                .with(userHttpBasic(user)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, deleteById);
    }
}
