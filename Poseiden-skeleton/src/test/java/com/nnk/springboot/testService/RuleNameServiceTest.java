package com.nnk.springboot.testService;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RuleNameServiceTest {


    private static final int id = 1;
    @InjectMocks
    RuleNameService ruleNameService;

    @Mock
    RuleNameRepository ruleNameRepository;

    /**
     * Test Create Rule Name
     */
    @Test
    public void testCreateRulename() {
        RuleName ruleName = mock(RuleName.class);
        ruleNameService.createRuleName(ruleName);
        verify(ruleNameRepository).save(ruleName);
    }

    /**
     * Test Read Rule Name by id
     */
    @Test
    public void testReadRulenameById() {
        ruleNameService.findById(id);
        verify(ruleNameRepository).findById(id);
    }

    /**
     * Test Read all Rule Name
     */
    @Test
    public void testReadAllRulename() {
        ruleNameService.findAll();
        verify(ruleNameRepository).findAll();
    }


    /**
     * Test Update Rule Name
     */
    @Test
    public void testUpdateRulename() {
        RuleName ruleName = mock(RuleName.class);
        when(ruleName.getId()).thenReturn(id);
        when(ruleName.getName()).thenReturn("Test Name");
        when(ruleName.getDescription()).thenReturn("Test Description");
        when(ruleName.getJson()).thenReturn("Test Json");
        when(ruleName.getTemplate()).thenReturn("Test Template");
        when(ruleName.getSqlStr()).thenReturn("Test Sql");
        when(ruleName.getSqlPart()).thenReturn("Test SqlPart");
        when(ruleNameRepository.findById(id)).thenReturn(java.util.Optional.of(ruleName));
        ruleNameService.updateRuleName(ruleName);
        verify(ruleNameRepository).save(ruleName);
    }

    /**
     * Test Delete Rule Name by id
     */
    @Test
    public void testDeleteRulenameById() {
        ruleNameService.deleteById(id);
        verify(ruleNameRepository).deleteById(id);
    }
}
