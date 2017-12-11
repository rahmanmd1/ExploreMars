package org.example.em.ExploreMars.controller;

import java.util.AbstractMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.example.em.ExploreMars.dto.RatingDto;
import org.example.em.ExploreMars.model.Tour;
import org.example.em.ExploreMars.model.TourRating;
import org.example.em.ExploreMars.model.TourRatingPk;
import org.example.em.ExploreMars.repo.TourRatingRepo;
import org.example.em.ExploreMars.repo.TourRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/tours/{tourid}/ratings")
public class TourRatingController {

	TourRatingRepo ratingRepo;
	TourRepo repo;

	@Autowired
	public TourRatingController(TourRatingRepo ratingRepo, TourRepo repo) {
		// super();
		this.ratingRepo = ratingRepo;
		this.repo = repo;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createTourRating(@PathVariable(value = "tourid") int tourId, @RequestBody @Validated RatingDto dto) {

		Tour tour = verifyTour(tourId);
		ratingRepo.save(new TourRating(new TourRatingPk(tour, dto.getCustomerId()), dto.getScore(), dto.getComment()));

	}

	
	@RequestMapping(method = RequestMethod.PUT)
	//@ResponseStatus(HttpStatus.CREATED)
	public void updateTourRating(@PathVariable(value = "tourid") int tourId, @RequestBody @Validated RatingDto dto) {

		TourRating tourRating = verifyTourRating(tourId, dto.getCustomerId());
		tourRating.setComment(dto.getComment());
		tourRating.setScore(dto.getScore());		
		ratingRepo.save(tourRating);

	}

	

	@RequestMapping(method = RequestMethod.PATCH )
	//@ResponseStatus(HttpStatus.CREATED)
	public void updateTourRatingWithPatch(@PathVariable(value = "tourid") int tourId, @RequestBody @Validated RatingDto dto) {

		TourRating tourRating = verifyTourRating(tourId, dto.getCustomerId());
		if(dto.getComment() != null) {
		tourRating.setComment(dto.getComment());
		}
		
		if(dto.getScore() != null) {
		tourRating.setScore(dto.getScore());	
		}
		
		ratingRepo.save(tourRating);

	}

	
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        ratingRepo.delete(rating);
    }
	
	
	
	@RequestMapping(method = RequestMethod.GET)
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourid") int tourId, Pageable pageable) {
        Tour tour = verifyTour(tourId);
        Page<TourRating> tourRatingPage = ratingRepo.findByPkTourId(tour.getId(), pageable);
        List<RatingDto> ratingDtoList = tourRatingPage.getContent().stream().map(tourRating -> convertToDto(tourRating)).collect(Collectors.toList());
        return new PageImpl<RatingDto>(ratingDtoList, pageable, tourRatingPage.getTotalPages());
    }
	
	

	@RequestMapping(method = RequestMethod.GET, path = "/average")
	public AbstractMap.SimpleEntry<String, Double> getAverageRating(@PathVariable(value = "tourid") int tourId) {

		verifyTour(tourId);
		OptionalDouble average = ratingRepo.findByPkTourId(tourId).stream().mapToInt(TourRating::getScore).average();
		return new AbstractMap.SimpleEntry<String, Double>("average",
				average.isPresent() ? average.getAsDouble() : null);
		// map(tourRating -> convertToDto(tourRating)).collect(Collectors.toList());
	}

	// helpers

	public RatingDto convertToDto(TourRating tourRating) {

		return new RatingDto(tourRating.getScore(), tourRating.getComment(), tourRating.getPk().getCustomerId());
	}

	private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        TourRating rating = ratingRepo.findByPkTourIdAndPkCustomerId(tourId, customerId);
        if (rating == null) {
            throw new NoSuchElementException("Tour-Rating pair for request("
                    + tourId + " for customer" + customerId);
        }
        return rating;
    }
	
	
	
	private Tour verifyTour(Integer id) throws NoSuchElementException {
		Tour findOne = repo.findOne(id);
		if (null == findOne) {
			throw new NoSuchElementException("Tours is not valid : " + id);
		}
		return findOne;
	}

	

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String return400(NoSuchElementException ex) {
		return ex.getMessage();
	}

}
