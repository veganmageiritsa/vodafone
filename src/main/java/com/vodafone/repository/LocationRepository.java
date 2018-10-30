package com.vodafone.repository;

import com.vodafone.domain.PointTwoD;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ktmle on 29/10/2018.
 */
public interface LocationRepository extends CrudRepository<PointTwoD,Long> {
    PointTwoD findByLocationId(Long locationId);
    PointTwoD findByXAndY(double x, double y);
    List<PointTwoD> findByFrequencyGreaterThanEqual(int freuqency);
}
