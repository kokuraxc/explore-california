package com.zhengjin.guo.explorecalifornia.repo;

import java.util.List;

import com.zhengjin.guo.explorecalifornia.domain.Tour;

import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<Tour, Integer> {
    List<Tour> findByTourPackageCode(String code);
}
