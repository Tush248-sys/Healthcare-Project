package com.example.healthcare;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findTopByPhoneOrderByCreatedAtDesc(String phone);
}
