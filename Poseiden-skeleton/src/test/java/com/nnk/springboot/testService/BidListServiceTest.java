package com.nnk.springboot.testService;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class BidListServiceTest {

    private static final long id = 1;
    @InjectMocks
    BidListService bidListService;
    @Mock
    BidListRepository bidListRepository;

    /**
     * Test Create a new BidList
     */
    @Test
    public void testCreateBidlist() {
        BidList bidList = mock(BidList.class);
        bidListService.createBidList(bidList);
        verify(bidListRepository).save(bidList);
    }

    /**
     * Test Read BidList by ID
     */
    @Test
    public void testReadBidlistById() {
        bidListService.findById(1);
        verify(bidListRepository).findById(1);
    }

    /**
     * Test Read all BidList
     */
    @Test
    public void testGetAllBidlist() {
        bidListService.findAll();
        verify(bidListRepository).findAll();
    }


    /**
     * Test Update BidList
     */
    @Test
    public void testUpdateBidlist() {
        BidList bidList = mock(BidList.class);
        when(bidList.getBidListId()).thenReturn(1);
        when(bidList.getAccount()).thenReturn("Test account");
        when(bidList.getType()).thenReturn("Test Description");
        when(bidList.getBidQuantity()).thenReturn(1.0);
        when(bidListRepository.findById(1)).thenReturn(java.util.Optional.of(bidList));
        bidListService.updateBidList(bidList);
        verify(bidListRepository).save(bidList);
    }

    /**
     * Test Delete BidList by ID
     */
    @Test
    public void testDeleteBidlistById() {
        bidListService.deleteById(1);
        verify(bidListRepository).deleteById(1);
    }

}
