package com.study.realworld.controller;

import com.study.realworld.domain.user.controller.UserRestController;
import com.study.realworld.domain.user.dto.UserRegisterRequestDTO;
import com.study.realworld.domain.user.dto.UserRegisterResponseDTO;
import com.study.realworld.domain.user.service.UserService;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private UserRegisterResponseDTO user;

    @BeforeAll
    void init() {
        UserRegisterRequestDTO requestDTO = UserRegisterRequestDTO.builder()
                .username("Jacob")
                .email("jake@jake.jake")
                .password("jakejake")
                .build();
        user = userService.save(requestDTO);
    }

    @Test
    @Order(1)
    @DisplayName("회원 가입 성공 테스트")
    void registerSuccessTest1() throws Exception {
        final String username = "Jacob";
        final String email = "jake@jake.jake.test";
        ResultActions result = mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"username\":\"" + username + "\",\"email\":\"" + email + "\",\"password\":\"jakejake\"}}")
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("register"))
                .andExpect(jsonPath("$.user.username", is(username)))
                .andExpect(jsonPath("$.user.email", is(email)));
    }

    @Test
    @Order(2)
    @DisplayName("회원 가입 실패 테스트 - 이미 존재하는 이메일")
    void registerFailureTest1() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"username\":\"" + user.getUsername() + "\",\"email\":\"" + user.getEmail() + "\",\"password\":\"jakejake\"}}")
        );
        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("register"))
                .andExpect(jsonPath("$.user.username").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    @Order(3)
    @DisplayName("회원 가입 실패 테스트 - 이메일 미입력")
    void registerFailureTest2() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"username\":\"" + user.getUsername() + "\",\"password\":\"jakejake\"}}")
        );
        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("register"))
                .andExpect(jsonPath("$.user.username").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    @Order(4)
    @DisplayName("회원 가입 실패 테스트 - 이름 미입력")
    void registerFailureTest3() throws Exception {
        final String email = "jake@jake.jake.test";
        ResultActions result = mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"email\":\"" + email + "\",\"password\":\"jakejake\"}}")
        );
        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("register"))
                .andExpect(jsonPath("$.user.username").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    @Order(5)
    @DisplayName("회원 가입 실패 테스트 - 비밀번호 미입력")
    void registerFailureTest4() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"username\":\"" + user.getUsername() + "\",\"email\":\"" + user.getEmail() + "\"}}")
        );
        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("register"))
                .andExpect(jsonPath("$.user.username").doesNotExist())
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    @Order(6)
    @DisplayName("로그인 성공 테스트 - 토큰 발급 성공")
    void loginSuccessTest1() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"email\":\"" + user.getEmail() + "\",\"password\": \"jakejake\"}}")
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.user.email").exists())
                .andExpect(jsonPath("$.user.token").exists());
        JSONObject responseJson = new JSONObject(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    @Order(7)
    @DisplayName("로그인 실패 테스트 - 비밀번호 미입력")
    void loginFailureTest1() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"email\":\"" + user.getEmail() + "\"}}")
        );
        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.token").doesNotExist());
    }

    @Test
    @Order(8)
    @DisplayName("로그인 실패 테스트 - 이메일 미입력")
    void loginFailureTest2() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"password\": \"jakejake\"}}")
        );
        result.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.user.email").doesNotExist())
                .andExpect(jsonPath("$.user.token").doesNotExist());
    }

    @Test
    @Order(9)
    @DisplayName("현재 회원 정보 조회 - token 기반 조회")
    void getCurrentUserInfoSuccessTest1() throws Exception {
        ResultActions loginResult = mockMvc.perform(
                post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"user\":{\"email\":\"" + user.getEmail() + "\",\"password\": \"jakejake\"}}")
        );
        loginResult.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.user.email").exists())
                .andExpect(jsonPath("$.user.token").exists());
        JSONObject responseJson = new JSONObject(loginResult.andReturn().getResponse().getContentAsString());
        final String token = responseJson.getJSONObject("user").getString("token");

        ResultActions getCurrentUserInfoResult = mockMvc.perform(
                get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,
                                "Bearer " + token)
        );
        getCurrentUserInfoResult.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserRestController.class))
                .andExpect(handler().methodName("getUserInfo"))
                .andExpect(jsonPath("$.user.email").exists())
                .andExpect(jsonPath("$.user.username").exists());
    }
}
