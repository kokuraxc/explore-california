package com.zhengjin.guo.explorecalifornia.web;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.zhengjin.guo.explorecalifornia.domain.Tour;
import com.zhengjin.guo.explorecalifornia.domain.TourRating;
import com.zhengjin.guo.explorecalifornia.domain.TourRatingPk;
import com.zhengjin.guo.explorecalifornia.repo.TourRatingRepository;
import com.zhengjin.guo.explorecalifornia.repo.TourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private final TourRatingRepository tourRatingRepository;
    private final TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @Valid @RequestBody RatingDto ratingDto) {
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(new TourRatingPk(tour, ratingDto.getCustomerId()),
                ratingDto.getScore(), ratingDto.getComment()));
    }

    @GetMapping
    public List<RatingDto> getAlllRatingsForTour(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return tourRatingRepository.findByPkTourId(tourId).stream().map(RatingDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByPkTourId(tourId).stream().mapToInt(TourRating::getScore)
                .average().orElseThrow(() -> new NoSuchElementException("Tour has no ratings")));
    }

    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour does not exist " + tourId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
