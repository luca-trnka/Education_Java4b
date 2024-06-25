//package com.example.Education_Java4b.config;
//
//import com.example.Education_Java4b.config.jwt.JwtTokenProvider;
//import com.example.Education_Java4b.models.Role;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class SecurityConfigIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @Test
//    public void testAccessToAdminEndpointWithoutTokenShouldReturnUnauthorized() throws Exception {
//        mockMvc.perform(get("/api/users/all")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    public void testAccessToAdminEndpointWithAdminTokenShouldReturnOk() throws Exception {
//        Set<Role> roles = new HashSet<>();
//        roles.add(Role.ADMIN);
//        String token = jwtTokenProvider.generateToken("adminUser", roles);
//
//        mockMvc.perform(get("/api/users/all")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token))
//                .andExpect(status().isOk());
//    }
//}