public class MainApp{
	public static void main(String[] args){
        Students student1 = new Students(1, "John", "Doe", 'M');
        Students student2 = new Students(2, "Jane", "Smith", 'F');
        Students student3 = new Students(3, "Mike", "Johnson", 'M');
        Students student4 = new Students(4, "Emily", "Davis", 'F');
        Students student5 = new Students(5, "David", "Wilson", 'M');
        Students student6 = new Students(6, "Sarah", "Brown", 'F');

        student1.displayStudentInfo();
        System.out.println("===== Student Information =====");
        student2.displayStudentInfo();
        System.out.println("===== Student Information =====");
        student3.displayStudentInfo();
        System.out.println("===== Student Information =====");
        student4.displayStudentInfo();
        System.out.println("===== Student Information =====");
        student5.displayStudentInfo();
        System.out.println("===== Student Information =====");
        student6.displayStudentInfo();
        System.out.println("====Student Information=====");

    }
}