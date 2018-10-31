package com.vodafone.bootstrap;

import com.vodafone.domain.PointTwoD;
import com.vodafone.repository.LocationRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

import static com.vodafone.utilities.Utilities.round;

@Component
@PropertySource("classpath:application.properties")
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

    private LocationRepository locationRepository;

    private Environment env;

    public ProductLoader(LocationRepository locationRepository,Environment env ) {
        this.locationRepository = locationRepository;
        this.env=env;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        for (int i = 0; i < Integer.valueOf(env.getProperty("numberoflocations")); i++) {
            PointTwoD location = new PointTwoD(round(ThreadLocalRandom.current().nextDouble(Double.valueOf(env.getProperty("xmin")), Double.valueOf(env.getProperty("xmax"))), 3),
                    round(ThreadLocalRandom.current().nextDouble(Double.valueOf(env.getProperty("ymin")), Double.valueOf(env.getProperty("ymax"))), 3));
            locationRepository.save(location);
        }
    }
}
