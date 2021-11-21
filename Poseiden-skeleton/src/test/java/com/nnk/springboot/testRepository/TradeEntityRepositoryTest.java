package com.nnk.springboot.testRepository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TradeEntityRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TradeRepository tradeRepository;

    @Test
    @Order(1)
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(tradeRepository).isNotNull();
    }

    // Junit test for Save Trade
    @Test
    @Order(2)
    @Rollback(value = false)
    public void createTradeTest() {
        // Given
        Trade trade = Trade.builder()
                .tradeId(1)
                .account("Test Account")
                .type("Test Type")
                .buyQuantity(12.23)
                .build();

        // When
        tradeRepository.save(trade);


        // Then
        Assertions.assertThat(trade.getTradeId()).isGreaterThan(0);
    }

    // Junit test for Read Trade
    @Test
    @Order(3)
    public void getTradeTest() {
        // Given
        Trade trade = Trade.builder()
                .tradeId(1)
                .account("Test Account")
                .type("Test Type")
                .buyQuantity(12.23)
                .build();

        // When
        tradeRepository.save(trade);

        Trade tradeResult = tradeRepository.findById(1).get();

        // Then
        assertThat(tradeResult.getTradeId()).isEqualTo(1);
    }

    // Junit test for Read All Trade
    @Test
    @Order(4)
    public void getListOfTradeTest() {

        // Given
        Trade trade = Trade.builder()
                .tradeId(1)
                .account("Test Account")
                .type("Test Type")
                .buyQuantity(12.23)
                .build();

        // When
        tradeRepository.save(trade);

        List<Trade> listTrade = tradeRepository.findAll();

        // Then
        Assertions.assertThat(listTrade.size()).isGreaterThan(0);
    }

    // Junit test for Update Trade
    @Test
    @Order(5)
    public void updateTradeTest() {
        // Given
        Trade trade = Trade.builder()
                .tradeId(1)
                .account("Test Account")
                .type("Test Type")
                .buyQuantity(12.23)
                .build();

        // When
        tradeRepository.save(trade);

        Trade tradeResult = tradeRepository.findById(1).get();
        tradeResult.setAccount("Update Account");
        tradeResult.setType("Update Type");
        tradeResult.setBuyQuantity(15.35);

        Trade tradeUpdated = tradeRepository.save(tradeResult);

        // Then
        assertThat(tradeUpdated.getAccount()).isEqualTo("Update Account");
        assertThat(tradeUpdated.getType()).isEqualTo("Update Type");
        assertThat(tradeUpdated.getBuyQuantity()).isEqualTo(15.35);
    }

    // Junit test for delete Trade
    @Test
    @Order(6)
    public void deleteTradeTest() {
        // Given
        Trade trade = Trade.builder()
                .tradeId(1)
                .account("Test Account")
                .type("Test Type")
                .buyQuantity(12.23)
                .build();

        Trade trade2 = null;

        // When
        tradeRepository.save(trade);
        tradeRepository.delete(trade);


        Optional<Trade> optionalTrade = tradeRepository.findById(1);
        if (optionalTrade.isPresent()) {
            trade2 = optionalTrade.get();
        }

        // Then
        Assertions.assertThat(trade2).isNull();

    }

    // Junit test for delete Trade by Id
    @Test
    @Order(7)
    public void deleteTradeByIdTest() {
        // Given
        Trade trade = Trade.builder()
                .tradeId(1)
                .account("Test Account")
                .type("Test Type")
                .buyQuantity(12.23)
                .build();

        Trade trade2 = null;

        // When
        tradeRepository.save(trade);
        tradeRepository.deleteById(1);

        Optional<Trade> optionalTrade = tradeRepository.findById(1);
        if (optionalTrade.isPresent()) {
            trade2 = optionalTrade.get();
        }

        // Then
        Assertions.assertThat(trade2).isNull();

    }

    /**
     * Test of Setter Trade.
     */
    @Test
    @Order(8)
    public void testTradeSetter() {

        // GIVEN
        String account = "Account Test";
        Trade trade = new Trade();

        // WHEN
        trade.setAccount(account);

        // THEN
        assertEquals(trade.getAccount(), account);
    }

    /**
     * Test of Getter Trade.
     */
    @Test
    @Order(9)
    public void testGetFlightNumber() {

        Trade trade = new Trade();

        int id = 1000;
        trade.setTradeId(id);

        int result = trade.getTradeId();
        assertEquals(id, result);
    }
}
