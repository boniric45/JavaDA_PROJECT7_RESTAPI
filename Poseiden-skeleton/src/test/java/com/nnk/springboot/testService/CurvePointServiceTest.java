package com.nnk.springboot.testService;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.junit.Test;
import org.junit.jupiter.api.Order;
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
public class CurvePointServiceTest {

    @InjectMocks
    CurvePointService curvePointService;

    @Mock
    CurvePointRepository curvePointRepository;

    /**
     * Test Create a new CurvePoint
     */
    @Test
    @Order(1)
    public void testCreateCurvePoint() {
        // Given
        CurvePoint curvePoint = new CurvePoint(1, 2, 12.5, 34.50);

        // When
        curvePointService.createCurvePoint(curvePoint);
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        // Then
        CurvePoint savedCurvePoint = curvePointRepository.save(curvePoint);
        assertThat(savedCurvePoint.getTerm()).isNotNull();
    }

    /**
     * Test Read All CurvePoint
     */
    @Test
    @Order(2)
    public void testGetAllCurvePoint() {
        // Given
        List<CurvePoint> listCurvepoint = new ArrayList<>();
        CurvePoint curvePoint1 = new CurvePoint(1, 2, 12.5, 34.50);
        CurvePoint curvePoint2 = new CurvePoint(2, 20, 15.5, 14.50);
        CurvePoint curvePoint3 = new CurvePoint(3, 25, 2.5, 3.50);

        listCurvepoint.add(curvePoint1);
        listCurvepoint.add(curvePoint2);
        listCurvepoint.add(curvePoint3);

        // When
        when(curvePointService.findAll()).thenReturn(listCurvepoint);
        List<CurvePoint> curvePointList = curvePointService.findAll();

        // Then
        assertEquals(3, curvePointList.size());

    }

    /**
     * Test Read CurvePoint by id
     */
    @Test
    @Order(3)
    public void testGetCurvePointById() {
        // Given
        when(curvePointService.getCurveById(1)).thenReturn(java.util.Optional.of(new CurvePoint(1, 2, 12.5, 34.5)));

        // When
        CurvePoint curvePointResult = curvePointService.getCurveById(1).get();

        String curvePointCurveId = String.valueOf(curvePointResult.getCurveId());
        String curvePointTerm = String.valueOf(curvePointResult.getTerm());
        String curvePointValue = String.valueOf(curvePointResult.getValue());

        // Then
        assertEquals("2", curvePointCurveId);
        assertEquals("12.5", curvePointTerm);
        assertEquals("34.5", curvePointValue);

    }

    /**
     * Test Update CurvePoint
     */
    @Test
    @Order(4)
    public void testUpdateCurvePoint() {
        // Given
        CurvePoint curvePoint = new CurvePoint(1, 2, 12.5, 34.50);
        given(curvePointRepository.findById(curvePoint.getId())).willReturn(Optional.of(curvePoint));
        CurvePoint curvePointUpdate = new CurvePoint(1, 3, 12.59, 38.50);

        // When
        curvePointService.updateCurvePoint(curvePointUpdate);

        // Then
        verify(curvePointRepository).save(curvePointUpdate);
    }

    /**
     * Test Delete CurvePoint by ID
     */
    @Test
    @Order(5)
    public void testDeleteCurvePointById() {
        // Given
        CurvePoint curvePoint = new CurvePoint(1, 2, 12.5, 34.50);

        // When
        when(curvePointRepository.findById(curvePoint.getId())).thenReturn(Optional.of(curvePoint));
        curvePointService.deleteById(curvePoint.getId());

        // Then
        verify(curvePointRepository).deleteById(curvePoint.getId());
    }

    /**
     * Test CurvePoint exist in DB
     */
    @Test
    @Order(6)
    public void CurvePointExistInDbSucces() {
        // Given
        CurvePoint curvePoint = new CurvePoint(1, 2, 12.5, 34.50);
        List<CurvePoint> listCurvePoint = new ArrayList<>();

        // When
        listCurvePoint.add(curvePoint);
        when(curvePointRepository.findAll()).thenReturn(listCurvePoint);

        // Then
        List fetChedCurvePoint = curvePointService.findAll();
        assertThat(fetChedCurvePoint.size()).isGreaterThan(0);
    }

}
