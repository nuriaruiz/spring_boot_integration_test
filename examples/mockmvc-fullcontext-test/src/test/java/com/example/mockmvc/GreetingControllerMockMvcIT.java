package com.example.mockmvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerMockMvcIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /greet integra todo el contexto y devuelve 200")
    void greet_fullContext_ok() throws Exception {
        mockMvc.perform(get("/greet")
                        .param("name", "Nuria")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hello, Nuria!"));
    }

    @Test
    @DisplayName("GET /greet sin name usa valor por defecto del servicio")
    void greet_default_ok() throws Exception {
        mockMvc.perform(get("/greet")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
    }
}
