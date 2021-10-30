package com.nnk.springboot.testController;

import com.nnk.springboot.controllers.HomeController;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomePageTest {

    @InjectMocks
    HomeController homeController;
    @Mock
    UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test get Home Page
     */
    @Test
    public void testGetHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}