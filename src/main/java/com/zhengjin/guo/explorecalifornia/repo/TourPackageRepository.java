package com.zhengjin.guo.explorecalifornia.repo;

import java.util.Optional;

import com.zhengjin.guo.explorecalifornia.domain.TourPackage;

import org.springframework.data.repository.CrudRepository;

public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
    Optional<TourPackage> findByName(String name);
}
