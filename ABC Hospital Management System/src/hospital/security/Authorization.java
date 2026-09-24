package hospital.security;

import hospital.models.User;

import java.util.EnumSet;
import java.util.Set;

public class Authorization {

    private Authorization() {
        // Prevent creating Authorization objects
    }

    // ========================================
    // GENERAL PERMISSION CHECK
    // ========================================
    public static boolean hasPermission(Permission permission) {

        User user = Session.getCurrentUser();

        if (user == null || permission == null) {
            return false;
        }

        return getPermissionsForRole(user.getRole()).contains(permission);
    }

    // ========================================
    // ROLE → PERMISSIONS
    // ========================================
    private static Set<Permission> getPermissionsForRole(String role) {

        if (role == null) {
            return EnumSet.noneOf(Permission.class);
        }

        switch (role.toUpperCase()) {

            case "ADMIN":
                return adminPermissions();

            case "RECEPTIONIST":
                return receptionistPermissions();

            case "DOCTOR":
                return doctorPermissions();

            case "NURSE":
                return nursePermissions();

            case "PHARMACIST":
                return pharmacistPermissions();

            case "LABORATORY_TECHNICIAN":
                return laboratoryTechnicianPermissions();

            case "STAFF":
                return staffPermissions();

            default:
                return EnumSet.noneOf(Permission.class);
        }
    }

    // ========================================
    // ADMIN
    // ========================================
    private static Set<Permission> adminPermissions() {

        return EnumSet.allOf(Permission.class);
    }

    // ========================================
    // RECEPTIONIST
    // ========================================
    private static Set<Permission> receptionistPermissions() {

        return EnumSet.of(
                Permission.VIEW_DASHBOARD,
                Permission.REGISTER_PATIENT,
                Permission.VIEW_PATIENTS,
                Permission.FIND_PATIENT,
                Permission.UPDATE_PATIENT,
                Permission.VIEW_PATIENT_PROFILE,
                Permission.VIEW_PATIENT_APPOINTMENTS,
                Permission.VIEW_PATIENT_ADMISSIONS,
                Permission.VIEW_PATIENT_BILLING,
                Permission.CREATE_APPOINTMENT,
                Permission.VIEW_APPOINTMENTS,
                Permission.FIND_APPOINTMENT,
                Permission.UPDATE_APPOINTMENT,
                Permission.CREATE_ADMISSION,
                Permission.VIEW_ADMISSIONS,
                Permission.FIND_ADMISSION,
                Permission.VIEW_BILLING,
                Permission.CREATE_INVOICE,
                Permission.VIEW_INVOICE,
                Permission.PROCESS_PAYMENT,
                Permission.VIEW_PAYMENT_HISTORY,
                Permission.VIEW_OWN_PROFILE,
                Permission.UPDATE_OWN_PROFILE,
                Permission.CHANGE_OWN_PASSWORD,
                Permission.LOGOUT
        );
    }

    // ========================================
    // DOCTOR
    // ========================================
    private static Set<Permission> doctorPermissions() {

        return EnumSet.of(
                Permission.VIEW_DASHBOARD,
                Permission.VIEW_STAFF,
                Permission.FIND_STAFF,
                Permission.VIEW_PATIENTS,
                Permission.FIND_PATIENT,
                Permission.VIEW_PATIENT_PROFILE,
                Permission.VIEW_MEDICAL_HISTORY,
                Permission.VIEW_PATIENT_APPOINTMENTS,
                Permission.VIEW_PATIENT_ADMISSIONS,
                Permission.VIEW_PATIENT_PRESCRIPTIONS,
                Permission.VIEW_APPOINTMENTS,
                Permission.FIND_APPOINTMENT,
                Permission.UPDATE_APPOINTMENT,
                Permission.VIEW_DOCTOR_APPOINTMENTS,
                Permission.VIEW_CLINICAL_RECORDS,
                Permission.CREATE_MEDICAL_RECORD,
                Permission.UPDATE_MEDICAL_RECORD,
                Permission.CREATE_DIAGNOSIS,
                Permission.VIEW_DIAGNOSIS,
                Permission.CREATE_TREATMENT,
                Permission.VIEW_TREATMENT,
                Permission.PRESCRIBE_MEDICATION,
                Permission.VIEW_LABORATORY,
                Permission.ORDER_LAB_TEST,
                Permission.VIEW_LAB_RESULTS,
                Permission.VIEW_PHARMACY,
                Permission.VIEW_MEDICATIONS,
                Permission.VIEW_PRESCRIPTIONS,
                Permission.VIEW_ADMISSIONS,
                Permission.FIND_ADMISSION,
                Permission.VIEW_OWN_PROFILE,
                Permission.UPDATE_OWN_PROFILE,
                Permission.CHANGE_OWN_PASSWORD,
                Permission.LOGOUT
        );
    }

    // ========================================
    // NURSE
    // ========================================
    private static Set<Permission> nursePermissions() {

        return EnumSet.of(
                Permission.VIEW_DASHBOARD,
                Permission.VIEW_STAFF,
                Permission.FIND_STAFF,
                Permission.VIEW_PATIENTS,
                Permission.FIND_PATIENT,
                Permission.VIEW_PATIENT_PROFILE,
                Permission.VIEW_MEDICAL_HISTORY,
                Permission.VIEW_PATIENT_APPOINTMENTS,
                Permission.VIEW_PATIENT_ADMISSIONS,
                Permission.VIEW_PATIENT_PRESCRIPTIONS,
                Permission.VIEW_APPOINTMENTS,
                Permission.FIND_APPOINTMENT,
                Permission.VIEW_DOCTOR_APPOINTMENTS,
                Permission.VIEW_CLINICAL_RECORDS,
                Permission.CREATE_MEDICAL_RECORD,
                Permission.UPDATE_MEDICAL_RECORD,
                Permission.VIEW_DIAGNOSIS,
                Permission.VIEW_TREATMENT,
                Permission.VIEW_LABORATORY,
                Permission.VIEW_LAB_RESULTS,
                Permission.VIEW_PHARMACY,
                Permission.VIEW_MEDICATIONS,
                Permission.VIEW_PRESCRIPTIONS,
                Permission.VIEW_ADMISSIONS,
                Permission.FIND_ADMISSION,
                Permission.UPDATE_ADMISSION,
                Permission.VIEW_OWN_PROFILE,
                Permission.UPDATE_OWN_PROFILE,
                Permission.CHANGE_OWN_PASSWORD,
                Permission.LOGOUT
        );
    }

    // ========================================
    // PHARMACIST
    // ========================================
    private static Set<Permission> pharmacistPermissions() {

        return EnumSet.of(
                Permission.VIEW_DASHBOARD,
                Permission.VIEW_STAFF,
                Permission.FIND_STAFF,
                Permission.VIEW_PATIENTS,
                Permission.FIND_PATIENT,
                Permission.VIEW_PATIENT_PROFILE,
                Permission.VIEW_PATIENT_PRESCRIPTIONS,
                Permission.VIEW_PHARMACY,
                Permission.VIEW_MEDICATIONS,
                Permission.MANAGE_MEDICATIONS,
                Permission.DISPENSE_MEDICATION,
                Permission.VIEW_PRESCRIPTIONS,
                Permission.MANAGE_PRESCRIPTIONS,
                Permission.VIEW_OWN_PROFILE,
                Permission.UPDATE_OWN_PROFILE,
                Permission.CHANGE_OWN_PASSWORD,
                Permission.LOGOUT
        );
    }

    // ========================================
    // LABORATORY TECHNICIAN
    // ========================================
    private static Set<Permission> laboratoryTechnicianPermissions() {

        return EnumSet.of(
                Permission.VIEW_DASHBOARD,
                Permission.VIEW_STAFF,
                Permission.FIND_STAFF,
                Permission.VIEW_PATIENTS,
                Permission.FIND_PATIENT,
                Permission.VIEW_PATIENT_PROFILE,
                Permission.VIEW_LABORATORY,
                Permission.PERFORM_LAB_TEST,
                Permission.VIEW_LAB_RESULTS,
                Permission.UPDATE_LAB_RESULTS,
                Permission.VIEW_OWN_PROFILE,
                Permission.UPDATE_OWN_PROFILE,
                Permission.CHANGE_OWN_PASSWORD,
                Permission.LOGOUT
        );
    }

    // ========================================
    // GENERAL STAFF
    // ========================================
    private static Set<Permission> staffPermissions() {

        return EnumSet.of(
                Permission.VIEW_DASHBOARD,
                Permission.VIEW_STAFF,
                Permission.FIND_STAFF,
                Permission.REGISTER_PATIENT,
                Permission.VIEW_PATIENTS,
                Permission.FIND_PATIENT,
                Permission.UPDATE_PATIENT,
                Permission.VIEW_PATIENT_PROFILE,
                Permission.CREATE_APPOINTMENT,
                Permission.VIEW_APPOINTMENTS,
                Permission.FIND_APPOINTMENT,
                Permission.UPDATE_APPOINTMENT,
                Permission.VIEW_ADMISSIONS,
                Permission.FIND_ADMISSION,
                Permission.VIEW_BILLING,
                Permission.VIEW_INVOICE,
                Permission.VIEW_PAYMENT_HISTORY,
                Permission.VIEW_OWN_PROFILE,
                Permission.UPDATE_OWN_PROFILE,
                Permission.CHANGE_OWN_PASSWORD,
                Permission.LOGOUT
        );
    }

    // ========================================
    // ROLE CHECKS
    // ========================================
    public static boolean hasRole(String role) {

        User user = Session.getCurrentUser();

        if (user == null || role == null) {
            return false;
        }

        return role.equalsIgnoreCase(user.getRole());
    }

    public static boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public static boolean isReceptionist() {
        return hasRole("RECEPTIONIST");
    }

    public static boolean isDoctor() {
        return hasRole("DOCTOR");
    }

    public static boolean isNurse() {
        return hasRole("NURSE");
    }

    public static boolean isPharmacist() {
        return hasRole("PHARMACIST");
    }

    public static boolean isLaboratoryTechnician() {
        return hasRole("LABORATORY_TECHNICIAN");
    }

    public static boolean isStaff() {
        return hasRole("STAFF");
    }

    // ========================================
    // EXISTING MENU CHECKS
    // ========================================
    public static boolean canViewPatients() {
        return hasPermission(Permission.VIEW_PATIENTS);
    }

    public static boolean canViewStaff() {
        return hasPermission(Permission.VIEW_STAFF);
    }

    public static boolean canViewAppointments() {
        return hasPermission(Permission.VIEW_APPOINTMENTS);
    }

    public static boolean canAccessAdmissions() {
        return hasPermission(Permission.VIEW_ADMISSIONS);
    }

    public static boolean canAccessClinical() {
        return hasPermission(Permission.VIEW_CLINICAL_RECORDS);
    }

    public static boolean canAccessLaboratory() {
        return hasPermission(Permission.VIEW_LABORATORY);
    }

    public static boolean canAccessPharmacy() {
        return hasPermission(Permission.VIEW_PHARMACY);
    }

    public static boolean canAccessBilling() {
        return hasPermission(Permission.VIEW_BILLING);
    }

    public static boolean canAccessAdministration() {
        return hasPermission(Permission.ACCESS_HOSPITAL_ADMINISTRATION);
    }

    public static boolean canManageOwnProfile() {
        return hasPermission(Permission.VIEW_OWN_PROFILE);
    }
}
