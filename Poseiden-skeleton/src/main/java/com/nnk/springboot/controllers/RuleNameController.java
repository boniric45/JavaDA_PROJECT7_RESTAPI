package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {

    private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

    @Autowired
    RuleNameService ruleNameService;

    /**
     * Create - Add a new RuleName
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    /**
     * Validate Rulename
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {

        if (!result.hasErrors()) {
            logger.info(" SUCCESS POST /ruleName/validate");
            ruleNameService.createRuleName(ruleName);
            model.addAttribute("ruleName", ruleNameService.findAll());
            return "redirect:/ruleName/list";
        } else {
            logger.error(" ERROR POST /ruleName/validate");
        }
        return "ruleName/add";
    }

    /**
     * Read all RuleName
     *
     * @return model
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNameList", ruleNameService.findAll());
        // TODO: find all RuleName, add to model
        return "ruleName/list";
    }

    /**
     * Read - Get one ruleName
     *
     * @param id The id of the ruleName
     * @return An ruleName object full filled
     */
    @GetMapping("/ruleName/{id}")
    public RuleName getRuleNameById(@PathVariable("id") int id) {

        Optional<RuleName> ruleNameOptional = ruleNameService.findById(id);

        if (ruleNameOptional.isPresent()) {
            logger.info(" SUCCESS READ /ruleName/" + id);
            return ruleNameOptional.get();
        } else {
            logger.error(" ERROR READ /ruleName/" + id);
            return null;
        }
    }

    /**
     * Update ruleName
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        RuleName ruleName = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Update RuleName Id:" + id));
        model.addAttribute("ruleName", ruleName);
        logger.info(" SUCCESS GET /ruleName/update/" + id);

        return "ruleName/update";
    }

    /**
     * Update Rule Name
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {

        if (result.hasErrors()) {
            logger.error(" ERROR POST /ruleName/update/" + id);
            return "ruleName/update";
        }

        ruleName.setId(id);
        ruleNameService.updateRuleName(ruleName);
        model.addAttribute("ruleName", ruleNameService.findAll());
        logger.info(" SUCCESS POST /ruleName/update/" + id);
        return "redirect:/ruleName/list";
    }

    /**
     * Delete RuleName By Id
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {

        RuleName ruleName = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id:" + id));
        ruleNameService.deleteById(ruleName.getId());
        model.addAttribute("ruleName", ruleNameService.findAll());
        logger.info(" SUCCESS POST /ruleName/delete/" + id);
        return "redirect:/ruleName/list";
    }

}
