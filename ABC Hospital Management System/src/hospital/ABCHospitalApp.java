package hospital;

import hospital.dao.AdmissionDAO;
import hospital.dao.AppointmentDAO;
import hospital.dao.BedDAO;
import hospital.dao.DepartmentDAO;
import hospital.dao.DiagnosisDAO;
import hospital.dao.LaboratoryTechnicianDAO;
import hospital.dao.PharmacistDAO;
import hospital.database.DatabaseConnection;
import hospital.dao.RoomDAO;
import hospital.dao.TreatmentDAO;
import hospital.models.Admission;
import hospital.models.NurseAssignment;
import hospital.dao.NurseAssignmentDAO;
import hospital.models.Bed;
import hospital.models.Room;
import hospital.dao.WardDAO;
import hospital.models.Ward;
import hospital.security.Authorization;
import hospital.security.Session;
import hospital.models.User;
import hospital.services.UserService;
import hospital.models.Patient;
import hospital.models.Doctor;
import hospital.models.Nurse;
import hospital.models.Department;
import hospital.models.Appointment;
import hospital.models.Diagnosis;
import hospital.models.LaboratoryTechnician;
import hospital.models.Pharmacist;
import hospital.models.Treatment;
import hospital.security.Permission;

import hospital.services.PatientService;
import hospital.services.DoctorService;
import hospital.services.NurseService;
import hospital.services.AppointmentService;

import hospital.dao.InvoiceDAO;
import hospital.dao.InvoiceItemDAO;
import hospital.dao.PaymentDAO;

import hospital.userview.PatientView;
import hospital.userview.DoctorView;
import hospital.userview.NurseView;
import hospital.userview.AppointmentView;

import hospital.dao.MedicalRecordDAO;
import hospital.models.MedicalRecord;

import hospital.dao.LaboratoryTestDAO;
import hospital.models.LaboratoryTest;
import hospital.models.LaboratoryTechnician;
import java.time.LocalDateTime;

import hospital.dao.MedicationDAO;
import hospital.dao.PrescriptionDAO;
import hospital.dao.PrescriptionItemDAO;
import hospital.dao.MedicationDispensingDAO;
import hospital.models.Invoice;
import hospital.models.InvoiceItem;

import hospital.models.Medication;
import hospital.models.Prescription;
import hospital.models.PrescriptionItem;
import hospital.models.MedicationDispensing;
import hospital.models.Payment;
import hospital.models.Pharmacist;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ABCHospitalApp {

    private static final Scanner scanner
            = new Scanner(System.in);

    // =========================================================
    // USER
    // =========================================================
    private static UserService userService = new UserService();
    private static User currentUser;

    // =========================================================
    // PATIENT
    // =========================================================
    private static final PatientService patientService
            = new PatientService();

    private static final PatientView patientView
            = new PatientView();

    // =========================================================
    // DOCTOR
    // =========================================================
    private static final DoctorService doctorService
            = new DoctorService();

    private static final DoctorView doctorView
            = new DoctorView();
    private static final DepartmentDAO departmentDAO = new DepartmentDAO();
    // =========================================================
    // NURSE
    // =========================================================
    private static final NurseService nurseService
            = new NurseService();

    private static final NurseView nurseView
            = new NurseView();

    // =========================================================
    // APPOINTMENT
    // =========================================================
    private static final AppointmentService appointmentService
            = new AppointmentService();

    private static final AppointmentView appointmentView
            = new AppointmentView();

    // =========================================================
    // DATE FORMATTER
    // =========================================================
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final MedicationDAO medicationDAO
            = new MedicationDAO();

    private static final PrescriptionDAO prescriptionDAO
            = new PrescriptionDAO();

    private static final PrescriptionItemDAO prescriptionItemDAO
            = new PrescriptionItemDAO();

    private static final MedicationDispensingDAO medicationDispensingDAO
            = new MedicationDispensingDAO();

    private static final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private static final InvoiceItemDAO invoiceItemDAO = new InvoiceItemDAO();
    private static final PaymentDAO paymentDAO = new PaymentDAO();

    // =========================================================
    // MAIN
    // =========================================================
    public static void main(String[] args) {
        currentUser = login();

        if (currentUser == null) {
            System.out.println("Application closed.");
            return;
        }

        while (true) {

            displayMainMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    dashboardMenu();
                    break;

                case 2:
                    if (Authorization.canViewPatients()) {
                        patientMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 3:
                    if (Authorization.canViewStaff()) {
                        staffMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 4:
                    if (Authorization.canViewAppointments()) {
                        appointmentMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 5:
                    if (Authorization.canAccessAdmissions()) {
                        admissionBedMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 6:
                    if (Authorization.canAccessClinical()) {
                        clinicalMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 7:
                    if (Authorization.canAccessLaboratory()) {
                        laboratoryMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 8:
                    if (Authorization.canAccessPharmacy()) {
                        pharmacyMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 9:
                    if (Authorization.canAccessBilling()) {
                        billingMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 10:
                    if (Authorization.canAccessAdministration()) {
                        hospitalAdministrationMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 11:
                    if (Authorization.canManageOwnProfile()) {
                        userAccountMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 0:
                    Session.end();
                    System.out.println();
                    System.out.println("You have been logged out.");
                    System.out.println("Thank you for using ABC Hospital Management System.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static User login() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          ABC HOSPITAL SYSTEM");
        System.out.println("                LOGIN");
        System.out.println("========================================");

        while (true) {

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println();
                System.out.println("Username and password cannot be empty.");
                System.out.println();
                continue;
            }

            User user = userService.authenticate(username, password);

            if (user == null) {
                System.out.println();
                System.out.println("Invalid username or password.");
                System.out.println("Please try again.");
                System.out.println();
                continue;
            }

            if (!user.getActive()) {
                System.out.println();
                System.out.println("This account is inactive.");
                System.out.println("Please contact the hospital administrator.");
                System.out.println();
                return null;
            }

            System.out.println();
            System.out.println("========================================");
            System.out.println("          LOGIN SUCCESSFUL");
            System.out.println("========================================");
            System.out.println("Welcome, " + user.getUsername());
            System.out.println("Role: " + user.getRole());
            System.out.println("Status: ACTIVE");
            System.out.println("========================================");
            System.out.println();

            Session.start(user);
            return user;
        }
    }

    // =========================================================
    // ACCESS DENIED
    // =========================================================
    private static void accessDenied() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             ACCESS DENIED");
        System.out.println("========================================");
        System.out.println("You do not have permission to access");
        System.out.println("this section.");
        System.out.println("========================================");
        System.out.println();
    }

    // =========================================================
    // MAIN MENU
    // =========================================================
    private static void displayMainMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       ABC HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println();
        System.out.println("1. Dashboard");
        System.out.println("2. Patient Management");
        System.out.println("3. Staff Management");
        System.out.println("4. Appointment Management");
        System.out.println("5. Admission & Bed Management");
        System.out.println("6. Clinical Management");
        System.out.println("7. Laboratory Services");
        System.out.println("8. Pharmacy Services");
        System.out.println("9. Billing & Payment");
        System.out.println("10. Hospital Administration");
        System.out.println("11. User Account / Profile");
        System.out.println("0. Logout / Exit");
        System.out.println();
        System.out.println("========================================");
    }

    // =========================================================
    // DASHBOARD
    // =========================================================
    private static void dashboardMenu() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("                DASHBOARD");
            System.out.println("========================================");
            System.out.println("ABC Hospital Management System");
            System.out.println("----------------------------------------");
            System.out.println("1. Patient Management");
            System.out.println("2. Staff Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Hospital Services");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    patientMenu();
                    break;
                case 2:
                    staffMenu();
                    break;
                case 3:
                    appointmentMenu();
                    break;
                case 4:
                    hospitalServicesOverview();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void hospitalServicesOverview() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("          HOSPITAL SERVICES");
        System.out.println("========================================");
        System.out.println("Admission & Bed Management");
        System.out.println("Clinical Management");
        System.out.println("Laboratory Services");
        System.out.println("Pharmacy Services");
        System.out.println("Billing & Payment");
        System.out.println("----------------------------------------");
        System.out.println("Use the main menu to open each service.");
        System.out.println("========================================");
        System.out.println();
    }

    private static void hospitalAdministrationMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("        HOSPITAL ADMINISTRATION");
            System.out.println("========================================");
            System.out.println("1. Department Management");
            System.out.println("2. Hospital Statistics");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    departmentManagementMenu();
                    break;

                case 2:
                    hospitalStatisticsMenu();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void hospitalStatisticsMenu() {

        DepartmentDAO departmentDAO = new DepartmentDAO();
        WardDAO wardDAO = new WardDAO();
        RoomDAO roomDAO = new RoomDAO();
        BedDAO bedDAO = new BedDAO();
        AdmissionDAO admissionDAO = new AdmissionDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        int totalDepartments = departmentDAO.findAllDepartments().size();
        int totalWards = wardDAO.findAllWards().size();
        int totalRooms = roomDAO.findAllRooms().size();

        int totalBeds = bedDAO.findAllBeds().size();
        int occupiedBeds = bedDAO.findOccupiedBeds().size();
        int availableBeds = bedDAO.findAvailableBeds().size();

        int totalAdmissions = admissionDAO.findAllAdmissions().size();
        int totalAppointments = appointmentDAO.getAllAppointments().size();

        System.out.println();
        System.out.println("========================================");
        System.out.println("        HOSPITAL STATISTICS");
        System.out.println("========================================");

        System.out.println();
        System.out.println("HOSPITAL STRUCTURE");
        System.out.println("----------------------------------------");
        System.out.println("Total Departments : " + totalDepartments);
        System.out.println("Total Wards       : " + totalWards);
        System.out.println("Total Rooms       : " + totalRooms);

        System.out.println();
        System.out.println("BED STATISTICS");
        System.out.println("----------------------------------------");
        System.out.println("Total Beds        : " + totalBeds);
        System.out.println("Occupied Beds     : " + occupiedBeds);
        System.out.println("Available Beds    : " + availableBeds);

        System.out.println();
        System.out.println("PATIENT STATISTICS");
        System.out.println("----------------------------------------");
        System.out.println("Total Admissions  : " + totalAdmissions);

        System.out.println();
        System.out.println("APPOINTMENT STATISTICS");
        System.out.println("----------------------------------------");
        System.out.println("Total Appointments: " + totalAppointments);

        System.out.println();
        System.out.println("========================================");
        System.out.println("Press Enter to return...");
        scanner.nextLine();
    }

    private static void departmentManagementMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("        DEPARTMENT MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Department");
            System.out.println("2. View All Departments");
            System.out.println("3. Find Department");
            System.out.println("4. Find Department by Name");
            System.out.println("5. Update Department");
            System.out.println("6. Delete Department");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addDepartmentMenu();
                    break;

                case 2:
                    viewAllDepartmentsMenu();
                    break;

                case 3:
                    findDepartmentMenu();
                    break;

                case 4:
                    findDepartmentByNameMenu();
                    break;

                case 5:
                    updateDepartmentMenu();
                    break;

                case 6:
                    deleteDepartmentMenu();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addDepartmentMenu() {

        System.out.println();
        System.out.println("========== ADD DEPARTMENT ==========");

        String name = readString("Enter department name: ");

        Department department = new Department();
        department.setName(name);

        try {
            DepartmentDAO dao = new DepartmentDAO();

            if (dao.addDepartment(department)) {
                System.out.println("Department added successfully.");
            } else {
                System.out.println("Failed to add department.");
            }

        } catch (Exception e) {
            System.out.println("Error adding department: " + e.getMessage());
        }
    }

    private static void viewAllDepartmentsMenu() {

        System.out.println();
        System.out.println("========== ALL DEPARTMENTS ==========");

        try {
            DepartmentDAO dao = new DepartmentDAO();

            List<Department> departments = dao.findAllDepartments();

            if (departments.isEmpty()) {
                System.out.println("No departments found.");
                return;
            }

            for (Department department : departments) {
                System.out.println("----------------------------------------");
                System.out.println("ID: " + department.getId());
                System.out.println("Name: " + department.getName());
            }

            System.out.println("----------------------------------------");

        } catch (Exception e) {
            System.out.println("Error retrieving departments: " + e.getMessage());
        }
    }

    private static void findDepartmentMenu() {

        System.out.println();
        System.out.println("========== FIND DEPARTMENT ==========");

        int id = readInt("Enter department ID: ");

        try {
            DepartmentDAO dao = new DepartmentDAO();

            Department department = dao.findDepartmentById(id);

            if (department == null) {
                System.out.println("Department not found.");
                return;
            }

            System.out.println("----------------------------------------");
            System.out.println("ID: " + department.getId());
            System.out.println("Name: " + department.getName());
            System.out.println("----------------------------------------");

        } catch (Exception e) {
            System.out.println("Error finding department: " + e.getMessage());
        }
    }

    private static void findDepartmentByNameMenu() {

        System.out.println();
        System.out.println("========== FIND DEPARTMENT BY NAME ==========");

        String name = readString("Enter department name: ");

        try {
            DepartmentDAO dao = new DepartmentDAO();

            Department department = dao.findDepartmentByName(name);

            if (department == null) {
                System.out.println("Department not found.");
                return;
            }

            System.out.println("----------------------------------------");
            System.out.println("ID: " + department.getId());
            System.out.println("Name: " + department.getName());
            System.out.println("----------------------------------------");

        } catch (Exception e) {
            System.out.println("Error finding department: " + e.getMessage());
        }
    }

    private static void updateDepartmentMenu() {

        System.out.println();
        System.out.println("========== UPDATE DEPARTMENT ==========");

        int id = readInt("Enter department ID: ");

        try {
            DepartmentDAO dao = new DepartmentDAO();

            Department department = dao.findDepartmentById(id);

            if (department == null) {
                System.out.println("Department not found.");
                return;
            }

            System.out.println("Current department name: " + department.getName());

            String newName = readString("Enter new department name: ");

            department.setName(newName);

            if (dao.updateDepartment(department)) {
                System.out.println("Department updated successfully.");
            } else {
                System.out.println("Failed to update department.");
            }

        } catch (Exception e) {
            System.out.println("Error updating department: " + e.getMessage());
        }
    }

    private static void deleteDepartmentMenu() {

        System.out.println();
        System.out.println("========== DELETE DEPARTMENT ==========");

        int id = readInt("Enter department ID: ");

        try {
            DepartmentDAO dao = new DepartmentDAO();

            Department department = dao.findDepartmentById(id);

            if (department == null) {
                System.out.println("Department not found.");
                return;
            }

            System.out.println("Department: " + department.getName());

            String confirm = readString("Are you sure you want to delete this department? (Y/N): ");

            if (!confirm.equalsIgnoreCase("Y")) {
                System.out.println("Delete cancelled.");
                return;
            }

            if (dao.deleteDepartment(id)) {
                System.out.println("Department deleted successfully.");
            } else {
                System.out.println("Failed to delete department.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting department: " + e.getMessage());
        }
    }

    // =========================================================
    // STAFF MANAGEMENT
    // =========================================================
    private static void staffMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("             STAFF MANAGEMENT");
            System.out.println("========================================");

            System.out.println("1. Doctor Management");
            System.out.println("2. Nurse Management");
            System.out.println("3. Pharmacist Management");
            System.out.println("4. Laboratory Technician Management");

            System.out.println("5. View All Staff");
            System.out.println("6. Find Staff");
            System.out.println("7. View Staff by Department");
            System.out.println("8. Staff Account Management");

            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    if (Authorization.hasPermission(Permission.VIEW_STAFF)) {
                        doctorMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 2:
                    if (Authorization.hasPermission(Permission.VIEW_STAFF)) {
                        nurseMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 3:
                    if (Authorization.hasPermission(Permission.VIEW_STAFF)) {
                        pharmacistManagementMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 4:
                    if (Authorization.hasPermission(Permission.VIEW_STAFF)) {
                        laboratoryTechnicianManagementMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 5:
                    if (Authorization.hasPermission(Permission.VIEW_STAFF)) {
                        viewAllStaffMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 6:
                    if (Authorization.hasPermission(Permission.FIND_STAFF)) {
                        findStaffMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 7:
                    if (Authorization.hasPermission(Permission.VIEW_STAFF_BY_DEPARTMENT)) {
                        viewStaffByDepartmentMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 8:
                    if (Authorization.hasPermission(Permission.MANAGE_STAFF_ACCOUNTS)) {
                        staffAccountManagementMenu();
                    } else {
                        accessDenied();
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

// ============================================================
// LABORATORY TECHNICIAN MANAGEMENT
// ============================================================
// Handles the registration, viewing, searching, updating,
// and deletion of laboratory technicians.
//
// This module uses LaboratoryTechnicianDAO for database
// operations and the LaboratoryTechnician model for data.
// ============================================================
// ============================================================
// LABORATORY TECHNICIAN MENU
// ============================================================
// Displays the Laboratory Technician Management menu and
// directs the user to the appropriate operation.
// ============================================================
    private static void laboratoryTechnicianManagementMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("   LABORATORY TECHNICIAN MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Register Laboratory Technician");
            System.out.println("2. View All Laboratory Technicians");
            System.out.println("3. Find Laboratory Technician");
            System.out.println("4. Update Laboratory Technician");
            System.out.println("5. Delete Laboratory Technician");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    registerLaboratoryTechnician();
                    break;

                case 2:
                    viewAllLaboratoryTechnicians();
                    break;

                case 3:
                    findLaboratoryTechnician();
                    break;

                case 4:
                    updateLaboratoryTechnician();
                    break;

                case 5:
                    deleteLaboratoryTechnician();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

// ============================================================
// REGISTER LABORATORY TECHNICIAN
// ============================================================
// Collects laboratory technician information from the user
// and saves the technician to the database.
// ============================================================
    private static void registerLaboratoryTechnician() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("   REGISTER LABORATORY TECHNICIAN");
        System.out.println("========================================");

        String firstName = readString("First Name: ");

        String lastName = readString("Last Name: ");

        char gender
                = readString("Gender (M/F): ").charAt(0);

        LocalDate dateOfBirth
                = readDate("Date of Birth (YYYY-MM-DD): ");

        String phone
                = readString("Phone: ");

        String email
                = readString("Email: ");

        String street
                = readString("Street: ");

        String city
                = readString("City: ");

        String country
                = readString("Country: ");

        int staffID
                = readInt("Staff ID: ");

        LocalDate employmentDate
                = readDate("Employment Date (YYYY-MM-DD): ");

        double salary
                = readDouble("Salary: ");

        int departmentId
                = readInt("Department ID: ");

        String qualification
                = readString("Qualification: ");

        String licenseNumber
                = readString("License Number: ");

        Department department = new Department();

        department.setId(departmentId);

        LaboratoryTechnician technician
                = new LaboratoryTechnician(
                        firstName,
                        lastName,
                        gender,
                        dateOfBirth,
                        phone,
                        email,
                        street,
                        city,
                        country,
                        staffID,
                        employmentDate,
                        salary,
                        department,
                        qualification,
                        licenseNumber
                );

        LaboratoryTechnicianDAO dao
                = new LaboratoryTechnicianDAO();

        if (dao.addLaboratoryTechnician(technician)) {

            System.out.println();
            System.out.println(
                    "Laboratory Technician registered successfully.");

        } else {

            System.out.println();
            System.out.println(
                    "Failed to register Laboratory Technician.");
        }
    }

// ============================================================
// VIEW ALL LABORATORY TECHNICIANS
// ============================================================
// Retrieves all laboratory technicians from the database
// and displays their information.
// ============================================================
    private static void viewAllLaboratoryTechnicians() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("     ALL LABORATORY TECHNICIANS");
        System.out.println("========================================");

        LaboratoryTechnicianDAO dao
                = new LaboratoryTechnicianDAO();

        List<LaboratoryTechnician> technicians
                = dao.findAllLaboratoryTechnicians();

        if (technicians == null || technicians.isEmpty()) {

            System.out.println(
                    "No laboratory technicians found.");

            return;
        }

        for (LaboratoryTechnician technician : technicians) {

            System.out.println("----------------------------------------");

            System.out.println(
                    "Staff ID: "
                    + technician.getStaffId());

            System.out.println(
                    "Name: "
                    + technician.getFirstName()
                    + " "
                    + technician.getLastName());

            System.out.println(
                    "Gender: "
                    + technician.getGender());

            System.out.println(
                    "Date of Birth: "
                    + technician.getDateOfBirth());

            System.out.println(
                    "Phone: "
                    + technician.getPhone());

            System.out.println(
                    "Email: "
                    + technician.getEmail());

            System.out.println(
                    "Employment Date: "
                    + technician.getEmploymentDate());

            System.out.println(
                    "Salary: "
                    + technician.getSalary());

            System.out.println(
                    "Qualification: "
                    + technician.getQualification());

            System.out.println(
                    "License Number: "
                    + technician.getLicenseNumber());
        }

        System.out.println("----------------------------------------");
    }

// ============================================================
// FIND LABORATORY TECHNICIAN
// ============================================================
// Searches for a laboratory technician using the Staff ID
// and displays the technician's information.
// ============================================================
    private static void findLaboratoryTechnician() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("      FIND LABORATORY TECHNICIAN");
        System.out.println("========================================");

        int id
                = readInt("Enter Staff ID: ");

        LaboratoryTechnicianDAO dao
                = new LaboratoryTechnicianDAO();

        LaboratoryTechnician technician
                = dao.findLaboratoryTechnicianById(id);

        if (technician == null) {

            System.out.println(
                    "Laboratory Technician not found.");

            return;
        }

        System.out.println();
        System.out.println("----------------------------------------");

        System.out.println(
                "Staff ID: "
                + technician.getStaffId());

        System.out.println(
                "Name: "
                + technician.getFirstName()
                + " "
                + technician.getLastName());

        System.out.println(
                "Gender: "
                + technician.getGender());

        System.out.println(
                "Date of Birth: "
                + technician.getDateOfBirth());

        System.out.println(
                "Phone: "
                + technician.getPhone());

        System.out.println(
                "Email: "
                + technician.getEmail());

        System.out.println(
                "Employment Date: "
                + technician.getEmploymentDate());

        System.out.println(
                "Salary: "
                + technician.getSalary());

        System.out.println(
                "Qualification: "
                + technician.getQualification());

        System.out.println(
                "License Number: "
                + technician.getLicenseNumber());

        System.out.println("----------------------------------------");
    }

// ============================================================
// UPDATE LABORATORY TECHNICIAN
// ============================================================
// Finds an existing laboratory technician and allows the
// user to replace their information with updated values.
// ============================================================
    private static void updateLaboratoryTechnician() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("     UPDATE LABORATORY TECHNICIAN");
        System.out.println("========================================");

        int id
                = readInt("Enter Staff ID: ");

        LaboratoryTechnicianDAO dao
                = new LaboratoryTechnicianDAO();

        LaboratoryTechnician technician
                = dao.findLaboratoryTechnicianById(id);

        if (technician == null) {

            System.out.println(
                    "Laboratory Technician not found.");

            return;
        }

        System.out.println();
        System.out.println("Enter the new information.");

        technician.setFirstName(
                readString("First Name: "));

        technician.setLastName(
                readString("Last Name: "));

        technician.setGender(
                readString("Gender (M/F): ").charAt(0));

        technician.setDateOfBirth(
                readDate("Date of Birth (YYYY-MM-DD): "));

        technician.setPhone(
                readString("Phone: "));

        technician.setEmail(
                readString("Email: "));

        technician.setStreet(
                readString("Street: "));

        technician.setCity(
                readString("City: "));

        technician.setCountry(
                readString("Country: "));

        technician.setEmploymentDate(
                readDate("Employment Date (YYYY-MM-DD): "));

        technician.setSalary(
                readDouble("Salary: "));

        int departmentId
                = readInt("Department ID: ");

        Department department
                = new Department();

        department.setId(departmentId);

        technician.setDepartment(department);

        technician.setQualification(
                readString("Qualification: "));

        technician.setLicenseNumber(
                readString("License Number: "));

        if (dao.update(technician)) {

            System.out.println();
            System.out.println(
                    "Laboratory Technician updated successfully.");

        } else {

            System.out.println();
            System.out.println(
                    "Failed to update Laboratory Technician.");
        }
    }

// ============================================================
// DELETE LABORATORY TECHNICIAN
// ============================================================
// Finds a laboratory technician by Staff ID, asks for
// confirmation, and removes the technician from the database.
// ============================================================
    private static void deleteLaboratoryTechnician() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("     DELETE LABORATORY TECHNICIAN");
        System.out.println("========================================");

        int id
                = readInt("Enter Staff ID: ");

        LaboratoryTechnicianDAO dao
                = new LaboratoryTechnicianDAO();

        LaboratoryTechnician technician
                = dao.findLaboratoryTechnicianById(id);

        if (technician == null) {

            System.out.println(
                    "Laboratory Technician not found.");

            return;
        }

        System.out.println();

        System.out.println(
                "Technician: "
                + technician.getFirstName()
                + " "
                + technician.getLastName());

        String confirmation
                = readString("Delete this technician? (Y/N): ");

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Delete cancelled.");

            return;
        }

        if (dao.delete(id)) {

            System.out.println();
            System.out.println(
                    "Laboratory Technician deleted successfully.");

        } else {

            System.out.println();
            System.out.println(
                    "Failed to delete Laboratory Technician.");
        }
    }

    private static void viewAllStaffMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             VIEW ALL STAFF");
        System.out.println("========================================");

        String sql = """
        SELECT
            s.StaffId,
            p.FirstName,
            p.LastName,
            p.Gender,
            p.Phone,
            p.Email,
            s.EmploymentDate,
            s.Salary,
            d.DepartmentId,
            d.Name AS DepartmentName,

            CASE
                WHEN doc.StaffId IS NOT NULL
                    THEN 'Doctor'

                WHEN n.StaffId IS NOT NULL
                    THEN 'Nurse'

                WHEN ph.StaffId IS NOT NULL
                    THEN 'Pharmacist'

                WHEN lt.StaffId IS NOT NULL
                    THEN 'Laboratory Technician'

                ELSE 'Staff'
            END AS StaffRole

        FROM Staff s

        INNER JOIN Person p
            ON s.PersonId = p.PersonId

        INNER JOIN Department d
            ON s.DepartmentId = d.DepartmentId

        LEFT JOIN Doctor doc
            ON s.StaffId = doc.StaffId

        LEFT JOIN Nurse n
            ON s.StaffId = n.StaffId

        LEFT JOIN Pharmacist ph
            ON s.StaffId = ph.StaffId

        LEFT JOIN LaboratoryTechnician lt
            ON s.StaffId = lt.StaffId

        ORDER BY p.FirstName, p.LastName
        """;

        try (
                Connection connection
                = DatabaseConnection.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql); ResultSet resultSet
                = statement.executeQuery()) {

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                System.out.println();
                System.out.println("----------------------------------------");

                System.out.println(
                        "Staff ID:        "
                        + resultSet.getInt("StaffId")
                );

                System.out.println(
                        "Name:            "
                        + resultSet.getString("FirstName")
                        + " "
                        + resultSet.getString("LastName")
                );

                System.out.println(
                        "Gender:          "
                        + resultSet.getString("Gender")
                );

                System.out.println(
                        "Phone:           "
                        + resultSet.getString("Phone")
                );

                System.out.println(
                        "Email:           "
                        + resultSet.getString("Email")
                );

                System.out.println(
                        "Role:            "
                        + resultSet.getString("StaffRole")
                );

                System.out.println(
                        "Department ID:   "
                        + resultSet.getInt("DepartmentId")
                );

                System.out.println(
                        "Department:      "
                        + resultSet.getString("DepartmentName")
                );

                System.out.println(
                        "Employment Date: "
                        + resultSet.getDate("EmploymentDate")
                );

                System.out.println(
                        "Salary:          "
                        + resultSet.getDouble("Salary")
                );
            }

            if (!found) {

                System.out.println();
                System.out.println("No staff members found.");
            }

            System.out.println();
            System.out.println("----------------------------------------");

        } catch (SQLException e) {

            System.out.println();
            System.out.println("Error retrieving all staff.");

            System.err.println(
                    "Database error: "
                    + e.getMessage()
            );
        }
    }

    private static void findStaffMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             FIND STAFF");
        System.out.println("========================================");

        int staffId = readInt("Enter Staff ID: ");

        String sql = """
        SELECT
            s.StaffId,
            p.FirstName,
            p.LastName,
            p.Gender,
            p.Phone,
            p.Email,
            s.EmploymentDate,
            s.Salary,
            d.DepartmentId,
            d.Name AS DepartmentName,

            CASE
                WHEN doc.StaffId IS NOT NULL
                    THEN 'Doctor'

                WHEN n.StaffId IS NOT NULL
                    THEN 'Nurse'

                WHEN ph.StaffId IS NOT NULL
                    THEN 'Pharmacist'

                WHEN lt.StaffId IS NOT NULL
                    THEN 'Laboratory Technician'

                ELSE 'Staff'
            END AS StaffRole

        FROM Staff s

        INNER JOIN Person p
            ON s.PersonId = p.PersonId

        INNER JOIN Department d
            ON s.DepartmentId = d.DepartmentId

        LEFT JOIN Doctor doc
            ON s.StaffId = doc.StaffId

        LEFT JOIN Nurse n
            ON s.StaffId = n.StaffId

        LEFT JOIN Pharmacist ph
            ON s.StaffId = ph.StaffId

        LEFT JOIN LaboratoryTechnician lt
            ON s.StaffId = lt.StaffId

        WHERE s.StaffId = ?
        """;

        try (
                Connection connection
                = DatabaseConnection.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setInt(1, staffId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    System.out.println();
                    System.out.println("----------------------------------------");

                    System.out.println(
                            "Staff ID:        "
                            + resultSet.getInt("StaffId")
                    );

                    System.out.println(
                            "Name:            "
                            + resultSet.getString("FirstName")
                            + " "
                            + resultSet.getString("LastName")
                    );

                    System.out.println(
                            "Gender:          "
                            + resultSet.getString("Gender")
                    );

                    System.out.println(
                            "Phone:           "
                            + resultSet.getString("Phone")
                    );

                    System.out.println(
                            "Email:           "
                            + resultSet.getString("Email")
                    );

                    System.out.println(
                            "Role:            "
                            + resultSet.getString("StaffRole")
                    );

                    System.out.println(
                            "Department ID:   "
                            + resultSet.getInt("DepartmentId")
                    );

                    System.out.println(
                            "Department:      "
                            + resultSet.getString("DepartmentName")
                    );

                    System.out.println(
                            "Employment Date: "
                            + resultSet.getDate("EmploymentDate")
                    );

                    System.out.println(
                            "Salary:          "
                            + resultSet.getDouble("Salary")
                    );

                    System.out.println("----------------------------------------");

                } else {

                    System.out.println();
                    System.out.println(
                            "No staff member found with Staff ID: "
                            + staffId
                    );

                    System.out.println("----------------------------------------");
                }
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println("Error finding staff.");

            System.err.println(
                    "Database error: "
                    + e.getMessage()
            );
        }
    }

// ============================================================
// VIEW STAFF BY DEPARTMENT
// ============================================================
// Displays all staff members belonging to a selected department.
//
// Staff information is obtained from:
// - Person
// - Staff
// - Department
//
// The professional role is determined from:
// - Doctor
// - Nurse
// - Pharmacist
// - LaboratoryTechnician
// ============================================================
    private static void viewStaffByDepartmentMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       STAFF BY DEPARTMENT");
        System.out.println("========================================");

        int departmentId
                = readInt("Enter Department ID: ");

        String sql = """
            SELECT
                s.StaffId,
                p.FirstName,
                p.LastName,
                p.Gender,
                p.Phone,
                p.Email,
                s.EmploymentDate,
                s.Salary,
                d.DepartmentId,
                d.Name AS DepartmentName,

                CASE
                    WHEN doc.StaffId IS NOT NULL
                        THEN 'Doctor'

                    WHEN n.StaffId IS NOT NULL
                        THEN 'Nurse'

                    WHEN ph.StaffId IS NOT NULL
                        THEN 'Pharmacist'

                    WHEN lt.StaffId IS NOT NULL
                        THEN 'Laboratory Technician'

                    ELSE 'Staff'
                END AS StaffRole

            FROM Staff s

            INNER JOIN Person p
                ON s.PersonId = p.PersonId

            INNER JOIN Department d
                ON s.DepartmentId = d.DepartmentId

            LEFT JOIN Doctor doc
                ON s.StaffId = doc.StaffId

            LEFT JOIN Nurse n
                ON s.StaffId = n.StaffId

            LEFT JOIN Pharmacist ph
                ON s.StaffId = ph.StaffId

            LEFT JOIN LaboratoryTechnician lt
                ON s.StaffId = lt.StaffId

            WHERE s.DepartmentId = ?

            ORDER BY p.FirstName, p.LastName
            """;

        try (
                Connection connection
                = DatabaseConnection.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setInt(1, departmentId);

            try (ResultSet resultSet
                    = statement.executeQuery()) {

                boolean found = false;

                while (resultSet.next()) {

                    found = true;

                    System.out.println();
                    System.out.println("----------------------------------------");

                    System.out.println(
                            "Staff ID:        "
                            + resultSet.getInt("StaffId")
                    );

                    System.out.println(
                            "Name:            "
                            + resultSet.getString("FirstName")
                            + " "
                            + resultSet.getString("LastName")
                    );

                    System.out.println(
                            "Gender:          "
                            + resultSet.getString("Gender")
                    );

                    System.out.println(
                            "Phone:           "
                            + resultSet.getString("Phone")
                    );

                    System.out.println(
                            "Email:           "
                            + resultSet.getString("Email")
                    );

                    System.out.println(
                            "Role:            "
                            + resultSet.getString("StaffRole")
                    );

                    System.out.println(
                            "Department ID:   "
                            + resultSet.getInt("DepartmentId")
                    );

                    System.out.println(
                            "Department:      "
                            + resultSet.getString("DepartmentName")
                    );

                    System.out.println(
                            "Employment Date: "
                            + resultSet.getDate("EmploymentDate")
                    );

                    System.out.println(
                            "Salary:          "
                            + resultSet.getDouble("Salary")
                    );
                }

                if (!found) {

                    System.out.println();
                    System.out.println(
                            "No staff members found in this department."
                    );
                }

                System.out.println();
                System.out.println("----------------------------------------");
            }

        } catch (SQLException e) {

            System.out.println();
            System.out.println(
                    "Error retrieving staff by department."
            );

            System.err.println(
                    "Database error: "
                    + e.getMessage()
            );
        }
    }
// =========================================================
// TREATMENT MANAGEMENT MENU
// =========================================================

    private static void treatmentManagementMenu() {

        TreatmentDAO dao
                = new TreatmentDAO();

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("          TREATMENT MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Record Treatment");
            System.out.println("2. View All Treatments");
            System.out.println("3. Find Treatment");
            System.out.println("4. Update Treatment");
            System.out.println("5. Delete Treatment");
            System.out.println("6. View Patient Treatments");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice
                    = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addTreatment(dao);
                    break;

                case 2:
                    viewAllTreatments(dao);
                    break;

                case 3:
                    findTreatment(dao);
                    break;

                case 4:
                    updateTreatment(dao);
                    break;

                case 5:
                    deleteTreatment(dao);
                    break;

                case 6:
                    viewPatientTreatments(dao);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

// =========================================================
// ADD / RECORD TREATMENT
// =========================================================
    private static void addTreatment(TreatmentDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("           RECORD TREATMENT");
        System.out.println("========================================");

        int patientId
                = readInt("Enter Patient ID: ");

        int doctorStaffId
                = readInt("Enter Doctor Staff ID: ");

        String diagnosisInput
                = readString(
                        "Enter Diagnosis ID "
                        + "(leave blank if none): ");

        int diagnosisId = 0;

        if (!diagnosisInput.isEmpty()) {

            try {
                diagnosisId
                        = Integer.parseInt(diagnosisInput);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid Diagnosis ID.");

                return;
            }
        }

        LocalDate treatmentDate
                = readDate(
                        "Enter Treatment Date "
                        + "(YYYY-MM-DD): ");

        String treatmentName
                = readString(
                        "Enter Treatment Name: ");

        String description
                = readString(
                        "Enter Description: ");

        String notes
                = readString(
                        "Enter Notes: ");

        String status
                = readString(
                        "Enter Status: ");

        // -----------------------------------------------------
        // Create Patient reference
        // -----------------------------------------------------
        Patient patient
                = new Patient();

        patient.setPatientId(patientId);

        // -----------------------------------------------------
        // Create Doctor reference
        // -----------------------------------------------------
        Doctor doctor
                = new Doctor();

        doctor.setStaffId(doctorStaffId);

        // -----------------------------------------------------
        // Create Treatment
        // -----------------------------------------------------
        Treatment treatment
                = new Treatment();

        treatment.setPatient(patient);
        treatment.setDoctor(doctor);

        treatment.setTreatmentDate(
                treatmentDate);

        treatment.setTreatmentName(
                treatmentName);

        treatment.setDescription(
                description);

        treatment.setNotes(notes);

        treatment.setStatus(status);

        // -----------------------------------------------------
        // Optional Diagnosis
        // -----------------------------------------------------
        if (diagnosisId > 0) {

            Diagnosis diagnosis
                    = new Diagnosis();

            diagnosis.setId(diagnosisId);

            treatment.setDiagnosis(
                    diagnosis);
        }

        // -----------------------------------------------------
        // Save treatment
        // -----------------------------------------------------
        if (dao.addTreatment(treatment)) {

            System.out.println();
            System.out.println(
                    "Treatment recorded successfully.");

        } else {

            System.out.println();
            System.out.println(
                    "Failed to record treatment.");
        }
    }

// =========================================================
// VIEW ALL TREATMENTS
// =========================================================
    private static void viewAllTreatments(
            TreatmentDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("            ALL TREATMENTS");
        System.out.println("========================================");

        List<Treatment> treatments
                = dao.findAllTreatments();

        if (treatments.isEmpty()) {

            System.out.println(
                    "No treatments found.");

            return;
        }

        for (Treatment treatment : treatments) {

            System.out.println(
                    "----------------------------------------");

            System.out.println(
                    "Treatment ID: "
                    + treatment.getId());

            System.out.println(
                    "Patient ID: "
                    + treatment.getPatient()
                            .getPatientId());

            System.out.println(
                    "Doctor Staff ID: "
                    + treatment.getDoctor()
                            .getStaffId());

            if (treatment.getDiagnosis() != null) {

                System.out.println(
                        "Diagnosis ID: "
                        + treatment.getDiagnosis()
                                .getId());

            } else {

                System.out.println(
                        "Diagnosis ID: None");
            }

            System.out.println(
                    "Date: "
                    + treatment.getTreatmentDate());

            System.out.println(
                    "Treatment: "
                    + treatment.getTreatmentName());

            System.out.println(
                    "Description: "
                    + treatment.getDescription());

            System.out.println(
                    "Notes: "
                    + treatment.getNotes());

            System.out.println(
                    "Status: "
                    + treatment.getStatus());
        }

        System.out.println(
                "----------------------------------------");
    }

// =========================================================
// FIND TREATMENT
// =========================================================
    private static void findTreatment(
            TreatmentDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("            FIND TREATMENT");
        System.out.println("========================================");

        int id
                = readInt("Enter Treatment ID: ");

        Treatment treatment
                = dao.findTreatmentById(id);

        if (treatment == null) {

            System.out.println(
                    "Treatment not found.");

            return;
        }

        System.out.println(
                "----------------------------------------");

        System.out.println(
                "Treatment ID: "
                + treatment.getId());

        System.out.println(
                "Patient ID: "
                + treatment.getPatient()
                        .getPatientId());

        System.out.println(
                "Doctor Staff ID: "
                + treatment.getDoctor()
                        .getStaffId());

        if (treatment.getDiagnosis() != null) {

            System.out.println(
                    "Diagnosis ID: "
                    + treatment.getDiagnosis()
                            .getId());

        } else {

            System.out.println(
                    "Diagnosis ID: None");
        }

        System.out.println(
                "Date: "
                + treatment.getTreatmentDate());

        System.out.println(
                "Treatment: "
                + treatment.getTreatmentName());

        System.out.println(
                "Description: "
                + treatment.getDescription());

        System.out.println(
                "Notes: "
                + treatment.getNotes());

        System.out.println(
                "Status: "
                + treatment.getStatus());

        System.out.println(
                "----------------------------------------");
    }

// =========================================================
// UPDATE TREATMENT
// =========================================================
    private static void updateTreatment(
            TreatmentDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("           UPDATE TREATMENT");
        System.out.println("========================================");

        int treatmentId
                = readInt("Enter Treatment ID: ");

        Treatment existing
                = dao.findTreatmentById(
                        treatmentId);

        if (existing == null) {

            System.out.println(
                    "Treatment not found.");

            return;
        }

        int patientId
                = readInt("Enter Patient ID: ");

        int doctorStaffId
                = readInt("Enter Doctor Staff ID: ");

        String diagnosisInput
                = readString(
                        "Enter Diagnosis ID "
                        + "(leave blank if none): ");

        int diagnosisId = 0;

        if (!diagnosisInput.isEmpty()) {

            try {

                diagnosisId
                        = Integer.parseInt(
                                diagnosisInput);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid Diagnosis ID.");

                return;
            }
        }

        LocalDate date
                = readDate(
                        "Enter Treatment Date "
                        + "(YYYY-MM-DD): ");

        String name
                = readString(
                        "Enter Treatment Name: ");

        String description
                = readString(
                        "Enter Description: ");

        String notes
                = readString(
                        "Enter Notes: ");

        String status
                = readString(
                        "Enter Status: ");

        // -----------------------------------------------------
        // Update Patient
        // -----------------------------------------------------
        Patient patient
                = new Patient();

        patient.setPatientId(
                patientId);

        // -----------------------------------------------------
        // Update Doctor
        // -----------------------------------------------------
        Doctor doctor
                = new Doctor();

        doctor.setStaffId(
                doctorStaffId);

        existing.setPatient(patient);
        existing.setDoctor(doctor);

        existing.setTreatmentDate(date);
        existing.setTreatmentName(name);
        existing.setDescription(description);
        existing.setNotes(notes);
        existing.setStatus(status);

        // -----------------------------------------------------
        // Update Diagnosis
        // -----------------------------------------------------
        if (diagnosisId > 0) {

            Diagnosis diagnosis
                    = new Diagnosis();

            diagnosis.setId(
                    diagnosisId);

            existing.setDiagnosis(
                    diagnosis);

        } else {

            existing.setDiagnosis(null);
        }

        // -----------------------------------------------------
        // Save changes
        // -----------------------------------------------------
        if (dao.updateTreatment(existing)) {

            System.out.println();
            System.out.println(
                    "Treatment updated successfully.");

        } else {

            System.out.println();
            System.out.println(
                    "Failed to update treatment.");
        }
    }

// =========================================================
// DELETE TREATMENT
// =========================================================
    private static void deleteTreatment(
            TreatmentDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("           DELETE TREATMENT");
        System.out.println("========================================");

        int id
                = readInt("Enter Treatment ID: ");

        if (dao.deleteTreatment(id)) {

            System.out.println();
            System.out.println(
                    "Treatment deleted successfully.");

        } else {

            System.out.println();
            System.out.println(
                    "Treatment not found or "
                    + "could not be deleted.");
        }
    }

// =========================================================
// VIEW PATIENT TREATMENTS
// =========================================================
    private static void viewPatientTreatments(
            TreatmentDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("         PATIENT TREATMENTS");
        System.out.println("========================================");

        int patientId
                = readInt("Enter Patient ID: ");

        List<Treatment> treatments
                = dao.findTreatmentsByPatient(
                        patientId);

        if (treatments.isEmpty()) {

            System.out.println();
            System.out.println(
                    "No treatments found for "
                    + "this patient.");

            return;
        }

        for (Treatment treatment : treatments) {

            System.out.println(
                    "----------------------------------------");

            System.out.println(
                    "Treatment ID: "
                    + treatment.getId());

            System.out.println(
                    "Date: "
                    + treatment.getTreatmentDate());

            System.out.println(
                    "Doctor Staff ID: "
                    + treatment.getDoctor()
                            .getStaffId());

            System.out.println(
                    "Treatment: "
                    + treatment.getTreatmentName());

            if (treatment.getDiagnosis() != null) {

                System.out.println(
                        "Diagnosis ID: "
                        + treatment.getDiagnosis()
                                .getId());
            }

            System.out.println(
                    "Description: "
                    + treatment.getDescription());

            System.out.println(
                    "Notes: "
                    + treatment.getNotes());

            System.out.println(
                    "Status: "
                    + treatment.getStatus());
        }

        System.out.println(
                "----------------------------------------");
    }

    private static void staffAccountManagementMenu() {
        showModuleStatus("STAFF ACCOUNT MANAGEMENT",
                "Staff accounts will be handled separately from professional records.",
                "The Users table can later be connected here for login and role management.");
    }

    // =========================================================
    // ADMISSION & BED MANAGEMENT
    // =========================================================
    private static void admissionBedMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("      ADMISSION & BED MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Ward Management");
            System.out.println("2. Room Management");
            System.out.println("3. Bed Management");
            System.out.println("4. Admission Management");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    wardManagementMenu();
                    break;

                case 2:
                    roomManagementMenu();
                    break;

                case 3:
                    bedManagementMenu();
                    break;

                case 4:
                    admissionManagementMenu();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // WARD MANAGEMENT MENU
    // =========================================================
    private static void wardManagementMenu() {

        WardDAO wardDAO = new WardDAO();

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("           WARD MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Ward");
            System.out.println("2. View All Wards");
            System.out.println("3. Find Ward");
            System.out.println("4. Update Ward");
            System.out.println("5. Delete Ward");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addWard(wardDAO);
                    break;

                case 2:
                    viewAllWards(wardDAO);
                    break;

                case 3:
                    findWard(wardDAO);
                    break;

                case 4:
                    updateWard(wardDAO);
                    break;

                case 5:
                    deleteWard(wardDAO);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
// ============================================================
// ADD WARD
// Collects ward information and saves it to the database
// ============================================================

    private static void addWard(WardDAO wardDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              ADD WARD");
        System.out.println("========================================");

        // Collect information from the user
        String name = readString("Ward name: ");
        String wardType = readString("Ward type: ");
        int capacity = readInt("Capacity: ");

        // Create a new Ward object
        Ward ward = new Ward();

        ward.setName(name);
        ward.setWardType(wardType);
        ward.setCapacity(capacity);

        // Save the ward through the DAO
        if (wardDAO.addWard(ward)) {

            System.out.println();
            System.out.println("Ward added successfully.");
            System.out.println("Ward ID: " + ward.getId());

        } else {

            System.out.println();
            System.out.println("Failed to add ward.");
        }
    }

// ============================================================
// VIEW ALL WARDS
// Retrieves and displays every ward in the database
// ============================================================
    private static void viewAllWards(WardDAO wardDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             ALL WARDS");
        System.out.println("========================================");

        // Get all wards from the database
        List<Ward> wards = wardDAO.findAllWards();

        // Check whether any wards exist
        if (wards.isEmpty()) {

            System.out.println("No wards found.");
            return;
        }

        // Display each ward
        for (Ward ward : wards) {

            System.out.println("----------------------------------------");
            System.out.println("Ward ID:     " + ward.getId());
            System.out.println("Name:        " + ward.getName());
            System.out.println("Type:        " + ward.getWardType());
            System.out.println("Capacity:    " + ward.getCapacity());
        }

        System.out.println("----------------------------------------");
    }

// ============================================================
// FIND WARD
// Searches for a ward using its Ward ID
// ============================================================
    private static void findWard(WardDAO wardDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              FIND WARD");
        System.out.println("========================================");

        // Ask the user for the Ward ID
        int wardId = readInt("Enter Ward ID: ");

        // Search the database
        Ward ward = wardDAO.findWardById(wardId);

        // Check whether the ward exists
        if (ward == null) {

            System.out.println("Ward not found.");
            return;
        }

        // Display the ward information
        System.out.println("----------------------------------------");
        System.out.println("Ward ID:     " + ward.getId());
        System.out.println("Name:        " + ward.getName());
        System.out.println("Type:        " + ward.getWardType());
        System.out.println("Capacity:    " + ward.getCapacity());
        System.out.println("----------------------------------------");
    }

// ============================================================
// UPDATE WARD
// Finds an existing ward and changes its information
// ============================================================
    private static void updateWard(WardDAO wardDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             UPDATE WARD");
        System.out.println("========================================");

        // Ask for the Ward ID
        int wardId = readInt("Enter Ward ID: ");

        // Find the existing ward
        Ward ward = wardDAO.findWardById(wardId);

        // Check whether the ward exists
        if (ward == null) {

            System.out.println("Ward not found.");
            return;
        }

        System.out.println();
        System.out.println("Current ward information:");
        System.out.println("Name:     " + ward.getName());
        System.out.println("Type:     " + ward.getWardType());
        System.out.println("Capacity: " + ward.getCapacity());

        System.out.println();

        // Get the new information
        String name = readString("New ward name: ");
        String wardType = readString("New ward type: ");
        int capacity = readInt("New capacity: ");

        // Update the Ward object
        ward.setName(name);
        ward.setWardType(wardType);
        ward.setCapacity(capacity);

        // Save the changes to the database
        if (wardDAO.updateWard(ward)) {

            System.out.println();
            System.out.println("Ward updated successfully.");

        } else {

            System.out.println();
            System.out.println("Failed to update ward.");
        }
    }

// ============================================================
// DELETE WARD
// Deletes a ward from the database after confirmation
// ============================================================
    private static void deleteWard(WardDAO wardDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             DELETE WARD");
        System.out.println("========================================");

        // Ask for the Ward ID
        int wardId = readInt("Enter Ward ID: ");

        // Find the ward before deleting it
        Ward ward = wardDAO.findWardById(wardId);

        // Check whether the ward exists
        if (ward == null) {

            System.out.println("Ward not found.");
            return;
        }

        // Display the ward that will be deleted
        System.out.println();
        System.out.println("Ward selected:");
        System.out.println("ID:   " + ward.getId());
        System.out.println("Name: " + ward.getName());

        // Ask for confirmation
        String confirmation
                = readString("Are you sure you want to delete this ward? (yes/no): ");

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println("Delete cancelled.");
            return;
        }

        // Delete the ward
        if (wardDAO.deleteWard(wardId)) {

            System.out.println();
            System.out.println("Ward deleted successfully.");

        } else {

            System.out.println();
            System.out.println("Failed to delete ward.");
            System.out.println("Make sure the ward does not contain any rooms.");
        }
    }

    // ============================================================
    // ROOM MANAGEMENT MENU
    // ============================================================
    private static void roomManagementMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("           ROOM MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. Find Room");
            System.out.println("4. View Rooms by Ward");
            System.out.println("5. Update Room");
            System.out.println("6. Delete Room");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewAllRooms();
                    break;
                case 3:
                    findRoom();
                    break;
                case 4:
                    viewRoomsByWard();
                    break;
                case 5:
                    updateRoom();
                    break;
                case 6:
                    deleteRoom();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // ============================================================
    // ADD ROOM
    // ============================================================
    private static void addRoom() {

        RoomDAO roomDAO = new RoomDAO();
        WardDAO wardDAO = new WardDAO();

        System.out.println();
        System.out.println("========================================");
        System.out.println("              ADD ROOM");
        System.out.println("========================================");

        int wardId = readInt("Enter Ward ID: ");
        Ward ward = wardDAO.findWardById(wardId);

        if (ward == null) {
            System.out.println("Ward not found.");
            return;
        }

        String roomNumber = readString("Room number: ");
        String roomType = readString("Room type: ");
        int capacity = readInt("Room capacity: ");

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setWard(ward);
        room.setRoomType(roomType);
        room.setCapacity(capacity);

        if (roomDAO.addRoom(room)) {
            System.out.println();
            System.out.println("Room added successfully.");
            System.out.println("Room ID: " + room.getId());
            System.out.println("Ward: " + ward.getName());
        } else {
            System.out.println("Failed to add room.");
        }
    }

    // ============================================================
    // VIEW ALL ROOMS
    // ============================================================
    private static void viewAllRooms() {

        RoomDAO roomDAO = new RoomDAO();
        List<Room> rooms = roomDAO.findAllRooms();

        System.out.println();
        System.out.println("========================================");
        System.out.println("             ALL ROOMS");
        System.out.println("========================================");

        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }

        for (Room room : rooms) {
            System.out.println("----------------------------------------");
            System.out.println("Room ID:     " + room.getId());
            System.out.println("Room Number: " + room.getRoomNumber());
            System.out.println("Room Type:   " + room.getRoomType());
            System.out.println("Capacity:    " + room.getCapacity());

            if (room.getWard() != null) {
                System.out.println("Ward ID:     " + room.getWard().getId());
                System.out.println("Ward:        " + room.getWard().getName());
            }
        }

        System.out.println("----------------------------------------");
    }

    // ============================================================
    // FIND ROOM
    // ============================================================
    private static void findRoom() {

        RoomDAO roomDAO = new RoomDAO();
        int roomId = readInt("Enter Room ID: ");
        Room room = roomDAO.findRoomById(roomId);

        System.out.println();
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.println("========================================");
        System.out.println("              ROOM DETAILS");
        System.out.println("========================================");
        System.out.println("Room ID:     " + room.getId());
        System.out.println("Room Number: " + room.getRoomNumber());
        System.out.println("Room Type:   " + room.getRoomType());
        System.out.println("Capacity:    " + room.getCapacity());

        if (room.getWard() != null) {
            System.out.println("Ward ID:     " + room.getWard().getId());
            System.out.println("Ward:        " + room.getWard().getName());
        }

        System.out.println("========================================");
    }

    // ============================================================
    // VIEW ROOMS BY WARD
    // ============================================================
    private static void viewRoomsByWard() {

        RoomDAO roomDAO = new RoomDAO();
        WardDAO wardDAO = new WardDAO();

        int wardId = readInt("Enter Ward ID: ");
        Ward ward = wardDAO.findWardById(wardId);

        if (ward == null) {
            System.out.println("Ward not found.");
            return;
        }

        List<Room> rooms = roomDAO.findRoomsByWard(wardId);

        System.out.println();
        System.out.println("Rooms in ward: " + ward.getName());

        if (rooms.isEmpty()) {
            System.out.println("No rooms found in this ward.");
            return;
        }

        for (Room room : rooms) {
            System.out.println("----------------------------------------");
            System.out.println("Room ID:     " + room.getId());
            System.out.println("Room Number: " + room.getRoomNumber());
            System.out.println("Room Type:   " + room.getRoomType());
            System.out.println("Capacity:    " + room.getCapacity());
        }
    }

    // ============================================================
    // UPDATE ROOM
    // ============================================================
    private static void updateRoom() {

        RoomDAO roomDAO = new RoomDAO();
        WardDAO wardDAO = new WardDAO();

        int roomId = readInt("Enter Room ID to update: ");
        Room room = roomDAO.findRoomById(roomId);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        int wardId = readInt("New Ward ID: ");
        Ward ward = wardDAO.findWardById(wardId);

        if (ward == null) {
            System.out.println("Ward not found.");
            return;
        }

        String roomNumber = readString("New room number: ");
        String roomType = readString("New room type: ");
        int capacity = readInt("New capacity: ");

        room.setRoomNumber(roomNumber);
        room.setWard(ward);
        room.setRoomType(roomType);
        room.setCapacity(capacity);

        if (roomDAO.updateRoom(room)) {
            System.out.println("Room updated successfully.");
        } else {
            System.out.println("Failed to update room.");
        }
    }

    // ============================================================
    // DELETE ROOM
    // ============================================================
    private static void deleteRoom() {

        RoomDAO roomDAO = new RoomDAO();

        int roomId = readInt("Enter Room ID to delete: ");
        Room room = roomDAO.findRoomById(roomId);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.println("Room: " + room.getRoomNumber());
        String confirmation = readString("Are you sure? (yes/no): ");

        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (roomDAO.deleteRoom(roomId)) {
            System.out.println("Room deleted successfully.");
        } else {
            System.out.println("Failed to delete room. Make sure the room has no beds.");
        }
    }

    // ============================================================
    // BED MANAGEMENT MENU
    // ============================================================
    private static void bedManagementMenu() {

        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("            BED MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Bed");
            System.out.println("2. View All Beds");
            System.out.println("3. Find Bed");
            System.out.println("4. View Beds by Room");
            System.out.println("5. View Available Beds");
            System.out.println("6. View Occupied Beds");
            System.out.println("7. Update Bed");
            System.out.println("8. Update Bed Occupancy");
            System.out.println("9. Delete Bed");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addBed();
                    break;
                case 2:
                    viewAllBeds();
                    break;
                case 3:
                    findBed();
                    break;
                case 4:
                    viewBedsByRoom();
                    break;
                case 5:
                    viewAvailableBeds();
                    break;
                case 6:
                    viewOccupiedBeds();
                    break;
                case 7:
                    updateBed();
                    break;
                case 8:
                    updateBedOccupancy();
                    break;
                case 9:
                    deleteBed();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ============================================================
    // ADD BED
    // ============================================================
    private static void addBed() {

        BedDAO bedDAO = new BedDAO();
        RoomDAO roomDAO = new RoomDAO();

        System.out.println();
        System.out.println("========================================");
        System.out.println("              ADD BED");
        System.out.println("========================================");

        int roomId = readInt("Enter Room ID: ");
        Room room = roomDAO.findRoomById(roomId);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        String bedNumber = readString("Bed number: ");

        Bed bed = new Bed();
        bed.setBedNumber(bedNumber);
        bed.setRoom(room);
        bed.setOccupied(false);

        if (bedDAO.addBed(bed)) {
            System.out.println();
            System.out.println("Bed added successfully.");
            System.out.println("Bed ID:     " + bed.getId());
            System.out.println("Bed Number: " + bed.getBedNumber());
            System.out.println("Room:       " + room.getRoomNumber());
            System.out.println("Status:     AVAILABLE");
        } else {
            System.out.println("Failed to add bed.");
        }
    }

    // ============================================================
    // VIEW ALL BEDS
    // ============================================================
    private static void viewAllBeds() {

        BedDAO bedDAO = new BedDAO();
        List<Bed> beds = bedDAO.findAllBeds();

        System.out.println();
        System.out.println("========================================");
        System.out.println("              ALL BEDS");
        System.out.println("========================================");

        if (beds.isEmpty()) {
            System.out.println("No beds found.");
            return;
        }

        for (Bed bed : beds) {
            System.out.println("----------------------------------------");
            System.out.println("Bed ID:     " + bed.getId());
            System.out.println("Bed Number: " + bed.getBedNumber());

            if (bed.getRoom() != null) {
                System.out.println("Room ID:    " + bed.getRoom().getId());
                System.out.println("Room:       " + bed.getRoom().getRoomNumber());
            }

            System.out.println("Status:     "
                    + (bed.isOccupied() ? "OCCUPIED" : "AVAILABLE"));
        }

        System.out.println("----------------------------------------");
    }

    // ============================================================
    // FIND BED
    // ============================================================
    private static void findBed() {

        BedDAO bedDAO = new BedDAO();
        int bedId = readInt("Enter Bed ID: ");
        Bed bed = bedDAO.findBedById(bedId);

        System.out.println();
        if (bed == null) {
            System.out.println("Bed not found.");
            return;
        }

        System.out.println("========================================");
        System.out.println("              BED DETAILS");
        System.out.println("========================================");
        System.out.println("Bed ID:     " + bed.getId());
        System.out.println("Bed Number: " + bed.getBedNumber());

        if (bed.getRoom() != null) {
            System.out.println("Room ID:    " + bed.getRoom().getId());
            System.out.println("Room:       " + bed.getRoom().getRoomNumber());
        }

        System.out.println("Status:     "
                + (bed.isOccupied() ? "OCCUPIED" : "AVAILABLE"));
        System.out.println("========================================");
    }

    // ============================================================
    // VIEW BEDS BY ROOM
    // ============================================================
    private static void viewBedsByRoom() {

        BedDAO bedDAO = new BedDAO();
        RoomDAO roomDAO = new RoomDAO();

        int roomId = readInt("Enter Room ID: ");
        Room room = roomDAO.findRoomById(roomId);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        List<Bed> beds = bedDAO.findBedsByRoom(roomId);

        System.out.println();
        System.out.println("Beds in room: " + room.getRoomNumber());

        if (beds.isEmpty()) {
            System.out.println("No beds found in this room.");
            return;
        }

        for (Bed bed : beds) {
            System.out.println("----------------------------------------");
            System.out.println("Bed ID:     " + bed.getId());
            System.out.println("Bed Number: " + bed.getBedNumber());
            System.out.println("Status:     "
                    + (bed.isOccupied() ? "OCCUPIED" : "AVAILABLE"));
        }
    }

    // ============================================================
    // VIEW AVAILABLE BEDS
    // ============================================================
    private static void viewAvailableBeds() {

        BedDAO bedDAO = new BedDAO();
        List<Bed> beds = bedDAO.findAvailableBeds();

        System.out.println();
        System.out.println("========================================");
        System.out.println("           AVAILABLE BEDS");
        System.out.println("========================================");

        if (beds.isEmpty()) {
            System.out.println("No available beds found.");
            return;
        }

        for (Bed bed : beds) {
            System.out.println("----------------------------------------");
            System.out.println("Bed ID:     " + bed.getId());
            System.out.println("Bed Number: " + bed.getBedNumber());
            if (bed.getRoom() != null) {
                System.out.println("Room:       " + bed.getRoom().getRoomNumber());
            }
            System.out.println("Status:     AVAILABLE");
        }
    }

    // ============================================================
    // VIEW OCCUPIED BEDS
    // ============================================================
    private static void viewOccupiedBeds() {

        BedDAO bedDAO = new BedDAO();
        List<Bed> beds = bedDAO.findOccupiedBeds();

        System.out.println();
        System.out.println("========================================");
        System.out.println("            OCCUPIED BEDS");
        System.out.println("========================================");

        if (beds.isEmpty()) {
            System.out.println("No occupied beds found.");
            return;
        }

        for (Bed bed : beds) {
            System.out.println("----------------------------------------");
            System.out.println("Bed ID:     " + bed.getId());
            System.out.println("Bed Number: " + bed.getBedNumber());
            if (bed.getRoom() != null) {
                System.out.println("Room:       " + bed.getRoom().getRoomNumber());
            }
            System.out.println("Status:     OCCUPIED");
        }
    }

    // ============================================================
    // UPDATE BED
    // ============================================================
    private static void updateBed() {

        BedDAO bedDAO = new BedDAO();
        RoomDAO roomDAO = new RoomDAO();

        int bedId = readInt("Enter Bed ID to update: ");
        Bed bed = bedDAO.findBedById(bedId);

        if (bed == null) {
            System.out.println("Bed not found.");
            return;
        }

        String bedNumber = readString("New bed number: ");
        int roomId = readInt("New Room ID: ");
        Room room = roomDAO.findRoomById(roomId);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        bed.setBedNumber(bedNumber);
        bed.setRoom(room);

        if (bedDAO.updateBed(bed)) {
            System.out.println("Bed updated successfully.");
        } else {
            System.out.println("Failed to update bed.");
        }
    }

    // ============================================================
    // UPDATE BED OCCUPANCY
    // ============================================================
    private static void updateBedOccupancy() {

        BedDAO bedDAO = new BedDAO();

        int bedId = readInt("Enter Bed ID: ");
        Bed bed = bedDAO.findBedById(bedId);

        if (bed == null) {
            System.out.println("Bed not found.");
            return;
        }

        String status = readString("Set status (occupied/available): ");
        boolean occupied;

        if (status.equalsIgnoreCase("occupied")) {
            occupied = true;
        } else if (status.equalsIgnoreCase("available")) {
            occupied = false;
        } else {
            System.out.println("Invalid status.");
            return;
        }

        if (bedDAO.updateBedOccupancy(bedId, occupied)) {
            System.out.println("Bed occupancy updated successfully.");
        } else {
            System.out.println("Failed to update bed occupancy.");
        }
    }

    // ============================================================
    // DELETE BED
    // ============================================================
    private static void deleteBed() {

        BedDAO bedDAO = new BedDAO();

        int bedId = readInt("Enter Bed ID to delete: ");
        Bed bed = bedDAO.findBedById(bedId);

        if (bed == null) {
            System.out.println("Bed not found.");
            return;
        }

        System.out.println("Bed: " + bed.getBedNumber());
        String confirmation = readString("Are you sure? (yes/no): ");

        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (bedDAO.deleteBed(bedId)) {
            System.out.println("Bed deleted successfully.");
        } else {
            System.out.println("Failed to delete bed.");
        }
    }

    // ============================================================
    // READ STRING
    // ============================================================
    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // ============================================================
    // ADMISSION MANAGEMENT MENU
    // ============================================================
    private static void admissionManagementMenu() {

        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("        ADMISSION MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Admit Patient");
            System.out.println("2. View All Admissions");
            System.out.println("3. Find Admission");
            System.out.println("4. Update Admission");
            System.out.println("5. Discharge Patient");
            System.out.println("6. View Patient Admissions");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addAdmission();
                    break;
                case 2:
                    viewAllAdmissions();
                    break;
                case 3:
                    findAdmission();
                    break;
                case 4:
                    updateAdmission();
                    break;
                case 5:
                    dischargePatient();
                    break;
                case 6:
                    viewPatientAdmissions();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ============================================================
    // ADD ADMISSION
    // Creates an admission and marks the selected bed as occupied
    // ============================================================
    private static void addAdmission() {

        int patientId = readInt("Enter Patient ID: ");
        Patient patient = patientService.getPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        int bedId = readInt("Enter available Bed ID: ");
        BedDAO bedDAO = new BedDAO();
        Bed bed = bedDAO.findBedById(bedId);

        if (bed == null) {
            System.out.println("Bed not found.");
            return;
        }

        if (bed.isOccupied()) {
            System.out.println("That bed is already occupied.");
            return;
        }

        String dateInput = readString("Admission Date (yyyy-MM-dd): ");
        LocalDate admissionDate;

        try {
            admissionDate = LocalDate.parse(dateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        String reason = readString("Reason for admission: ");
        String notes = readString("Notes: ");

        String sql = """
            INSERT INTO Admission
                (PatientId, AdmissionDate, BedNumber, Reason, Status, Notes)
            VALUES
                (?, ?, ?, ?, 'Admitted', ?)
            """;

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, patientId);
                statement.setTimestamp(2,
                        Timestamp.valueOf(admissionDate.atStartOfDay()));
                statement.setString(3, bed.getBedNumber());
                statement.setString(4, reason);
                statement.setString(5, notes);

                statement.executeUpdate();
            }

            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Bed SET Occupied = 1 WHERE BedId = ?")) {
                statement.setInt(1, bedId);
                statement.executeUpdate();
            }

            connection.commit();

            System.out.println();
            System.out.println("Patient admitted successfully.");
            System.out.println("Patient: "
                    + patient.getFirstName() + " " + patient.getLastName());
            System.out.println("Bed: " + bed.getBedNumber());

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    System.out.println("Rollback failed: "
                            + rollbackException.getMessage());
                }
            }

            System.out.println("Failed to admit patient: " + e.getMessage());

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing database connection: "
                            + e.getMessage());
                }
            }
        }
    }

    // ============================================================
    // VIEW ALL ADMISSIONS
    // ============================================================
    private static void viewAllAdmissions() {

        String sql = """
            SELECT
                a.AdmissionId,
                a.PatientId,
                a.AdmissionDate,
                a.DischargeDate,
                a.BedNumber,
                a.Reason,
                a.Status,
                a.Notes,
                p.FirstName,
                p.LastName
            FROM Admission a
            INNER JOIN Patient pt ON a.PatientId = pt.PatientId
            INNER JOIN Person p ON pt.PersonId = p.PersonId
            ORDER BY a.AdmissionId
            """;

        System.out.println();
        System.out.println("========================================");
        System.out.println("          ALL ADMISSIONS");
        System.out.println("========================================");

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            boolean found = false;

            while (resultSet.next()) {
                found = true;
                printAdmissionResult(resultSet);
            }

            if (!found) {
                System.out.println("No admissions found.");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving admissions: " + e.getMessage());
        }
    }

    // ============================================================
    // FIND ADMISSION
    // ============================================================
    private static void findAdmission() {

        int admissionId = readInt("Enter Admission ID: ");

        String sql = """
            SELECT
                a.AdmissionId,
                a.PatientId,
                a.AdmissionDate,
                a.DischargeDate,
                a.BedNumber,
                a.Reason,
                a.Status,
                a.Notes,
                p.FirstName,
                p.LastName
            FROM Admission a
            INNER JOIN Patient pt ON a.PatientId = pt.PatientId
            INNER JOIN Person p ON pt.PersonId = p.PersonId
            WHERE a.AdmissionId = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, admissionId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println();
                    System.out.println("========================================");
                    System.out.println("          ADMISSION DETAILS");
                    System.out.println("========================================");
                    printAdmissionResult(resultSet);
                } else {
                    System.out.println("Admission not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding admission: " + e.getMessage());
        }
    }

    // ============================================================
    // UPDATE ADMISSION
    // ============================================================
    private static void updateAdmission() {

        int admissionId = readInt("Enter Admission ID to update: ");

        String checkSql = """
            SELECT PatientId, BedNumber, AdmissionDate, Reason, Status, Notes
            FROM Admission
            WHERE AdmissionId = ?
            """;

        String newDateInput = readString("New Admission Date (yyyy-MM-dd): ");
        LocalDate newDate;

        try {
            newDate = LocalDate.parse(newDateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        String newBedNumber = readString("New Bed Number: ");
        String reason = readString("New reason: ");
        String status = readString("New status: ");
        String notes = readString("New notes: ");

        String oldBedNumber;
        int patientId;

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(checkSql)) {

            statement.setInt(1, admissionId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    System.out.println("Admission not found.");
                    return;
                }

                patientId = resultSet.getInt("PatientId");
                oldBedNumber = resultSet.getString("BedNumber");
            }

        } catch (SQLException e) {
            System.out.println("Error checking admission: " + e.getMessage());
            return;
        }

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            if (!newBedNumber.equalsIgnoreCase(oldBedNumber)) {

                try (PreparedStatement statement = connection.prepareStatement(
                        "SELECT BedId, Occupied FROM Bed WHERE BedNumber = ?")) {

                    statement.setString(1, newBedNumber);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (!resultSet.next()) {
                            System.out.println("New bed not found.");
                            connection.rollback();
                            return;
                        }

                        if (resultSet.getBoolean("Occupied")) {
                            System.out.println("New bed is already occupied.");
                            connection.rollback();
                            return;
                        }
                    }
                }

                try (PreparedStatement statement = connection.prepareStatement(
                        "UPDATE Bed SET Occupied = 0 WHERE BedNumber = ?")) {
                    statement.setString(1, oldBedNumber);
                    statement.executeUpdate();
                }

                try (PreparedStatement statement = connection.prepareStatement(
                        "UPDATE Bed SET Occupied = 1 WHERE BedNumber = ?")) {
                    statement.setString(1, newBedNumber);
                    statement.executeUpdate();
                }
            }

            String updateSql = """
                UPDATE Admission
                SET AdmissionDate = ?,
                    BedNumber = ?,
                    Reason = ?,
                    Status = ?,
                    Notes = ?
                WHERE AdmissionId = ?
                """;

            try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
                statement.setTimestamp(1,
                        Timestamp.valueOf(newDate.atStartOfDay()));
                statement.setString(2, newBedNumber);
                statement.setString(3, reason);
                statement.setString(4, status);
                statement.setString(5, notes);
                statement.setInt(6, admissionId);
                statement.executeUpdate();
            }

            connection.commit();
            System.out.println("Admission updated successfully.");

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    System.out.println("Rollback failed: "
                            + rollbackException.getMessage());
                }
            }

            System.out.println("Failed to update admission: " + e.getMessage());

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: "
                            + e.getMessage());
                }
            }
        }
    }

    // ============================================================
    // DISCHARGE PATIENT
    // Updates the admission and releases its bed
    // ============================================================
    private static void dischargePatient() {

        int admissionId = readInt("Enter Admission ID to discharge: ");

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            String bedNumber = null;

            String findSql = """
                SELECT BedNumber, Status
                FROM Admission
                WHERE AdmissionId = ?
                """;

            try (PreparedStatement statement = connection.prepareStatement(findSql)) {
                statement.setInt(1, admissionId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        System.out.println("Admission not found.");
                        connection.rollback();
                        return;
                    }

                    bedNumber = resultSet.getString("BedNumber");

                    if ("Discharged".equalsIgnoreCase(
                            resultSet.getString("Status"))) {
                        System.out.println("Patient is already discharged.");
                        connection.rollback();
                        return;
                    }
                }
            }

            String updateAdmissionSql = """
                UPDATE Admission
                SET DischargeDate = ?,
                    Status = 'Discharged'
                WHERE AdmissionId = ?
                """;

            try (PreparedStatement statement = connection.prepareStatement(
                    updateAdmissionSql)) {
                statement.setTimestamp(1, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
                statement.setInt(2, admissionId);
                statement.executeUpdate();
            }

            if (bedNumber != null && !bedNumber.isBlank()) {
                try (PreparedStatement statement = connection.prepareStatement(
                        "UPDATE Bed SET Occupied = 0 WHERE BedNumber = ?")) {
                    statement.setString(1, bedNumber);
                    statement.executeUpdate();
                }
            }

            connection.commit();
            System.out.println("Patient discharged successfully.");

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    System.out.println("Rollback failed: "
                            + rollbackException.getMessage());
                }
            }

            System.out.println("Failed to discharge patient: " + e.getMessage());

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: "
                            + e.getMessage());
                }
            }
        }
    }

    // ============================================================
    // VIEW PATIENT ADMISSIONS
    // ============================================================
    private static void viewPatientAdmissions() {

        int patientId = readInt("Enter Patient ID: ");
        Patient patient = patientService.getPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        String sql = """
            SELECT
                a.AdmissionId,
                a.PatientId,
                a.AdmissionDate,
                a.DischargeDate,
                a.BedNumber,
                a.Reason,
                a.Status,
                a.Notes,
                p.FirstName,
                p.LastName
            FROM Admission a
            INNER JOIN Patient pt ON a.PatientId = pt.PatientId
            INNER JOIN Person p ON pt.PersonId = p.PersonId
            WHERE a.PatientId = ?
            ORDER BY a.AdmissionId
            """;

        System.out.println();
        System.out.println("Admissions for "
                + patient.getFirstName() + " " + patient.getLastName());

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                boolean found = false;

                while (resultSet.next()) {
                    found = true;
                    printAdmissionResult(resultSet);
                }

                if (!found) {
                    System.out.println("No admissions found for this patient.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving patient admissions: "
                    + e.getMessage());
        }
    }

    // ============================================================
    // PRINT ADMISSION RESULT
    // Helper method used by admission search/list operations
    // ============================================================
    private static void printAdmissionResult(ResultSet resultSet)
            throws SQLException {

        System.out.println("----------------------------------------");
        System.out.println("Admission ID:  " + resultSet.getInt("AdmissionId"));
        System.out.println("Patient ID:    " + resultSet.getInt("PatientId"));
        System.out.println("Patient:       "
                + resultSet.getString("FirstName") + " "
                + resultSet.getString("LastName"));
        System.out.println("Admission Date: "
                + resultSet.getTimestamp("AdmissionDate"));
        System.out.println("Discharge Date: "
                + resultSet.getTimestamp("DischargeDate"));
        System.out.println("Bed Number:    " + resultSet.getString("BedNumber"));
        System.out.println("Reason:        " + resultSet.getString("Reason"));
        System.out.println("Status:        " + resultSet.getString("Status"));
        System.out.println("Notes:         " + resultSet.getString("Notes"));
    }

    // =========================================================
    // LABORATORY MENU
    // =========================================================
    // =========================================================
// LABORATORY MENU
// =========================================================
    private static void laboratoryMenu() {

        LaboratoryTestDAO dao = new LaboratoryTestDAO();

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("          LABORATORY SERVICES");
            System.out.println("========================================");
            System.out.println("1. Create Laboratory Test");
            System.out.println("2. View All Laboratory Tests");
            System.out.println("3. Find Laboratory Test");
            System.out.println("4. View Patient Laboratory Tests");
            System.out.println("5. View Technician Laboratory Tests");
            System.out.println("6. Update Laboratory Test");
            System.out.println("7. Delete Laboratory Test");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addLaboratoryTest(dao);
                    break;

                case 2:
                    viewAllLaboratoryTests(dao);
                    break;

                case 3:
                    findLaboratoryTest(dao);
                    break;

                case 4:
                    viewPatientLaboratoryTests(dao);
                    break;

                case 5:
                    viewTechnicianLaboratoryTests(dao);
                    break;

                case 6:
                    updateLaboratoryTest(dao);
                    break;

                case 7:
                    deleteLaboratoryTest(dao);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

// =========================================================
// ADD LABORATORY TEST
// =========================================================
    private static void addLaboratoryTest(LaboratoryTestDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       CREATE LABORATORY TEST");
        System.out.println("========================================");

        int patientId = readInt("Enter Patient ID: ");
        int technicianStaffId = readInt("Enter Laboratory Technician Staff ID: ");

        String testName = readString("Enter Test Name: ");
        LocalDateTime testDate = readDateTime(
                "Enter Test Date/Time (YYYY-MM-DDTHH:MM): "
        );

        String result = readString("Enter Result: ");
        String referenceRange = readString("Enter Reference Range: ");
        String status = readString("Enter Status: ");

        Patient patient = new Patient();
        patient.setPatientId(patientId);

        LaboratoryTechnician technician = new LaboratoryTechnician();
        technician.setStaffId(technicianStaffId);

        LaboratoryTest test = new LaboratoryTest();

        test.setPatient(patient);
        test.setTechnician(technician);
        test.setTestName(testName);
        test.setTestDate(testDate);
        test.setResult(result);
        test.setReferenceRange(referenceRange);
        test.setStatus(status);

        dao.addLaboratoryTest(test);
    }

// =========================================================
// VIEW ALL LABORATORY TESTS
// =========================================================
    private static void viewAllLaboratoryTests(LaboratoryTestDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       ALL LABORATORY TESTS");
        System.out.println("========================================");

        java.util.List<LaboratoryTest> tests
                = dao.findAllLaboratoryTests();

        if (tests.isEmpty()) {
            System.out.println("No laboratory tests found.");
            return;
        }

        for (LaboratoryTest test : tests) {

            System.out.println("----------------------------------------");
            System.out.println("Test ID: " + test.getId());

            if (test.getPatient() != null) {
                System.out.println(
                        "Patient ID: "
                        + test.getPatient().getPatientId()
                );
            }

            if (test.getTechnician() != null) {
                System.out.println(
                        "Technician Staff ID: "
                        + test.getTechnician().getStaffId()
                );
            }

            System.out.println("Test Name: " + test.getTestName());
            System.out.println("Test Date: " + test.getTestDate());
            System.out.println("Result: " + test.getResult());
            System.out.println(
                    "Reference Range: " + test.getReferenceRange()
            );
            System.out.println("Status: " + test.getStatus());
        }

        System.out.println("----------------------------------------");
    }

// =========================================================
// FIND LABORATORY TEST
// =========================================================
    private static void findLaboratoryTest(LaboratoryTestDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("        FIND LABORATORY TEST");
        System.out.println("========================================");

        int id = readInt("Enter Laboratory Test ID: ");

        LaboratoryTest test = dao.findLaboratoryTestById(id);

        if (test == null) {
            System.out.println("Laboratory test not found.");
            return;
        }

        System.out.println();
        System.out.println("Laboratory Test Found");
        System.out.println("----------------------------------------");
        System.out.println("Test ID: " + test.getId());

        if (test.getPatient() != null) {
            System.out.println(
                    "Patient ID: "
                    + test.getPatient().getPatientId()
            );
        }

        if (test.getTechnician() != null) {
            System.out.println(
                    "Technician Staff ID: "
                    + test.getTechnician().getStaffId()
            );
        }

        System.out.println("Test Name: " + test.getTestName());
        System.out.println("Test Date: " + test.getTestDate());
        System.out.println("Result: " + test.getResult());
        System.out.println(
                "Reference Range: " + test.getReferenceRange()
        );
        System.out.println("Status: " + test.getStatus());
    }

// =========================================================
// VIEW PATIENT LABORATORY TESTS
// =========================================================
    private static void viewPatientLaboratoryTests(
            LaboratoryTestDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("     PATIENT LABORATORY TESTS");
        System.out.println("========================================");

        int patientId = readInt("Enter Patient ID: ");

        java.util.List<LaboratoryTest> tests
                = dao.findLaboratoryTestsByPatient(patientId);

        if (tests.isEmpty()) {
            System.out.println(
                    "No laboratory tests found for this patient."
            );
            return;
        }

        for (LaboratoryTest test : tests) {

            System.out.println("----------------------------------------");
            System.out.println("Test ID: " + test.getId());
            System.out.println("Test Name: " + test.getTestName());
            System.out.println("Test Date: " + test.getTestDate());
            System.out.println("Result: " + test.getResult());
            System.out.println(
                    "Reference Range: " + test.getReferenceRange()
            );
            System.out.println("Status: " + test.getStatus());

            if (test.getTechnician() != null) {
                System.out.println(
                        "Technician Staff ID: "
                        + test.getTechnician().getStaffId()
                );
            }
        }
    }

// =========================================================
// VIEW TECHNICIAN LABORATORY TESTS
// =========================================================
    private static void viewTechnicianLaboratoryTests(
            LaboratoryTestDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("    TECHNICIAN LABORATORY TESTS");
        System.out.println("========================================");

        int staffId = readInt(
                "Enter Laboratory Technician Staff ID: "
        );

        java.util.List<LaboratoryTest> tests
                = dao.findLaboratoryTestsByTechnician(staffId);

        if (tests.isEmpty()) {
            System.out.println(
                    "No laboratory tests found for this technician."
            );
            return;
        }

        for (LaboratoryTest test : tests) {

            System.out.println("----------------------------------------");
            System.out.println("Test ID: " + test.getId());
            System.out.println("Patient ID: "
                    + test.getPatient().getPatientId());
            System.out.println("Test Name: " + test.getTestName());
            System.out.println("Test Date: " + test.getTestDate());
            System.out.println("Result: " + test.getResult());
            System.out.println("Status: " + test.getStatus());
        }
    }

// =========================================================
// UPDATE LABORATORY TEST
// =========================================================
    private static void updateLaboratoryTest(
            LaboratoryTestDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       UPDATE LABORATORY TEST");
        System.out.println("========================================");

        int id = readInt("Enter Laboratory Test ID: ");

        LaboratoryTest existing
                = dao.findLaboratoryTestById(id);

        if (existing == null) {
            System.out.println("Laboratory test not found.");
            return;
        }

        System.out.println();
        System.out.println("Enter the new laboratory test details.");

        int patientId = readInt("Enter Patient ID: ");
        int technicianStaffId = readInt(
                "Enter Laboratory Technician Staff ID: "
        );

        String testName = readString("Enter Test Name: ");

        LocalDateTime testDate = readDateTime(
                "Enter Test Date/Time (YYYY-MM-DDTHH:MM): "
        );

        String result = readString("Enter Result: ");
        String referenceRange = readString(
                "Enter Reference Range: "
        );
        String status = readString("Enter Status: ");

        Patient patient = new Patient();
        patient.setPatientId(patientId);

        LaboratoryTechnician technician
                = new LaboratoryTechnician();
        technician.setStaffId(technicianStaffId);

        LaboratoryTest test = new LaboratoryTest();

        test.setId(id);
        test.setPatient(patient);
        test.setTechnician(technician);
        test.setTestName(testName);
        test.setTestDate(testDate);
        test.setResult(result);
        test.setReferenceRange(referenceRange);
        test.setStatus(status);

        dao.updateLaboratoryTest(test);
    }

// =========================================================
// DELETE LABORATORY TEST
// =========================================================
    private static void deleteLaboratoryTest(
            LaboratoryTestDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       DELETE LABORATORY TEST");
        System.out.println("========================================");

        int id = readInt("Enter Laboratory Test ID: ");

        LaboratoryTest test
                = dao.findLaboratoryTestById(id);

        if (test == null) {
            System.out.println("Laboratory test not found.");
            return;
        }

        System.out.println(
                "Test: " + test.getTestName()
        );

        String confirmation = readString(
                "Are you sure you want to delete this test? (yes/no): "
        );

        if (confirmation.equalsIgnoreCase("yes")) {
            dao.deleteLaboratoryTest(id);
        } else {
            System.out.println("Delete cancelled.");
        }
    }

// PHARMACY MENU
// =========================================================
    private static void pharmacyMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("            PHARMACY SERVICES");
            System.out.println("========================================");

            System.out.println("1. Medication Management");
            System.out.println("2. Prescription Management");
            System.out.println("3. Prescription Item Management");
            System.out.println("4. Medication Dispensing");
            System.out.println("0. Back");

            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    medicationMenu();
                    break;

                case 2:
                    prescriptionMenu();
                    break;

                case 3:
                    prescriptionItemMenu();
                    break;

                case 4:
                    medicationDispensingMenu();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void prescriptionItemMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("      PRESCRIPTION ITEM MANAGEMENT");
            System.out.println("========================================");

            System.out.println("1. Add Prescription Item");
            System.out.println("2. View All Prescription Items");
            System.out.println("3. Find Prescription Item");
            System.out.println("4. View Items by Prescription");
            System.out.println("5. Update Prescription Item");
            System.out.println("6. Delete Prescription Item");
            System.out.println("0. Back");

            System.out.println("========================================");

            int choice
                    = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addPrescriptionItem();
                    break;

                case 2:
                    viewAllPrescriptionItems();
                    break;

                case 3:
                    findPrescriptionItem();
                    break;

                case 4:
                    viewPrescriptionItems();
                    break;

                case 5:
                    updatePrescriptionItem();
                    break;

                case 6:
                    deletePrescriptionItem();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addPrescriptionItem() {

        int prescriptionId
                = readInt("Prescription ID: ");

        Prescription prescription
                = prescriptionDAO
                        .findPrescriptionById(
                                prescriptionId);

        if (prescription == null) {
            System.out.println("Prescription not found.");
            return;
        }

        int medicationId
                = readInt("Medication ID: ");

        Medication medication
                = medicationDAO
                        .findMedicationById(
                                medicationId);

        if (medication == null) {
            System.out.println("Medication not found.");
            return;
        }

        PrescriptionItem item
                = new PrescriptionItem();

        item.setPrescription(prescription);
        item.setMedication(medication);

        item.setDosage(
                readString("Dosage: "));

        item.setFrequency(
                readString("Frequency: "));

        item.setDuration(
                readInt("Duration: "));

        item.setDurationUnit(
                readString("Duration Unit: "));

        item.setInstructions(
                readString("Instructions: "));

        if (prescriptionItemDAO
                .addPrescriptionItem(item)) {

            System.out.println(
                    "Prescription item added successfully.");

        } else {

            System.out.println(
                    "Failed to add prescription item.");
        }
    }

    private static void viewAllPrescriptionItems() {

        List<PrescriptionItem> items
                = prescriptionItemDAO
                        .findAllPrescriptionItems();

        if (items.isEmpty()) {
            System.out.println(
                    "No prescription items found.");
            return;
        }

        for (PrescriptionItem item : items) {
            displayPrescriptionItem(item);
        }
    }

    private static void findPrescriptionItem() {

        int id
                = readInt("Prescription Item ID: ");

        PrescriptionItem item
                = prescriptionItemDAO
                        .findPrescriptionItemById(id);

        if (item == null) {
            System.out.println(
                    "Prescription item not found.");
            return;
        }

        displayPrescriptionItem(item);
    }

    private static void viewPrescriptionItems() {

        int prescriptionId
                = readInt("Prescription ID: ");

        List<PrescriptionItem> items
                = prescriptionItemDAO
                        .findItemsByPrescription(
                                prescriptionId);

        if (items.isEmpty()) {
            System.out.println(
                    "No items found for this prescription.");
            return;
        }

        for (PrescriptionItem item : items) {
            displayPrescriptionItem(item);
        }
    }

    private static void displayPrescriptionItem(
            PrescriptionItem item) {

        System.out.println("----------------------------------------");

        System.out.println(
                "Item ID: " + item.getId());

        System.out.println(
                "Prescription ID: "
                + item.getPrescription().getId());

        System.out.println(
                "Medication ID: "
                + item.getMedication().getId());

        System.out.println(
                "Dosage: " + item.getDosage());

        System.out.println(
                "Frequency: " + item.getFrequency());

        System.out.println(
                "Duration: "
                + item.getDuration()
                + " "
                + item.getDurationUnit());

        System.out.println(
                "Instructions: "
                + item.getInstructions());
    }

    private static void updatePrescriptionItem() {

        int id
                = readInt("Prescription Item ID: ");

        PrescriptionItem item
                = prescriptionItemDAO
                        .findPrescriptionItemById(id);

        if (item == null) {
            System.out.println(
                    "Prescription item not found.");
            return;
        }

        int prescriptionId
                = readInt("New Prescription ID: ");

        Prescription prescription
                = prescriptionDAO
                        .findPrescriptionById(
                                prescriptionId);

        if (prescription == null) {
            System.out.println(
                    "Prescription not found.");
            return;
        }

        int medicationId
                = readInt("New Medication ID: ");

        Medication medication
                = medicationDAO
                        .findMedicationById(
                                medicationId);

        if (medication == null) {
            System.out.println(
                    "Medication not found.");
            return;
        }

        item.setPrescription(prescription);
        item.setMedication(medication);

        item.setDosage(
                readString("Dosage: "));

        item.setFrequency(
                readString("Frequency: "));

        item.setDuration(
                readInt("Duration: "));

        item.setDurationUnit(
                readString("Duration Unit: "));

        item.setInstructions(
                readString("Instructions: "));

        if (prescriptionItemDAO
                .updatePrescriptionItem(item)) {

            System.out.println(
                    "Prescription item updated successfully.");

        } else {

            System.out.println(
                    "Prescription item update failed.");
        }
    }

    private static void deletePrescriptionItem() {

        int id
                = readInt("Prescription Item ID: ");

        String confirm
                = readString(
                        "Delete this item? (Y/N): ");

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (prescriptionItemDAO
                .deletePrescriptionItem(id)) {

            System.out.println(
                    "Prescription item deleted successfully.");

        } else {

            System.out.println(
                    "Prescription item deletion failed.");
        }
    }

    private static void medicationDispensingMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("        MEDICATION DISPENSING");
            System.out.println("========================================");

            System.out.println("1. Dispense Medication");
            System.out.println("2. View All Dispensings");
            System.out.println("3. Find Dispensing");
            System.out.println("4. View Patient Dispensings");
            System.out.println("5. Delete Dispensing");
            System.out.println("0. Back");

            System.out.println("========================================");

            int choice
                    = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    dispenseMedication();
                    break;

                case 2:
                    viewAllDispensings();
                    break;

                case 3:
                    findDispensing();
                    break;

                case 4:
                    viewPatientDispensings();
                    break;

                case 5:
                    deleteDispensing();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void dispenseMedication() {

        System.out.println();
        System.out.println("========== DISPENSE MEDICATION ==========");

        int prescriptionId
                = readInt("Prescription ID: ");

        Prescription prescription
                = prescriptionDAO
                        .findPrescriptionById(
                                prescriptionId);

        if (prescription == null) {
            System.out.println(
                    "Prescription not found.");
            return;
        }

        int itemId
                = readInt("Prescription Item ID: ");

        PrescriptionItem item
                = prescriptionItemDAO
                        .findPrescriptionItemById(
                                itemId);

        if (item == null) {
            System.out.println(
                    "Prescription item not found.");
            return;
        }

        if (item.getPrescription().getId()
                != prescriptionId) {

            System.out.println(
                    "Prescription item does not belong "
                    + "to this prescription.");

            return;
        }

        int patientId
                = readInt("Patient ID: ");

        Patient patient
                = patientService.getPatientById(
                        patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        int pharmacistStaffId
                = readInt("Pharmacist/Staff ID: ");

        System.out.println(
                "Enter pharmacist staff ID "
                + "registered under Pharmacist.");

        Pharmacist pharmacist
                = new Pharmacist();

        pharmacist.setStaffId(
                pharmacistStaffId);

        int quantity
                = readInt("Quantity to dispense: ");

        if (quantity <= 0) {
            System.out.println(
                    "Quantity must be greater than zero.");
            return;
        }

        MedicationDispensing dispensing
                = new MedicationDispensing();

        dispensing.setPrescription(
                prescription);

        dispensing.setPrescriptionItem(
                item);

        dispensing.setPatient(
                patient);

        dispensing.setPharmacist(
                pharmacist);

        dispensing.setDispensingDate(
                LocalDate.now());

        dispensing.setQuantity(quantity);

        dispensing.setStatus(
                readString("Status: "));

        dispensing.setNotes(
                readString("Notes: "));

        if (medicationDispensingDAO
                .addDispensing(dispensing)) {

            System.out.println();
            System.out.println(
                    "Medication dispensed successfully.");

            System.out.println(
                    "Medication stock has been updated.");

        } else {

            System.out.println();
            System.out.println(
                    "Medication dispensing failed.");
        }
    }

    private static void viewAllDispensings() {

        List<MedicationDispensing> dispensings
                = medicationDispensingDAO
                        .findAllDispensings();

        System.out.println();
        System.out.println("========== ALL DISPENSINGS ==========");

        if (dispensings.isEmpty()) {
            System.out.println(
                    "No dispensing records found.");
            return;
        }

        for (MedicationDispensing dispensing
                : dispensings) {

            displayDispensing(dispensing);
        }
    }

    private static void findDispensing() {

        int id
                = readInt("Dispensing ID: ");

        MedicationDispensing dispensing
                = medicationDispensingDAO
                        .findDispensingById(id);

        if (dispensing == null) {
            System.out.println(
                    "Dispensing record not found.");
            return;
        }

        displayDispensing(dispensing);
    }

    private static void viewPatientDispensings() {

        int patientId
                = readInt("Patient ID: ");

        List<MedicationDispensing> dispensings
                = medicationDispensingDAO
                        .findDispensingsByPatient(
                                patientId);

        if (dispensings.isEmpty()) {
            System.out.println(
                    "No dispensing records found "
                    + "for this patient.");
            return;
        }

        for (MedicationDispensing dispensing
                : dispensings) {

            displayDispensing(dispensing);
        }
    }

    private static void displayDispensing(
            MedicationDispensing dispensing) {

        System.out.println("----------------------------------------");

        System.out.println(
                "Dispensing ID: "
                + dispensing.getId());

        System.out.println(
                "Prescription ID: "
                + dispensing.getPrescription()
                        .getId());

        System.out.println(
                "Prescription Item ID: "
                + dispensing.getPrescriptionItem()
                        .getId());

        System.out.println(
                "Pharmacist/Staff ID: "
                + dispensing.getPharmacist()
                        .getStaffId());

        System.out.println(
                "Patient ID: "
                + dispensing.getPatient()
                        .getPatientId());

        System.out.println(
                "Dispensing Date: "
                + dispensing.getDispensingDate());

        System.out.println(
                "Quantity: "
                + dispensing.getQuantity());

        System.out.println(
                "Status: "
                + dispensing.getStatus());

        System.out.println(
                "Notes: "
                + dispensing.getNotes());
    }

    private static void deleteDispensing() {

        int id
                = readInt("Dispensing ID to delete: ");

        MedicationDispensing dispensing
                = medicationDispensingDAO
                        .findDispensingById(id);

        if (dispensing == null) {
            System.out.println(
                    "Dispensing record not found.");
            return;
        }

        String confirm
                = readString(
                        "Delete this dispensing record? (Y/N): ");

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (medicationDispensingDAO
                .deleteDispensing(id)) {

            System.out.println(
                    "Dispensing deleted successfully.");

            System.out.println(
                    "Medication stock has been restored.");

        } else {

            System.out.println(
                    "Dispensing deletion failed.");
        }
    }

    private static void prescriptionMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("        PRESCRIPTION MANAGEMENT");
            System.out.println("========================================");

            System.out.println("1. Create Prescription");
            System.out.println("2. View All Prescriptions");
            System.out.println("3. Find Prescription");
            System.out.println("4. View Patient Prescriptions");
            System.out.println("5. View Doctor Prescriptions");
            System.out.println("6. Update Prescription");
            System.out.println("7. Delete Prescription");
            System.out.println("0. Back");

            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    createPrescription();
                    break;

                case 2:
                    viewAllPrescriptions();
                    break;

                case 3:
                    findPrescription();
                    break;

                case 4:
                    viewPatientPrescriptions();
                    break;

                case 5:
                    viewDoctorPrescriptions();
                    break;

                case 6:
                    updatePrescription();
                    break;

                case 7:
                    deletePrescription();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void createPrescription() {

        System.out.println();
        System.out.println("========== CREATE PRESCRIPTION ==========");

        int patientId
                = readInt("Patient ID: ");

        Patient patient
                = patientService.getPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        int doctorStaffId
                = readInt("Doctor/Staff ID: ");

        Doctor doctor
                = doctorService.getDoctorById(doctorStaffId);

        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        Prescription prescription
                = new Prescription();

        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setPrescriptionDate(
                readDate("Prescription Date (yyyy-MM-dd): "));

        if (prescriptionDAO.addPrescription(prescription)) {
            System.out.println(
                    "Prescription created successfully.");
        } else {
            System.out.println(
                    "Failed to create prescription.");
        }
    }

    private static void viewAllPrescriptions() {

        List<Prescription> prescriptions
                = prescriptionDAO.findAllPrescriptions();

        System.out.println();
        System.out.println("========== ALL PRESCRIPTIONS ==========");

        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions found.");
            return;
        }

        for (Prescription prescription : prescriptions) {

            System.out.println("----------------------------------------");

            System.out.println(
                    "Prescription ID: "
                    + prescription.getId());

            System.out.println(
                    "Patient ID: "
                    + prescription.getPatient()
                            .getPatientId());

            System.out.println(
                    "Doctor/Staff ID: "
                    + prescription.getDoctor()
                            .getStaffId());

            System.out.println(
                    "Date: "
                    + prescription.getPrescriptionDate());
        }

        System.out.println("----------------------------------------");
    }

    private static void findPrescription() {

        int id
                = readInt("Prescription ID: ");

        Prescription prescription
                = prescriptionDAO.findPrescriptionById(id);

        if (prescription == null) {
            System.out.println("Prescription not found.");
            return;
        }

        displayPrescription(prescription);
    }

    private static void viewPatientPrescriptions() {

        int patientId
                = readInt("Patient ID: ");

        List<Prescription> prescriptions
                = prescriptionDAO
                        .findPrescriptionsByPatient(patientId);

        if (prescriptions.isEmpty()) {
            System.out.println(
                    "No prescriptions found for this patient.");
            return;
        }

        for (Prescription prescription
                : prescriptions) {

            displayPrescription(prescription);
        }
    }

    private static void viewDoctorPrescriptions() {

        int staffId
                = readInt("Doctor/Staff ID: ");

        List<Prescription> prescriptions
                = prescriptionDAO
                        .findPrescriptionsByDoctor(staffId);

        if (prescriptions.isEmpty()) {
            System.out.println(
                    "No prescriptions found for this doctor.");
            return;
        }

        for (Prescription prescription
                : prescriptions) {

            displayPrescription(prescription);
        }
    }

    private static void displayPrescription(
            Prescription prescription) {

        System.out.println("----------------------------------------");

        System.out.println(
                "Prescription ID: "
                + prescription.getId());

        System.out.println(
                "Patient ID: "
                + prescription.getPatient()
                        .getPatientId());

        System.out.println(
                "Doctor/Staff ID: "
                + prescription.getDoctor()
                        .getStaffId());

        System.out.println(
                "Prescription Date: "
                + prescription.getPrescriptionDate());
    }

    private static void updatePrescription() {

        int id
                = readInt("Prescription ID to update: ");

        Prescription prescription
                = prescriptionDAO.findPrescriptionById(id);

        if (prescription == null) {
            System.out.println("Prescription not found.");
            return;
        }

        int patientId
                = readInt("New Patient ID: ");

        Patient patient
                = patientService.getPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        int doctorStaffId
                = readInt("New Doctor/Staff ID: ");

        Doctor doctor
                = doctorService.getDoctorById(doctorStaffId);

        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        prescription.setPatient(patient);
        prescription.setDoctor(doctor);

        prescription.setPrescriptionDate(
                readDate(
                        "New Prescription Date (yyyy-MM-dd): "));

        if (prescriptionDAO.updatePrescription(
                prescription)) {

            System.out.println(
                    "Prescription updated successfully.");

        } else {

            System.out.println(
                    "Prescription update failed.");
        }
    }

    private static void deletePrescription() {

        int id
                = readInt("Prescription ID to delete: ");

        Prescription prescription
                = prescriptionDAO.findPrescriptionById(id);

        if (prescription == null) {
            System.out.println("Prescription not found.");
            return;
        }

        String confirm
                = readString(
                        "Delete prescription? (Y/N): ");

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (prescriptionDAO.deletePrescription(id)) {

            System.out.println(
                    "Prescription deleted successfully.");

        } else {

            System.out.println(
                    "Prescription deletion failed.");
        }
    }

    private static void addMedication() {

        System.out.println();
        System.out.println("========== ADD MEDICATION ==========");

        Medication medication = new Medication();

        medication.setName(
                readString("Medication Name: "));

        medication.setDescription(
                readString("Description: "));

        medication.setDosageForm(
                readString("Dosage Form: "));

        medication.setPrice(
                readDouble("Price: "));

        medication.setQuantityInStock(
                readInt("Quantity in Stock: "));

        if (medication.getPrice() < 0
                || medication.getQuantityInStock() < 0) {

            System.out.println(
                    "Price and stock cannot be negative.");

            return;
        }

        if (medicationDAO.addMedication(medication)) {
            System.out.println(
                    "Medication added successfully.");
        } else {
            System.out.println(
                    "Failed to add medication.");
        }
    }

    private static void viewAllMedications() {

        List<Medication> medications
                = medicationDAO.findAllMedications();

        System.out.println();
        System.out.println("========== ALL MEDICATIONS ==========");

        if (medications.isEmpty()) {
            System.out.println("No medications found.");
            return;
        }

        for (Medication medication : medications) {

            System.out.println("----------------------------------------");
            System.out.println(
                    "Medication ID: "
                    + medication.getId());

            System.out.println(
                    "Name: "
                    + medication.getName());

            System.out.println(
                    "Description: "
                    + medication.getDescription());

            System.out.println(
                    "Dosage Form: "
                    + medication.getDosageForm());

            System.out.println(
                    "Price: "
                    + medication.getPrice());

            System.out.println(
                    "Stock: "
                    + medication.getQuantityInStock());
        }

        System.out.println("----------------------------------------");
    }

    private static void findMedication() {

        int id = readInt(
                "Enter Medication ID: ");

        Medication medication
                = medicationDAO.findMedicationById(id);

        if (medication == null) {
            System.out.println("Medication not found.");
            return;
        }

        System.out.println();
        System.out.println("Medication ID: "
                + medication.getId());

        System.out.println("Name: "
                + medication.getName());

        System.out.println("Description: "
                + medication.getDescription());

        System.out.println("Dosage Form: "
                + medication.getDosageForm());

        System.out.println("Price: "
                + medication.getPrice());

        System.out.println("Stock: "
                + medication.getQuantityInStock());
    }

    private static void updateMedication() {

        int id = readInt(
                "Enter Medication ID to update: ");

        Medication medication
                = medicationDAO.findMedicationById(id);

        if (medication == null) {
            System.out.println("Medication not found.");
            return;
        }

        medication.setName(
                readString("New Name: "));

        medication.setDescription(
                readString("New Description: "));

        medication.setDosageForm(
                readString("New Dosage Form: "));

        medication.setPrice(
                readDouble("New Price: "));

        medication.setQuantityInStock(
                readInt("New Quantity in Stock: "));

        if (medicationDAO.updateMedication(medication)) {
            System.out.println(
                    "Medication updated successfully.");
        } else {
            System.out.println(
                    "Medication update failed.");
        }
    }

    private static void deleteMedication() {

        int id = readInt(
                "Enter Medication ID to delete: ");

        Medication medication
                = medicationDAO.findMedicationById(id);

        if (medication == null) {
            System.out.println("Medication not found.");
            return;
        }

        System.out.println(
                "Medication: "
                + medication.getName());

        String confirm
                = readString("Delete this medication? (Y/N): ");

        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (medicationDAO.deleteMedication(id)) {
            System.out.println(
                    "Medication deleted successfully.");
        } else {
            System.out.println(
                    "Medication deletion failed.");
        }
    }

    private static void medicationMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("         MEDICATION MANAGEMENT");
            System.out.println("========================================");

            System.out.println("1. Add Medication");
            System.out.println("2. View All Medications");
            System.out.println("3. Find Medication");
            System.out.println("4. Update Medication");
            System.out.println("5. Delete Medication");
            System.out.println("0. Back");

            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addMedication();
                    break;

                case 2:
                    viewAllMedications();
                    break;

                case 3:
                    findMedication();
                    break;

                case 4:
                    updateMedication();
                    break;

                case 5:
                    deleteMedication();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================================================
    // Billing MENU
    // =========================================================
    private static void billingMenu() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("          BILLING & PAYMENT");
            System.out.println("========================================");
            System.out.println("1. Invoice Management");
            System.out.println("2. Invoice Item Management");
            System.out.println("3. Payment Management");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    invoiceMenu();
                    break;
                case 2:
                    invoiceItemMenu();
                    break;
                case 3:
                    paymentMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void invoiceMenu() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("          INVOICE MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Create Invoice");
            System.out.println("2. View All Invoices");
            System.out.println("3. Find Invoice");
            System.out.println("4. View Patient Invoices");
            System.out.println("5. Update Invoice");
            System.out.println("6. Delete Invoice");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    createInvoice();
                    break;
                case 2:
                    viewAllInvoices();
                    break;
                case 3:
                    findInvoice();
                    break;
                case 4:
                    viewPatientInvoices();
                    break;
                case 5:
                    updateInvoice();
                    break;
                case 6:
                    deleteInvoice();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void invoiceItemMenu() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("        INVOICE ITEM MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Invoice Item");
            System.out.println("2. View All Invoice Items");
            System.out.println("3. Find Invoice Item");
            System.out.println("4. View Items by Invoice");
            System.out.println("5. Update Invoice Item");
            System.out.println("6. Delete Invoice Item");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addInvoiceItem();
                    break;
                case 2:
                    viewAllInvoiceItems();
                    break;
                case 3:
                    findInvoiceItem();
                    break;
                case 4:
                    viewInvoiceItems();
                    break;
                case 5:
                    updateInvoiceItem();
                    break;
                case 6:
                    deleteInvoiceItem();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void createInvoice() {
        System.out.println();
        System.out.println("========== CREATE INVOICE ==========");

        int patientId = readInt("Enter Patient ID: ");

        try {
            Patient patient = patientService.getPatientById(patientId);

            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }

            LocalDate invoiceDate = readDate("Enter Invoice Date (YYYY-MM-DD): ");
            String status = readString("Enter Invoice Status: ");

            Invoice invoice = new Invoice();
            invoice.setPatient(patient);
            invoice.setInvoiceDate(invoiceDate);
            invoice.setTotalAmount(0.0);
            invoice.setStatus(status);

            invoiceDAO.addInvoice(invoice);

            System.out.println("Invoice created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating invoice: " + e.getMessage());
        }
    }

    private static void viewAllInvoices() {
        System.out.println();
        System.out.println("========== ALL INVOICES ==========");

        try {
            var invoices = invoiceDAO.findAllInvoices();

            if (invoices.isEmpty()) {
                System.out.println("No invoices found.");
                return;
            }

            for (Invoice invoice : invoices) {
                System.out.println("----------------------------------------");
                System.out.println("Invoice ID: " + invoice.getId());

                if (invoice.getPatient() != null) {
                    System.out.println("Patient ID: "
                            + invoice.getPatient().getPatientId());
                    System.out.println("Patient Name: "
                            + invoice.getPatient().getFirstName()
                            + " "
                            + invoice.getPatient().getLastName());
                }

                System.out.println("Invoice Date: " + invoice.getInvoiceDate());
                System.out.println("Total Amount: " + invoice.getTotalAmount());
                System.out.println("Status: " + invoice.getStatus());
            }

            System.out.println("----------------------------------------");

        } catch (Exception e) {
            System.out.println("Error viewing invoices: " + e.getMessage());
        }
    }

    private static void addInvoiceItem() {
        System.out.println();
        System.out.println("========== ADD INVOICE ITEM ==========");

        int invoiceId = readInt("Enter Invoice ID: ");

        try {
            Invoice invoice = invoiceDAO.findInvoiceById(invoiceId);

            if (invoice == null) {
                System.out.println("Invoice not found.");
                return;
            }

            String description = readString("Enter Description: ");
            int quantity = readInt("Enter Quantity: ");
            double unitPrice = readDouble("Enter Unit Price: ");

            if (quantity <= 0 || unitPrice < 0) {
                System.out.println("Invalid quantity or unit price.");
                return;
            }

            InvoiceItem item = new InvoiceItem();
            item.setInvoice(invoice);
            item.setDescription(description);
            item.setQuantity(quantity);
            item.setUnitPrice(unitPrice);

            invoiceItemDAO.addInvoiceItem(item);

            System.out.println("Invoice item added successfully.");
            System.out.println("Amount: " + item.getAmount());

        } catch (Exception e) {
            System.out.println("Error adding invoice item: "
                    + e.getMessage());
        }
    }

    private static void viewAllInvoiceItems() {
        System.out.println();
        System.out.println("========== ALL INVOICE ITEMS ==========");

        try {
            var items = invoiceItemDAO.findAllInvoiceItems();

            if (items.isEmpty()) {
                System.out.println("No invoice items found.");
                return;
            }

            for (InvoiceItem item : items) {
                System.out.println("----------------------------------------");
                System.out.println("Item ID: " + item.getId());
                System.out.println("Invoice ID: "
                        + item.getInvoice().getId());
                System.out.println("Description: "
                        + item.getDescription());
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("Unit Price: " + item.getUnitPrice());
                System.out.println("Amount: " + item.getAmount());
            }

        } catch (Exception e) {
            System.out.println("Error viewing invoice items: "
                    + e.getMessage());
        }
    }

    private static void findInvoiceItem() {
        System.out.println();
        int itemId = readInt("Enter Invoice Item ID: ");

        try {
            InvoiceItem item
                    = invoiceItemDAO.findInvoiceItemById(itemId);

            if (item == null) {
                System.out.println("Invoice item not found.");
                return;
            }

            System.out.println();
            System.out.println("========== INVOICE ITEM ==========");
            System.out.println("Item ID: " + item.getId());
            System.out.println("Invoice ID: "
                    + item.getInvoice().getId());
            System.out.println("Description: "
                    + item.getDescription());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Unit Price: " + item.getUnitPrice());
            System.out.println("Amount: " + item.getAmount());

        } catch (Exception e) {
            System.out.println("Error finding invoice item: "
                    + e.getMessage());
        }
    }

    private static void viewInvoiceItems() {
        System.out.println();
        int invoiceId = readInt("Enter Invoice ID: ");

        try {
            Invoice invoice = invoiceDAO.findInvoiceById(invoiceId);

            if (invoice == null) {
                System.out.println("Invoice not found.");
                return;
            }

            var items = invoiceItemDAO.findItemsByInvoice(invoiceId);

            if (items.isEmpty()) {
                System.out.println("No items found for this invoice.");
                return;
            }

            System.out.println();
            System.out.println("========== INVOICE ITEMS ==========");

            for (InvoiceItem item : items) {
                System.out.println("----------------------------------------");
                System.out.println("Item ID: " + item.getId());
                System.out.println("Description: "
                        + item.getDescription());
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("Unit Price: " + item.getUnitPrice());
                System.out.println("Amount: " + item.getAmount());
            }

        } catch (Exception e) {
            System.out.println("Error viewing invoice items: "
                    + e.getMessage());
        }
    }

    private static void updateInvoiceItem() {
        System.out.println();
        int itemId = readInt("Enter Invoice Item ID to update: ");

        try {
            InvoiceItem item
                    = invoiceItemDAO.findInvoiceItemById(itemId);

            if (item == null) {
                System.out.println("Invoice item not found.");
                return;
            }

            String description = readString("Enter new Description: ");
            int quantity = readInt("Enter new Quantity: ");
            double unitPrice = readDouble("Enter new Unit Price: ");

            if (quantity <= 0 || unitPrice < 0) {
                System.out.println("Invalid quantity or unit price.");
                return;
            }

            item.setDescription(description);
            item.setQuantity(quantity);
            item.setUnitPrice(unitPrice);

            invoiceItemDAO.updateInvoiceItem(item);

            System.out.println("Invoice item updated successfully.");

        } catch (Exception e) {
            System.out.println("Error updating invoice item: "
                    + e.getMessage());
        }
    }

    private static void deleteInvoiceItem() {
        System.out.println();
        int itemId = readInt("Enter Invoice Item ID to delete: ");

        try {
            InvoiceItem item
                    = invoiceItemDAO.findInvoiceItemById(itemId);

            if (item == null) {
                System.out.println("Invoice item not found.");
                return;
            }

            invoiceItemDAO.deleteInvoiceItem(itemId);

            System.out.println("Invoice item deleted successfully.");

        } catch (Exception e) {
            System.out.println("Error deleting invoice item: "
                    + e.getMessage());
        }
    }

    private static void findInvoice() {
        System.out.println();
        int invoiceId = readInt("Enter Invoice ID: ");

        try {
            Invoice invoice = invoiceDAO.findInvoiceById(invoiceId);

            if (invoice == null) {
                System.out.println("Invoice not found.");
                return;
            }

            System.out.println();
            System.out.println("========== INVOICE ==========");
            System.out.println("Invoice ID: " + invoice.getId());

            if (invoice.getPatient() != null) {
                System.out.println("Patient ID: "
                        + invoice.getPatient().getPatientId());
                System.out.println("Patient Name: "
                        + invoice.getPatient().getFirstName()
                        + " "
                        + invoice.getPatient().getLastName());
            }

            System.out.println("Invoice Date: " + invoice.getInvoiceDate());
            System.out.println("Total Amount: " + invoice.getTotalAmount());
            System.out.println("Status: " + invoice.getStatus());

        } catch (Exception e) {
            System.out.println("Error finding invoice: " + e.getMessage());
        }
    }

    private static void viewPatientInvoices() {
        System.out.println();
        int patientId = readInt("Enter Patient ID: ");

        try {
            Patient patient = patientService.getPatientById(patientId);

            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }

            var invoices = invoiceDAO.findInvoicesByPatient(patientId);

            if (invoices.isEmpty()) {
                System.out.println("No invoices found for this patient.");
                return;
            }

            System.out.println();
            System.out.println("========== PATIENT INVOICES ==========");

            for (Invoice invoice : invoices) {
                System.out.println("----------------------------------------");
                System.out.println("Invoice ID: " + invoice.getId());
                System.out.println("Date: " + invoice.getInvoiceDate());
                System.out.println("Total: " + invoice.getTotalAmount());
                System.out.println("Status: " + invoice.getStatus());
            }

        } catch (Exception e) {
            System.out.println("Error viewing patient invoices: "
                    + e.getMessage());
        }
    }

    private static void updateInvoice() {
        System.out.println();
        int invoiceId = readInt("Enter Invoice ID to update: ");

        try {
            Invoice invoice = invoiceDAO.findInvoiceById(invoiceId);

            if (invoice == null) {
                System.out.println("Invoice not found.");
                return;
            }

            LocalDate invoiceDate
                    = readDate("Enter new Invoice Date (YYYY-MM-DD): ");

            String status = readString("Enter new Invoice Status: ");

            invoice.setInvoiceDate(invoiceDate);
            invoice.setStatus(status);

            invoiceDAO.updateInvoice(invoice);

            System.out.println("Invoice updated successfully.");

        } catch (Exception e) {
            System.out.println("Error updating invoice: " + e.getMessage());
        }
    }

    private static void deleteInvoice() {
        System.out.println();
        int invoiceId = readInt("Enter Invoice ID to delete: ");

        try {
            Invoice invoice = invoiceDAO.findInvoiceById(invoiceId);

            if (invoice == null) {
                System.out.println("Invoice not found.");
                return;
            }

            invoiceDAO.deleteInvoice(invoiceId);

            System.out.println("Invoice deleted successfully.");

        } catch (Exception e) {
            System.out.println(
                    "Could not delete invoice. "
                    + "Make sure it has no invoice items or payments attached."
            );
            System.out.println("Details: " + e.getMessage());
        }
    }

    // =========================================================
    // USER ACCOUNT MENU
    // =========================================================
    private static void userAccountMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("         USER ACCOUNT / PROFILE");
        System.out.println("========================================");

        if (Session.isLoggedIn()) {
            System.out.println("Username : " + Session.getUsername());
            System.out.println("Role     : " + Session.getRole());
            System.out.println("Status   : ACTIVE");
        } else {
            System.out.println("No user is currently logged in.");
        }

        System.out.println("========================================");
        System.out.println();

        System.out.println("Press Enter to return...");
        scanner.nextLine();
    }

    // =========================================================
    // CLINICAL MANAGEMENT
    // =========================================================
//*
//    * ============================================================
// * CLINICAL MANAGEMENT MENU
//    * ============================================================
// *
// * This is the main menu for all clinical
//    -related operations
//    .
// *
// * Diagnosis and Treatment are already implemented
//    .
// * Nurse Assignment is now fully implemented
//    .
// * Medical Records will be implemented later
//
//    .
// */
    private static void clinicalMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("          CLINICAL MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Medical Records");
            System.out.println("2. Diagnosis Management");
            System.out.println("3. Treatment Management");
            System.out.println("4. Nurse Assignments");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                // Medical Records will be implemented later.
                case 1:
                    medicalRecordManagementMenu();
                    break;

                // Existing Diagnosis module.
                case 2:
                    diagnosisManagementMenu();
                    break;

                // Existing Treatment module.
                case 3:
                    treatmentManagementMenu();
                    break;

                // New Nurse Assignment module.
                case 4:
                    nurseAssignmentManagementMenu();
                    break;

                // Return to the previous menu.
                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
// =========================================================
// DIAGNOSIS MANAGEMENT MENU
// =========================================================

// =========================================================
// DIAGNOSIS MANAGEMENT MENU
// =========================================================
    private static void diagnosisManagementMenu() {

        DiagnosisDAO diagnosisDAO = new DiagnosisDAO();

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("        DIAGNOSIS MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Record Diagnosis");
            System.out.println("2. View All Diagnoses");
            System.out.println("3. Find Diagnosis");
            System.out.println("4. Update Diagnosis");
            System.out.println("5. Delete Diagnosis");
            System.out.println("6. View Patient Diagnoses");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addDiagnosis(diagnosisDAO);
                    break;

                case 2:
                    viewAllDiagnoses(diagnosisDAO);
                    break;

                case 3:
                    findDiagnosis(diagnosisDAO);
                    break;

                case 4:
                    updateDiagnosis(diagnosisDAO);
                    break;

                case 5:
                    deleteDiagnosis(diagnosisDAO);
                    break;

                case 6:
                    viewPatientDiagnoses(diagnosisDAO);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

// =========================================================
// ADD DIAGNOSIS
// =========================================================
    private static void addDiagnosis(DiagnosisDAO diagnosisDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          RECORD DIAGNOSIS");
        System.out.println("========================================");

        int patientId = readInt("Enter Patient ID: ");

        int doctorStaffId = readInt("Enter Doctor Staff ID: ");

        LocalDate diagnosisDate
                = readDate("Enter Diagnosis Date (YYYY-MM-DD): ");

        String condition
                = readString("Enter Condition: ");

        String description
                = readString("Enter Description: ");

        String notes
                = readString("Enter Notes: ");

        Patient patient = new Patient();
        patient.setPatientId(patientId);

        Doctor doctor = new Doctor();
        doctor.setStaffId(doctorStaffId);

        Diagnosis diagnosis = new Diagnosis();

        diagnosis.setPatient(patient);
        diagnosis.setDoctor(doctor);
        diagnosis.setDiagnosisDate(diagnosisDate);
        diagnosis.setCondition(condition);
        diagnosis.setDescription(description);
        diagnosis.setNotes(notes);

        if (diagnosisDAO.addDiagnosis(diagnosis)) {

            System.out.println();
            System.out.println("Diagnosis recorded successfully.");

        } else {

            System.out.println();
            System.out.println("Failed to record diagnosis.");
        }
    }

// =========================================================
// VIEW ALL DIAGNOSES
// =========================================================
    private static void viewAllDiagnoses(
            DiagnosisDAO diagnosisDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("           ALL DIAGNOSES");
        System.out.println("========================================");

        List<Diagnosis> diagnoses
                = diagnosisDAO.findAllDiagnoses();

        if (diagnoses.isEmpty()) {

            System.out.println("No diagnoses found.");
            return;
        }

        for (Diagnosis diagnosis : diagnoses) {

            System.out.println("----------------------------------------");

            System.out.println(
                    "Diagnosis ID : "
                    + diagnosis.getId()
            );

            if (diagnosis.getPatient() != null) {

                System.out.println(
                        "Patient ID   : "
                        + diagnosis.getPatient().getPatientId()
                );
            }

            if (diagnosis.getDoctor() != null) {

                System.out.println(
                        "Doctor Staff ID : "
                        + diagnosis.getDoctor().getStaffId()
                );
            }

            System.out.println(
                    "Date         : "
                    + diagnosis.getDiagnosisDate()
            );

            System.out.println(
                    "Condition    : "
                    + diagnosis.getCondition()
            );

            System.out.println(
                    "Description  : "
                    + diagnosis.getDescription()
            );

            System.out.println(
                    "Notes        : "
                    + diagnosis.getNotes()
            );
        }

        System.out.println("----------------------------------------");
    }

// =========================================================
// FIND DIAGNOSIS
// =========================================================
    private static void findDiagnosis(
            DiagnosisDAO diagnosisDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("            FIND DIAGNOSIS");
        System.out.println("========================================");

        int diagnosisId
                = readInt("Enter Diagnosis ID: ");

        Diagnosis diagnosis
                = diagnosisDAO.findDiagnosisById(diagnosisId);

        if (diagnosis == null) {

            System.out.println("Diagnosis not found.");
            return;
        }

        System.out.println();
        System.out.println("----------------------------------------");

        System.out.println(
                "Diagnosis ID : "
                + diagnosis.getId()
        );

        if (diagnosis.getPatient() != null) {

            System.out.println(
                    "Patient ID   : "
                    + diagnosis.getPatient().getPatientId()
            );
        }

        if (diagnosis.getDoctor() != null) {

            System.out.println(
                    "Doctor Staff ID : "
                    + diagnosis.getDoctor().getStaffId()
            );
        }

        System.out.println(
                "Date         : "
                + diagnosis.getDiagnosisDate()
        );

        System.out.println(
                "Condition    : "
                + diagnosis.getCondition()
        );

        System.out.println(
                "Description  : "
                + diagnosis.getDescription()
        );

        System.out.println(
                "Notes        : "
                + diagnosis.getNotes()
        );

        System.out.println("----------------------------------------");
    }

// =========================================================
// UPDATE DIAGNOSIS
// =========================================================
    private static void updateDiagnosis(
            DiagnosisDAO diagnosisDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          UPDATE DIAGNOSIS");
        System.out.println("========================================");

        int diagnosisId
                = readInt("Enter Diagnosis ID: ");

        Diagnosis existing
                = diagnosisDAO.findDiagnosisById(diagnosisId);

        if (existing == null) {

            System.out.println("Diagnosis not found.");
            return;
        }

        System.out.println();
        System.out.println("Enter the new diagnosis information.");

        int patientId
                = readInt("Enter Patient ID: ");

        int doctorStaffId
                = readInt("Enter Doctor Staff ID: ");

        LocalDate diagnosisDate
                = readDate("Enter Diagnosis Date (YYYY-MM-DD): ");

        String condition
                = readString("Enter Condition: ");

        String description
                = readString("Enter Description: ");

        String notes
                = readString("Enter Notes: ");

        Patient patient = new Patient();
        patient.setPatientId(patientId);

        Doctor doctor = new Doctor();
        doctor.setStaffId(doctorStaffId);

        existing.setPatient(patient);
        existing.setDoctor(doctor);
        existing.setDiagnosisDate(diagnosisDate);
        existing.setCondition(condition);
        existing.setDescription(description);
        existing.setNotes(notes);

        if (diagnosisDAO.update(existing)) {

            System.out.println();
            System.out.println("Diagnosis updated successfully.");

        } else {

            System.out.println();
            System.out.println("Failed to update diagnosis.");
        }
    }

// =========================================================
// DELETE DIAGNOSIS
// =========================================================
    private static void deleteDiagnosis(
            DiagnosisDAO diagnosisDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          DELETE DIAGNOSIS");
        System.out.println("========================================");

        int diagnosisId
                = readInt("Enter Diagnosis ID: ");

        Diagnosis existing
                = diagnosisDAO.findDiagnosisById(diagnosisId);

        if (existing == null) {

            System.out.println("Diagnosis not found.");
            return;
        }

        System.out.println();
        System.out.println(
                "Diagnosis: "
                + existing.getCondition()
        );

        String confirmation
                = readString("Are you sure you want to delete it? (Y/N): ");

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println("Delete cancelled.");
            return;
        }

        if (diagnosisDAO.delete(diagnosisId)) {

            System.out.println();
            System.out.println("Diagnosis deleted successfully.");

        } else {

            System.out.println();
            System.out.println("Failed to delete diagnosis.");
        }
    }

// =========================================================
// VIEW PATIENT DIAGNOSES
// =========================================================
    private static void viewPatientDiagnoses(
            DiagnosisDAO diagnosisDAO) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("        PATIENT DIAGNOSES");
        System.out.println("========================================");

        int patientId
                = readInt("Enter Patient ID: ");

        List<Diagnosis> diagnoses
                = diagnosisDAO.findDiagnosesByPatient(patientId);

        if (diagnoses.isEmpty()) {

            System.out.println(
                    "No diagnoses found for Patient ID "
                    + patientId + "."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "Diagnoses for Patient ID: "
                + patientId
        );

        for (Diagnosis diagnosis : diagnoses) {

            System.out.println("----------------------------------------");

            System.out.println(
                    "Diagnosis ID : "
                    + diagnosis.getId()
            );

            if (diagnosis.getDoctor() != null) {

                System.out.println(
                        "Doctor Staff ID : "
                        + diagnosis.getDoctor().getStaffId()
                );
            }

            System.out.println(
                    "Date         : "
                    + diagnosis.getDiagnosisDate()
            );

            System.out.println(
                    "Condition    : "
                    + diagnosis.getCondition()
            );

            System.out.println(
                    "Description  : "
                    + diagnosis.getDescription()
            );

            System.out.println(
                    "Notes        : "
                    + diagnosis.getNotes()
            );
        }

        System.out.println("----------------------------------------");
    }

    // =========================================================
    // USER ACCOUNT / PROFILE
    // =========================================================
    private static void userAccountProfileMenu() {
        showModuleStatus("USER ACCOUNT / PROFILE",
                "User Account / Profile is now part of the main navigation.",
                "The Users table can later support login, profile and role-based access control.");
    }

    // =========================================================
    // CONTROLLED MODULE STATUS VIEW
    // =========================================================
    private static void showModuleStatus(String title, String message, String nextStep) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("        " + title);
        System.out.println("========================================");
        System.out.println(message);
        System.out.println();
        System.out.println("Next integration point:");
        System.out.println(nextStep);
        System.out.println("========================================");
        System.out.println("Press ENTER to return...");
        scanner.nextLine();
    }

    // =========================================================
    // PATIENT MENU
    // =========================================================
    private static void patientMenu() {

        while (true) {

            System.out.println();

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "           PATIENT MANAGEMENT"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "1. Register Patient"
            );

            System.out.println(
                    "2. View All Patients"
            );

            System.out.println(
                    "3. Find Patient"
            );

            System.out.println(
                    "4. Update Patient"
            );

            System.out.println(
                    "5. Delete Patient"
            );

            System.out.println(
                    "0. Back"
            );

            System.out.println(
                    "========================================"
            );

            int choice
                    = readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    registerPatient();
                    break;

                case 2:
                    viewAllPatients();
                    break;

                case 3:
                    findPatient();
                    break;

                case 4:
                    updatePatient();
                    break;

                case 5:
                    deletePatient();
                    break;

                case 0:
                    return;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    private static void paymentMenu() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("          PAYMENT MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Record Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. Find Payment");
            System.out.println("4. View Payments by Invoice");
            System.out.println("5. Update Payment");
            System.out.println("6. Delete Payment");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    recordPayment();
                    break;
                case 2:
                    viewAllPayments();
                    break;
                case 3:
                    findPayment();
                    break;
                case 4:
                    viewInvoicePayments();
                    break;
                case 5:
                    updatePayment();
                    break;
                case 6:
                    deletePayment();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void recordPayment() {
        System.out.println();
        System.out.println("========== RECORD PAYMENT ==========");

        int invoiceId = readInt("Enter Invoice ID: ");

        try {
            Invoice invoice = invoiceDAO.findInvoiceById(invoiceId);

            if (invoice == null) {
                System.out.println("Invoice not found.");
                return;
            }

            double amount = readDouble("Enter Payment Amount: ");

            if (amount <= 0) {
                System.out.println("Payment amount must be greater than zero.");
                return;
            }

            LocalDate paymentDate
                    = readDate("Enter Payment Date (YYYY-MM-DD): ");

            String paymentMethod
                    = readString("Enter Payment Method: ");

            Payment payment = new Payment();
            payment.setInvoice(invoice);
            payment.setAmount(amount);
            payment.setPaymentDate(paymentDate);
            payment.setPaymentMethod(paymentMethod);

            paymentDAO.addPayment(payment);

            System.out.println("Payment recorded successfully.");

        } catch (Exception e) {
            System.out.println("Error recording payment: "
                    + e.getMessage());
        }
    }

    private static void viewAllPayments() {
        System.out.println();
        System.out.println("========== ALL PAYMENTS ==========");

        try {
            var payments = paymentDAO.findAllPayments();

            if (payments.isEmpty()) {
                System.out.println("No payments found.");
                return;
            }

            for (Payment payment : payments) {
                System.out.println("----------------------------------------");
                System.out.println("Payment ID: " + payment.getId());
                System.out.println("Invoice ID: "
                        + payment.getInvoice().getId());
                System.out.println("Amount: " + payment.getAmount());
                System.out.println("Payment Date: "
                        + payment.getPaymentDate());
                System.out.println("Payment Method: "
                        + payment.getPaymentMethod());
            }

        } catch (Exception e) {
            System.out.println("Error viewing payments: "
                    + e.getMessage());
        }
    }

    private static void findPayment() {
        System.out.println();
        int paymentId = readInt("Enter Payment ID: ");

        try {
            Payment payment
                    = paymentDAO.findPaymentById(paymentId);

            if (payment == null) {
                System.out.println("Payment not found.");
                return;
            }

            System.out.println();
            System.out.println("========== PAYMENT ==========");
            System.out.println("Payment ID: " + payment.getId());
            System.out.println("Invoice ID: "
                    + payment.getInvoice().getId());
            System.out.println("Amount: " + payment.getAmount());
            System.out.println("Payment Date: "
                    + payment.getPaymentDate());
            System.out.println("Payment Method: "
                    + payment.getPaymentMethod());

        } catch (Exception e) {
            System.out.println("Error finding payment: "
                    + e.getMessage());
        }
    }

    private static void viewInvoicePayments() {
        System.out.println();
        int invoiceId = readInt("Enter Invoice ID: ");

        try {
            Invoice invoice = invoiceDAO.findInvoiceById(invoiceId);

            if (invoice == null) {
                System.out.println("Invoice not found.");
                return;
            }

            var payments = paymentDAO.findPaymentsByInvoice(invoiceId);

            if (payments.isEmpty()) {
                System.out.println("No payments found for this invoice.");
                return;
            }

            System.out.println();
            System.out.println("========== INVOICE PAYMENTS ==========");

            double totalPaid = 0;

            for (Payment payment : payments) {
                System.out.println("----------------------------------------");
                System.out.println("Payment ID: " + payment.getId());
                System.out.println("Amount: " + payment.getAmount());
                System.out.println("Date: " + payment.getPaymentDate());
                System.out.println("Method: "
                        + payment.getPaymentMethod());

                totalPaid += payment.getAmount();
            }

            System.out.println("----------------------------------------");
            System.out.println("Invoice Total: "
                    + invoice.getTotalAmount());
            System.out.println("Total Paid: " + totalPaid);
            System.out.println("Balance: "
                    + (invoice.getTotalAmount() - totalPaid));

        } catch (Exception e) {
            System.out.println("Error viewing invoice payments: "
                    + e.getMessage());
        }
    }

    private static void updatePayment() {
        System.out.println();
        int paymentId = readInt("Enter Payment ID to update: ");

        try {
            Payment payment
                    = paymentDAO.findPaymentById(paymentId);

            if (payment == null) {
                System.out.println("Payment not found.");
                return;
            }

            double amount = readDouble("Enter new Payment Amount: ");

            if (amount <= 0) {
                System.out.println("Payment amount must be greater than zero.");
                return;
            }

            LocalDate paymentDate
                    = readDate("Enter new Payment Date (YYYY-MM-DD): ");

            String paymentMethod
                    = readString("Enter new Payment Method: ");

            payment.setAmount(amount);
            payment.setPaymentDate(paymentDate);
            payment.setPaymentMethod(paymentMethod);

            paymentDAO.updatePayment(payment);

            System.out.println("Payment updated successfully.");

        } catch (Exception e) {
            System.out.println("Error updating payment: "
                    + e.getMessage());
        }
    }

    private static void deletePayment() {
        System.out.println();
        int paymentId = readInt("Enter Payment ID to delete: ");

        try {
            Payment payment
                    = paymentDAO.findPaymentById(paymentId);

            if (payment == null) {
                System.out.println("Payment not found.");
                return;
            }

            paymentDAO.deletePayment(paymentId);

            System.out.println("Payment deleted successfully.");

        } catch (Exception e) {
            System.out.println("Error deleting payment: "
                    + e.getMessage());
        }
    }

    // =========================================================
    // REGISTER PATIENT
    // =========================================================
    private static void registerPatient() {

        System.out.println();

        System.out.println(
                "========== REGISTER PATIENT =========="
        );

        System.out.print(
                "First Name: "
        );

        String firstName
                = scanner.nextLine();

        System.out.print(
                "Last Name: "
        );

        String lastName
                = scanner.nextLine();

        System.out.print(
                "Gender (M/F): "
        );

        char gender
                = scanner.nextLine()
                        .charAt(0);

        System.out.print(
                "Date of Birth (yyyy-MM-dd): "
        );

        LocalDate dateOfBirth
                = LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.print(
                "Phone: "
        );

        String phone
                = scanner.nextLine();

        System.out.print(
                "Email: "
        );

        String email
                = scanner.nextLine();

        System.out.print(
                "Street: "
        );

        String street
                = scanner.nextLine();

        System.out.print(
                "City: "
        );

        String city
                = scanner.nextLine();

        System.out.print(
                "Country: "
        );

        String country
                = scanner.nextLine();

        System.out.print(
                "Blood Group: "
        );

        String bloodGroup
                = scanner.nextLine();

        System.out.print(
                "Genotype: "
        );

        String genotype
                = scanner.nextLine();

        System.out.print(
                "Allergies: "
        );

        String allergies
                = scanner.nextLine();

        System.out.print(
                "Emergency Contact: "
        );

        String emergencyContact
                = scanner.nextLine();

        System.out.print(
                "Emergency Phone: "
        );

        String emergencyPhone
                = scanner.nextLine();

        Patient patient
                = new Patient(
                        0,
                        bloodGroup,
                        genotype,
                        allergies,
                        emergencyContact,
                        emergencyPhone,
                        firstName,
                        lastName,
                        gender,
                        dateOfBirth,
                        phone,
                        email,
                        street,
                        city,
                        country
                );

        boolean success
                = patientService.registerPatient(
                        patient
                );

        if (success) {

            System.out.println();

            System.out.println(
                    "Patient registered successfully."
            );

        } else {

            System.out.println();

            System.out.println(
                    "Failed to register patient."
            );
        }
    }

    // =========================================================
    // VIEW ALL PATIENTS
    // =========================================================
    private static void viewAllPatients() {

        List<Patient> patients
                = patientService.getAllPatients();

        patientView.displayPatients(
                patients
        );
    }

    // =========================================================
    // FIND PATIENT
    // =========================================================
    private static void findPatient() {

        int id
                = readInt(
                        "Enter Patient ID: "
                );

        Patient patient
                = patientService.getPatientById(
                        id
                );

        patientView.displayPatient(
                patient
        );
    }

    // =========================================================
    // UPDATE PATIENT
    // =========================================================
    private static void updatePatient() {

        int id
                = readInt(
                        "Enter Patient ID to update: "
                );

        Patient patient
                = patientService.getPatientById(
                        id
                );

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "Enter new patient information."
        );

        System.out.print(
                "First Name: "
        );

        patient.setFirstName(
                scanner.nextLine()
        );

        System.out.print(
                "Last Name: "
        );

        patient.setLastName(
                scanner.nextLine()
        );

        System.out.print(
                "Gender (M/F): "
        );

        patient.setGender(
                scanner.nextLine()
                        .charAt(0)
        );

        System.out.print(
                "Date of Birth (yyyy-MM-dd): "
        );

        patient.setDateOfBirth(
                LocalDate.parse(
                        scanner.nextLine()
                )
        );

        System.out.print(
                "Phone: "
        );

        patient.setPhone(
                scanner.nextLine()
        );

        System.out.print(
                "Email: "
        );

        patient.setEmail(
                scanner.nextLine()
        );

        System.out.print(
                "Street: "
        );

        patient.setStreet(
                scanner.nextLine()
        );

        System.out.print(
                "City: "
        );

        patient.setCity(
                scanner.nextLine()
        );

        System.out.print(
                "Country: "
        );

        patient.setCountry(
                scanner.nextLine()
        );

        System.out.print(
                "Blood Group: "
        );

        patient.setBloodGroup(
                scanner.nextLine()
        );

        System.out.print(
                "Genotype: "
        );

        patient.setGenotype(
                scanner.nextLine()
        );

        System.out.print(
                "Allergies: "
        );

        patient.setAllergies(
                scanner.nextLine()
        );

        System.out.print(
                "Emergency Contact: "
        );

        patient.setEmergencyContact(
                scanner.nextLine()
        );

        System.out.print(
                "Emergency Phone: "
        );

        patient.setEmergencyPhone(
                scanner.nextLine()
        );

        boolean success
                = patientService.updatePatient(
                        patient
                );

        if (success) {

            System.out.println(
                    "Patient updated successfully."
            );

        } else {

            System.out.println(
                    "Patient update failed."
            );
        }
    }

    // =========================================================
    // DELETE PATIENT
    // =========================================================
    private static void deletePatient() {

        int id
                = readInt(
                        "Enter Patient ID to delete: "
                );

        Patient patient
                = patientService.getPatientById(
                        id
                );

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

            return;
        }

        patientView.displayPatient(
                patient
        );

        System.out.print(
                "Are you sure you want to delete this patient? (Y/N): "
        );

        String answer
                = scanner.nextLine();

        if (!answer.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        boolean success
                = patientService.deletePatient(
                        id
                );

        if (success) {

            System.out.println(
                    "Patient deleted successfully."
            );

        } else {

            System.out.println(
                    "Patient deletion failed."
            );
        }
    }

    // =========================================================
    // DOCTOR MENU
    // =========================================================
    private static void doctorMenu() {

        while (true) {

            System.out.println();

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "           DOCTOR MANAGEMENT"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "1. Register Doctor"
            );

            System.out.println(
                    "2. View All Doctors"
            );

            System.out.println(
                    "3. Find Doctor"
            );

            System.out.println(
                    "4. Update Doctor"
            );

            System.out.println(
                    "5. Delete Doctor"
            );

            System.out.println(
                    "0. Back"
            );

            System.out.println(
                    "========================================"
            );

            int choice
                    = readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    registerDoctor();
                    break;

                case 2:
                    viewAllDoctors();
                    break;

                case 3:
                    findDoctor();
                    break;

                case 4:
                    updateDoctor();
                    break;

                case 5:
                    deleteDoctor();
                    break;

                case 0:
                    return;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    // =========================================================
    // REGISTER DOCTOR
    // =========================================================
    private static void registerDoctor() {

        System.out.println();

        System.out.println(
                "========== REGISTER DOCTOR =========="
        );

        System.out.print(
                "First Name: "
        );

        String firstName
                = scanner.nextLine();

        System.out.print(
                "Last Name: "
        );

        String lastName
                = scanner.nextLine();

        System.out.print(
                "Gender (M/F): "
        );

        char gender
                = scanner.nextLine()
                        .charAt(0);

        System.out.print(
                "Date of Birth (yyyy-MM-dd): "
        );

        LocalDate dateOfBirth
                = LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.print(
                "Phone: "
        );

        String phone
                = scanner.nextLine();

        System.out.print(
                "Email: "
        );

        String email
                = scanner.nextLine();

        System.out.print(
                "Street: "
        );

        String street
                = scanner.nextLine();

        System.out.print(
                "City: "
        );

        String city
                = scanner.nextLine();

        System.out.print(
                "Country: "
        );

        String country
                = scanner.nextLine();

        System.out.print(
                "Employment Date (yyyy-MM-dd): "
        );

        LocalDate employmentDate
                = LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.print(
                "Salary: "
        );

        double salary
                = Double.parseDouble(
                        scanner.nextLine()
                );

        int departmentId
                = readInt(
                        "Department ID: "
                );

        System.out.print(
                "Department Name: "
        );

        String departmentName
                = scanner.nextLine();

        Department department
                = new Department();

        department.setId(
                departmentId
        );

        department.setName(
                departmentName
        );

        System.out.print(
                "Specialization: "
        );

        String specialization
                = scanner.nextLine();

        System.out.print(
                "License Number: "
        );

        String licenseNumber
                = scanner.nextLine();

        Doctor doctor
                = new Doctor(
                        firstName,
                        lastName,
                        gender,
                        dateOfBirth,
                        phone,
                        email,
                        street,
                        city,
                        country,
                        0,
                        employmentDate,
                        salary,
                        department,
                        specialization,
                        licenseNumber
                );

        boolean success
                = doctorService.registerDoctor(
                        doctor
                );

        if (success) {

            System.out.println();

            System.out.println(
                    "Doctor registered successfully."
            );

        } else {

            System.out.println();

            System.out.println(
                    "Failed to register doctor."
            );
        }
    }

    // =========================================================
    // VIEW ALL DOCTORS
    // =========================================================
    private static void viewAllDoctors() {

        List<Doctor> doctors
                = doctorService.getAllDoctors();

        doctorView.displayDoctors(
                doctors
        );
    }

    // =========================================================
    // FIND DOCTOR
    // =========================================================
    private static void findDoctor() {

        int id
                = readInt(
                        "Enter Doctor/Staff ID: "
                );

        Doctor doctor
                = doctorService.getDoctorById(
                        id
                );

        doctorView.displayDoctor(
                doctor
        );
    }

    // =========================================================
    // UPDATE DOCTOR
    // =========================================================
    private static void updateDoctor() {

        int id
                = readInt(
                        "Enter Doctor/Staff ID to update: "
                );

        Doctor doctor
                = doctorService.getDoctorById(
                        id
                );

        if (doctor == null) {

            System.out.println(
                    "Doctor not found."
            );

            return;
        }

        System.out.println();

        doctorView.displayDoctor(
                doctor
        );

        System.out.println();

        System.out.println(
                "Enter new doctor information."
        );

        System.out.print(
                "First Name: "
        );

        doctor.setFirstName(
                scanner.nextLine()
        );

        System.out.print(
                "Last Name: "
        );

        doctor.setLastName(
                scanner.nextLine()
        );

        System.out.print(
                "Gender (M/F): "
        );

        doctor.setGender(
                scanner.nextLine()
                        .charAt(0)
        );

        System.out.print(
                "Date of Birth (yyyy-MM-dd): "
        );

        doctor.setDateOfBirth(
                LocalDate.parse(
                        scanner.nextLine()
                )
        );

        System.out.print(
                "Phone: "
        );

        doctor.setPhone(
                scanner.nextLine()
        );

        System.out.print(
                "Email: "
        );

        doctor.setEmail(
                scanner.nextLine()
        );

        System.out.print(
                "Street: "
        );

        doctor.setStreet(
                scanner.nextLine()
        );

        System.out.print(
                "City: "
        );

        doctor.setCity(
                scanner.nextLine()
        );

        System.out.print(
                "Country: "
        );

        doctor.setCountry(
                scanner.nextLine()
        );

        System.out.print(
                "Employment Date (yyyy-MM-dd): "
        );

        doctor.setEmploymentDate(
                LocalDate.parse(
                        scanner.nextLine()
                )
        );

        System.out.print(
                "Salary: "
        );

        doctor.setSalary(
                Double.parseDouble(
                        scanner.nextLine()
                )
        );

        int departmentId
                = readInt(
                        "Department ID: "
                );

        System.out.print(
                "Department Name: "
        );

        String departmentName
                = scanner.nextLine();

        Department department
                = new Department();

        department.setId(
                departmentId
        );

        department.setName(
                departmentName
        );

        doctor.setDepartment(
                department
        );

        System.out.print(
                "Specialization: "
        );

        doctor.setSpecialization(
                scanner.nextLine()
        );

        System.out.print(
                "License Number: "
        );

        doctor.setLicenseNumber(
                scanner.nextLine()
        );

        boolean success
                = doctorService.updateDoctor(
                        doctor
                );

        if (success) {

            System.out.println();

            System.out.println(
                    "Doctor updated successfully."
            );

        } else {

            System.out.println();

            System.out.println(
                    "Doctor update failed."
            );
        }
    }

    // =========================================================
    // DELETE DOCTOR
    // =========================================================
    private static void deleteDoctor() {

        int id
                = readInt(
                        "Enter Doctor/Staff ID to delete: "
                );

        Doctor doctor
                = doctorService.getDoctorById(
                        id
                );

        if (doctor == null) {

            System.out.println(
                    "Doctor not found."
            );

            return;
        }

        doctorView.displayDoctor(
                doctor
        );

        System.out.print(
                "Are you sure you want to delete this doctor? (Y/N): "
        );

        String answer
                = scanner.nextLine();

        if (!answer.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        boolean success
                = doctorService.deleteDoctor(
                        id
                );

        if (success) {

            System.out.println(
                    "Doctor deleted successfully."
            );

        } else {

            System.out.println(
                    "Doctor deletion failed."
            );
        }
    }

    /*
 * ============================================================
 * NURSE ASSIGNMENT MANAGEMENT
 * ============================================================
 *
 * This section handles all nurse assignment operations:
 *
 * 1. Add Nurse Assignment
 * 2. View All Nurse Assignments
 * 3. Find Nurse Assignment
 * 4. Update Nurse Assignment
 * 5. Delete Nurse Assignment
 * 6. View Patient Assignments
 * 7. View Nurse Assignments
 *
 * The NurseAssignmentDAO handles all database operations.
     */
 /*
 * ============================================================
 * NURSE ASSIGNMENT MANAGEMENT MENU
 * ============================================================
     */
    private static void nurseAssignmentManagementMenu() {

        // Create the DAO used by all nurse assignment operations.
        NurseAssignmentDAO dao = new NurseAssignmentDAO();

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("       NURSE ASSIGNMENT MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Nurse Assignment");
            System.out.println("2. View All Nurse Assignments");
            System.out.println("3. Find Nurse Assignment");
            System.out.println("4. Update Nurse Assignment");
            System.out.println("5. Delete Nurse Assignment");
            System.out.println("6. View Patient Assignments");
            System.out.println("7. View Nurse Assignments");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addNurseAssignment(dao);
                    break;

                case 2:
                    viewAllNurseAssignments(dao);
                    break;

                case 3:
                    findNurseAssignment(dao);
                    break;

                case 4:
                    updateNurseAssignment(dao);
                    break;

                case 5:
                    deleteNurseAssignment(dao);
                    break;

                case 6:
                    viewPatientNurseAssignments(dao);
                    break;

                case 7:
                    viewNurseAssignments(dao);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    // ============================================================
// MEDICAL RECORD MANAGEMENT MENU
// ============================================================
// This method controls the Medical Record Management submenu.
//
// Medical records are linked to patients through PatientId.
// The database allows only ONE medical record per patient.
//
// Options:
// 1. Create Medical Record
// 2. View All Medical Records
// 3. Find Medical Record
// 4. Find Patient Medical Record
// 5. Update Medical Record
// 6. Delete Medical Record
// 0. Back
// ============================================================

    private static void medicalRecordManagementMenu() {

        // Create one DAO object that will handle all
        // database operations for medical records.
        MedicalRecordDAO dao = new MedicalRecordDAO();

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("        MEDICAL RECORD MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Create Medical Record");
            System.out.println("2. View All Medical Records");
            System.out.println("3. Find Medical Record");
            System.out.println("4. Find Patient Medical Record");
            System.out.println("5. Update Medical Record");
            System.out.println("6. Delete Medical Record");
            System.out.println("0. Back");
            System.out.println("========================================");

            // Read the user's menu choice.
            int choice = readInt("Enter your choice: ");

            switch (choice) {

                // ------------------------------------------------
                // CREATE MEDICAL RECORD
                // ------------------------------------------------
                case 1:
                    addMedicalRecord(dao);
                    break;

                // ------------------------------------------------
                // VIEW ALL MEDICAL RECORDS
                // ------------------------------------------------
                case 2:
                    viewAllMedicalRecords(dao);
                    break;

                // ------------------------------------------------
                // FIND MEDICAL RECORD BY RECORD ID
                // ------------------------------------------------
                case 3:
                    findMedicalRecord(dao);
                    break;

                // ------------------------------------------------
                // FIND MEDICAL RECORD BY PATIENT ID
                // ------------------------------------------------
                case 4:
                    findPatientMedicalRecord(dao);
                    break;

                // ------------------------------------------------
                // UPDATE MEDICAL RECORD
                // ------------------------------------------------
                case 5:
                    updateMedicalRecord(dao);
                    break;

                // ------------------------------------------------
                // DELETE MEDICAL RECORD
                // ------------------------------------------------
                case 6:
                    deleteMedicalRecord(dao);
                    break;

                // ------------------------------------------------
                // RETURN TO CLINICAL MANAGEMENT MENU
                // ------------------------------------------------
                case 0:
                    return;

                // ------------------------------------------------
                // INVALID OPTION
                // ------------------------------------------------
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

// ============================================================
// ADD MEDICAL RECORD
// ============================================================
// Creates a new medical record for an existing patient.
//
// Database fields:
// - PatientId
// - CreatedDate
//
// MedicalRecordId is generated automatically by SQL Server.
// ============================================================
    private static void addMedicalRecord(MedicalRecordDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("         CREATE MEDICAL RECORD");
        System.out.println("========================================");

        // Ask for the patient who owns this medical record.
        int patientId = readInt("Enter Patient ID: ");

        // Ask when the medical record was created.
        LocalDate createdDate = readDate(
                "Enter Created Date (YYYY-MM-DD): "
        );

        // Create a Patient object containing the selected
        // patient's ID.
        Patient patient = new Patient();
        patient.setPatientId(patientId);

        // Create the MedicalRecord object.
        MedicalRecord record = new MedicalRecord();

        // Attach the patient to the medical record.
        record.setPatient(patient);

        // Set the creation date.
        record.setCreatedDate(createdDate);

        // Send the record to the DAO for insertion.
        dao.addMedicalRecord(record);
    }

// ============================================================
// VIEW ALL MEDICAL RECORDS
// ============================================================
// Retrieves every medical record from the database
// and displays its basic information.
// ============================================================
    private static void viewAllMedicalRecords(MedicalRecordDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          ALL MEDICAL RECORDS");
        System.out.println("========================================");

        // Retrieve all records from the database.
        List<MedicalRecord> records
                = dao.findAllMedicalRecords();

        // Check whether the database returned anything.
        if (records.isEmpty()) {

            System.out.println("No medical records found.");
            return;
        }

        // Display each medical record.
        for (MedicalRecord record : records) {

            System.out.println("----------------------------------------");

            System.out.println(
                    "Medical Record ID: "
                    + record.getId()
            );

            System.out.println(
                    "Patient ID: "
                    + record.getPatient().getPatientId()
            );

            System.out.println(
                    "Created Date: "
                    + record.getCreatedDate()
            );
        }

        System.out.println("----------------------------------------");
    }

// ============================================================
// FIND MEDICAL RECORD BY MEDICAL RECORD ID
// ============================================================
// Allows the user to enter a MedicalRecordId and retrieve
// that specific medical record.
// ============================================================
    private static void findMedicalRecord(MedicalRecordDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          FIND MEDICAL RECORD");
        System.out.println("========================================");

        // Ask for the Medical Record ID.
        int id = readInt("Enter Medical Record ID: ");

        // Search the database.
        MedicalRecord record
                = dao.findMedicalRecordById(id);

        // If nothing was found, inform the user.
        if (record == null) {

            System.out.println("Medical record not found.");
            return;
        }

        // Display the record.
        System.out.println("----------------------------------------");

        System.out.println(
                "Medical Record ID: "
                + record.getId()
        );

        System.out.println(
                "Patient ID: "
                + record.getPatient().getPatientId()
        );

        System.out.println(
                "Created Date: "
                + record.getCreatedDate()
        );

        System.out.println("----------------------------------------");
    }

// ============================================================
// FIND MEDICAL RECORD BY PATIENT
// ============================================================
// Since PatientId is UNIQUE in the MedicalRecord table,
// each patient can have only one medical record.
//
// This method allows the user to enter a PatientId and
// retrieve that patient's medical record.
// ============================================================
    private static void findPatientMedicalRecord(
            MedicalRecordDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("      FIND PATIENT MEDICAL RECORD");
        System.out.println("========================================");

        // Ask for the patient's ID.
        int patientId = readInt("Enter Patient ID: ");

        // Search for the patient's medical record.
        MedicalRecord record
                = dao.findMedicalRecordByPatient(patientId);

        // No record was found.
        if (record == null) {

            System.out.println(
                    "No medical record found for this patient."
            );

            return;
        }

        // Display the patient's medical record.
        System.out.println("----------------------------------------");

        System.out.println(
                "Medical Record ID: "
                + record.getId()
        );

        System.out.println(
                "Patient ID: "
                + record.getPatient().getPatientId()
        );

        System.out.println(
                "Created Date: "
                + record.getCreatedDate()
        );

        System.out.println("----------------------------------------");
    }

// ============================================================
// UPDATE MEDICAL RECORD
// ============================================================
// Updates the patient associated with the record and/or
// changes its creation date.
//
// The Medical Record ID itself remains unchanged.
// ============================================================
    private static void updateMedicalRecord(
            MedicalRecordDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("         UPDATE MEDICAL RECORD");
        System.out.println("========================================");

        // Identify the record that should be updated.
        int id = readInt("Enter Medical Record ID: ");

        // Ask for the new patient ID.
        int patientId = readInt("Enter new Patient ID: ");

        // Ask for the new creation date.
        LocalDate createdDate = readDate(
                "Enter new Created Date (YYYY-MM-DD): "
        );

        // Create the Patient object.
        Patient patient = new Patient();
        patient.setPatientId(patientId);

        // Create the MedicalRecord object containing
        // the updated information.
        MedicalRecord record = new MedicalRecord();

        record.setId(id);
        record.setPatient(patient);
        record.setCreatedDate(createdDate);

        // Send the updated record to the DAO.
        dao.updateMedicalRecord(record);
    }

// ============================================================
// DELETE MEDICAL RECORD
// ============================================================
// Deletes a medical record using its MedicalRecordId.
// ============================================================
    private static void deleteMedicalRecord(
            MedicalRecordDAO dao) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("         DELETE MEDICAL RECORD");
        System.out.println("========================================");

        // Ask which medical record should be deleted.
        int id = readInt("Enter Medical Record ID: ");

        // Delete the record through the DAO.
        dao.deleteMedicalRecord(id);
    }


    /*
 * ============================================================
 * ADD NURSE ASSIGNMENT
 * ============================================================
 *
 * Creates a new nurse assignment.
 *
 * The assignment connects:
 *
 * Nurse -> Patient -> Optional Admission
     */
    private static void addNurseAssignment(NurseAssignmentDAO dao) {

        System.out.println();
        System.out.println("----------- ADD NURSE ASSIGNMENT -----------");

        // Nurse Staff ID.
        int nurseId = readInt("Enter Nurse Staff ID: ");

        // Patient ID.
        int patientId = readInt("Enter Patient ID: ");

        /*
     * Admission is optional.
     * Press Enter without typing anything if there is no admission.
         */
        String admissionInput = readString(
                "Enter Admission ID (leave blank if none): "
        );

        // Assignment start date and time.
        LocalDateTime assignmentDate = readDateTime(
                "Enter Assignment Date/Time (YYYY-MM-DD HH:MM): "
        );

        /*
     * End date is optional.
     * Leave blank if the assignment is still active.
         */
        String endDateInput = readString(
                "Enter End Date/Time (leave blank if ongoing): "
        );

        // Additional assignment information.
        String shift = readString("Enter Shift: ");
        String status = readString("Enter Status: ");
        String notes = readString("Enter Notes: ");

        /*
     * Create Nurse object.
     *
     * Our Nurse model uses Staff ID.
         */
        Nurse nurse = new Nurse();
        nurse.setStaffId(nurseId);

        /*
     * Create Patient object using the supplied Patient ID.
         */
        Patient patient = new Patient();
        patient.setPatientId(patientId);

        /*
     * Admission is optional.
         */
        Admission admission = null;

        if (!admissionInput.isEmpty()) {

            try {

                admission = new Admission();

                // Admission model uses getId()/setId().
                admission.setId(
                        Integer.parseInt(admissionInput)
                );

            } catch (NumberFormatException e) {

                System.out.println("Invalid Admission ID.");
                return;
            }
        }

        /*
     * Convert the optional end-date string into LocalDateTime.
         */
        LocalDateTime endDate = null;

        if (!endDateInput.isEmpty()) {

            try {

                /*
             * User enters:
             *
             * 2026-09-14 14:30
             *
             * LocalDateTime.parse() expects:
             *
             * 2026-09-14T14:30
             *
             * Therefore, replace the space with T.
                 */
                endDate = LocalDateTime.parse(
                        endDateInput.replace(" ", "T")
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid end date/time."
                );

                System.out.println(
                        "Use the format: YYYY-MM-DD HH:MM"
                );

                return;
            }
        }

        /*
     * Create the NurseAssignment object.
         */
        NurseAssignment assignment = new NurseAssignment();

        assignment.setNurse(nurse);
        assignment.setPatient(patient);
        assignment.setAdmission(admission);
        assignment.setAssignmentDate(assignmentDate);
        assignment.setEndDate(endDate);
        assignment.setShift(shift);
        assignment.setStatus(status);
        assignment.setNotes(notes);

        /*
     * Send the assignment to the DAO.
     *
     * The DAO performs the SQL INSERT.
         */
        dao.addNurseAssignment(assignment);
    }


    /*
 * ============================================================
 * VIEW ALL NURSE ASSIGNMENTS
 * ============================================================
 *
 * Retrieves and displays all nurse assignments.
     */
    private static void viewAllNurseAssignments(
            NurseAssignmentDAO dao) {

        System.out.println();
        System.out.println(
                "----------- ALL NURSE ASSIGNMENTS -----------"
        );

        List<NurseAssignment> assignments
                = dao.findAllNurseAssignments();

        // Check whether there are any assignments.
        if (assignments.isEmpty()) {

            System.out.println(
                    "No nurse assignments found."
            );

            return;
        }

        /*
     * Display every assignment.
         */
        for (NurseAssignment assignment : assignments) {

            System.out.println("----------------------------------------");

            System.out.println(
                    "Assignment ID: "
                    + assignment.getId()
            );

            if (assignment.getNurse() != null) {

                System.out.println(
                        "Nurse Staff ID: "
                        + assignment.getNurse().getStaffId()
                );
            }

            if (assignment.getPatient() != null) {

                System.out.println(
                        "Patient ID: "
                        + assignment.getPatient().getPatientId()
                );
            }

            if (assignment.getAdmission() != null) {

                System.out.println(
                        "Admission ID: "
                        + assignment.getAdmission().getId()
                );
            }

            System.out.println(
                    "Assignment Date: "
                    + assignment.getAssignmentDate()
            );

            System.out.println(
                    "End Date: "
                    + assignment.getEndDate()
            );

            System.out.println(
                    "Shift: "
                    + assignment.getShift()
            );

            System.out.println(
                    "Status: "
                    + assignment.getStatus()
            );

            System.out.println(
                    "Notes: "
                    + assignment.getNotes()
            );
        }

        System.out.println("----------------------------------------");
    }


    /*
 * ============================================================
 * FIND NURSE ASSIGNMENT
 * ============================================================
 *
 * Finds one nurse assignment using its ID.
     */
    private static void findNurseAssignment(
            NurseAssignmentDAO dao) {

        System.out.println();
        System.out.println(
                "----------- FIND NURSE ASSIGNMENT -----------"
        );

        int id = readInt(
                "Enter Nurse Assignment ID: "
        );

        NurseAssignment assignment
                = dao.findNurseAssignmentById(id);

        // Assignment does not exist.
        if (assignment == null) {

            System.out.println(
                    "Nurse assignment not found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "Assignment ID: "
                + assignment.getId()
        );

        if (assignment.getNurse() != null) {

            System.out.println(
                    "Nurse Staff ID: "
                    + assignment.getNurse().getStaffId()
            );
        }

        if (assignment.getPatient() != null) {

            System.out.println(
                    "Patient ID: "
                    + assignment.getPatient().getPatientId()
            );
        }

        if (assignment.getAdmission() != null) {

            System.out.println(
                    "Admission ID: "
                    + assignment.getAdmission().getId()
            );
        }

        System.out.println(
                "Assignment Date: "
                + assignment.getAssignmentDate()
        );

        System.out.println(
                "End Date: "
                + assignment.getEndDate()
        );

        System.out.println(
                "Shift: "
                + assignment.getShift()
        );

        System.out.println(
                "Status: "
                + assignment.getStatus()
        );

        System.out.println(
                "Notes: "
                + assignment.getNotes()
        );
    }


    /*
 * ============================================================
 * UPDATE NURSE ASSIGNMENT
 * ============================================================
 *
 * Finds an existing assignment and updates its information.
     */
    private static void updateNurseAssignment(
            NurseAssignmentDAO dao) {

        System.out.println();
        System.out.println(
                "----------- UPDATE NURSE ASSIGNMENT -----------"
        );

        // Find the assignment to update.
        int id = readInt(
                "Enter Nurse Assignment ID: "
        );

        NurseAssignment existing
                = dao.findNurseAssignmentById(id);

        if (existing == null) {

            System.out.println(
                    "Nurse assignment not found."
            );

            return;
        }

        // Get updated nurse.
        int nurseId = readInt(
                "Enter new Nurse Staff ID: "
        );

        // Get updated patient.
        int patientId = readInt(
                "Enter new Patient ID: "
        );

        /*
     * Admission is optional.
         */
        String admissionInput = readString(
                "Enter new Admission ID (leave blank if none): "
        );

        // Get new assignment date/time.
        LocalDateTime assignmentDate = readDateTime(
                "Enter new Assignment Date/Time "
                + "(YYYY-MM-DD HH:MM): "
        );

        /*
     * Get new end date/time.
     * Leave blank if assignment is ongoing.
         */
        String endDateInput = readString(
                "Enter new End Date/Time "
                + "(leave blank if ongoing): "
        );

        String shift = readString(
                "Enter new Shift: "
        );

        String status = readString(
                "Enter new Status: "
        );

        String notes = readString(
                "Enter new Notes: "
        );

        /*
     * Create updated Nurse reference.
         */
        Nurse nurse = new Nurse();
        nurse.setStaffId(nurseId);

        /*
     * Create updated Patient reference.
         */
        Patient patient = new Patient();
        patient.setPatientId(patientId);

        /*
     * Create updated Admission reference if supplied.
         */
        Admission admission = null;

        if (!admissionInput.isEmpty()) {

            try {

                admission = new Admission();

                admission.setId(
                        Integer.parseInt(admissionInput)
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid Admission ID."
                );

                return;
            }
        }

        /*
     * Convert the optional end date/time.
         */
        LocalDateTime endDate = null;

        if (!endDateInput.isEmpty()) {

            try {

                endDate = LocalDateTime.parse(
                        endDateInput.replace(" ", "T")
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid end date/time."
                );

                System.out.println(
                        "Use the format: YYYY-MM-DD HH:MM"
                );

                return;
            }
        }

        /*
     * Update the existing assignment object.
         */
        existing.setNurse(nurse);
        existing.setPatient(patient);
        existing.setAdmission(admission);
        existing.setAssignmentDate(assignmentDate);
        existing.setEndDate(endDate);
        existing.setShift(shift);
        existing.setStatus(status);
        existing.setNotes(notes);

        /*
     * Send the updated assignment to the DAO.
         */
        dao.updateNurseAssignment(existing);
    }


    /*
 * ============================================================
 * DELETE NURSE ASSIGNMENT
 * ============================================================
 *
 * Deletes an assignment after confirmation.
     */
    private static void deleteNurseAssignment(
            NurseAssignmentDAO dao) {

        System.out.println();
        System.out.println(
                "----------- DELETE NURSE ASSIGNMENT -----------"
        );

        int id = readInt(
                "Enter Nurse Assignment ID: "
        );

        /*
     * Confirm that the assignment exists first.
         */
        NurseAssignment assignment
                = dao.findNurseAssignmentById(id);

        if (assignment == null) {

            System.out.println(
                    "Nurse assignment not found."
            );

            return;
        }

        /*
     * Ask the user to confirm deletion.
         */
        String confirmation = readString(
                "Are you sure you want to delete this assignment? (Y/N): "
        );

        if (confirmation.equalsIgnoreCase("Y")) {

            dao.deleteNurseAssignment(id);

        } else {

            System.out.println(
                    "Delete cancelled."
            );
        }
    }


    /*
 * ============================================================
 * VIEW PATIENT NURSE ASSIGNMENTS
 * ============================================================
 *
 * Displays all nurses assigned to a particular patient.
     */
    private static void viewPatientNurseAssignments(
            NurseAssignmentDAO dao) {

        System.out.println();
        System.out.println(
                "----------- PATIENT NURSE ASSIGNMENTS -----------"
        );

        int patientId = readInt(
                "Enter Patient ID: "
        );

        List<NurseAssignment> assignments
                = dao.findNurseAssignmentsByPatient(patientId);

        if (assignments.isEmpty()) {

            System.out.println(
                    "No nurse assignments found for this patient."
            );

            return;
        }

        /*
     * Display every assignment belonging to the patient.
         */
        for (NurseAssignment assignment : assignments) {

            System.out.println("----------------------------------------");

            System.out.println(
                    "Assignment ID: "
                    + assignment.getId()
            );

            if (assignment.getNurse() != null) {

                System.out.println(
                        "Nurse Staff ID: "
                        + assignment.getNurse().getStaffId()
                );
            }

            System.out.println(
                    "Assignment Date: "
                    + assignment.getAssignmentDate()
            );

            System.out.println(
                    "End Date: "
                    + assignment.getEndDate()
            );

            System.out.println(
                    "Shift: "
                    + assignment.getShift()
            );

            System.out.println(
                    "Status: "
                    + assignment.getStatus()
            );

            System.out.println(
                    "Notes: "
                    + assignment.getNotes()
            );
        }
    }


    /*
 * ============================================================
 * VIEW NURSE ASSIGNMENTS
 * ============================================================
 *
 * Displays all patients currently or previously assigned
 * to a particular nurse.
 *
 * IMPORTANT:
 * The user enters the Nurse's Staff ID because that is what
 * the Java Nurse model exposes through getStaffID().
     */
    private static void viewNurseAssignments(
            NurseAssignmentDAO dao) {

        System.out.println();
        System.out.println(
                "----------- NURSE ASSIGNMENTS -----------"
        );

        int nurseId = readInt(
                "Enter Nurse Staff ID: "
        );

        List<NurseAssignment> assignments
                = dao.findNurseAssignmentsByNurse(nurseId);

        if (assignments.isEmpty()) {

            System.out.println(
                    "No assignments found for this nurse."
            );

            return;
        }

        /*
     * Display every assignment belonging to the nurse.
         */
        for (NurseAssignment assignment : assignments) {

            System.out.println("----------------------------------------");

            System.out.println(
                    "Assignment ID: "
                    + assignment.getId()
            );

            if (assignment.getPatient() != null) {

                System.out.println(
                        "Patient ID: "
                        + assignment.getPatient().getPatientId()
                );
            }

            System.out.println(
                    "Assignment Date: "
                    + assignment.getAssignmentDate()
            );

            System.out.println(
                    "End Date: "
                    + assignment.getEndDate()
            );

            System.out.println(
                    "Shift: "
                    + assignment.getShift()
            );

            System.out.println(
                    "Status: "
                    + assignment.getStatus()
            );

            System.out.println(
                    "Notes: "
                    + assignment.getNotes()
            );
        }
    }


    /*
 * ============================================================
 * READ DATE AND TIME
 * ============================================================
 *
 * Reads a LocalDateTime from the console.
 *
 * User enters:
 *
 *     YYYY-MM-DD HH:MM
 *
 * Example:
 *
 *     2026-09-14 14:30
 *
 * Internally, Java parses:
 *
 *     2026-09-14T14:30
     */
    private static LocalDateTime readDateTime(
            String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {

                return LocalDateTime.parse(
                        input.replace(" ", "T")
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date/time."
                );

                System.out.println(
                        "Please use the format: YYYY-MM-DD HH:MM"
                );
            }
        }
    }

    // =========================================================
    // NURSE MENU
    // =========================================================
    private static void nurseMenu() {

        while (true) {

            System.out.println();

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "            NURSE MANAGEMENT"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "1. Register Nurse"
            );

            System.out.println(
                    "2. View All Nurses"
            );

            System.out.println(
                    "3. Find Nurse"
            );

            System.out.println(
                    "4. Update Nurse"
            );

            System.out.println(
                    "5. Delete Nurse"
            );

            System.out.println(
                    "0. Back"
            );

            System.out.println(
                    "========================================"
            );

            int choice
                    = readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    registerNurse();
                    break;

                case 2:
                    viewAllNurses();
                    break;

                case 3:
                    findNurse();
                    break;

                case 4:
                    updateNurse();
                    break;

                case 5:
                    deleteNurse();
                    break;

                case 0:
                    return;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    // =========================================================
    // REGISTER NURSE
    // =========================================================
    private static void registerNurse() {

        System.out.println();

        System.out.println(
                "========== REGISTER NURSE =========="
        );

        System.out.println();

        System.out.println(
                "----- PERSONAL INFORMATION -----"
        );

        System.out.print(
                "First Name: "
        );

        String firstName
                = scanner.nextLine();

        System.out.print(
                "Last Name: "
        );

        String lastName
                = scanner.nextLine();

        System.out.print(
                "Gender (M/F): "
        );

        char gender
                = scanner.nextLine()
                        .charAt(0);

        System.out.print(
                "Date of Birth (yyyy-MM-dd): "
        );

        LocalDate dateOfBirth
                = LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.print(
                "Phone: "
        );

        String phone
                = scanner.nextLine();

        System.out.print(
                "Email: "
        );

        String email
                = scanner.nextLine();

        System.out.print(
                "Street: "
        );

        String street
                = scanner.nextLine();

        System.out.print(
                "City: "
        );

        String city
                = scanner.nextLine();

        System.out.print(
                "Country: "
        );

        String country
                = scanner.nextLine();

        System.out.println();

        System.out.println(
                "----- STAFF INFORMATION -----"
        );

        System.out.print(
                "Employment Date (yyyy-MM-dd): "
        );

        LocalDate employmentDate
                = LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.print(
                "Salary: "
        );

        double salary
                = Double.parseDouble(
                        scanner.nextLine()
                );

        System.out.println();

        System.out.println(
                "----- DEPARTMENT INFORMATION -----"
        );

        int departmentId
                = readInt(
                        "Department ID: "
                );

        System.out.print(
                "Department Name: "
        );

        String departmentName
                = scanner.nextLine();

        Department department
                = new Department();

        department.setId(
                departmentId
        );

        department.setName(
                departmentName
        );

        System.out.println();

        System.out.println(
                "----- NURSE INFORMATION -----"
        );

        System.out.print(
                "Nursing License: "
        );

        String nursingLicense
                = scanner.nextLine();

        System.out.print(
                "Qualification: "
        );

        String qualification
                = scanner.nextLine();

        Nurse nurse
                = new Nurse(
                        firstName,
                        lastName,
                        gender,
                        dateOfBirth,
                        phone,
                        email,
                        street,
                        city,
                        country,
                        0,
                        employmentDate,
                        salary,
                        department,
                        nursingLicense,
                        qualification
                );

        boolean success
                = nurseService.registerNurse(
                        nurse
                );

        if (success) {

            System.out.println();

            System.out.println(
                    "Nurse registered successfully."
            );

        } else {

            System.out.println();

            System.out.println(
                    "Failed to register nurse."
            );
        }
    }

    // =========================================================
    // VIEW ALL NURSES
    // =========================================================
    private static void viewAllNurses() {

        List<Nurse> nurses
                = nurseService.getAllNurses();

        nurseView.displayNurses(
                nurses
        );
    }

    // =========================================================
    // FIND NURSE
    // =========================================================
    private static void findNurse() {

        int staffId
                = readInt(
                        "Enter Nurse/Staff ID: "
                );

        Nurse nurse
                = nurseService.getNurseById(
                        staffId
                );

        nurseView.displayNurse(
                nurse
        );
    }

    // =========================================================
    // UPDATE NURSE
    // =========================================================
    private static void updateNurse() {

        int staffId
                = readInt(
                        "Enter Nurse/Staff ID to update: "
                );

        Nurse nurse
                = nurseService.getNurseById(
                        staffId
                );

        if (nurse == null) {

            System.out.println(
                    "Nurse not found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "Current nurse information:"
        );

        nurseView.displayNurse(
                nurse
        );

        System.out.println();

        System.out.println(
                "Enter new nurse information."
        );

        System.out.println();

        System.out.println(
                "----- PERSONAL INFORMATION -----"
        );

        System.out.print(
                "First Name: "
        );

        nurse.setFirstName(
                scanner.nextLine()
        );

        System.out.print(
                "Last Name: "
        );

        nurse.setLastName(
                scanner.nextLine()
        );

        System.out.print(
                "Gender (M/F): "
        );

        nurse.setGender(
                scanner.nextLine()
                        .charAt(0)
        );

        System.out.print(
                "Date of Birth (yyyy-MM-dd): "
        );

        nurse.setDateOfBirth(
                LocalDate.parse(
                        scanner.nextLine()
                )
        );

        System.out.print(
                "Phone: "
        );

        nurse.setPhone(
                scanner.nextLine()
        );

        System.out.print(
                "Email: "
        );

        nurse.setEmail(
                scanner.nextLine()
        );

        System.out.print(
                "Street: "
        );

        nurse.setStreet(
                scanner.nextLine()
        );

        System.out.print(
                "City: "
        );

        nurse.setCity(
                scanner.nextLine()
        );

        System.out.print(
                "Country: "
        );

        nurse.setCountry(
                scanner.nextLine()
        );

        System.out.println();

        System.out.println(
                "----- STAFF INFORMATION -----"
        );

        System.out.print(
                "Employment Date (yyyy-MM-dd): "
        );

        nurse.setEmploymentDate(
                LocalDate.parse(
                        scanner.nextLine()
                )
        );

        System.out.print(
                "Salary: "
        );

        nurse.setSalary(
                Double.parseDouble(
                        scanner.nextLine()
                )
        );

        System.out.println();

        System.out.println(
                "----- DEPARTMENT INFORMATION -----"
        );

        int departmentId
                = readInt(
                        "Department ID: "
                );

        System.out.print(
                "Department Name: "
        );

        String departmentName
                = scanner.nextLine();

        Department department
                = new Department();

        department.setId(
                departmentId
        );

        department.setName(
                departmentName
        );

        nurse.setDepartment(
                department
        );

        System.out.println();

        System.out.println(
                "----- NURSE INFORMATION -----"
        );

        System.out.print(
                "Nursing License: "
        );

        nurse.setNursingLicense(
                scanner.nextLine()
        );

        System.out.print(
                "Qualification: "
        );

        nurse.setQualification(
                scanner.nextLine()
        );

        boolean success
                = nurseService.updateNurse(
                        nurse
                );

        if (success) {

            System.out.println();

            System.out.println(
                    "Nurse updated successfully."
            );

        } else {

            System.out.println();

            System.out.println(
                    "Nurse update failed."
            );
        }
    }

    // =========================================================
    // DELETE NURSE
    // =========================================================
    private static void deleteNurse() {

        int staffId
                = readInt(
                        "Enter Nurse/Staff ID to delete: "
                );

        Nurse nurse
                = nurseService.getNurseById(
                        staffId
                );

        if (nurse == null) {

            System.out.println(
                    "Nurse not found."
            );

            return;
        }

        nurseView.displayNurse(
                nurse
        );

        System.out.print(
                "Are you sure you want to delete this nurse? (Y/N): "
        );

        String answer
                = scanner.nextLine();

        if (!answer.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        boolean success
                = nurseService.deleteNurse(
                        staffId
                );

        if (success) {

            System.out.println(
                    "Nurse deleted successfully."
            );

        } else {

            System.out.println(
                    "Nurse deletion failed."
            );
        }
    }
// ============================================================
// PHARMACIST MANAGEMENT MENU
// ============================================================
// Handles all pharmacist-related operations.
// ============================================================

    private static void pharmacistManagementMenu() {

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("        PHARMACIST MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Register Pharmacist");
            System.out.println("2. View All Pharmacists");
            System.out.println("3. Find Pharmacist");
            System.out.println("4. Update Pharmacist");
            System.out.println("5. Delete Pharmacist");
            System.out.println("0. Back");
            System.out.println("========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addPharmacist();
                    break;

                case 2:
                    viewAllPharmacists();
                    break;

                case 3:
                    findPharmacist();
                    break;

                case 4:
                    updatePharmacist();
                    break;

                case 5:
                    deletePharmacist();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

// ============================================================
// ADD PHARMACIST
// ============================================================
    private static void addPharmacist() {

        PharmacistDAO pharmacistDAO = new PharmacistDAO();

        System.out.println();
        System.out.println("========================================");
        System.out.println("          REGISTER PHARMACIST");
        System.out.println("========================================");

        Pharmacist pharmacist = new Pharmacist();

        // --------------------------------------------------------
        // Personal information
        // --------------------------------------------------------
        pharmacist.setFirstName(readString("First name: "));
        pharmacist.setLastName(readString("Last name: "));

        String gender = readString("Gender (M/F): ");

        if (!gender.isEmpty()) {
            pharmacist.setGender(gender.charAt(0));
        }

        pharmacist.setDateOfBirth(
                readDate("Date of birth (YYYY-MM-DD): ")
        );

        pharmacist.setPhone(readString("Phone: "));
        pharmacist.setEmail(readString("Email: "));
        pharmacist.setStreet(readString("Street: "));
        pharmacist.setCity(readString("City: "));
        pharmacist.setCountry(readString("Country: "));

        // --------------------------------------------------------
        // Staff information
        // --------------------------------------------------------
        pharmacist.setEmploymentDate(
                readDate("Employment date (YYYY-MM-DD): ")
        );

        pharmacist.setSalary(
                readDouble("Salary: ")
        );

        int departmentId
                = readInt("Department ID: ");

        Department department = new Department();
        department.setId(departmentId);

        pharmacist.setDepartment(department);

        // --------------------------------------------------------
        // Pharmacist-specific information
        // --------------------------------------------------------
        pharmacist.setQualification(
                readString("Qualification: ")
        );

        pharmacist.setLicenseNumber(
                readString("License number: ")
        );

        // --------------------------------------------------------
        // Save pharmacist
        // --------------------------------------------------------
        if (pharmacistDAO.addPharmacist(pharmacist)) {

            System.out.println();
            System.out.println("Pharmacist registered successfully.");
            System.out.println("Staff ID: " + pharmacist.getStaffId());

        } else {

            System.out.println();
            System.out.println("Failed to register pharmacist.");
        }
    }

// ============================================================
// VIEW ALL PHARMACISTS
// ============================================================
    private static void viewAllPharmacists() {

        PharmacistDAO pharmacistDAO
                = new PharmacistDAO();

        System.out.println();
        System.out.println("========================================");
        System.out.println("          ALL PHARMACISTS");
        System.out.println("========================================");

        List<Pharmacist> pharmacists
                = pharmacistDAO.findAllPharmacists();

        if (pharmacists.isEmpty()) {

            System.out.println("No pharmacists found.");
            return;
        }

        for (Pharmacist pharmacist : pharmacists) {

            System.out.println("----------------------------------------");

            System.out.println(
                    "Staff ID:       "
                    + pharmacist.getStaffId()
            );

            System.out.println(
                    "Name:            "
                    + pharmacist.getFirstName()
                    + " "
                    + pharmacist.getLastName()
            );

            System.out.println(
                    "Gender:          "
                    + pharmacist.getGender()
            );

            System.out.println(
                    "Phone:           "
                    + pharmacist.getPhone()
            );

            System.out.println(
                    "Email:           "
                    + pharmacist.getEmail()
            );

            System.out.println(
                    "Qualification:   "
                    + pharmacist.getQualification()
            );

            System.out.println(
                    "License Number:  "
                    + pharmacist.getLicenseNumber()
            );

            System.out.println(
                    "Salary:          "
                    + pharmacist.getSalary()
            );

            if (pharmacist.getDepartment() != null) {

                System.out.println(
                        "Department ID:   "
                        + pharmacist.getDepartment().getId()
                );

                System.out.println(
                        "Department:      "
                        + pharmacist.getDepartment().getName()
                );
            }
        }

        System.out.println("----------------------------------------");
    }

// ============================================================
// FIND PHARMACIST
// ============================================================
    private static void findPharmacist() {

        PharmacistDAO pharmacistDAO
                = new PharmacistDAO();

        System.out.println();
        System.out.println("========================================");
        System.out.println("           FIND PHARMACIST");
        System.out.println("========================================");

        int staffId
                = readInt("Enter Staff ID: ");

        Pharmacist pharmacist
                = pharmacistDAO.findPharmacistById(staffId);

        if (pharmacist == null) {

            System.out.println("Pharmacist not found.");
            return;
        }

        System.out.println();
        System.out.println("Pharmacist found.");
        System.out.println("----------------------------------------");

        System.out.println(
                "Staff ID:       "
                + pharmacist.getStaffId()
        );

        System.out.println(
                "Name:            "
                + pharmacist.getFirstName()
                + " "
                + pharmacist.getLastName()
        );

        System.out.println(
                "Gender:          "
                + pharmacist.getGender()
        );

        System.out.println(
                "Date of Birth:   "
                + pharmacist.getDateOfBirth()
        );

        System.out.println(
                "Phone:           "
                + pharmacist.getPhone()
        );

        System.out.println(
                "Email:           "
                + pharmacist.getEmail()
        );

        System.out.println(
                "Qualification:   "
                + pharmacist.getQualification()
        );

        System.out.println(
                "License Number:  "
                + pharmacist.getLicenseNumber()
        );

        System.out.println(
                "Employment Date: "
                + pharmacist.getEmploymentDate()
        );

        System.out.println(
                "Salary:          "
                + pharmacist.getSalary()
        );

        if (pharmacist.getDepartment() != null) {

            System.out.println(
                    "Department ID:   "
                    + pharmacist.getDepartment().getId()
            );

            System.out.println(
                    "Department:      "
                    + pharmacist.getDepartment().getName()
            );
        }

        System.out.println("----------------------------------------");
    }

// ============================================================
// UPDATE PHARMACIST
// ============================================================
    private static void updatePharmacist() {

        PharmacistDAO pharmacistDAO
                = new PharmacistDAO();

        System.out.println();
        System.out.println("========================================");
        System.out.println("          UPDATE PHARMACIST");
        System.out.println("========================================");

        int staffId
                = readInt("Enter Staff ID: ");

        Pharmacist pharmacist
                = pharmacistDAO.findPharmacistById(staffId);

        if (pharmacist == null) {

            System.out.println("Pharmacist not found.");
            return;
        }

        System.out.println();
        System.out.println("Enter the updated information.");

        pharmacist.setFirstName(
                readString("First name: ")
        );

        pharmacist.setLastName(
                readString("Last name: ")
        );

        String gender
                = readString("Gender (M/F): ");

        if (!gender.isEmpty()) {
            pharmacist.setGender(
                    gender.charAt(0)
            );
        }

        pharmacist.setDateOfBirth(
                readDate("Date of birth (YYYY-MM-DD): ")
        );

        pharmacist.setPhone(
                readString("Phone: ")
        );

        pharmacist.setEmail(
                readString("Email: ")
        );

        pharmacist.setStreet(
                readString("Street: ")
        );

        pharmacist.setCity(
                readString("City: ")
        );

        pharmacist.setCountry(
                readString("Country: ")
        );

        pharmacist.setEmploymentDate(
                readDate("Employment date (YYYY-MM-DD): ")
        );

        pharmacist.setSalary(
                readDouble("Salary: ")
        );

        int departmentId
                = readInt("Department ID: ");

        Department department
                = new Department();

        department.setId(
                departmentId
        );

        pharmacist.setDepartment(
                department
        );

        pharmacist.setQualification(
                readString("Qualification: ")
        );

        pharmacist.setLicenseNumber(
                readString("License number: ")
        );

        if (pharmacistDAO.update(pharmacist)) {

            System.out.println();
            System.out.println(
                    "Pharmacist updated successfully."
            );

        } else {

            System.out.println();
            System.out.println(
                    "Failed to update pharmacist."
            );
        }
    }

// ============================================================
// READ DATE
// ============================================================
// Reads a date from the user and converts it to LocalDate.
//
// Expected format:
// YYYY-MM-DD
//
// If the user enters an invalid date, the method keeps asking
// until a valid date is provided.
// ============================================================
    private static LocalDate readDate(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {

                return LocalDate.parse(input);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date. Please use the format YYYY-MM-DD."
                );
            }
        }
    }

// ============================================================
// READ DOUBLE
// ============================================================
// Reads a decimal number from the user.
//
// This is useful for values such as:
// - Salary
// - Price
// - Payment amount
// - Other monetary values
//
// If the user enters something that is not a valid number,
// the method keeps asking until a valid number is provided.
// ============================================================
    private static double readDouble(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {

                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid number. Please enter a valid amount."
                );
            }
        }
    }

// ============================================================
// DELETE PHARMACIST
// ============================================================
    private static void deletePharmacist() {

        PharmacistDAO pharmacistDAO
                = new PharmacistDAO();

        System.out.println();
        System.out.println("========================================");
        System.out.println("          DELETE PHARMACIST");
        System.out.println("========================================");

        int staffId
                = readInt("Enter Staff ID: ");

        Pharmacist pharmacist
                = pharmacistDAO.findPharmacistById(staffId);

        if (pharmacist == null) {

            System.out.println("Pharmacist not found.");
            return;
        }

        System.out.println();
        System.out.println(
                "Pharmacist: "
                + pharmacist.getFirstName()
                + " "
                + pharmacist.getLastName()
        );

        String confirmation
                = readString(
                        "Are you sure you want to delete this pharmacist? (Y/N): "
                );

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Delete operation cancelled."
            );

            return;
        }

        if (pharmacistDAO.delete(staffId)) {

            System.out.println();
            System.out.println(
                    "Pharmacist deleted successfully."
            );

        } else {

            System.out.println();
            System.out.println(
                    "Failed to delete pharmacist."
            );
        }
    }

    // =========================================================
    // APPOINTMENT MENU
    // =========================================================
    private static void appointmentMenu() {

        while (true) {

            System.out.println();

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "        APPOINTMENT MANAGEMENT"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "1. Create Appointment"
            );

            System.out.println(
                    "2. View All Appointments"
            );

            System.out.println(
                    "3. Find Appointment"
            );

            System.out.println(
                    "4. Update Appointment"
            );

            System.out.println(
                    "5. Delete Appointment"
            );

            System.out.println(
                    "6. View Patient Appointments"
            );

            System.out.println(
                    "7. View Doctor Appointments"
            );

            System.out.println(
                    "0. Back"
            );

            System.out.println(
                    "========================================"
            );

            int choice
                    = readInt(
                            "Enter your choice: "
                    );

            switch (choice) {

                case 1:
                    createAppointment();
                    break;

                case 2:
                    viewAllAppointments();
                    break;

                case 3:
                    findAppointment();
                    break;

                case 4:
                    updateAppointment();
                    break;

                case 5:
                    deleteAppointment();
                    break;

                case 6:
                    viewPatientAppointments();
                    break;

                case 7:
                    viewDoctorAppointments();
                    break;

                case 0:
                    return;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    // =========================================================
    // CREATE APPOINTMENT
    // =========================================================
    private static void createAppointment() {

        System.out.println();

        System.out.println(
                "========== CREATE APPOINTMENT =========="
        );

        // =====================================================
        // PATIENT
        // =====================================================
        int patientId
                = readInt(
                        "Enter Patient ID: "
                );

        Patient patient
                = patientService.getPatientById(
                        patientId
                );

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "Patient: "
                + patient.getFirstName()
                + " "
                + patient.getLastName()
        );

        // =====================================================
        // DOCTOR
        // =====================================================
        int doctorId
                = readInt(
                        "Enter Doctor/Staff ID: "
                );

        Doctor doctor
                = doctorService.getDoctorById(
                        doctorId
                );

        if (doctor == null) {

            System.out.println(
                    "Doctor not found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "Doctor: Dr. "
                + doctor.getFirstName()
                + " "
                + doctor.getLastName()
        );

        System.out.println(
                "Specialization: "
                + doctor.getSpecialization()
        );

        // =====================================================
        // APPOINTMENT DATE
        // =====================================================
        System.out.println();

        System.out.println(
                "Date format: yyyy-MM-dd HH:mm"
        );

        System.out.print(
                "Appointment Date: "
        );

        String dateInput
                = scanner.nextLine();

        LocalDateTime appointmentDate;

        try {

            appointmentDate
                    = LocalDateTime.parse(
                            dateInput,
                            DATE_TIME_FORMATTER
                    );

        } catch (Exception e) {

            System.out.println(
                    "Invalid date/time format."
            );

            System.out.println(
                    "Please use: yyyy-MM-dd HH:mm"
            );

            return;
        }

        // =====================================================
        // REASON
        // =====================================================
        System.out.print(
                "Reason for Appointment: "
        );

        String reason
                = scanner.nextLine();

        // =====================================================
        // STATUS
        // =====================================================
        String status
                = selectAppointmentStatus();

        if (status == null) {
            return;
        }

        // =====================================================
        // NOTES
        // =====================================================
        System.out.print(
                "Notes: "
        );

        String notes
                = scanner.nextLine();

        // =====================================================
        // CREATE OBJECT
        // =====================================================
        Appointment appointment
                = new Appointment();

        // ID is generated by SQL Server.
        // Do not set the ID manually.
        appointment.setPatient(
                patient
        );

        appointment.setDoctor(
                doctor
        );

        appointment.setAppointmentDate(
                appointmentDate
        );

        appointment.setReason(
                reason
        );

        appointment.setStatus(
                status
        );

        appointment.setNotes(
                notes
        );

        // =====================================================
        // SAVE
        // =====================================================
        boolean success
                = appointmentService.addAppointment(
                        appointment
                );

        if (success) {

            System.out.println();

            System.out.println(
                    "Appointment created successfully."
            );

            if (appointment.getId() > 0) {

                System.out.println(
                        "Appointment ID: "
                        + appointment.getId()
                );
            }

        } else {

            System.out.println();

            System.out.println(
                    "Failed to create appointment."
            );
        }
    }

    // =========================================================
    // SELECT APPOINTMENT STATUS
    // =========================================================
    private static String selectAppointmentStatus() {

        System.out.println();

        System.out.println(
                "Appointment Status"
        );

        System.out.println(
                "1. Scheduled"
        );

        System.out.println(
                "2. Completed"
        );

        System.out.println(
                "3. Cancelled"
        );

        System.out.println(
                "4. Pending"
        );

        int choice
                = readInt(
                        "Select status: "
                );

        switch (choice) {

            case 1:
                return "Scheduled";

            case 2:
                return "Completed";

            case 3:
                return "Cancelled";

            case 4:
                return "Pending";

            default:

                System.out.println(
                        "Invalid status."
                );

                return null;
        }
    }

    // =========================================================
    // VIEW ALL APPOINTMENTS
    // =========================================================
    private static void viewAllAppointments() {

        List<Appointment> appointments
                = appointmentService.getAllAppointments();

        appointmentView.displayAppointments(
                appointments
        );
    }

    // =========================================================
    // FIND APPOINTMENT
    // =========================================================
    private static void findAppointment() {

        int appointmentId
                = readInt(
                        "Enter Appointment ID: "
                );

        Appointment appointment
                = appointmentService.getAppointmentById(
                        appointmentId
                );

        appointmentView.displayAppointment(
                appointment
        );
    }

    // =========================================================
    // UPDATE APPOINTMENT
    // =========================================================
    private static void updateAppointment() {

        int appointmentId
                = readInt(
                        "Enter Appointment ID to update: "
                );

        Appointment appointment
                = appointmentService.getAppointmentById(
                        appointmentId
                );

        if (appointment == null) {

            System.out.println(
                    "Appointment not found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "Current appointment information:"
        );

        appointmentView.displayAppointment(
                appointment
        );

        // =====================================================
        // PATIENT
        // =====================================================
        int patientId
                = readInt(
                        "Enter new Patient ID: "
                );

        Patient patient
                = patientService.getPatientById(
                        patientId
                );

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

            return;
        }

        appointment.setPatient(
                patient
        );

        // =====================================================
        // DOCTOR
        // =====================================================
        int doctorId
                = readInt(
                        "Enter new Doctor/Staff ID: "
                );

        Doctor doctor
                = doctorService.getDoctorById(
                        doctorId
                );

        if (doctor == null) {

            System.out.println(
                    "Doctor not found."
            );

            return;
        }

        appointment.setDoctor(
                doctor
        );

        // =====================================================
        // DATE
        // =====================================================
        System.out.println();

        System.out.println(
                "Date format: yyyy-MM-dd HH:mm"
        );

        System.out.print(
                "Appointment Date: "
        );

        String dateInput
                = scanner.nextLine();

        try {

            appointment.setAppointmentDate(
                    LocalDateTime.parse(
                            dateInput,
                            DATE_TIME_FORMATTER
                    )
            );

        } catch (Exception e) {

            System.out.println(
                    "Invalid date/time format."
            );

            System.out.println(
                    "Please use: yyyy-MM-dd HH:mm"
            );

            return;
        }

        // =====================================================
        // REASON
        // =====================================================
        System.out.print(
                "Reason: "
        );

        appointment.setReason(
                scanner.nextLine()
        );

        // =====================================================
        // STATUS
        // =====================================================
        String status
                = selectAppointmentStatus();

        if (status == null) {
            return;
        }

        appointment.setStatus(
                status
        );

        // =====================================================
        // NOTES
        // =====================================================
        System.out.print(
                "Notes: "
        );

        appointment.setNotes(
                scanner.nextLine()
        );

        // =====================================================
        // UPDATE
        // =====================================================
        boolean success
                = appointmentService.updateAppointment(
                        appointment
                );

        if (success) {

            System.out.println();

            System.out.println(
                    "Appointment updated successfully."
            );

        } else {

            System.out.println();

            System.out.println(
                    "Appointment update failed."
            );
        }
    }

    // =========================================================
    // DELETE APPOINTMENT
    // =========================================================
    private static void deleteAppointment() {

        int appointmentId
                = readInt(
                        "Enter Appointment ID to delete: "
                );

        Appointment appointment
                = appointmentService.getAppointmentById(
                        appointmentId
                );

        if (appointment == null) {

            System.out.println(
                    "Appointment not found."
            );

            return;
        }

        appointmentView.displayAppointment(
                appointment
        );

        System.out.print(
                "Are you sure you want to delete this appointment? (Y/N): "
        );

        String answer
                = scanner.nextLine();

        if (!answer.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        boolean success
                = appointmentService.deleteAppointment(
                        appointmentId
                );

        if (success) {

            System.out.println(
                    "Appointment deleted successfully."
            );

        } else {

            System.out.println(
                    "Appointment deletion failed."
            );
        }
    }

    // =========================================================
    // VIEW PATIENT APPOINTMENTS
    // =========================================================
    private static void viewPatientAppointments() {

        int patientId
                = readInt(
                        "Enter Patient ID: "
                );

        Patient patient
                = patientService.getPatientById(
                        patientId
                );

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "Appointments for "
                + patient.getFirstName()
                + " "
                + patient.getLastName()
        );

        List<Appointment> appointments
                = appointmentService.getAppointmentsByPatient(
                        patientId
                );

        appointmentView.displayAppointments(
                appointments
        );
    }

    // =========================================================
    // VIEW DOCTOR APPOINTMENTS
    // =========================================================
    private static void viewDoctorAppointments() {

        int doctorId
                = readInt(
                        "Enter Doctor/Staff ID: "
                );

        Doctor doctor
                = doctorService.getDoctorById(
                        doctorId
                );

        if (doctor == null) {

            System.out.println(
                    "Doctor not found."
            );

            return;
        }

        System.out.println();

        System.out.println(
                "Appointments for Dr. "
                + doctor.getFirstName()
                + " "
                + doctor.getLastName()
        );

        List<Appointment> appointments
                = appointmentService.getAppointmentsByDoctor(
                        doctorId
                );

        appointmentView.displayAppointments(
                appointments
        );
    }

    // =========================================================
    // READ INTEGER
    // =========================================================
    private static int readInt(
            String message
    ) {

        while (true) {

            try {

                System.out.print(
                        message
                );

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}
