package cl.tenpo.desafio;

import cl.tenpo.desafio.domain.User;
import cl.tenpo.desafio.exception.DuplicateUsernameException;
import cl.tenpo.desafio.services.UserService;
import cl.tenpo.desafio.web.model.CredentialsDto;
import cl.tenpo.desafio.web.model.OperationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by martin.saporiti
 * on 26/12/2020
 * Github: https://github.com/martinsaporiti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = DesafioApplication.class)
public class OperationApiTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    UserService userService;

    private MockMvc mockMvc;
    private CredentialsDto credentials = new CredentialsDto("user", "password");


    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        return mapper;
    }


    @Before
    public void setup() throws DuplicateUsernameException, JsonProcessingException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();

        userService.saveNewUser(User.builder()
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .build());
    }

    private String getJsonCredentials() throws JsonProcessingException {
        return objectMapper().writeValueAsString(credentials);
    }

    @Test
    public void plusEndpointTest() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonCredentials()))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String accessToken = jsonParser.parseMap(resultString).get("token").toString();
        Assert.assertNotNull(accessToken);

        OperationDto operation = OperationDto.builder().num1(2).num2(3).build();
        mockMvc.perform(post("/api/v1/operation/plus")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper().writeValueAsString(operation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5));
    }

}
