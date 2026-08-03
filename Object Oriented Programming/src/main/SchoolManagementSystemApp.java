package main;

import java.time.LocalDate;
import java.util.Scanner;
import school.GraduateStudent;
import school.Student;
import school.UnderGraduateStudent;

public class SchoolManagementSystemApp {
    public static void main(String[] args){
     Scanner scan = new Scanner(System.in);
     
     System.out.println("Enter one for Graduate Student");
          System.out.println("Enter 2 for Graduate Student");

         System.out.println("Enter your choice");
        int choice = scan.nextInt();
        scan.nextLine();
        
        switch(choice){
            case 1 -> {
            System.out.println("Enter your StudentId");
             int studentID = scan.nextInt();
    scan.nextLine();
          System.out.println("Enter Student first name");
          String firstName = scan.nextLine();


          System.out.print("Enter student last name");
          String lastName = scan.nextLine();

          
                 System.out.println("Enter student gender: ");
                 char gender = scan.next().charAt(0);
                scan.nextLine();
                 
                 System.out.println("Enter Student dob: ");
                 String dateOfBirth = scan.nextLine();

                 
                 System.out.println("Enter Student phone number: ");
                 String phoneNumber = scan.nextLine();

                 
                 System.out.print("Enter Final Year Project: ");
                 String finalYearProject = scan.nextLine();
                scan.nextLine();
                GraduateStudent gStudent = 
                         new GraduateStudent(studentID, firstName, lastName, gender,
                               LocalDate.parse(dateOfBirth), phoneNumber,finalYearProject);
                gStudent.displayStudentInfo();
        }
            case 2 -> {
             System.out.print("Enter your StudentId: ");
         int studentID = scan.nextInt();
scan.nextLine();
          System.out.print("Enter Student first name: ");
          String firstName = scan.nextLine();


          System.out.print("Enter student last name: ");
          String lastName = scan.nextLine();

          
                 System.out.print("Enter student gender: ");
                 char gender = scan.next().charAt(0);
                    scan.nextLine();
                 
                 System.out.print("Enter Student dob: ");
                 String dateOfBirth = scan.nextLine();

                 
                 System.out.print("Enter Student phone number: ");
                 String phoneNumber = scan.nextLine();

                 
                 System.out.print("Enter level: ");
                 int level = scan.nextInt();
                 scan.nextLine();
                UnderGraduateStudent ugstudent = 
                         new UnderGraduateStudent(studentID, firstName, lastName, gender,
                               LocalDate.parse(dateOfBirth), phoneNumber,level);
                                               ugstudent.displayStudentInfo();

        }
            case 3->{
                System.out.print("Choose the following option");
                System.out.println("Eenter 1: Amount only");
                System.out.println("Enter 2: Amount, fullName only");
                System.out.println("Enter 3: Amount, fullName, paymentMethod only");
                
                System.out.println("Enter your choice");
                int myOption = scan.nextInt();
                
                switch(myOption){
                    case 1:{
                        System.out.println("Enter studentID");
                        int studentID = scan.nextInt();
                        System.out.print("Enter Student first name: ");
          String firstName = scan.nextLine();


          System.out.print("Enter student last name: ");
          String lastName = scan.nextLine();

          
                 System.out.print("Enter student gender: ");
                 char gender = scan.next().charAt(0);
                    scan.nextLine();
                 
                 System.out.print("Enter Student dob: ");
                 String dateOfBirth = scan.nextLine();

                 
                 System.out.print("Enter Student phone number: ");
                 String phoneNumber = scan.nextLine();
                    
                 Student student = new Student(studentID, firstName, lastName, gender,
                               LocalDate.parse(dateOfBirth), phoneNumber);

                        System.out.println("Enter amount");
                        double amount = scan.nextDouble();
                        student.payFees(amount);
                    
                                         }
                    case 2:{
                    }
                    case 3:{
                    }
                    default :{
                    System.out.println("invalid input");
                    }
                }
                
            
            }
            
            
                    
        default -> System.out.print("Invalid Input");
    }
    }
    }