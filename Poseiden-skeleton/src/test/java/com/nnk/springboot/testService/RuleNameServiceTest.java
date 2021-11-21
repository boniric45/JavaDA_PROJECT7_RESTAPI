package com.nnk.springboot.testService;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RuleNameServiceTest {

    @InjectMocks
    RuleNameService ruleNameService;

    @Mock
    RuleNameRepository ruleNameRepository;

    /**
     * Test Create a new RuleName
     */
    @Test
    @Order(1)
    public void testCreateRuleName() {
        // Given
        RuleName ruleName = new RuleName(1, "Name", "Description", "json", "template", "sql", "sqlPart");

        // When
        ruleNameService.createRuleName(ruleName);
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        // Then
        RuleName savedRuleName = ruleNameRepository.save(ruleName);
        assertThat(savedRuleName.getName()).isNotNull();
    }

    /**
     * Test Read All RuleName
     */
    @Test
    @Order(2)
    public void testGetAllRuleName() {
        // Given
        List<RuleName> ruleNameList = new ArrayList<>();
        RuleName ruleName1 = new RuleName(1, "Name1", "Description", "json", "template", "sql", "sqlPart");
        RuleName ruleName2 = new RuleName(2, "Name2", "Description", "json", "template", "sql", "sqlPart");
        RuleName ruleName3 = new RuleName(3, "Name3", "Description", "json", "template", "sql", "sqlPart");

        ruleNameList.add(ruleName1);
        ruleNameList.add(ruleName2);
        ruleNameList.add(ruleName3);

        // When
        when(ruleNameService.findAll()).thenReturn(ruleNameList);
        List<RuleName> ruleList = ruleNameService.findAll();

        // Then
        assertEquals(3, ruleList.size());

    }

    /**
     * Test Read RuleName by id
     */
    @Test
    @Order(3)
    public void testGetRuleNameById() {
        // Given
        when(ruleNameService.findById(1)).thenReturn(java.util.Optional.of(new RuleName(1, "Name", "Description", "json", "template", "sql", "sqlPart")));

        // When
        RuleName ruleNameResult = ruleNameService.findById(1).get();

        // Then
        assertEquals("Name", ruleNameResult.getName());
        assertEquals("Description", ruleNameResult.getDescription());
        assertEquals("json", ruleNameResult.getJson());
        assertEquals("template", ruleNameResult.getTemplate());
        assertEquals("sql", ruleNameResult.getSqlStr());
        assertEquals("sqlPart", ruleNameResult.getSqlPart());

    }

    /**
     * Test Update RuleName
     */
    @Test
    @Order(4)
    public void testUpdateRuleName() {
        // Given
        RuleName ruleName = new RuleName(1, "Name", "Description", "json", "template", "sql", "sqlPart");
        given(ruleNameRepository.findById(ruleName.getId())).willReturn(Optional.of(ruleName));
        RuleName ruleNameUpdate = new RuleName(1, "NameUpdate", "DescriptionUpdate", "jsonUpdate", "templateUpdate", "sqlUpdate", "sqlPartUpdate");

        // When
        ruleNameService.updateRuleName(ruleNameUpdate);

        // Then
        verify(ruleNameRepository).save(ruleNameUpdate);
    }

    /**
     * Test Delete RuleName by ID
     */
    @Test
    @Order(5)
    public void testDeleteRuleNameById() {
        // Given
        RuleName ruleName = new RuleName(1, "Name", "Description", "json", "template", "sql", "sqlPart");

        // When
        when(ruleNameRepository.findById(ruleName.getId())).thenReturn(Optional.of(ruleName));
        ruleNameService.deleteById(ruleName.getId());

        // Then
        verify(ruleNameRepository).deleteById(ruleName.getId());
    }

    /**
     * Test RuleName exist in DB
     */
    @Test
    @Order(6)
    public void RuleNameExistInDbSucces() {
        // Given
        RuleName ruleName = new RuleName(1, "Name", "Description", "json", "template", "sql", "sqlPart");
        List<RuleName> ruleNameList = new ArrayList<>();

        // When
        ruleNameList.add(ruleName);
        when(ruleNameRepository.findAll()).thenReturn(ruleNameList);

        // Then
        List fetChedRuleName = ruleNameService.findAll();
        assertThat(fetChedRuleName.size()).isGreaterThan(0);
    }
}
