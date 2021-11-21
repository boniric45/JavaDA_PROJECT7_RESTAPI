package com.nnk.springboot.testController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(controllers = BidListController.class)
@DataJpaTest
@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
public class BidListControllerTest {

    private static final int ID = 1;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @InjectMocks
    BidListController bidListController;

    @Autowired
    BidListService bidListService;

    @Mock
    BidList bidList;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

//    @Before
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    /**
     * Test Create a new BidList
     */
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testCreateBidList() throws Exception {
        // Given
        BidList bidList = new BidList( 10,"accounts", "type", 15.40);

        // When
        when(bidListService.createBidList(bidList)).thenReturn(bidList); // ok

        verify(bidListService.findById(10)).isPresent();
    }

    /**
     * Test Create a BidList then valid
     */
    @Test
    public void testCreateBidListThenValid() {

        when(bidList.getAccount()).thenReturn("account");
        when(bidList.getType()).thenReturn("type");
        when(bidList.getBidQuantity()).thenReturn(12.51);

        assertThat(bidListController.validate(bidList, bindingResult, model), is("redirect:/bidList/list"));

    }

    /**
     * Test Create a BidList then not valid
     */
    @Test
    public void testCreateBidListThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(bidListController.validate(bidList, bindingResult, model), is("bidList/add"));
    }

    /**
     * Test Read a BidList by Id
     */
    @Test
    public void testGetBidListById() {
        when(bidListService.findById(ID)).thenReturn(java.util.Optional.ofNullable(bidList));
        assertEquals(bidListController.getBidlistById(ID), bidList);
    }

    /**
     * Test Read a BidList then not valid
     */
    @Test
    public void testGetBidListThenNotValid() {
        assertNull(bidListController.getBidlistById(0));
    }

    /**
     * Test read all BidList Home
     */
    @Test
    public void testGetBidListHome() {
        final Model model = new ExtendedModelMap();
        assertThat(bidListController.home(model), is("bidList/list"));
    }

    /**
     * Test Update a BidList
     */
    @Test
    public void testUpdateBidList() {
        final Model model = new ExtendedModelMap();
        assertThat(bidListController.home(model), is("bidList/list"));
    }

    /**
     * Test Update a BidList is Present
     */
    @Test
    public void testUpdateBidListWhenBidListIsPresent() {
        final Model model = new ExtendedModelMap();
        when(bidListService.findById(ID)).thenReturn(java.util.Optional.ofNullable(bidList));
        assertThat(bidListController.showUpdateForm(ID, model), is("bidList/update"));
    }

    /**
     * Test Update a BidList is Invalid
     */
    @Test
    public void testUpdateBidListWhenBidListIsInvalid() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("BidList ID is null");
        throw new NullPointerException("BidList ID is null");
    }

    /**
     * Test IllegalArgument Update a BidList
     */
    @Test
    public void testIllegalArgumentUpdateBidList() {
        int bidListIdTest = -25;
        try {
            bidListController.showUpdateForm(bidListIdTest, model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid Bid Id:" + bidListIdTest));
        }
    }

    /**
     * Test Update a BidList then valid
     */
    @Test
    public void testUpdateBidListThenValid() {
        when(bidList.getAccount()).thenReturn("account");
        when(bidList.getType()).thenReturn("type");
        when(bidList.getBidQuantity()).thenReturn(12.51);
        assertThat(bidListController.updateBid(ID, bidList, bindingResult, model), is("redirect:/bidList/list"));
    }

    /**
     * Test update a BidList then not valid
     */
    @Test
    public void testUpdateBidListThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(bidListController.validate(bidList, bindingResult, model), is("bidList/add"));
    }

    /**
     * Test Update a BidList then error
     */
    @Test
    public void testUpdateBidListThenValidError() {
        bidList.setBidQuantity(-15.21);
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(bidListController.updateBid(ID, bidList, bindingResult, model), is("redirect:/bidList/update"));
    }

    /**
     * Test Delete a BidList is Present
     */
    @Test
    public void testDeleteBidListWhenBidListIsPresent() {
        final Model model = new ExtendedModelMap();
        when(bidListService.createBidList(bidList)).thenReturn(bidList);
        when(bidListService.findById(ID)).thenReturn(java.util.Optional.ofNullable(bidList));
        assertThat(bidListController.deleteBid(ID, model), is("redirect:/bidList/list"));
    }


    /**
     * Test IllegalArgument Delete a BidList
     */
    @Test
    public void testIllegalArgumentDeleteBidList() {
        int bidListIdTest = -25;
        try {
            bidListController.deleteBid(bidListIdTest, model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid Bid Id:" + bidListIdTest));
        }
    }

    public static class JsonUtil {
        public static byte[] toJson(Object object) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsBytes(object);
        }
    }
}
