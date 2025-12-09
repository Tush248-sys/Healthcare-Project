package com.example.healthcare;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final AppointmentRepository appointmentRepository;

    public RegistrationService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment registerPatient(PatientRegistrationForm form) {
        Appointment appointment = new Appointment();
        appointment.setFirstName(form.getFirstName());
        appointment.setLastName(form.getLastName());
        appointment.setEmail(form.getEmail());
        appointment.setPhone(form.getPhone());
        appointment.setDateOfBirth(parseLocalDate(form.getDateOfBirth()));
        appointment.setInsuranceNumber(form.getInsuranceNumber());
        appointment.setHospital(form.getHospital());
        appointment.setDepartment(form.getDepartment());
        appointment.setAppointmentDate(parseLocalDate(form.getAppointmentDate()));
        appointment.setCreatedAt(LocalDateTime.now());
        return appointmentRepository.save(appointment);
    }

    private LocalDate parseLocalDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDate.parse(value);
    }
}
