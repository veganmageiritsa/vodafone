package com.vodafone.service;

import com.vodafone.domain.PointTwoD;

import java.util.List;

/**
 * Created by ktmle on 29/10/2018.
 */
public interface LocationService {

    Iterable<PointTwoD> getLocations();

    void save(PointTwoD location);

    PointTwoD findByLocationId(Long locationId);

    PointTwoD findByXAndY(double x, double y);

    List<PointTwoD> getFrequentLocations(int counterValue);
}
