package com.nnk.springboot.testService;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CurvePointServiceTest {

    @InjectMocks
    CurvePointService curvePointService;

    @Mock
    CurvePointRepository curvePointRepository;


    /**
     * Test Create Curvepoint
     */
    @Test
    public void testCreateCurvepoint() {
        CurvePoint curvePoint = mock(CurvePoint.class);
        curvePointService.createCurvePoint(curvePoint);
        verify(curvePointRepository).save(curvePoint);
    }

    /**
     * Test Read Curvepoint by id
     */
    @Test
    public void testReadCurvepointByID() {
        curvePointService.getCurveById(1);
        verify(curvePointRepository).findById(1);
    }

    /**
     * Test Read all Curvepoint
     */
    @Test
    public void testGetAllCurvepoint() {
        curvePointService.findAll();
        verify(curvePointRepository).findAll();
    }

    /**
     * Test Update Curvepoint
     */
    @Test
    public void testUpdateCurvepoint() {
        CurvePoint curvePoint = mock(CurvePoint.class);
        when(curvePoint.getCurveId()).thenReturn(1);
        when(curvePoint.getId()).thenReturn(1);
        when(curvePoint.getTerm()).thenReturn(1.0);
        when(curvePoint.getValue()).thenReturn(2.0);
        when(curvePointRepository.findById(1)).thenReturn(java.util.Optional.of(curvePoint));
        curvePointService.updateCurvePoint(curvePoint);
        verify(curvePointRepository).save(curvePoint);
    }

    /**
     * Test Delete Curvepoint by id
     */
    @Test
    public void testDeleteCurvepointById() {
        curvePointService.deleteById(1);
        verify(curvePointRepository).deleteById(1);
    }

}
