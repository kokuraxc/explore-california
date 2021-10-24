package com.zhengjin.guo.explorecalifornia.repo;

import com.zhengjin.guo.explorecalifornia.domain.Tour;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TourRepository extends PagingAndSortingRepository<Tour, Integer> {
    Page<Tour> findByTourPackageCode(String code, Pageable pageable);
}
