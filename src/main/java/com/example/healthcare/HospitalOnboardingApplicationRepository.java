package com.example.healthcare;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalOnboardingApplicationRepository extends JpaRepository<HospitalOnboardingApplication, Long> {

    Optional<HospitalOnboardingApplication> findByApplicationId(String applicationId);

    Optional<HospitalOnboardingApplication> findByOfficialEmail(String officialEmail);
}
