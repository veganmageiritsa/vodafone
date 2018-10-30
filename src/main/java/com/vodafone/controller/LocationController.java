package com.vodafone.controller;

import com.vodafone.domain.PointTwoD;
import com.vodafone.dto.Point2DDto;
import com.vodafone.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ktmle on 29/10/2018.
 */
@RequestMapping("/location")
@RestController
public class LocationController {

static final String GETFREqPOINTSBYVALUE = "/gfpv";
    Logger log = Logger.getLogger(LocationController.class);

@Autowired
    private LocationService locationService;
    @RequestMapping(value = GETFREqPOINTSBYVALUE, method = RequestMethod.GET,produces = "application/json" )
    public ResponseEntity customerOrder(
                                        @RequestParam("counterValue") int counterValue) {
        try {
            List<PointTwoD> freqLocations = locationService.getFrequentLocations(counterValue);
            List<Point2DDto> freqLocationsResponse = new ArrayList<>();
            freqLocations.forEach(location->freqLocationsResponse.add(new Point2DDto(location)));
            return new ResponseEntity(freqLocationsResponse, HttpStatus.OK);

        } catch (Exception e) {
            log.error("error in database");
            return new ResponseEntity("", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
