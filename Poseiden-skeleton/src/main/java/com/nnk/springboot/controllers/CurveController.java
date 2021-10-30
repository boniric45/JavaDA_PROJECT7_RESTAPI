package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.utils.DigitalFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CurveController {
    private static final Logger logger = LoggerFactory.getLogger(CurveController.class);

    @Autowired
    CurvePointRepository curvePointRepository;

    @Autowired
    CurvePointService curvePointService;

    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoint", curvePointService.findAll());
        return "curvePoint/list";
    }

    /**
     * Create - Add a new CurvePoint
     */

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Validate CurvePoint
     */

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

        if (!result.hasErrors() && DigitalFormValidator.formIsOk(curvePoint.getValue()) && DigitalFormValidator.formIsOk(curvePoint.getTerm())) {
            logger.info(" SUCCESS POST /curvePoint/validate ");
            curvePointService.createCurvePoint(curvePoint);
            model.addAttribute("curvePoint", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        } else {
            logger.error(" ERROR POST /curvePoint/validate ");
            curvePoint.setTerm(0.00);
            curvePoint.setValue(0.00);
            return "curvePoint/add";
        }
    }

    /**
     * Read - Get one CurvePoint
     *
     * @param id The id of the CurvePoint
     * @return An CurvePoint object full filled
     */
    @RequestMapping("/curve/{id}")
    public CurvePoint getCurvePointById(@PathVariable("id") final int id) {
        Optional<CurvePoint> curve = curvePointService.getCurveById(id);
        if (curve.isPresent()) {
            logger.info(" SUCCESS READ ONE /CurvePoint  ");
            return curve.get();
        } else {
            logger.error(" ERROR READ ONE /CurvePoint ");
            return null;
        }
    }

    /**
     * Update  CurvePoint
     */

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        CurvePoint curvePoint = curvePointService.getCurveById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Curve Point Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        logger.info(" SUCCESS GET /curvePoint/update/" + id);
        return "curvePoint/update";
    }

    /**
     * Post Update Curve Point
     */

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                                   BindingResult result, Model model) {

        if (!result.hasErrors() && DigitalFormValidator.formIsOk(curvePoint.getValue()) && DigitalFormValidator.formIsOk(curvePoint.getTerm())) {
            curvePoint.setCurveId(id);
            curvePointService.updateCurvePoint(curvePoint);
            model.addAttribute("curvePoint", curvePointService.findAll());
            logger.info(" SUCCESS POST /curvePoint/update/" + id);
            return "redirect:/curvePoint/list";
        } else {
            logger.error(" ERROR POST /curvePoint/update/" + id);
            curvePoint.setTerm(0.00);
            curvePoint.setValue(0.00);
            return "curvePoint/update";
        }
    }

    /**
     * Get
     * Delete CurvePoint by id
     *
     * @return Curve List
     */

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {

        CurvePoint curvePoint = curvePointService.getCurveById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Curve Point Id:" + id));
        curvePointService.deleteById(curvePoint.getCurveId());
        model.addAttribute("curvePoint", curvePointService.findAll());
        logger.info(" SUCCESS DELETE /bidList/delete/" + id);

        return "redirect:/curvePoint/list";
    }
}
