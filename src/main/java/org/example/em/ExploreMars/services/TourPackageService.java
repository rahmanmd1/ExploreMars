package org.example.em.ExploreMars.services;

import org.example.em.ExploreMars.model.TourPackage;
import org.example.em.ExploreMars.repo.TourPackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TourPackageService {
	
	private TourPackageRepo tourPackageRepo;

	
	@Autowired
	public TourPackageService(TourPackageRepo tourPackageRepo) {
		this.tourPackageRepo = tourPackageRepo;
	}
	
	
	public TourPackage createTourPackage(String code, String name) {
		
		if( !tourPackageRepo.exists(code)) {
			
			tourPackageRepo.save(new TourPackage(code, name));
		}
		
		return null;
	}
	
	public Iterable<TourPackage>  lookup(){
		
		
		return tourPackageRepo.findAll();
	}
	
public long gettotal() {
	
	return tourPackageRepo.count();
	
}
	
	
}
