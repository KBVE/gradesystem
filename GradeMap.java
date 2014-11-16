///import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 *  GradeMap was suppose to be a simple hashmap / array list combo but it seems that the concept does not scale, 
 *  once passing 100 entries, there was a slight performance drop, which seem to cause too much lag. Thus I decided to drop
 *  the arraylist and do a hashmap within a hashmap. Furthermore, I decided to treat each test as its own object, known as 
 *  Grade.
 *  
 *  Previously it was StudentID, {ArrayList<Grade>()} but the scaling with entries was not that effective....
 *  thus I decided to rewrite it using the below..
 *  
 *  GradeData => {StudentID}, {HashMap<TestID, Grade()>} 
 * 
 * 
 * 
 * 
 * 
 * @author h0lybyte
 * This is for my CS102 class...
 */



public class GradeMap {
	
	
//    public static HashMap<Integer,ArrayList<Grade>> GradeData = new HashMap<Integer,ArrayList<Grade>>();
	 public static HashMap<Integer, HashMap<Integer, Grade>> GradeData =  new HashMap<Integer, HashMap<Integer, Grade>>();
   
	/** Inserts a grade into the GradeData hashmap, if the studentID is not in the system, it will add the student id and 
	 *  repeat itself. (semi-recursion).
	 * 
	 *  	=> Check if studentID is in the GradeData
	 * 	if not, then .put(studentID, empty ArrayList<Grade) and recursion
	 *  else
	 *  if, then check if ArrayList is there or check if the Arraylist is null.
	 *    if the array list is null, then it will create a temp arraylist and add the new grade.
	 * 	  if the array list is there, it will check if the testid exists OR it will add the grade.
	 * @param studentID
	 * @param testID
	 * @param grade
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Boolean insertGrade(int studentID, int testID , int grade)
	{
	// Temp Variables to play with have to be added, they will be used as a temp reference, hence the variable name.. temp.
	 // First check if the GradeData has the studentID
	 if(GradeData.containsKey(studentID))
	 	{
		 /**
		 // Check if the GradeData has a valid object
		  * 
		  * 
		  */
			 if(GradeData.get(studentID) == null)
				 {
				 //	ArrayList<Grade> temp = new ArrayList<Grade>();
				 // It returns null, so we have to add a new personal Map, with the first entry being that testID.
				 	HashMap<Integer, Grade> temp = new HashMap<Integer, Grade>();
				 	temp.put(testID, new Grade(studentID, testID, grade));
				 	GradeData.remove(studentID);
					GradeData.put(studentID, temp);
					//"Personal Grade was made and the first entry was this test grade.
					System.out.println("Personal Grade was made and the first entry was this test grade.");
					return true;
				 }
			 else
			 	{
				 	//	personal gradeData map exists for the user... now there are 2 areas...
				 	// First we must check to see if the testID already exists
				 	if(GradeData.get(studentID).get(testID) != null)
				 	{
				 		System.out.println("testID is already in the system, please use the update method");
				 		return false;
				 	}
				 	// Since its not in the system, we will go ahead and add the new Grade..
				 	else
				 	{
				 		System.out.println("Grade as been inserted");
				 		GradeData.get(studentID).put(testID, new Grade(studentID, testID, grade));
				 		// After some trimming, I was able to reduce the actual insert operation to 1 line. 
				 		// GradeData.get(studentID) fetches the personal HashMap<testID, Grade()>
				 		// put(testID, new Grade(studentID, testID, grade) inserts the testID and the new grade.
				 		return true;
				 	}
				 	
			 	}
				 
	 	}
		else
			
	/** If the studentID is not in the system, then we will go ahead and automatically add the studentID and their test
	 * 
	 * 
	 */
		{
			 	HashMap<Integer, Grade> temp = new HashMap<Integer, Grade>();  // New temp map
			 	temp.put(testID, new Grade(studentID, testID, grade)); // insert the first test scores
			 	GradeData.remove(studentID); // remove any old information, to prevent collisions
				GradeData.put(studentID, temp); // insert the student id and their first test. 
				
			 //	 GradeData.put(studentID, new ArrayList<Grade>());

				
				 System.out.println("StudentID was not in the system... automatically inserting");
			 return true;
		}
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void fetchGrades(int studentID)
	{
		
		// Checks if the student is in the system, if null, then system.out that he is not there.
		if(GradeData.get(studentID) == null)
		{
			System.out.println("The student is not in the system");
		}
		else
		{
			//ArrayList<Grade> temp = GradeData.get(studentID);
			Iterator<Entry<Integer, Grade>> it = GradeData.get(studentID).entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey() + " = " + pairs.getValue());
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
		}
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void fetchAverage(int studentID)
	{
		int temp_test = 0;
		int temp_total = 0;
		// Checks if the student is in the system, if null, then system.out that he is not there.
		if(GradeData.get(studentID) == null)
		{
			System.out.println("The student is not in the system");
		}
		else
		{
			//ArrayList<Grade> temp = GradeData.get(studentID);
			Iterator<Entry<Integer, Grade>> it = GradeData.get(studentID).entrySet().iterator();
		    while (it.hasNext()) {
		    	 Map.Entry pairs = (Map.Entry)it.next();
		       // System.out.println(pairs.getKey() + " = " + pairs.getValue());
		        temp_test++;
		    	temp_total = temp_total + ((Grade)pairs.getValue()).getgrade();
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
		    System.out.println("StudentID " + studentID + " : Average => " + temp_total/temp_test);
		}
	}
	
	
	public static Integer getAverage(int studentID)
	{
		int temp_test = 0;
		int temp_total = 0;
		// Checks if the student is in the system, if null, then system.out that he is not there.
		if(GradeData.get(studentID) == null)
		{
			System.out.println("The student is not in the system");
		}
		else
		{
			//ArrayList<Grade> temp = GradeData.get(studentID);
			Iterator<Entry<Integer, Grade>> it = GradeData.get(studentID).entrySet().iterator();
		    while (it.hasNext()) {
		    	 Map.Entry pairs = (Map.Entry)it.next();
		       // System.out.println(pairs.getKey() + " = " + pairs.getValue());
		        temp_test++;
		    	temp_total = temp_total + ((Grade)pairs.getValue()).getgrade();
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
		    return temp_total/temp_test;
		  //  System.out.println("StudentID " + studentID + " : Average => " + temp_total/temp_test);
		}
		return -1;
	}
	
	public static int getScore(int studentID, int testID)
	{
		if(GradeData.get(studentID) == null)
		{
			System.out.println("The student is not in the system");
		}
		else
		{
			Iterator<Entry<Integer, Grade>> it = GradeData.get(studentID).entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        if(pairs.getKey().equals(testID))
		        {
		        
		        //System.out.println(((Grade)pairs.getValue()).getgrade());
		        return ((Grade)pairs.getValue()).getgrade();
		        }
		        

		    }
		
		}
		return -1;
		
	}
	
	// This method returns true or false based on the removal of the testID from the personal Hashmap for the studentID
	
	public static Boolean removeGrade(int studentID, int testID)
	{
		// Checks if the student is in the GradeData Map
		
		if(GradeData.get(studentID) == null)
		{
		//StudentID's Personal Grade map was not in the system.
		  System.out.println("StudentID's Personal Grade map was not in the system.");
		  return false;
		}
		else
		{
		// If the student is in the map, check if they have taken the test.
			if(!GradeData.get(studentID).containsKey(testID))
			{
				System.out.println("Student: "+studentID + " has no records of " + testID);
				return false;
			}
			else
			{
				GradeData.get(studentID).remove(testID);
				System.out.println("Removed " + testID +  " from StudentID " + studentID);
				return true;
			}
		}
		//return false;
		
	}
	
	// Simple function to convert an integer to a Letter (char)
	
	public static char score2letter(int score) {   
		   char result;
		   if (score >= 90) { result = 'A'; }
		   else if (score >= 80) { result = 'B'; }
		   else if (score >= 70) { result = 'C'; }
		   else if (score >= 60) { result = 'D'; }
		   else { result = 'F'; }
		   return result;
		}
	
	public Array getArray(int studentID)
	{
		return null;
		
	}
	public static void displayMenu()
	{
		System.out.println("Menu (Press 0)");
		System.out.println("Press 1 to insert grade: Format: (studentid, testid, grade)");
		System.out.println("Press 2 to fetch average: Format: (studentID)");
		System.out.println("Press 3 to remove grade Format: (studentid, testid)");
		System.out.println("Press 6+ to exit");
		
	}
	
	@SuppressWarnings("resource")
	public static Boolean AddGradeSir()
	{
		int studentID, testID, gradeID;
		Scanner aScanner = new Scanner(System.in);
		System.out.println("Welcome to Grade Submissions");
		System.out.println("What was the studentID? ");
		studentID = aScanner.nextInt();
		System.out.println("What was the testID ");
		testID = aScanner.nextInt();
		System.out.println("What was the grade? ");
		gradeID = aScanner.nextInt();
		if(insertGrade(studentID,testID,gradeID))
		{
			fetchGrades(studentID);
			return true;
		}
		return false;
		
	}
	
	public static void main(String [] args)
	{
	// Lets do some basic debuging
	// Insert 2 tests and fetch user
		int studentID = 0;
		int menuoption = 0;
		Scanner aScanner = new Scanner(System.in);
		while(menuoption < 6)
		{
			
			if(menuoption == 0)
			{
				displayMenu();
			}
		  	if(menuoption == 1)
		  	{
		  		AddGradeSir();
		  		displayMenu();
		  	}
		  	if(menuoption == 2)
		  	{
		  		System.out.println("What is the studentID?");
		  		studentID = aScanner.nextInt();
		  		fetchAverage(studentID);
		  		System.out.println(score2letter(getAverage(studentID)));
		  		displayMenu();
		  	}
			menuoption = aScanner.nextInt();
		//	menuoption = System.in.read();
			
		}
		System.out.println("Thanks for using GradeMap! Bye!");
	}
}
