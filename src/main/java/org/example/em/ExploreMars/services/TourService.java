package org.example.em.ExploreMars.services;

import org.example.em.ExploreMars.model.Difficulty;
import org.example.em.ExploreMars.model.Region;
import org.example.em.ExploreMars.model.Tour;
import org.example.em.ExploreMars.model.TourPackage;
import org.example.em.ExploreMars.repo.TourPackageRepo;
import org.example.em.ExploreMars.repo.TourRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {

	private TourPackageRepo tourPackageRepo;
	private TourRepo tourRepo;

	@Autowired
	public TourService(TourPackageRepo tourPackageRepo, TourRepo repo) {
		// super();
		this.tourPackageRepo = tourPackageRepo;
		this.tourRepo = repo;
	}

	public Tour crateTour(String title, String description, String blurb, Double price, String duration,
			String bullets, String keywords, String tourPackageCode, Difficulty difficulty, Region region) {

		TourPackage tourPackage = tourPackageRepo.findByName(tourPackageCode);

		if (null == tourPackage) {

			throw new RuntimeException("Mah... Package dosn't exist" + tourPackageCode);
		}

		return tourRepo.save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage,
				difficulty, region));

	}

	public Iterable<Tour> lookup() {

		return tourRepo.findAll();
	}

	public long gettotal() {

		return tourRepo.count();

	}

}
