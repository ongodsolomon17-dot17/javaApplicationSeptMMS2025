package school;

 import java.time.LocalDate;


public class UnderGraduateStudent extends Student {
   
    private int level;
    
    public UnderGraduateStudent(int studentID, String firstName, String lastName, 
            char gender, LocalDate dOB, String phoneNumber,int level) {
        super(studentID, firstName, lastName, gender, dOB, phoneNumber);
        
        this.level = level;
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
@Override
    public void displayStudentInfo(){
    super.displayStudentInfo();
    System.out.println("Level" +level);
    }
}