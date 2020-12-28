package cl.tenpo.desafio;

import cl.tenpo.desafio.domain.User;
import cl.tenpo.desafio.services.UserService;
import cl.tenpo.desafio.web.model.CredentialsDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Esta clase testea la api rest de usuarios.
 * Implementa test de integración utilizando el contexto de Spring propio
 * de la aplicación (no utiliza mocks ni un contexto diferente para test).
 *
 * Created by martin.saporiti
 * on 26/12/2020
 * Github: https://github.com/martinsaporiti
 */
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserApiTests {

    MockMvc mockMvc;

    @Autowired
    UserService userService;

    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        return mapper;
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void signUpTest() throws Exception {
        CredentialsDto credentialsDto = new CredentialsDto("user1", "user1234");
        String jsonCredentialsDto = objectMapper().writeValueAsString(credentialsDto);

        mockMvc.perform(post("/api/v1/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCredentialsDto))
                .andExpect(status().isCreated());
    }

    @Test
    public void loginTestSuccess() throws Exception {

        CredentialsDto credentialsDto = new CredentialsDto("user2", "user1234");
        String jsonCredentialsDto = objectMapper().writeValueAsString(credentialsDto);
        userService.saveNewUser(User.builder().username("user2").password("user1234").build());

        ResultActions result = mockMvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCredentialsDto))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String token = jsonParser.parseMap(resultString).get("token").toString();
        Assert.assertNotNull(token);
    }


    @Test
    public void loginTestFailure() throws Exception {
        CredentialsDto credentialsDto = new CredentialsDto("user3", "user1234");
        String jsonCredentialsDto = objectMapper().writeValueAsString(credentialsDto);
        userService.saveNewUser(User.builder().username("user3").password("user12345").build());
        mockMvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCredentialsDto))
                .andExpect(status().is(401));

    }

}
