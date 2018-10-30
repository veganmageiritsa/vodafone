package com.vodafone.service.implementation;

import com.vodafone.domain.PointTwoD;
import com.vodafone.repository.LocationRepository;
import com.vodafone.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by ktmle on 29/10/2018.
 */
@Service
public class LocationServiceImplementation implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Iterable<PointTwoD> getLocations(){
        return locationRepository.findAll();
    }

    @Override
    public void save(PointTwoD location) {
        locationRepository.save(location);
    }

    @Override
    public PointTwoD findByLocationId(Long locationId) {
        return locationRepository.findByLocationId(locationId);
    }

    @Override
    public PointTwoD findByXAndY(double x, double y) {
        return locationRepository.findByXAndY(x,y);
    }

    @Override
    public List<PointTwoD> getFrequentLocations(int counterValue) {
        return locationRepository.findByFrequencyGreaterThanEqual(counterValue);
    }


}
