package com.nnk.springboot.testController;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.utils.DigitalFormValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {
    @Autowired
    MockMvc mvc;
    Logger logger = LoggerFactory.getLogger(BidListController.class); // Logger
    @InjectMocks
    private BidListController bidListController;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private BidListService bidListService;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test // Test Endpoint /bidList/add
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void getFormBidListAdd() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/bidList/add")
                        .accept(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(view().name("bidList/add"));
    }

    /**
     * Create - Add a new BidList
     */
    @GetMapping("/bidList/add")
    public String addNewBidlist(BidList bidList) {
        return "bidList/add";
    }

    /**
     * Validate BidList
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, @org.jetbrains.annotations.NotNull BindingResult result, Model model) {

        if (!result.hasErrors() && DigitalFormValidator.formIsOk(bidList.getBidQuantity())) {
            logger.info(" SUCCESS POST /bidList/validate");
            bidListService.createBidList(bidList);
            model.addAttribute("bidList", bidListService.findAll());
            return "redirect:/bidList/list";
        } else {
            logger.error(" ERROR POST /bidList/validate : Account > " + bidList.getAccount() + " Type > " + bidList.getType() + " Bid Quantity > " + bidList.getBidQuantity());
            bidList.setBidQuantity(0.00);
            return "bidList/add";
        }
    }

    /**
     * Read - all Bidlist
     *
     * @return String
     */
    @RequestMapping("/bidList/list")
    public String home(@org.jetbrains.annotations.NotNull Model model) {
        List bidLists = bidListService.findAll();
        model.addAttribute("bidList", bidLists);
        return "bidList/list";
    }

    /**
     * Read - Get one bidlist
     *
     * @param id The id of the bidlistId
     * @return An bidlist object full filled
     */
    @GetMapping("/bidlist/{id}")
    public BidList getBidlistById(@PathVariable("id") int id) {
        Optional<BidList> bidListOptional = bidListService.findById(id);

        if (bidListOptional.isPresent()) {
            logger.info(" SUCCESS READ /bidlist/" + id);
            return bidListOptional.get();
        } else {
            logger.error(" ERROR READ /bidlist/" + id);
            return null;
        }
    }

    /**
     * Update BidList
     */
    @NotNull
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, @org.jetbrains.annotations.NotNull Model model) {

        BidList bidList = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Bid Id:" + id));
        model.addAttribute("bidList", bidList);
        logger.info(" SUCCESS  /bidList/update/" + id);
        return "bidList/update";
    }

    /**
     * Update BidList Valid
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            @org.jetbrains.annotations.NotNull BindingResult result, Model model) {

        if (!result.hasErrors() && DigitalFormValidator.formIsOk(bidList.getBidQuantity())) {
            bidList.setBidListId(id);
            bidListService.createBidList(bidList);
            model.addAttribute("bidList", bidListService.findAll());
            logger.info(" SUCCESS POST /bidList/update/" + id);
            return "redirect:/bidList/list";

        } else {
            logger.error(" ERROR  /bidList/update/" + id);
            bidList.setBidQuantity(0.00);
            return "redirect:/bidList/update";
        }
    }

    /**
     * Get
     * Delete BidList
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, @org.jetbrains.annotations.NotNull Model model) {

        BidList bidList = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Bid Id:" + id));
        bidListService.deleteById(bidList.getBidListId());
        model.addAttribute("bidList", bidListService.findAll());
        logger.info(" SUCCESS DELETE /bidList/delete/" + id);
        return "redirect:/bidList/list";
    }
}
