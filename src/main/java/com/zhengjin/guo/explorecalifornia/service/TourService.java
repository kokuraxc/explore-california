package com.zhengjin.guo.explorecalifornia.service;

import java.util.Map;

import com.zhengjin.guo.explorecalifornia.domain.Tour;
import com.zhengjin.guo.explorecalifornia.domain.TourPackage;
import com.zhengjin.guo.explorecalifornia.repo.TourPackageRepository;
import com.zhengjin.guo.explorecalifornia.repo.TourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {
    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(String title, String tourPackageName, Map<String, String> details) {
        TourPackage tourPackage = this.tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour package does not exist: " + tourPackageName));
        return this.tourRepository.save(new Tour(title, tourPackage, details));
    }

    public long total() {
        return this.tourRepository.count();
    }
}
