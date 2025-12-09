package com.example.healthcare;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class HospitalOnboardingService {

    private static final long MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024; // 5 MB
    private static final List<String> ALLOWED_EXTENSIONS = List.of("pdf", "jpg", "jpeg", "png");

    private final HospitalOnboardingApplicationRepository repository;
    private final Path uploadRoot;

    public HospitalOnboardingService(HospitalOnboardingApplicationRepository repository) {
        this.repository = repository;
        this.uploadRoot = Path.of("uploads", "hospitals");
    }

    public HospitalOnboardingApplication submitApplication(
            HospitalOnboardingForm form,
            MultipartFile registrationCertificate,
            MultipartFile nodalIdProof,
            MultipartFile sealAuthorizationLetter,
            MultipartFile infraReadinessDoc) throws IOException {

        HospitalOnboardingApplication entity = new HospitalOnboardingApplication();
        copyFormToEntity(form, entity);

        // handle documents
        Files.createDirectories(uploadRoot);

        entity.setRegistrationCertificateFile(
                storeIfPresent(registrationCertificate, "registration-certificate"));
        entity.setNodalIdProofFile(
                storeIfPresent(nodalIdProof, "nodal-id-proof"));
        entity.setSealAuthorizationFile(
                storeIfPresent(sealAuthorizationLetter, "seal-authorization"));
        entity.setInfraReadinessFile(
                storeIfPresent(infraReadinessDoc, "infra-readiness"));

        entity.setStatus(HospitalApplicationStatus.SUBMITTED);
        entity.setSubmittedAt(LocalDateTime.now());
        entity.setApplicationId(generateApplicationId());

        return repository.save(entity);
    }

    public java.util.List<HospitalOnboardingApplication> findAllApplications() {
        return repository.findAll();
    }

    private void copyFormToEntity(HospitalOnboardingForm form, HospitalOnboardingApplication entity) {
        entity.setHospitalName(form.getHospitalName());
        entity.setHospitalType(form.getHospitalType());
        entity.setHospitalCategory(form.getHospitalCategory());
        entity.setRegistrationNumber(form.getRegistrationNumber());
        entity.setYearOfEstablishment(form.getYearOfEstablishment());

        entity.setAddress(form.getAddress());
        entity.setState(form.getState());
        entity.setDistrict(form.getDistrict());
        entity.setPinCode(form.getPinCode());
        entity.setGeoLocation(form.getGeoLocation());

        entity.setPrimaryContactNumber(form.getPrimaryContactNumber());
        entity.setOfficialEmail(form.getOfficialEmail());
        entity.setSecondaryContactNumber(form.getSecondaryContactNumber());

        entity.setNodalName(form.getNodalName());
        entity.setNodalDesignation(form.getNodalDesignation());
        entity.setNodalMobile(form.getNodalMobile());
        entity.setNodalEmail(form.getNodalEmail());

        entity.setHasComputers(form.isHasComputers());
        entity.setHasInternet(form.isHasInternet());
        entity.setHasPrinterScanner(form.isHasPrinterScanner());
        entity.setHasAbhaIntegration(form.isHasAbhaIntegration());
        entity.setHasItStaff(form.isHasItStaff());
        entity.setInfraNotes(form.getInfraNotes());

        entity.setDepartments(form.getDepartments());
    }

    private String storeIfPresent(MultipartFile file, String prefix) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new IOException("File too large. Max allowed size is 5 MB");
        }

        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);

        if (!extension.isEmpty() && !ALLOWED_EXTENSIONS.contains(extension.toLowerCase(Locale.ROOT))) {
            throw new IOException("Unsupported file type: " + extension);
        }

        String storedName = prefix + "-" + UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
        Path target = uploadRoot.resolve(storedName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return storedName;
    }

    private String extractExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int idx = filename.lastIndexOf('.');
        if (idx == -1 || idx == filename.length() - 1) {
            return "";
        }
        return filename.substring(idx + 1);
    }

    private String generateApplicationId() {
        // Simple human-readable ID for demo, e.g. HOSP-20251206-abc123
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase(Locale.ROOT);
        return "HOSP-" + random;
    }
}
