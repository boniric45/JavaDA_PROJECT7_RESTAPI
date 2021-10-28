package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurvePointService.class);
    @Autowired
    CurvePointRepository curvePointRepository;

    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    public Optional<CurvePoint> getCurveById(int id) {
        LOGGER.info("Getting curve list identified by id");
        return curvePointRepository.findById(id);
    }

    public List findAll() {
        return curvePointRepository.findAll();
    }

    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
        LOGGER.info("Updating curvePoint list");
        return curvePointRepository.save(curvePoint);
    }

    public void deleteById(Integer curveId) {
        LOGGER.info("Deleting curvePoint list");
        curvePointRepository.deleteById(curveId);
    }
}
