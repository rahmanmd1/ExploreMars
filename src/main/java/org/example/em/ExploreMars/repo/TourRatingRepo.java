package org.example.em.ExploreMars.repo;

import java.util.List;

import org.example.em.ExploreMars.model.TourRating;
import org.example.em.ExploreMars.model.TourRatingPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface TourRatingRepo  extends CrudRepository<TourRating, TourRatingPk>{
	
    List<TourRating> findByPkTourId(Integer tourId); 
    Page<TourRating> findByPkTourId(Integer tourId, Pageable pageable); 
    TourRating findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);
	
}
