package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    Logger LOGGER = LoggerFactory.getLogger(BidListService.class);

    @Autowired
    BidListRepository bidListRepository;

    /**
     * Create a new BidList
     */
    public BidList createBidList(BidList bidList) {
        LOGGER.info("Adding new bid list");
        return bidListRepository.save(bidList);
    }

    public List findAll() {
        return bidListRepository.findAll();
    }

    public Optional<BidList> findById(Integer BidListId) {
        LOGGER.info("Getting bid list identified by id");
        return bidListRepository.findById(BidListId);
    }

    public BidList updateBidList(BidList bidList) {
        LOGGER.info("Updating bid list");
        return bidListRepository.save(bidList);
    }

    public void deleteById(Integer BidListId) {
        LOGGER.info("Deleting bid list");
        bidListRepository.deleteById(BidListId);
    }


}

