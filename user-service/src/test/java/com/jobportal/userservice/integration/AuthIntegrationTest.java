package com.jobportal.userservice.integration;

import com.jobportal.userservice.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Transactional
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void signup_success_returns200WithJwt() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fullName": "John Doe",
                                    "email": "john@integration.com",
                                    "password": "password123",
                                    "phone": "555-0100",
                                    "role": "ROLE_JOB_SEEKER"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Registered Successfully!"))
                .andExpect(jsonPath("$.user.email").value("john@integration.com"));
    }

    @Test
    void signup_duplicateEmail_returns409() throws Exception {
        var body = """
                {
                    "fullName": "Alice",
                    "email": "alice@integration.com",
                    "password": "pass123",
                    "phone": "111",
                    "role": "ROLE_JOB_SEEKER"
                }
                """;
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    void signup_adminRole_returns403() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fullName": "Admin",
                                    "email": "admin@integration.com",
                                    "password": "admin123",
                                    "phone": "000",
                                    "role": "ROLE_ADMIN"
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    void login_success_returns200WithJwt() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fullName": "Bob",
                                    "email": "bob@integration.com",
                                    "password": "bobpass123",
                                    "phone": "222",
                                    "role": "ROLE_EMPLOYER"
                                }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "bob@integration.com",
                                    "password": "bobpass123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Logged in Successfully!"));
    }
}
