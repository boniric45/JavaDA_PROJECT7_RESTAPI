package com.nnk.springboot.testController;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.utils.DigitalFormValidator;
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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

    private static final int ID = 1;

    @InjectMocks
    TradeController tradeController;

    @Mock
    TradeService tradeService;

    @Mock
    Trade trade;

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
     * Test Create a new Trade
     */
    @Test
    public void createTradeTest() {
        assertThat(tradeController.addTrade(trade), is("trade/add"));
    }

    /**
     * Test Create a Trade then valid
     */
    @Test
    public void createTradeTestThenValid() {
        when(trade.getAccount()).thenReturn("Account");
        when(trade.getType()).thenReturn("Type");
        when(trade.getBuyQuantity()).thenReturn(0.01);
        assertThat(tradeController.validate(trade, bindingResult, model), is("redirect:/trade/list"));
    }

    /**
     * Test Create a Trade then not valid
     */
    @Test
    public void createCurvePointTestThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(tradeController.validate(trade, bindingResult, model), is("trade/add"));
    }

    /**
     * Test Read a Trade by Id
     */
    @Test
    public void getTradeByIdTest() {
        when(tradeService.findById(ID)).thenReturn(java.util.Optional.ofNullable(trade));
        assertEquals(tradeController.getTradeById(ID), trade);
    }

    /**
     * Test Read a CurvePoint then not valid
     */
    @Test
    public void getTradeTestThenNotValid() {
        assertNull(tradeController.getTradeById(0));
    }

    /**
     * Test read all Trade Home
     */
    @Test
    public void getTradeHomeTest() {
        final Model model = new ExtendedModelMap();
        assertThat(tradeController.home(model), is("trade/list"));
    }

    /**
     * Test show Update a Trade
     */
    @Test
    public void showUpdateTradeTest() {
        final Model model = new ExtendedModelMap();
        trade.setTradeId(ID);
        when(tradeService.findById(ID)).thenReturn(java.util.Optional.ofNullable(trade));
        assertThat(tradeController.showUpdateForm(ID, model), is("trade/update"));
    }

    /**
     * Test Update a Trade is Present
     */
    @Test
    public void updateTradeTestWhenCurvePointIsPresent() {
        final Model model = new ExtendedModelMap();
        trade.setTradeId(ID);
        when(tradeService.findById(ID)).thenReturn(java.util.Optional.ofNullable(trade));
        assertThat(tradeController.showUpdateForm(ID, model), is("trade/update"));
    }

    /**
     * Test Update a Trade then valid
     */
    @Test
    public void updateTradeTestThenValid() {
        when(trade.getAccount()).thenReturn("Account");
        when(trade.getType()).thenReturn("Type");
        when(trade.getBuyQuantity()).thenReturn(0.01);
        assertThat(tradeController.updateTrade(ID, trade, bindingResult, model), is("redirect:/trade/list"));
    }

    /**
     * Test update a Trade then not valid
     */
    @Test
    public void updateTradeTestThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(tradeController.updateTrade(ID, trade, bindingResult, model), is("trade/update"));
    }

    /**
     * Test IllegalArgument Update a Trade
     */
    @Test
    public void testIllegalArgumentUpdateTrade() {
        int tradeId = -25;
        try {
            tradeController.showUpdateForm(tradeId, model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid Update Trade Id:" + tradeId));
        }
    }

    /**
     * Test Update a Trade then error
     */
    @Test
    public void updateTradeTestThenValidError() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(tradeController.updateTrade(ID, trade, bindingResult, model), is("trade/update"));
    }


    /**
     * Test Delete a Trade is Present
     */
    @Test
    public void deleteTradeTestWhenCurvePointIsPresent() {
        final Model model = new ExtendedModelMap();
        when(tradeService.createTrade(trade)).thenReturn(trade);
        when(tradeService.findById(ID)).thenReturn(java.util.Optional.ofNullable(trade));
        assertThat(tradeController.deleteTrade(ID, model), is("redirect:/trade/list"));
    }

    /**
     * Test IllegalArgument Delete a Trade
     */
    @Test
    public void testIllegalArgumentDeleteTrade() {
        int tradeId = -25;
        try {
            tradeController.deleteTrade(tradeId,model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid Delete Trade Id:" + tradeId));
        }
    }

}


