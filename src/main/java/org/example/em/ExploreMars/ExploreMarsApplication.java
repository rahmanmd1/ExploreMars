package org.example.em.ExploreMars;

import java.io.IOException;
import java.util.List;

import org.example.em.ExploreMars.model.Difficulty;
import org.example.em.ExploreMars.model.Region;
import org.example.em.ExploreMars.services.TourPackageService;
import org.example.em.ExploreMars.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ExploreMarsApplication implements CommandLineRunner {

	@Autowired
	TourPackageService tourPackageService;

	@Autowired
	TourService tourService;

	public static void main(String[] args) {
		SpringApplication.run(ExploreMarsApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {

		tourPackageService.createTourPackage("MC", "Mars Calm");
		tourPackageService.createTourPackage("FMS", "From Desert to Sea");
		tourPackageService.createTourPackage("BM", "BackPack Mars");
		tourPackageService.createTourPackage("TM", "Test of Mars");

		tourPackageService.lookup().forEach(r -> System.out.println(r));
		
		
		
		
		DummyTours.getDummyToursFromFile().forEach( dmTr -> 
		
		tourService.crateTour(dmTr.title, dmTr.description, dmTr.blurb, Double.parseDouble(dmTr.price), dmTr.duration, dmTr.bullets, dmTr.keywords,dmTr.packageType, 
				Difficulty.valueOf(dmTr.difficulty), Region.findByLabel(dmTr.region)		
				));
		
		System.out.println(tourService.gettotal());
		
		
		
		
		

	}

	static class DummyTours {

		String title, description, blurb, price, duration, bullets, keywords, packageType, difficulty, region;

		static List<DummyTours> getDummyToursFromFile() throws IOException{
			
			return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).
					readValue(DummyTours.class.getResourceAsStream("/tours.json"), new TypeReference<List<DummyTours>>() {
			});
			
		}

	}

}
