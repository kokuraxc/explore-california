package com.zhengjin.guo.explorecalifornia.repo;

import java.util.List;
import java.util.Optional;

import com.zhengjin.guo.explorecalifornia.domain.TourRating;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, String> {
    List<TourRating> findByTourId(String tourId);

    Optional<TourRating> findByTourIdAndCustomerId(String tourId, Integer customerId);

    Page<TourRating> findByTourId(String tourId, Pageable pageable);
}
