package com.nnk.springboot.testRepository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BidListEntityRepositoryTest {

    @Autowired    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BidListRepository bidListRepository;

    @Test
    @Order(1)
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(bidListRepository).isNotNull();
    }

    // Junit test for Save BidList
    @Test
    @Order(2)
    @Rollback(value = false)
    public void createBidListTest() {
        // Given
        BidList bidList = BidList.builder()
                .bidListId(1)
                .account("TestRepoAccount")
                .type("TestRepoType")
                .bidQuantity(12.45)
                .build();

        // When
        bidListRepository.save(bidList);

        // Then
        assertThat(bidList.getBidListId()).isGreaterThan(0);
    }

    // Junit test for Read Bidlist
    @Test
    @Order(3)
       public void getBidlistTest() {
        // Given
        BidList bidList = BidList.builder()
                .bidListId(1)
                .account("TestRepoAccount")
                .type("TestRepoType")
                .bidQuantity(12.45)
                .build();

        // When
        bidListRepository.save(bidList);

        BidList bidListResult = bidListRepository.findById(1).get();

        // Then
        assertThat(bidListResult.getBidListId()).isEqualTo(1);
    }

    // Junit test for Read All Bidlist
    @Test
    @Order(4)
    public void getListOfBidlistTest() {

        // Given
        BidList bidList = BidList.builder()
                .bidListId(1)
                .account("TestRepoAccount")
                .type("TestRepoType")
                .bidQuantity(12.45)
                .build();

        // When
        bidListRepository.save(bidList);
        List<BidList> listBidlist = bidListRepository.findAll();

        // Then
        Assertions.assertThat(listBidlist.size()).isGreaterThan(0);
    }

    // Junit test for Update Bidlist
    @Test
    @Order(5)
    public void updateBidlistTest() {
        // Given
        BidList bidList = BidList.builder()
                .bidListId(1)
                .account("TestRepoAccount")
                .type("TestRepoType")
                .bidQuantity(12.45)
                .build();

        // When
        bidListRepository.save(bidList);

        BidList bidListResult = bidListRepository.findById(1).get();
        bidListResult.setAccount("TestRepoAccountUpdate");
        bidListResult.setType("TestRepoTypeUpdate");
        bidListResult.setBidQuantity(13.56);

        BidList bidListUpdated = bidListRepository.save(bidListResult);

        // Then
        assertThat(bidListUpdated.getAccount()).isEqualTo("TestRepoAccountUpdate");
        assertThat(bidListUpdated.getType()).isEqualTo("TestRepoTypeUpdate");
        assertThat(bidListUpdated.getBidQuantity()).isEqualTo(13.56);
    }

    // Junit test for delete Bidlist
    @Test
    @Order(6)
    public void deleteBidlistTest() {
        // Given
        BidList bidList = BidList.builder()
                .bidListId(1)
                .account("TestRepoAccount")
                .type("TestRepoType")
                .bidQuantity(12.45)
                .build();

        BidList bidList2 = null;

        // When
        bidListRepository.save(bidList);
        bidListRepository.delete(bidList);

        Optional<BidList> optionalBidList = bidListRepository.findById(1);
        if (optionalBidList.isPresent()) {
            bidList2 = optionalBidList.get();
        }

        // Then
        Assertions.assertThat(bidList2).isNull();

    }

    // Junit test for delete Bidlist by Id
    @Test
    @Order(7)
    public void deleteBidlistByIdTest() {
        // Given
        BidList bidList = BidList.builder()
                .bidListId(1)
                .account("TestRepoAccount")
                .type("TestRepoType")
                .bidQuantity(12.45)
                .build();

        BidList bidList2 = null;

        // When
        bidListRepository.save(bidList);
        bidListRepository.deleteById(1);

        Optional<BidList> optionalBidList = bidListRepository.findById(1);
        if (optionalBidList.isPresent()) {
            bidList2 = optionalBidList.get();
        }

        // Then
        Assertions.assertThat(bidList2).isNull();

    }
}
