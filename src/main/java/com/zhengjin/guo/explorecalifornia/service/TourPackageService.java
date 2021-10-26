package com.zhengjin.guo.explorecalifornia.service;

import com.zhengjin.guo.explorecalifornia.domain.TourPackage;
import com.zhengjin.guo.explorecalifornia.repo.TourPackageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourPackageService {
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourPackageService(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    public TourPackage createTourPackage(String code, String name) {
        return this.tourPackageRepository.findById(code)
                .orElse(this.tourPackageRepository.save(new TourPackage(code, name)));
    }

    public Iterable<TourPackage> lookup() {
        return this.tourPackageRepository.findAll();
    }

    public long total() {
        return this.tourPackageRepository.count();
    }
}
