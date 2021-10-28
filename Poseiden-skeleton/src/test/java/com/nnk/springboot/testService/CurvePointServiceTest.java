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

    private static final long id = 1;
    @InjectMocks
    CurvePointService curvePointService;

    @Mock
    CurvePointRepository curvePointRepository;

    @Test
    public void findByIdTest() {
        curvePointService.getCurveById(1);
        verify(curvePointRepository).findById(1);
    }

    @Test
    public void getAllTest() {
        curvePointService.findAll();
        verify(curvePointRepository).findAll();
    }

    @Test
    public void saveTest() {
        CurvePoint curvePoint = mock(CurvePoint.class);
        curvePointService.save(curvePoint);
        verify(curvePointRepository).save(curvePoint);
    }

    @Test
    public void updateTest() {
        CurvePoint curvePoint = mock(CurvePoint.class);
        when(curvePoint.getCurveId()).thenReturn(1);
        when(curvePoint.getId()).thenReturn(1);
        when(curvePoint.getTerm()).thenReturn(1.0);
        when(curvePoint.getValue()).thenReturn(2.0);
        when(curvePointRepository.findById(1)).thenReturn(java.util.Optional.of(curvePoint));
        curvePointService.updateCurvePoint(curvePoint);
        verify(curvePointRepository).save(curvePoint);
    }

    @Test
    public void deleteTest() {
        curvePointService.deleteById(1);
        verify(curvePointRepository).deleteById(1);
    }

}
