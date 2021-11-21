package com.nnk.springboot.testRepository;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurvePointEntityRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    @Order(1)
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(curvePointRepository).isNotNull();
    }

    // Junit test for Save CurvePoint
    @Test
    @Order(2)
    @Rollback(value = false)
    public void createCurvePointTest() {
        // Given
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(10.2)
                .value(3.2)
                .build();

        // When
        curvePointRepository.save(curvePoint);

        // Then
        Assertions.assertThat(curvePoint.getId()).isGreaterThan(0);
    }

    //    // Junit test for Read CurvePoint
    @Test
    @Order(3)
    public void getCurvePointTest() {
        // Given
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(10.2)
                .value(3.2)
                .build();

        // When
        curvePointRepository.save(curvePoint);
        CurvePoint curvePointResult = curvePointRepository.findById(1).get();

        // Then
        assertThat(curvePointResult.getId()).isEqualTo(1);
    }

    //     Junit test for Read All CurvePoint
    @Test
    @Order(4)
    public void getListOfCurvepointTest() {

        // Given
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(10.2)
                .value(3.2)
                .build();

        // When
        curvePointRepository.save(curvePoint);
        List<CurvePoint> listCurvePoint = curvePointRepository.findAll();

        // Then
        Assertions.assertThat(listCurvePoint.size()).isGreaterThan(0);
    }

    //    Junit test for Update CurvePoint
    @Test
    @Order(5)
    public void updateCurvePointTest() {
        // Given
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(10.2)
                .value(3.2)
                .build();

        // When
        curvePointRepository.save(curvePoint);
        CurvePoint curvePointResult = curvePointRepository.findById(1).get();
        curvePointResult.setCurveId(2);
        curvePointResult.setTerm(11.3);
        curvePointResult.setValue(56.48);

        CurvePoint curvePointUpdated = curvePointRepository.save(curvePointResult);

        // Then
        assertThat(curvePointUpdated.getCurveId()).isEqualTo(2);
        assertThat(curvePointUpdated.getTerm()).isEqualTo(11.3);
        assertThat(curvePointUpdated.getValue()).isEqualTo(56.48);

    }

    // Junit test for delete CurvePoint
    @Test
    @Order(6)
    public void deleteCurvePointTest() {
        // Given
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(2)
                .term(10.2)
                .value(3.2)
                .build();

        CurvePoint curvePoint2 = null;

        // When
        curvePointRepository.save(curvePoint);

        curvePointRepository.delete(curvePoint);

        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(2);
        if (optionalCurvePoint.isPresent()) {
            curvePoint2 = optionalCurvePoint.get();
        }

        // Then
        Assertions.assertThat(curvePoint2).isNull();

    }

    // Junit test for delete CurvePoint by Id
    @Test
    @Order(7)
    public void deleteCurvePointByIdTest() {
        // Given
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(10.2)
                .value(3.2)
                .build();

        CurvePoint curvePoint2 = null;

        // When
        curvePointRepository.save(curvePoint);
        curvePointRepository.deleteById(1);

        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(1);
        if (optionalCurvePoint.isPresent()) {
            curvePoint2 = optionalCurvePoint.get();
        }

        // Then
        Assertions.assertThat(curvePoint2).isNull();

    }

}
