package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.utils.DigitalFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Controller
public class BidListController {

    Logger logger = LoggerFactory.getLogger(BidListController.class); // Logger

    @Autowired
    private BidListService bidListService;

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
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {

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
    public String home(Model model) {
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
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

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
                            BindingResult result, Model model) {

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
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        BidList bidList = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Bid Id:" + id));
        bidListService.deleteById(bidList.getBidListId());
        model.addAttribute("bidList", bidListService.findAll());
        logger.info(" SUCCESS DELETE /bidList/delete/" + id);
        return "redirect:/bidList/list";
    }
}

