package com.nnk.springboot.testController;

import com.nnk.springboot.controllers.HomeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomePageTest {

    @InjectMocks
    HomeController homeController;

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

    /**
     * Test get Admin Home Page
     */
    @Test
    public void testGetAdminHomePage() {
        final Model model = new ExtendedModelMap();
        assertThat(homeController.adminHome(), is("redirect:/bidList/list"));
    }

}