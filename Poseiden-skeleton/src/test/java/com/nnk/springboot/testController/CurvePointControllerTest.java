package com.nnk.springboot.testController;


import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTest {

    private static final int ID = 1;

    @InjectMocks
    CurveController curveController;

    @Mock
    CurvePointService curvePointService;

    @Mock
    CurvePoint curvePoint;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Test Create a new CurvePoint
     */
    @Test
    public void testCreateCurvePoint() {
        assertThat(curveController.addCurvePointForm(curvePoint), is("curvePoint/add"));
    }

    /**
     * Test Create a CurvePoint then valid
     */
    @Test
    public void testCreateCurvePointThenValid() {
        when(curvePoint.getCurveId()).thenReturn(1);
        when(curvePoint.getTerm()).thenReturn(2.5);
        when(curvePoint.getValue()).thenReturn(4.50);
        assertThat(curveController.validate(curvePoint, bindingResult, model), is("redirect:/curvePoint/list"));
    }

    /**
     * Test Create a CurvePoint then not valid
     */
    @Test
    public void testCreateCurvePointThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(curveController.validate(curvePoint, bindingResult, model), is("curvePoint/add"));
    }


    /**
     * Test Read a CurvePoint by Id
     */
    @Test
    public void testGetCurvePointById() {
        when(curvePointService.getCurveById(ID)).thenReturn(java.util.Optional.ofNullable(curvePoint));
        assertEquals(curveController.getCurvePointById(ID), curvePoint);
    }

    /**
     * Test Read a CurvePoint then not valid
     */
    @Test
    public void testGetCurvePointThenNotValid() {
        assertNull(curveController.getCurvePointById(0));
    }


    /**
     * Test read all CurvePoint Home
     */
    @Test
    public void testGetCurvePointHome() {
        final Model model = new ExtendedModelMap();
        assertThat(curveController.home(model), is("curvePoint/list"));
    }

    /**
     * Test Update a CurvePoint
     */
    @Test
    public void testUpdateCurvePoint() {
        final Model model = new ExtendedModelMap();
        assertThat(curveController.home(model), is("curvePoint/list"));
    }


    /**
     * Test Update a CurvePoint is Present
     */
    @Test
    public void testUpdateCurvePointWhenCurvePointIsPresent() {
        final Model model = new ExtendedModelMap();
        curvePoint.setCurveId(ID);
        when(curvePointService.getCurveById(ID)).thenReturn(java.util.Optional.ofNullable(curvePoint));
        assertThat(curveController.showUpdateForm(ID, model), is("curvePoint/update"));
    }

    /**
     * Test Update a CurvePoint then valid
     */
    @Test
    public void testUpdateCurvePointThenValid() {
        when(curvePoint.getCurveId()).thenReturn(1);
        when(curvePoint.getTerm()).thenReturn(2.5);
        when(curvePoint.getValue()).thenReturn(4.50);
        assertThat(curveController.updateCurvePoint(ID, curvePoint, bindingResult, model), is("redirect:/curvePoint/list"));
    }

    /**
     * Test update a CurvePoint then not valid
     */
    @Test
    public void testUpdateCurvePointThenNotValid() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(curveController.validate(curvePoint, bindingResult, model), is("curvePoint/add"));
    }

    /**
     * Test Update a CurvePoint then error
     */
    @Test
    public void testUpdateCurvePointThenValidError() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThat(curveController.updateCurvePoint(ID, curvePoint, bindingResult, model), is("curvePoint/update"));
    }

    /**
     * Test IllegalArgument Update a Curvepoint
     */
    @Test
    public void testIllegalArgumentUpdateCurvePoint() {
        int curvePointId = -25;

        try {
            curveController.showUpdateForm(curvePointId,model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid Curve Point Id:" + curvePointId));
        }
    }
    /**
     * Test Delete a Curvepoint is Present
     */
    @Test
    public void testDeleteCurvePointWhenCurvePointIsPresent() {
        final Model model = new ExtendedModelMap();
        when(curvePointService.save(curvePoint)).thenReturn(curvePoint);
        when(curvePointService.getCurveById(ID)).thenReturn(java.util.Optional.ofNullable(curvePoint));
        assertThat(curveController.deleteCurve(ID, model), is("redirect:/curvePoint/list"));
    }

    /**
     * Test IllegalArgument Delete a Curvepoint
     */
    @Test
    public void testIllegalArgumentDeleteCurvePoint() {
        int curvePointId = -25;
        try {
            curveController.deleteCurve(curvePointId,model);
        } catch (IllegalArgumentException illegalArgumentException) {
            assert (illegalArgumentException.getMessage().contains("Invalid Curve Point Id:" + curvePointId));
        }
    }

}
