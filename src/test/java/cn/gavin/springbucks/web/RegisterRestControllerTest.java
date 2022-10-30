package cn.gavin.springbucks.web;

import cn.gavin.springbucks.domain.RegisterUseCase;
import cn.gavin.springbucks.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RegisterRestController.class)
public class RegisterRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterUseCase registerUseCase;

    @Test
    void whenValidUrlAndMethodAndContentType_thenReturns200() throws Exception {
        UserResource user = new UserResource("gavin", "gavin@email.com");

        mockMvc.perform(post("/forums/42/register")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        UserResource user = new UserResource("gavin", "gavin@email.com");

        mockMvc.perform(post("/forums/{forums}/register", 42L)
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidInput_thenMapsToBusinessModel() throws Exception {
        UserResource user = new UserResource("gavin", "gavin@email.com");

        mockMvc.perform(post("/forums/{forums}/register", 42L)
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(registerUseCase, times(1)).registerUser(userArgumentCaptor.capture(), eq(true));
        assertThat(userArgumentCaptor.getValue().getName()).isEqualTo("gavin");
        assertThat(userArgumentCaptor.getValue().getEmail()).isEqualTo("gavin@email.com");
    }

    @Test
    void whenValidInput_thenReturnsUserResource() throws Exception {
        UserResource user = new UserResource("gavin", "gavin@email.com");

        MvcResult mvcResult = mockMvc.perform(post("/forums/{forums}/register", 42L)
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResource expectedResponseBody = user;
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(expectedResponseBody));
    }

    @Test
    void whenValidInput_thenReturnUserResource_withFluentApi() throws Exception {
        UserResource user = new UserResource("gavin", "gavin@email.com");
        UserResource expectedResponseBody = user;

        mockMvc.perform(post("/forums/{forums}/register", 42L)
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(expectedResponseBody, UserResource.class));
    }

    @Test
    void whenValidInput_thenReturnUserResource_withTypeAssertion() throws Exception{
        UserResource user = new UserResource("gavin", "gavin@email.com");

        MvcResult mvcResult = mockMvc.perform(post("/forums/{forums}/register", 42L)
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResource expected = user;
        UserResource actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),UserResource.class);
        assertThat(actualResponseBody.getName()).isEqualTo(expected.getName());
        assertThat(actualResponseBody.getMail()).isEqualTo(expected.getMail());
    }

    @Test
    void whenNullValue_thenReturns400() throws Exception{
        UserResource user = new UserResource(null, "gavin@email.com");
        mockMvc.perform(post("/forums/{forums}/register", 42L)
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenNullValue_thenReturns400AndErrorResult() throws Exception{
        UserResource user = new UserResource(null, "gavin@email.com");
        MvcResult mvcResult = mockMvc.perform(post("/forums/{forums}/register", 42L)
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResult expectedErrorResponse = new ErrorResult("name","must not be null");
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedErrorResponse);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void whenNullValue_thenReturns400AndErrorResult_withFluentApi() throws Exception{
        UserResource user = new UserResource(null, "gavin@email.com");

        mockMvc.perform(post("/forums/{forums}/register", 42L)
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsError("name","must not be null"));
    }

    static ResponseBodyMatchers responseBody() {
        return new ResponseBodyMatchers();
    }
}
