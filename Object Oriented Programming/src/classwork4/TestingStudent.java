
package classwork4;

public class TestingStudent {
    public static void main(String[] args){
    Student[] studentObject = {
        new GraduateStudent(),
        new UnderGraduateStudent(),
    };
    for(Student student : studentObject){
    student.study();
    }
    
    }
    
}
