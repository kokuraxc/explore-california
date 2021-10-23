package com.zhengjin.guo.explorecalifornia.service;

import com.zhengjin.guo.explorecalifornia.domain.Difficulty;
import com.zhengjin.guo.explorecalifornia.domain.Region;
import com.zhengjin.guo.explorecalifornia.domain.Tour;
import com.zhengjin.guo.explorecalifornia.domain.TourPackage;
import com.zhengjin.guo.explorecalifornia.repo.TourPackageRepository;
import com.zhengjin.guo.explorecalifornia.repo.TourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {
    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(String title, String description, String blurb, Integer price, String duration,
            String bullets, String keywords, String tourPackageName, Difficulty difficulty, Region region) {
        TourPackage tourPackage = this.tourPackageRepository.findById(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour package does not exist: " + tourPackageName));
        return this.tourRepository.save(new Tour(title, description, blurb, price, duration, bullets, keywords,
                tourPackage, difficulty, region));
    }

    public long total() {
        return this.tourRepository.count();
    }
}
