package com.nnk.springboot.testService;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
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
public class TradeServiceTest {

    @InjectMocks
    TradeService tradeService;

    @Mock
    TradeRepository tradeRepository;

    /**
     * Test Create a new Trade
     */
    @Test
    @Order(1)
    public void testCreateTrade() {
        // Given
        Trade trade = new Trade(1, "Account", "type", 15.20);

        // When
        tradeService.createTrade(trade);
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        // Then
        Trade savedTrade = tradeRepository.save(trade);
        assertThat(savedTrade.getAccount()).isNotNull();
    }

    /**
     * Test Read All Trade
     */
    @Test
    @Order(2)
    public void testGetAllTrade() {
        // Given
        List<Trade> tradeList = new ArrayList<>();
        Trade trade1 = new Trade(1, "Account1", "type1", 15.20);
        Trade trade2 = new Trade(2, "Account2", "type2", 16.20);
        Trade trade3 = new Trade(3, "Account3", "type3", 17.20);
        tradeList.add(trade1);
        tradeList.add(trade2);
        tradeList.add(trade3);

        // When
        when(tradeService.findAllTrade()).thenReturn(tradeList);
        List<Trade> tradeListResult = tradeService.findAllTrade();

        // Then
        assertEquals(3, tradeListResult.size());

    }

    /**
     * Test Read Trade by id
     */
    @Test
    @Order(3)
    public void testGetTradeById() {
        // Given
        when(tradeService.findById(1)).thenReturn(java.util.Optional.of(new Trade(1, "Account", "type", 17.5)));

        // When
        Trade tradeResult = tradeService.findById(1).get();
        String bidQuantity = String.valueOf(tradeResult.getBuyQuantity());

        // Then
        assertEquals("Account", tradeResult.getAccount());
        assertEquals("type", tradeResult.getType());
        assertEquals("17.5", bidQuantity);

    }

    /**
     * Test Update Trade
     */
    @Test
    @Order(4)
    public void testUpdateTrade() {
        // Given
        Trade trade = new Trade(1, "Account1", "type1", 15.20);
        given(tradeRepository.findById(trade.getTradeId())).willReturn(Optional.of(trade));
        Trade tradeUpdated = new Trade(1, "Account2", "type2", 19.20);

        // When
        tradeService.updateTrade(tradeUpdated);

        // Then
        verify(tradeRepository).save(tradeUpdated);
    }

    /**
     * Test Delete Trade by ID
     */
    @Test
    @Order(5)
    public void testDeleteTradeById() {
        // Given
        Trade trade = new Trade(1, "Account1", "type1", 15.20);

        // When
        when(tradeRepository.findById(trade.getTradeId())).thenReturn(Optional.of(trade));
        tradeService.deleteTradeById(trade.getTradeId());

        // Then
        verify(tradeRepository).deleteById(trade.getTradeId());
    }

    /**
     * Test Trade exist in DB
     */
    @Test
    @Order(6)
    public void tradeExistInDbSucces() {
        // Given
        Trade trade = new Trade(1, "Account1", "type1", 15.20);
        List<Trade> listTrade = new ArrayList<>();

        // When
        listTrade.add(trade);
        when(tradeRepository.findAll()).thenReturn(listTrade);

        // Then
        List fetChedTrade = tradeService.findAllTrade();
        assertThat(fetChedTrade.size()).isGreaterThan(0);
    }
}
