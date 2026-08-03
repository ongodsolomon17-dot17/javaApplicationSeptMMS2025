package school;

import java.time.LocalDate;

public class GraduateStudent extends Student {
      private String finalYearProject;

    public GraduateStudent(int studentID, String firstName, 
            String lastName, char gender, LocalDate dOB, String phoneNumber,String finalYearProject) {
        super(studentID, firstName, lastName, gender, dOB, phoneNumber);
        this.finalYearProject = finalYearProject;
           }

    public String getFinalYearProject() {
        return finalYearProject;
    }

    public void setFinalYearProject(String finalYearProject) {
        this.finalYearProject = finalYearProject;
    }
    
@Override
    public void displayStudentInfo(){
    super.displayStudentInfo();
    System.out.println("FinalYear Project" +finalYearProject);
    }
    }
    
