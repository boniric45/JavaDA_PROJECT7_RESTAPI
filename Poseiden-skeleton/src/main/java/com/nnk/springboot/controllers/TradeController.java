package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
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
import java.util.Optional;

@Controller
public class TradeController {
    // TODO: Inject Trade service
    private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trade", tradeService.findAllTrade());
        // TODO: find all Trade, add to model
        return "trade/list";
    }

    /**
     * Create - Add a new Trade
     */
    @GetMapping("/trade/add")
    public String addTrade(Trade trade) {
        return "trade/add";
    }


    /**
     * Validate new Trade
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        if (!result.hasErrors() && DigitalFormValidator.formIsOk(trade.getBuyQuantity()))  {
                tradeService.createTrade(trade);
                model.addAttribute("trade", tradeService.findAllTrade());
                logger.info(" SUCCESS POST /trade/validate");
                return "redirect:/trade/list";
        } else {
            logger.error(" ERROR POST /trade/validate");
            trade.setBuyQuantity(0.00);
            return "trade/add";
        }
    }

    /**
     * Read - Get one Trade
     *
     * @param id The id of the Trade
     * @return An trade object full filled
     */
    @GetMapping("/trade/{id}")
    public Trade getTradeById(@PathVariable("id") int id) {

        Optional<Trade> tradeOptional = tradeService.findById(id);

        if (tradeOptional.isPresent()) {
            logger.info(" SUCCESS READ /trade/" + id);
            return tradeOptional.get();
        } else {
            logger.error(" ERROR READ /trade/" + id);
            return null;
        }
    }

    /**
     * Update Trade
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Update Trade Id:" + id));
        model.addAttribute("trade", trade);
        logger.info(" SUCCESS GET /trade/update/" + id);
        return "trade/update";
    }

    /**
     * Update Validate Trade
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list

        if (!result.hasErrors() && DigitalFormValidator.formIsOk(trade.getBuyQuantity())) {
            trade.setTradeId(id);
            tradeService.updateTrade(trade);
            model.addAttribute("trade", tradeService.findAllTrade());
            logger.info(" SUCCESS POST /trade/update/" + id);
            return "redirect:/trade/list";
        } else {
            logger.error(" ERROR POST /trade/update/" + id);
            trade.setBuyQuantity(0.00);
            return "trade/update";
        }

      }

    /**
     * Delete Trade
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list

        Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Delete Trade Id:" + id));
        tradeService.deleteTradeById(trade.getTradeId());
        model.addAttribute("trade", tradeService.findAllTrade());
        logger.info(" SUCCESS DELETE /trade/delete/" + id);

        return "redirect:/trade/list";
    }


}
