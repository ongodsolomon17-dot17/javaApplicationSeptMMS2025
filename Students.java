public class Students{
	
	// properties
    
	private int studentID;
    private String firstName;
    private String lastName;
    private char gender;
		
		
	//constructor
	
    public Students(int studentID, String firstName, String lastName, char gender) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /*public void eat() {
        System.out.println("I am eating);

    }

    public void sleep() {
        System.out.println("I am sleeping");
    }

    public void sing() {
        System.out.println("I am singing");*/
    }
	
	public void displayStudentInfo(){
		System.out.println("Student ID: " + studentID);
		System.out.println("First Name: " + firstName);
		System.out.println("Last Name: " + lastName);
		System.out.println("Gender: " + gender);
	}
}