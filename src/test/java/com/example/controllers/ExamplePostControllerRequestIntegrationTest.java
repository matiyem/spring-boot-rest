package com.example.controllers;

import com.example.ServletInitializer;
import com.example.requestResponceBody.ExamplePostController;
import com.example.requestResponceBody.LoginForm;
import com.example.service.ExampleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServletInitializer.class)
public class ExamplePostControllerRequestIntegrationTest {

    //از این برای تست کردن rest استفاده میشود
    MockMvc mockMvc;

    //از سرویس زیر یک نمونه برای تست ایجاد میشود
    @Mock private ExampleService exampleService;

    //.از سرویس و نیازمندی هایش یک object میسازد ئ inject میکنددر اصل برای سرویس های درون کلاس باید برای هر کدام یک mock بسازیم
    @InjectMocks private ExamplePostController exampleController;
    private final String jsonBody = "{\"username\": \"username\", \"password\": \"password\"}";
    private LoginForm lf = new LoginForm();

    @Before
    public void preTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
          .standaloneSetup(exampleController)
          .build();
        lf.setPassword("password");
        lf.setUserName("username");
    }

    @Test
    public void requestBodyTest() {
        try {
            when(exampleService.fakeAuthentication(lf)).thenReturn(true);
            mockMvc
              .perform(post("/post/request")
                .content(jsonBody)
                .contentType("application/json"))
              .andDo(print())
              .andExpect(status().isOk());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}