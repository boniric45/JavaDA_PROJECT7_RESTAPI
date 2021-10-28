package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleNameService.class);
    @Autowired
    RuleNameRepository ruleNameRepository;

    public RuleName createRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public Optional<RuleName> findById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    public List findAll() {
        return ruleNameRepository.findAll();
    }

    public RuleName updateRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }

}
