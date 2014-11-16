
public class Grade {
	
	// Basic Grade constructor
	
	
	// Data to be stored within the constructor
	public int grade;
	public int studentID;
//	public String studentName;
	public int testID;
	//public String testName;

	public Grade(int studentID, int testID , int grade) {
	   this.grade = grade;
	   this.studentID = studentID;
	   this.testID = testID;
	}
	
	public int getstudentID()
	{
		return this.studentID;
		
	}
	public int gettestID()
	{
		return this.testID;
		
	}
	public int getgrade()
	{
		return this.grade;
	}
	public String toString()
	{
		return "Student ID : [" + this.studentID + "] TestID: [" + this.testID + "] Grade: [" + this.grade + "]";
	}
}
