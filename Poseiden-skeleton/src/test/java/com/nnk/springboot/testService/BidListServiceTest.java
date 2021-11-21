package com.nnk.springboot.testService;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
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
public class BidListServiceTest {

    @InjectMocks
    BidListService bidListService;
    @Mock
    BidListRepository bidListRepository;

    /**
     * Test Create a new BidList
     */
    @Test
    @Order(1)
    public void testCreateBidlist() {
        // Given
        BidList bidList = new BidList();
        bidList.setAccount("Account");
        bidList.setType("Type");
        bidList.setBidQuantity(12.5);

        // When
        bidListService.createBidList(bidList);
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        // Then
        BidList savedBidlist = bidListRepository.save(bidList);
        assertThat(savedBidlist.getAccount()).isNotNull();
    }

    /**
     * Test Read All BidList
     */
    @Test
    @Order(2)
    public void testGetAllBidlist() {
        // Given
        List<BidList> listBidlist = new ArrayList<>();
        BidList bidList1 = new BidList(1, "account1", "type1", 75.10);
        BidList bidList2 = new BidList(2, "account2", "type2", 95.70);
        BidList bidList3 = new BidList(3, "account3", "type3", 45.40);
        listBidlist.add(bidList1);
        listBidlist.add(bidList2);
        listBidlist.add(bidList3);

        // When
        when(bidListService.findAll()).thenReturn(listBidlist);
        List<BidList> bidLists = bidListService.findAll();

        // Then
        assertEquals(3, bidLists.size());

    }

    /**
     * Test Read BidList by id
     */
    @Test
    @Order(3)
    public void testGetBidlistById() {
        // Given
        when(bidListService.findById(1)).thenReturn(java.util.Optional.of(new BidList(1, "account", "type", 98.65)));

        // When
        BidList bidListResult = bidListService.findById(1).get();
        String bidQuantity = String.valueOf(bidListResult.getBidQuantity());

        // Then
        assertEquals("account", bidListResult.getAccount());
        assertEquals("type", bidListResult.getType());
        assertEquals("98.65", bidQuantity);

    }

    /**
     * Test Update BidList
     */
    @Test
    @Order(4)
    public void testUpdateBidlist() {
        // Given
        BidList bidList = new BidList(1, "account", "type", 98.65);
        given(bidListRepository.findById(bidList.getBidListId())).willReturn(Optional.of(bidList));
        BidList bidListUpdated = new BidList(1, "accountUpdate", "typeUpdate", 99.25);

        // When
        bidListService.updateBidList(bidListUpdated);

        // Then
        verify(bidListRepository).save(bidListUpdated);
    }

    /**
     * Test Delete BidList by ID
     */
    @Test
    @Order(5)
    public void testDeleteBidlistById() {
        // Given
        BidList bidList = new BidList(1, "account", "type", 98.65);

        // When
        when(bidListRepository.findById(bidList.getBidListId())).thenReturn(Optional.of(bidList));
        bidListService.deleteById(bidList.getBidListId());

        // Then
        verify(bidListRepository).deleteById(bidList.getBidListId());
    }

    /**
     * Test Bidlist exist in DB
     */
    @Test
    @Order(6)
    public void bidListExistInDbSucces() {
        // Given
        BidList bidList = new BidList();
        bidList.setAccount("Account");
        bidList.setType("Type");
        bidList.setBidQuantity(12.5);

        List<BidList> listBidList = new ArrayList<>();

        // When
        listBidList.add(bidList);
        when(bidListRepository.findAll()).thenReturn(listBidList);

        // Then
        List fetChedBidlist = bidListService.findAll();
        assertThat(fetChedBidlist.size()).isGreaterThan(0);
    }

}
