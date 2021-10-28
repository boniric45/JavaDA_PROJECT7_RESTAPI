package com.nnk.springboot.testRepository;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
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
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(tradeRepository).isNotNull();
    }

    /**
     * Test of Setter Trade.
     */
    @Test
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
    public void testGetFlightNumber() {

        Trade trade = new Trade();

        int id = 1000;
        trade.setTradeId(id);

        int result = trade.getTradeId();
        assertEquals(id, result);
    }
}
