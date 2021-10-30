package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    /**
     * Create a new BidList
     */
    public BidList createBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    /**
     * Read all bidlist
     *
     * @return bidlist list
     */
    public List findAll() {
        return bidListRepository.findAll();
    }

    /**
     * Read Bidlist by Id
     */
    public Optional<BidList> findById(Integer BidListId) {
        return bidListRepository.findById(BidListId);
    }

    /**
     * Update Bidlist
     */
    public BidList updateBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    /**
     * Delete Bidlist
     */
    public void deleteById(Integer BidListId) {
        bidListRepository.deleteById(BidListId);
    }

}

