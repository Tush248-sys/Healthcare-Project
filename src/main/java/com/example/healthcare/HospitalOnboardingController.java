package com.example.healthcare;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/hospital")
public class HospitalOnboardingController {

    private final HospitalOnboardingService onboardingService;
    private final OtpService otpService;
    private final HospitalOnboardingApplicationRepository applicationRepository;
    private final JavaMailSender mailSender;

    public HospitalOnboardingController(HospitalOnboardingService onboardingService,
                                        OtpService otpService,
                                        HospitalOnboardingApplicationRepository applicationRepository,
                                        JavaMailSender mailSender) {
        this.onboardingService = onboardingService;
        this.otpService = otpService;
        this.applicationRepository = applicationRepository;
        this.mailSender = mailSender;
    }

    @GetMapping("/onboarding-manual")
    public String onboardingManual() {
        return "hospital-onboarding-manual";
    }

    @GetMapping("/join")
    public String showJoinForm(Model model) {
        model.addAttribute("hospital", new HospitalOnboardingForm());
        return "hospital-join";
    }

    @PostMapping("/join")
    public String submitJoinForm(
            @ModelAttribute("hospital") HospitalOnboardingForm form,
            @RequestParam(value = "registrationCertificate", required = false) MultipartFile registrationCertificate,
            @RequestParam(value = "nodalIdProof", required = false) MultipartFile nodalIdProof,
            @RequestParam(value = "sealAuthorizationLetter", required = false) MultipartFile sealAuthorizationLetter,
            @RequestParam(value = "infraReadinessDoc", required = false) MultipartFile infraReadinessDoc,
            Model model) {
        try {
            HospitalOnboardingApplication application = onboardingService.submitApplication(
                    form,
                    registrationCertificate,
                    nodalIdProof,
                    sealAuthorizationLetter,
                    infraReadinessDoc);
            model.addAttribute("application", application);
            return "hospital-join-success";
        } catch (IOException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "hospital-join";
        }
    }

    @GetMapping("/login")
    public String hospitalLogin() {
        return "hospital-login";
    }

    @PostMapping("/login/send-otp")
    public String sendLoginOtp(@RequestParam("username") String username, Model model) {
        String normalized = username == null ? null : username.trim().toLowerCase(Locale.ROOT);
        if (normalized == null || normalized.isEmpty()) {
            model.addAttribute("error", "Please enter the registered hospital email as username.");
            return "hospital-login";
        }

        HospitalOnboardingApplication application = applicationRepository.findByOfficialEmail(normalized)
                .orElse(null);
        if (application == null) {
            model.addAttribute("error", "No hospital found for the given username (official email).");
            return "hospital-login";
        }

        String otp = otpService.generateOtp("HOSPITAL_LOGIN_" + normalized, 5);

        // Send OTP to nodal officer email (or official email if nodal email missing)
        String recipient = application.getNodalEmail() != null && !application.getNodalEmail().isBlank()
                ? application.getNodalEmail()
                : application.getOfficialEmail();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject("Hospital Login OTP - Online Registration System (Demo)");
        message.setText("Your OTP for hospital login is: " + otp + " (valid for 5 minutes).");
        mailSender.send(message);

        model.addAttribute("info", "OTP has been sent to the registered email address of the Nodal Officer.");
        model.addAttribute("username", normalized);
        model.addAttribute("otpRequested", true);
        return "hospital-login";
    }

    @PostMapping("/login/verify")
    public String verifyLoginOtp(@RequestParam("username") String username,
                                 @RequestParam("otp") String otp,
                                 Model model) {
        String normalized = username == null ? null : username.trim().toLowerCase(Locale.ROOT);
        if (normalized == null || normalized.isEmpty()) {
            model.addAttribute("error", "Username is required.");
            return "hospital-login";
        }

        boolean ok = otpService.verifyOtp("HOSPITAL_LOGIN_" + normalized, otp);
        if (!ok) {
            model.addAttribute("error", "Invalid or expired OTP.");
            model.addAttribute("username", normalized);
            model.addAttribute("otpRequested", true);
            return "hospital-login";
        }

        // For demo: redirect to admin applications dashboard as "logged-in" home
        return "redirect:/hospital/admin/applications";
    }

    @GetMapping("/admin/applications")
    public String listApplications(Model model) {
        List<HospitalOnboardingApplication> applications = onboardingService.findAllApplications();
        long total = applications.size();
        long submitted = applications.stream().filter(a -> a.getStatus() == HospitalApplicationStatus.SUBMITTED).count();
        long underReview = applications.stream().filter(a -> a.getStatus() == HospitalApplicationStatus.UNDER_REVIEW).count();
        long approved = applications.stream().filter(a -> a.getStatus() == HospitalApplicationStatus.APPROVED).count();
        long rejected = applications.stream().filter(a -> a.getStatus() == HospitalApplicationStatus.REJECTED).count();

        model.addAttribute("applications", applications);
        model.addAttribute("total", total);
        model.addAttribute("submittedCount", submitted);
        model.addAttribute("underReviewCount", underReview);
        model.addAttribute("approvedCount", approved);
        model.addAttribute("rejectedCount", rejected);
        return "hospital-admin-applications";
    }
}
