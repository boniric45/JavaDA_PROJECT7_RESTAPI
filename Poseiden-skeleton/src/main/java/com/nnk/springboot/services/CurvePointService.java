package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    @Autowired
    CurvePointRepository curvePointRepository;

    /**
     * Create CurvePoint
     */
    public CurvePoint createCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Read CurvePoint by Id
     */
    public Optional<CurvePoint> getCurveById(int id) {
        return curvePointRepository.findById(id);
    }

    /**
     * Read all CurvePoint
     */
    public List findAll() {
        return curvePointRepository.findAll();
    }

    /**
     * Update CurvePoint
     */
    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Delete CurvePoint by Id
     */
    public void deleteById(Integer curveId) {
        curvePointRepository.deleteById(curveId);
    }
}
