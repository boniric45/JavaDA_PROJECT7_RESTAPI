package com.nnk.springboot.testRepository;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameEntityRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    @Order(1)
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(ruleNameRepository).isNotNull();
    }

    // Junit test for Save RuleName
    @Test
    @Order(2)
    @Rollback(value = false)
    public void createRuleNameTest() {
        // Given
        RuleName ruleName = RuleName.builder()
                .id(1)
                .name("Test Name")
                .description("Test Description")
                .json("Test Json")
                .template("Test template")
                .sqlStr("Test Sql")
                .sqlPart("Test SqlPart")
                .build();

        // When
        ruleNameRepository.save(ruleName);

        // Then
        Assertions.assertThat(ruleName.getId()).isGreaterThan(0);
    }

    // Junit test for Read RuleName
    @Test
    @Order(3)
    @Rollback(value = false)
    public void getRuleNameTest() {
        // Given
        RuleName ruleName = RuleName.builder()
                .id(2)
                .name("Test Name")
                .description("Test Description")
                .json("Test Json")
                .template("Test template")
                .sqlStr("Test Sql")
                .sqlPart("Test SqlPart")
                .build();


        // When
        ruleNameRepository.save(ruleName);
        RuleName ruleNameResult = ruleNameRepository.getOne(ruleName.getId());

        // Then
        assertThat(ruleNameResult.getId()).isEqualTo(2);
    }

    // Junit test for Read All RuleName
    @Test
    @Order(4)
    public void getListOfRuleNameTest() {
        // Given
        RuleName ruleName = RuleName.builder()
                .id(1)
                .name("Test Name")
                .description("Test Description")
                .json("Test Json")
                .template("Test template")
                .sqlStr("Test Sql")
                .sqlPart("Test SqlPart")
                .build();

        // When
        ruleNameRepository.save(ruleName);

        List<RuleName> listRuleName = ruleNameRepository.findAll();

        // Then
        Assertions.assertThat(listRuleName.size()).isGreaterThan(0);
    }

    // Junit test for Update RuleName
    @Test
    @Order(5)
    public void updateRuleNameTest() {
        // Given
        RuleName ruleName = RuleName.builder()
                .id(1)
                .name("Test Name")
                .description("Test Description")
                .json("Test Json")
                .template("Test template")
                .sqlStr("Test Sql")
                .sqlPart("Test SqlPart")
                .build();

        // When
        ruleNameRepository.save(ruleName);
        RuleName ruleNameResult = new RuleName();

        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(1);
        if (optionalRuleName.isPresent()) {
            ruleNameResult = optionalRuleName.get();
        }

        ruleNameResult.setName("Update Name");
        ruleNameResult.setDescription("Update Description");
        ruleNameResult.setJson("Update Json");
        ruleNameResult.setTemplate("Update Template");
        ruleNameResult.setSqlStr("Update Sql");
        ruleNameResult.setSqlPart("Update SqlPart");

        RuleName ruleNameUpdate = ruleNameRepository.save(ruleNameResult);

        // Then
        assertThat(ruleNameUpdate.getName()).isEqualTo("Update Name");
        assertThat(ruleNameUpdate.getDescription()).isEqualTo("Update Description");
        assertThat(ruleNameUpdate.getJson()).isEqualTo("Update Json");
        assertThat(ruleNameUpdate.getTemplate()).isEqualTo("Update Template");
        assertThat(ruleNameUpdate.getSqlStr()).isEqualTo("Update Sql");
        assertThat(ruleNameUpdate.getSqlPart()).isEqualTo("Update SqlPart");

    }

    // Junit test for delete RuleName
    @Test
    @Order(6)
    public void deleteRuleNameTest() {
        // Given
        RuleName ruleName = RuleName.builder()
                .id(1)
                .name("Test Name")
                .description("Test Description")
                .json("Test Json")
                .template("Test template")
                .sqlStr("Test Sql")
                .sqlPart("Test SqlPart")
                .build();

        RuleName ruleName2 = null;

        // When
        ruleNameRepository.save(ruleName);
        ruleNameRepository.delete(ruleName);

        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(1);
        if (optionalRuleName.isPresent()) {
            ruleName2 = optionalRuleName.get();
        }

        // Then
        Assertions.assertThat(ruleName2).isNull();

    }

    // Junit test for delete RuleName by Id
    @Test
    @Order(7)
    public void deleteRuleNameByIdTest() {
        // Given
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleNameRepository.save(ruleName);
        RuleName ruleNameResult = null;

        // When
        ruleNameRepository.deleteById(ruleName.getId());

        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(ruleName.getId());
        if (optionalRuleName.isPresent()) {
            ruleNameResult = optionalRuleName.get();
        }

        // Then
        Assertions.assertThat(ruleNameResult).isNull();

    }


}
