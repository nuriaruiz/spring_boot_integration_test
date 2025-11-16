package com.example.webmvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GreetingController.class)
class GreetingControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService greetingService;

    @Test
    @DisplayName("GET /greet?name=Alice devuelve 200 y el saludo de servicio")
    void greet_withName_returnsOkAndBody() throws Exception {
        Mockito.when(greetingService.greet("Alice")).thenReturn("Hello, Alice!");

        mockMvc.perform(get("/greet")
                        .param("name", "Alice")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hello, Alice!"));
    }

    @Test
    @DisplayName("GET /greet sin name usa el valor por defecto del servicio")
    void greet_withoutName_returnsDefault() throws Exception {
        Mockito.when(greetingService.greet(null)).thenReturn("Hello, World!");

        mockMvc.perform(get("/greet").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
    }
}
