package com.project.enotes_api_service.integeration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.enotes_api_service.dto.CategoryDto;
import com.project.enotes_api_service.dto.LoginRequest;
import com.project.enotes_api_service.entity.Category;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.method.HandlerMethod;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
@Slf4j
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Category category=null;

    private CategoryDto categoryDto=null;

    private  String token=null;

    @Mock
    private MockHttpServletRequestBuilder  mockHttpServletRequestBuilder;

    @BeforeEach
    public void initialize(){
        categoryDto= CategoryDto.builder()
                .id(null)
                .name("Java Category")
                .description("This is my Description")
                .build();
        category = Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();
    }


    @Test
    public void testSaveCategory() throws JsonProcessingException,Exception {
        token=generateToken("abdulrahmanpatni@gmail.com", "hello");
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/category/save-category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .header("Authorization", token)
                ).andExpect(status().isCreated()).andDo(print())
                .andExpect(jsonPath("$.message").value("saved Success"))
                .andExpect(jsonPath("$.status").value("Success"))
                .andReturn();
        HandlerMethod handlerMethod=(HandlerMethod) mvcResult.getHandler();
        log.info("This MockMvcResultHandler is Exceuted: {}",handlerMethod);
    }


    public String generateToken(String email,String password) throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();
        MvcResult mvcResult = mockMvc.perform(post("http://localhost:8080/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))).
                andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);
        JsonNode dataNode = jsonNode.get("data").path("token");
        return "Bearer "+dataNode.textValue();
    }








}
