///import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public Boolean removeGrade(int studentID, int testID)
	{
		if(GradeData.get(studentID) == null)
		{
		  System.out.println("StudentID's Personal Grade map was not in the system.");
			
		}
		return false;
		
	}
	
	public static void main(String [] args)
	{
	// Lets do some basic debuging
	// Insert 2 tests and fetch user
/**
		System.out.println("<----------------------------->");
		System.out.println("<----------------------------->");
	System.out.println("Insert 2 tests and fetch that user's score...");
	  insertGrade(1, 0, 100);
	  insertGrade(1, 1, 100);
	  fetchGrades(1);

		System.out.println("<----------------------------->");
	System.out.println("<----------------------------->");
	
	System.out.println("Lets fetch a score with student ID of 2");
	  fetchGrades(2);
	System.out.println("It returns the error that the student wasnt in the system, since we did not add him.");
	System.out.println("<----------------------------->");
	System.out.println("<----------------------------->");
	
**/
	// insertGrade(studentID, testID, grade)
		insertGrade(1,0,100);
		insertGrade(1,1,83);
		insertGrade(2,0,89);
		insertGrade(2,1,100);
		fetchGrades(1);
		fetchAverage(2);
		
	}
}
