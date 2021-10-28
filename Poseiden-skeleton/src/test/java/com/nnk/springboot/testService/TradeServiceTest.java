package com.nnk.springboot.testService;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.utils.DigitalFormValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TradeServiceTest {

    private static final Integer id = 1;
    @InjectMocks
    TradeService tradeService;

    @Mock
    TradeRepository tradeRepository;

    @Test
    public void findByIdTest() {
        tradeService.findById(id);
        verify(tradeRepository).findById(id);
    }

    @Test
    public void getAllTest() {
        tradeService.findAllTrade();
        verify(tradeRepository).findAll();
    }

    @Test
    public void saveTest() {
        Trade trade = mock(Trade.class);
        tradeService.createTrade(trade);
        verify(tradeRepository).save(trade);
    }

    @Test
    public void updateTest() {
        Trade trade = mock(Trade.class);

        when(trade.getTradeId()).thenReturn(id);
        when(trade.getAccount()).thenReturn("Test Account");
        when(trade.getType()).thenReturn("Test type");
        when(trade.getBuyQuantity()).thenReturn(2.0);
        when(tradeRepository.findById(id)).thenReturn(java.util.Optional.of(trade));
        tradeService.updateTrade(trade);
        verify(tradeRepository).save(trade);
    }

    @Test
    public void deleteTest() {
        tradeService.deleteTradeById(id);
        verify(tradeRepository).deleteById(id);
    }
}
