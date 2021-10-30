package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;

    /**
     * Create Trade
     */
    public Trade createTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    /**
     * Read all trade
     */
    public List findAllTrade() {
        return tradeRepository.findAll();
    }

    /**
     * Read trade by id
     */
    public Optional<Trade> findById(Integer id) {
        return tradeRepository.findById(id);
    }

    /**
     * Update Trade
     */
    public Trade updateTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    /**
     * Delete Trade by id
     */
    public void deleteTradeById(Integer id) {
        tradeRepository.deleteById(id);
    }

}
