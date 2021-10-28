package com.nnk.springboot.testController;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTest {

    private static final int ID = 1;

    @InjectMocks
    com.nnk.springboot.controllers.RuleNameController ruleNameController;

    @Mock
    RuleNameService ruleNameService;

    @Mock
    RuleName ruleName;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Test Create a new RuleName
     */
    @Test
    public void testCreateRuleName() {
        assertThat(ruleNameController.addRuleForm(ruleName), is("ruleName/add"));
    }

    /**
     * Test Create a RuleName then valid
     */
    @Test
    public void testCreateRuleNameThenValid() {
        final Model model = new ExtendedModelMap();
        assertThat(ruleNameController.validate(ruleName, bindingResult, model), is("redirect:/ruleName/list"));
    }

    /**
     * Test Create a RuleName then not valid
     */
    @Test
    public void testCreateRuleNameThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(ruleNameController.validate(ruleName, bindingResult, model), is("ruleName/add"));
    }

    /**
     * Test Read a RuleName by Id
     */
    @Test
    public void testGetRuleNameById() {
        when(ruleNameService.findById(ID)).thenReturn(java.util.Optional.ofNullable(ruleName));
        assertEquals(ruleNameController.getRuleNameById(ID), ruleName);
    }

    /**
     * Test Read a RuleName then not valid
     */
    @Test
    public void testGetRuleNameThenNotValid() {
        assertNull(ruleNameController.getRuleNameById(0));
    }


    /**
     * Test read all RuleName Home
     */
    @Test
    public void testGetRuleNameHome() {
        final Model model = new ExtendedModelMap();
        assertThat(ruleNameController.home(model), is("ruleName/list"));
    }

    /**
     * Test show Update a RuleName
     */
    @Test
    public void testShowUpdateRuleName() {
        final Model model = new ExtendedModelMap();
        ruleName.setId(ID);
        when(ruleNameService.findById(ID)).thenReturn(java.util.Optional.ofNullable(ruleName));
        assertThat(ruleNameController.showUpdateForm(ID, model), is("ruleName/update"));
    }

    /**
     * Test IllegalArgument Update a RuleName
     */
    @Test
    public void testIllegalArgumentUpdateBidList() {
        int ruleNameIdTest = -25;
        try {
            ruleNameController.showUpdateForm(ruleNameIdTest,model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid Update RuleName Id:" + ruleNameIdTest));
        }
    }

    /**
     * Test Update a RuleName is Present
     */
    @Test
    public void testUpdateRuleNameWhenRuleNameIsPresent() {
        final Model model = new ExtendedModelMap();
        ruleName.setId(ID);
        when(ruleNameService.findById(ID)).thenReturn(java.util.Optional.ofNullable(ruleName));
        assertThat(ruleNameController.showUpdateForm(ID, model), is("ruleName/update"));
    }

    /**
     * Test Update a RuleName then valid
     */
    @Test
    public void testUpdateRuleNameThenValid() {
        final Model model = new ExtendedModelMap();
        assertThat(ruleNameController.updateRuleName(ID, ruleName, bindingResult, model), is("redirect:/ruleName/list"));
    }

    /**
     * Test update a RuleName then not valid
     */
    @Test
    public void testUpdateRuleNameThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(ruleNameController.updateRuleName(ID, ruleName, bindingResult, model), is("ruleName/update"));
    }

    /**
     * Test Update a RuleName then error
     */
    @Test
    public void testUpdateRuleNameThenValidError() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(ruleNameController.updateRuleName(ID, ruleName, bindingResult, model), is("ruleName/update"));
    }

    /**
     * Test Delete a RuleName is Present
     */
    @Test
    public void testDeleteRuleNameWhenCurvePointIsPresent() {
        final Model model = new ExtendedModelMap();
        ruleName.setId(ID);
        when(ruleNameService.createRuleName(ruleName)).thenReturn(ruleName);
        when(ruleNameService.findById(ID)).thenReturn(java.util.Optional.ofNullable(ruleName));
        assertThat(ruleNameController.deleteRuleName(ID, model), is("redirect:/ruleName/list"));
    }

    /**
     * Test IllegalArgument Delete a RuleName
     */
    @Test
    public void testIllegalArgumentDeleteRuleName() {
        int ruleNameId = -25;
        try {
            ruleNameController.deleteRuleName(ruleNameId, model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid RuleName Id:" + ruleNameId));
        }
    }
}
