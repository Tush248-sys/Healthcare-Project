package com.example.healthcare;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;
    private final OtpService otpService;
    private final CaptchaService captchaService;
    private final AppointmentRepository appointmentRepository;
    private final MailService mailService;

    public RegistrationController(RegistrationService registrationService,
                                  OtpService otpService,
                                  CaptchaService captchaService,
                                  AppointmentRepository appointmentRepository,
                                  MailService mailService) {
        this.registrationService = registrationService;
        this.otpService = otpService;
        this.captchaService = captchaService;
        this.appointmentRepository = appointmentRepository;
        this.mailService = mailService;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        String loggedInMobile = (String) session.getAttribute("LOGGED_IN_MOBILE");
        boolean loggedIn = loggedInMobile != null;
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("loggedInMobile", loggedInMobile);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        String captcha = captchaService.generateCaptchaCode();
        session.setAttribute("LOGIN_CAPTCHA", captcha);
        model.addAttribute("captcha", captcha);
        model.addAttribute("otpRequested", false);
        return "login";
    }

    @PostMapping("/login/send-otp")
    public String sendPatientOtp(@RequestParam("mobile") String mobile,
                                 @RequestParam(value = "email", required = false) String email,
                                 @RequestParam("captcha") String captchaInput,
                                 Model model,
                                 HttpSession session) {
        String expectedCaptcha = (String) session.getAttribute("LOGIN_CAPTCHA");
        if (!captchaService.isValid(expectedCaptcha, captchaInput)) {
            model.addAttribute("error", "Invalid captcha. Please try again.");
            String newCaptcha = captchaService.generateCaptchaCode();
            session.setAttribute("LOGIN_CAPTCHA", newCaptcha);
            model.addAttribute("captcha", newCaptcha);
            model.addAttribute("otpRequested", false);
            return "login";
        }

        String normalizedMobile = mobile.trim();
        String otp = otpService.generateOtp("PATIENT_LOGIN_" + normalizedMobile, 5);

        // store mobile in session so OTP page can display it
        session.setAttribute("PENDING_LOGIN_MOBILE", normalizedMobile);

        // If we have an appointment with this mobile, prefer the registered email;
        // otherwise fall back to the email provided in the login form.
        var appointmentOpt = appointmentRepository.findTopByPhoneOrderByCreatedAtDesc(normalizedMobile);
        String targetEmail = null;
        if (appointmentOpt.isPresent()) {
            targetEmail = appointmentOpt.get().getEmail();
        } else if (email != null && !email.isBlank()) {
            targetEmail = email.trim();
        }

        if (targetEmail == null) {
            model.addAttribute("error", "Please provide an email address to receive the OTP.");
            String newCaptcha = captchaService.generateCaptchaCode();
            session.setAttribute("LOGIN_CAPTCHA", newCaptcha);
            model.addAttribute("captcha", newCaptcha);
            model.addAttribute("otpRequested", false);
            return "login";
        }

        if (!mailService.sendPatientLoginOtp(targetEmail, otp)) {
            model.addAttribute("error", "Failed to send OTP. Please try again later.");
                String newCaptcha = captchaService.generateCaptchaCode();
                session.setAttribute("LOGIN_CAPTCHA", newCaptcha);
                model.addAttribute("captcha", newCaptcha);
                model.addAttribute("otpRequested", false);
                return "login";
            }
            model.addAttribute("info", "OTP sent to your registered email address. It is valid for 5 minutes.");
        } else {
            // No appointment yet for this mobile.
            // For real SMS-based OTP this branch would trigger the SMS send.
            model.addAttribute("info", "OTP generated and sent (demo). Please check your SMS.");
        }

        // redirect to dedicated OTP page
        return "redirect:/login/otp";
    }

    @GetMapping("/login/otp")
    public String showOtpPage(Model model, HttpSession session) {
        String mobile = (String) session.getAttribute("PENDING_LOGIN_MOBILE");
        if (mobile == null) {
            // No pending login; send back to login page
            return "redirect:/login";
        }
        model.addAttribute("mobile", mobile);
        return "login-otp";
    }

    @PostMapping("/login/verify")
    public String verifyPatientOtp(@RequestParam("otp") String otp,
                                   Model model,
                                   HttpSession session) {
        String mobile = (String) session.getAttribute("PENDING_LOGIN_MOBILE");
        if (mobile == null) {
            return "redirect:/login";
        }

        boolean valid = otpService.verifyOtp("PATIENT_LOGIN_" + mobile.trim(), otp.trim());
        if (!valid) {
            model.addAttribute("mobile", mobile);
            model.addAttribute("error", "Invalid or expired OTP. Please request a new one.");
            return "login-otp";
        }

        // Mark user as logged in
        session.removeAttribute("PENDING_LOGIN_MOBILE");
        session.setAttribute("LOGGED_IN_MOBILE", mobile.trim());

        return "redirect:/";
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
        // if a patient with this phone already exists, treat as existing user
        var existing = appointmentRepository.findTopByPhoneOrderByCreatedAtDesc(form.getPhone());
        if (existing.isPresent()) {
            model.addAttribute("patient", form);
            model.addAttribute("hospitals", getHospitals());
            model.addAttribute("departments", getDepartments());
            model.addAttribute("error", "This mobile number is already registered. Please login instead.");
            return "register";
        }

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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
