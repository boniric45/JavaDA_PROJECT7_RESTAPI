package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeService.class);
    @Autowired
    TradeRepository tradeRepository;

    public Trade createTrade(Trade trade) {
        System.out.println(trade);
//        if (!DigitalFormValidator.formIsOk(trade.getBuyQuantity())){
//            return null;
//        }
        return tradeRepository.save(trade);
    }

    public List findAllTrade() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> findById(Integer id) {
        LOGGER.info("Getting trade identified by id");
        return tradeRepository.findById(id);
    }

    public Trade updateTrade(Trade trade) {
        LOGGER.info("update trade");
        return tradeRepository.save(trade);
    }

    public void deleteTradeById(Integer id) {
        LOGGER.info("delete trade id: " + id);
        tradeRepository.deleteById(id);
    }


}
