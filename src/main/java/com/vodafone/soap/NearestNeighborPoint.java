package com.vodafone.soap;


import com.vodafone.domain.KdTree;
import com.vodafone.domain.PointTwoD;
import com.vodafone.domain.RectHV;
import com.vodafone.service.LocationService;
import com.vodafone.xml.point.NearestNeighborRequest;
import com.vodafone.xml.point.NearestNeighborResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


/**
 * Created by ktmle on 27/10/2018.
 */
@Endpoint
public class NearestNeighborPoint
{
    private static final String NAMESPACE_URI = "http://vodafone/xml/point";
    @Autowired
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
        if(kdTree.size()==0) {
            preprocesData();
         }
        PointTwoD point2D=new PointTwoD(request.getLatitude(),request.getLongitude());
        PointTwoD neighbor = kdTree.nearest(point2D);
        neighbor=locationService.findByXAndY(neighbor.x(),neighbor.y());
        neighbor.setFrequency(neighbor.getFrequency()+1);
        locationService.save(neighbor);
        response.setPoint2D(neighbor);

        return response;
    }

    private void preprocesData() {
        Iterable<PointTwoD> locations = locationService.getLocations();
        locations.forEach(location -> kdTree.insert(location));
        RectHV container = new RectHV(Double.valueOf(env.getProperty("xmin")), Double.valueOf(env.getProperty("ymin")),
                Double.valueOf(env.getProperty("xmax")), Double.valueOf(env.getProperty("xmax")));
        KdTree.setContainer(container);
    }

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }
}
