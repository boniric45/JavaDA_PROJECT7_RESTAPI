package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    @Autowired
    RuleNameRepository ruleNameRepository;

    /**
     * Create rule name
     */
    public RuleName createRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Read rule name by id
     */
    public Optional<RuleName> findById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    /**
     * Read all rule name
     */
    public List findAll() {
        return ruleNameRepository.findAll();
    }

    /**
     * Update rule name
     */
    public RuleName updateRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Delete rule name by id
     */
    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }

}
