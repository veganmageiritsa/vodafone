package com.vodafone.soap;


import com.vodafone.domain.KdTree;
import com.vodafone.domain.PointTwoD;
import com.vodafone.domain.RectHV;
import com.vodafone.service.LocationService;
import com.vodafone.utilities.Utilities;
import com.vodafone.xml.point.NearestNeighborRequest;
import com.vodafone.xml.point.NearestNeighborResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


/**
 * Created by ktmle on 27/10/2018.
 */
@Endpoint
@PropertySource("classpath:application.properties")
public class NearestNeighborPoint {

    Logger log = Logger.getLogger(NearestNeighborPoint.class);

    private static final String NAMESPACE_URI = "http://vodafone/xml/point";
    private Environment environment;
    private KdTree kdTree;
    private LocationService locationService;
    private Environment env;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "NearestNeighborRequest")
    @ResponsePayload
    public NearestNeighborResponse getNearestNeighbor(@RequestPayload NearestNeighborRequest request) {
        NearestNeighborResponse response = new NearestNeighborResponse();
        PointTwoD point2D = new PointTwoD(request.getLatitude(), request.getLongitude());
        if (validateIput(request)) {
            if (kdTree.size() == 0) {
                preprocesData();
            }
            if (Utilities.isNotEmpty(request.getLatitude()) && Utilities.isNotEmpty(request.getLongitude()) && Utilities.isNotEmpty(kdTree)) {

                try {
                    PointTwoD neighbor = kdTree.nearest(point2D);
                    neighbor = locationService.findByXAndY(neighbor.x(), neighbor.y());
                    neighbor.setFrequency(neighbor.getFrequency() + 1);
                    locationService.save(neighbor);
                    response.setPoint2D(neighbor);
                    return response;
                } catch (Exception e) {
                    log.error(String.format("Cannot find Nearest Neighbor for Location", point2D.toString()), e);
                }
            }
        }
        response.setPoint2D(null);
        log.error(String.format("Not valid input ", point2D.toString()));
        return response;
    }

    private boolean validateIput(NearestNeighborRequest request) {
        return (request.getLatitude() >= Double.valueOf(env.getProperty("xmin"))
                && request.getLatitude() <= Double.valueOf(env.getProperty("xmax"))
                && request.getLongitude() >= Double.valueOf(env.getProperty("ymin"))
                && request.getLongitude() <= Double.valueOf(env.getProperty("ymax")));
    }

    private void preprocesData() {
        try {
            Iterable<PointTwoD> locations = locationService.getLocations();
            locations.forEach(location -> kdTree.insert(location));
            RectHV container = new RectHV(Double.valueOf(env.getProperty("xmin")), Double.valueOf(env.getProperty("ymin")),
                    Double.valueOf(env.getProperty("xmax")), Double.valueOf(env.getProperty("xmax")));
            KdTree.setContainer(container);
        } catch (Exception e) {
            log.error(String.format("Error in Inserting Locations in DataStructure 2DTree"), e);

        }
    }

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void setKdTree(KdTree kdTree) {
        this.kdTree = kdTree;
    }


}
