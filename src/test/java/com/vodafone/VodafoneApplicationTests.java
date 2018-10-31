package com.vodafone;

import com.vodafone.domain.KdTree;
import com.vodafone.domain.PointTwoD;
import com.vodafone.domain.RectHV;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VodafoneApplicationTests {

	@Autowired
	KdTree kdTree;
	@Test
	public void treeInsertion() {

		PointTwoD pointTwoD = new PointTwoD(0.0,0.0);
		kdTree.insert(pointTwoD);
		RectHV container = new RectHV(0,0,1,1);
		kdTree.setContainer(container);
		KdTree.setContainer(container);
		PointTwoD pointTwoD1=new PointTwoD(1.0,1.0);
		kdTree.insert(pointTwoD1);
		Random generator = new Random();
		double x[]=new double[100];
		double y[]=new double[100];
		for (int i=0; i<100;i++){
			x[i]= generator.nextDouble();
			y[i]= generator.nextDouble();
			PointTwoD pointTwoSearch= new PointTwoD(x[i],y[i]);
			PointTwoD neighbor=kdTree.nearest(pointTwoSearch);
			if(x[i]+y[i]>1.0) {
				assertEquals(1.0, neighbor.x(), 0.001);
				assertEquals(1.0, neighbor.y(), 0.001);
			}else {
				assertEquals(0.0, neighbor.x(), 0.001);
				assertEquals(0.0, neighbor.y(), 0.001);
			}

		}



	}

}
