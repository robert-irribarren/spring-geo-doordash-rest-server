package com.robert.dd.doordashserver;

import com.robert.dd.doordashserver.seed.GooglePlacesSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

import com.vividsolutions.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@EnableJpaAuditing
@SpringBootApplication
public class DoordashServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoordashServerApplication.class, args);
	}

	@Autowired
	GooglePlacesSeed googlePlacesSeed;

	@Value("${seed_enabled}")
	boolean SEED_MODE;

	private double[][] points =
			{
					//{37.7782221,-122.4170817}, // sf
					//{37.5548545,-121.9629831}, // fremont always dream park
					{37.3924529,-122.0791343}, // mountain view albertos
			};

	@PostConstruct
	public void init(){
		// init code goes here
		if (SEED_MODE){
			googlePlacesSeed.seed(points);
		}

	}

}
