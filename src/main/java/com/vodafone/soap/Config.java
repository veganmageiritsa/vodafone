package com.vodafone.soap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
@PropertySource("classpath:application.properties")
public class Config extends WsConfigurerAdapter {

	@Value("${xmin}")
	private static int xmin;
	@Value("${ymin}")
	private static int ymin;
	@Value("${xmax}")
	private static int xmax;
	@Value("${xmax}")
	private static int ymax;
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/service/*");
	}

	@Bean(name = "nearestNeighborWsdl")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("NearestNeighborPort");
		wsdl11Definition.setLocationUri("/service/nearest-neighbor");
		wsdl11Definition.setTargetNamespace("http://vodafone/xml/point");
		wsdl11Definition.setSchema(schema);
		return wsdl11Definition;
	}


	@Bean
	public XsdSchema studentSchema() {
		return new SimpleXsdSchema(new ClassPathResource("point.xsd"));
	}

	public static int getXmin() {
		return xmin;
	}

	public static void setXmin(int xmin) {
		Config.xmin = xmin;
	}

	public static int getYmin() {
		return ymin;
	}

	public static void setYmin(int ymin) {
		Config.ymin = ymin;
	}

	public static int getXmax() {
		return xmax;
	}

	public static void setXmax(int xmax) {
		Config.xmax = xmax;
	}

	public static int getYmax() {
		return ymax;
	}

	public static void setYmax(int ymax) {
		Config.ymax = ymax;
	}
}