package com.example.healthcare;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/abha")
    public String abha() {
        return "abha";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new PatientRegistrationForm());
        model.addAttribute("hospitals", getHospitals());
        model.addAttribute("departments", getDepartments());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute("patient") PatientRegistrationForm form, Model model) {
        Appointment appointment = registrationService.registerPatient(form);
        model.addAttribute("appointment", appointment);
        model.addAttribute("message", "Registration submitted successfully!");
        return "register-success";
    }

    // In a larger system these would come from a database or configuration;
    // here we hard-code a few sample options similar to an ORS-style portal.
    private List<String> getHospitals() {
        return List.of(
                "Central Hospital, Demo City",
                "City General Hospital",
                "Regional Medical Institute"
        );
    }

    private List<String> getDepartments() {
        return List.of(
                "General Medicine",
                "Orthopaedics",
                "Cardiology",
                "Paediatrics"
        );
    }
}
