package com.zhengjin.guo.explorecalifornia.web;

import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.zhengjin.guo.explorecalifornia.domain.TourRating;
import com.zhengjin.guo.explorecalifornia.repo.TourRatingRepository;
import com.zhengjin.guo.explorecalifornia.repo.TourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourRating createTourRating(@PathVariable(value = "tourId") String tourId,
            @Valid @RequestBody TourRating tourRating) {
        verifyTour(tourId);
        return tourRatingRepository.save(
                new TourRating(tourId, tourRating.getCustomerId(), tourRating.getScore(), tourRating.getComment()));
    }

    @GetMapping
    public Page<TourRating> getRatings(@PathVariable(value = "tourId") String tourId, Pageable pageable) {
        return tourRatingRepository.findByTourId(tourId, pageable);
    }

    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId) {
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByTourId(tourId).stream().mapToInt(TourRating::getScore)
                .average().orElseThrow(() -> new NoSuchElementException("Tour has no ratings")));
    }

    @PutMapping
    public TourRating UpdateWithPut(@PathVariable(value = "tourId") String tourId,
            @RequestBody @Valid TourRating tourRating) {
        TourRating verifiedTourRating = verifyTourRating(tourId, tourRating.getCustomerId());
        verifiedTourRating.setScore(tourRating.getScore());
        verifiedTourRating.setComment(tourRating.getComment());
        return tourRatingRepository.save(verifiedTourRating);
    }

    @PatchMapping
    public TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId,
            @RequestBody @Valid TourRating tourRating) {
        TourRating verifiedTourRating = verifyTourRating(tourId, tourRating.getCustomerId());
        if (tourRating.getScore() != null) {
            verifiedTourRating.setScore(tourRating.getScore());
        }
        if (tourRating.getComment() != null) {
            verifiedTourRating.setComment(tourRating.getComment());
        }
        return tourRatingRepository.save(verifiedTourRating);
    }

    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") String tourId,
            @PathVariable(value = "customerId") int customerId) {
        TourRating tourRating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(tourRating);
    }

    private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Tour-Rating pair for request(" + tourId + " for customer " + customerId + ")"));
    }

    private void verifyTour(String tourId) throws NoSuchElementException {
        if (!tourRepository.existsById(tourId)) {
            throw new NoSuchElementException("Tour does not exist " + tourId);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
