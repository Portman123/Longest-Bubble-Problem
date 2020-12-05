import javax.xml.crypto.Data;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Main{

    public static ArrayList ReadData(String pathname) {
        ArrayList strlist = new ArrayList();
        try {

            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            int j = 0;
            String line = "";
            while ((line = br.readLine()) != null) {
                strlist.add(line);
            }
            return strlist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strlist;
    }

    public static ArrayList DataWash(ArrayList Datalist) {
        ArrayList AIS = new ArrayList();
        ArrayList IS = new ArrayList();
        for (int i = 0; i < Datalist.size(); i++) {
            String Tstr = (String) Datalist.get(i);
            if (Tstr.equals("A") == false) {
                IS.add(Integer.parseInt(Tstr));
            }
            if (Tstr.equals("A")) {
                AIS.add(IS.clone());
                IS.clear();
            }
        }
        return AIS;
    }



//%%%%%%%%%%%%%%%%%%%%%%% Procedure LongestBubble() that will contain your code:
    
    public static int longestAscending(int[] A, int n) {
    	
    	// L[i] Length of longest increasing subsequence that ends at and includes item i
    	int[] L = new int[n];
    	
    	// The first item's L value is always 1 since it's the only item in the ascending sequence up to and including it.
    	L[0] = 1;
    	
    	int longestSequence = 0;
    	
    	// Find L values for remaining elements in array
    	for (int i=1; i<n; i++) {
    		//find maximum L value of items to left of i that are smaller than A[i]
    		int maxL = 0;
    		
    		for (int j=0; j<i; j++) {
    			// if element in array is greater than element being considered and it's L value is greater than the current max found, make it the new maxL
    			if (A[i] > A[j] && L[j] > maxL) {
    				maxL = L[j];
    			}
    		}
    		//L value for A[i] is itself plus largest maxL value found it can be part of
    		L[i] = maxL +1;
    		
    		//if this is bigger than current longest sequence, make it new longest sequence number
    		if (maxL + 1 > longestSequence) {
    			longestSequence = maxL +1;
    		}
    	}
    	return(longestSequence);
    }
    
    public static int longestDescending(int[] A, int n) {
    	// This is essentially the same function as ascending except:
    	// the algorithm starts from the end of the input array and works backwards
    	int[] L = new int[n];
    	L[n-1] = 1;
    	
    	int longestSequence = 0;
    	
    	for (int i = n-2; i > -1; i--) {
    		int maxL = 0;
    		
    		for (int j=n-1; j > i; j--) {
    			if (A[i] > A[j] && L[j] > maxL) {
    				maxL = L[j];
    			}
    		}
    		L[i] = maxL +1;
    		
    		if (maxL + 1 > longestSequence) {
    			longestSequence = maxL +1;
    		}
    	}
    	return(longestSequence);
    }
    
    public static int[] allItemsLessThan(int item, int[] A) {
    	//if array is empty, return empty array 
    	if (A.length < 1) {
    		return(new int[0]);
    	}
    	
    	// temporary array that is the same length as input array to store all items less than item
    	int [] temp = new int[A.length];
    	// will become length of final array 
    	int length = 0;
    	
    	// feed temp array with items less than input item
    	for (int i=0; i < A.length; i++) {
    		if (A[i] < item) {
    			temp[length] = A[i];
    			length++;
    		}
    	}
    	
    	//create final array with correct size
    	int[] itemsLessThan = new int[length];
    	
    	//fill array
    	for (int i=0; i <length; i++) {
    		itemsLessThan[i] = temp[i];
    	}
    	
    	return(itemsLessThan);
    }

    public static int LongestBubble(int[] A, int n){
        /*     
     	MAIN IDEAS
     	-The algorithm goes through each element in the input array and treats it as a pivot number.
     	-It then creates two smaller arrays. The first is the left side of the pivot number up to and including the pivot number.
     	-The second is the right side of the pivot number including the pivot.
     	
     	-Both arrays are then fed to a function that returns the same array but only with items less than the pivot number (as well as the pivot number)
     	-The result of the left side is then given to the longestAscending function which finds the longest ascending subsequence of a given array
     		-It does this by iterating over every element and finding the largest ascending subsequence up to the iteration and adding itself to it if it can
     		-Returning the largest subsequence it found after checking all elements
     	-The result of the right side is then given to the longestDescending function which finds the longest descending subsequence of a given array
     		-This uses the same method as the longest ascending subsequence function but in reverse and starting from the end of the array rather than the start
     		-This is so it can build upon it's knowledge of the largest sequence as it goes along
     	-The final result of longest bubble (for a given pivot number)is then the longest ascending subsequence before the pivot PLUS the longest descending subsequence after MINUS one 
     		-(to account for the pivot number being fed to both subsequence finding functions)
     	-Out of the longest bubbles for each pivot number, the largest is the final longest bubble
        */

        /*
		TIME ANALYSIS 
		-The function's time is dominated by the nested loops in the Ascending and Descending sequence finding algorithms
		-It does this for every item in the the input sequence. 
		-So the running time is O(n^2)
        */

        int longestBubble = 0;
        
        for (int i=1; i<n-1; i++) {
        	// create arrays for left and right side of pivot number including pivot number
        	int[] left = new int[i+1];
        	int[] right = new int[n-i];
        	for (int j=0; j<left.length; j++) {
        		left[j] = A[j];
        	}
        	for (int j=0; j<n-i; j++) {
        		right[j] = A[i+j];
        	}
        	
        	//left and right arrays but with only items less than A[i] and the item A[i]
        	int[] leftFeed = allItemsLessThan(A[i]+1, left);
        	int[] rightFeed = allItemsLessThan(A[i]+1, right);
        	
        	//calculate length of longest sequences left and right of pivot number including pivot
        	int bestLeft = longestAscending(leftFeed, leftFeed.length);
        	int bestRight = longestDescending(rightFeed, rightFeed.length);
        	
        	//Check if this makes a bubble longer than the longest one found (don't accept bubbles if there was no ascending or descending sequence)
        	if (bestLeft > 1 && bestRight > 1) {
        		if (bestLeft + bestRight -1 > longestBubble) {
            		longestBubble = bestLeft + bestRight -1;
            	}
        	}
        }
        
      return(longestBubble);
    } // end of procedure LongestBubble()




    public static int Computation(ArrayList Instance, int opt){
        // opt=1 here means option 1 as in -opt1, and opt=2 means option 2 as in -opt2
        int NGounp = 0;
        int size = 0;
        int Correct = 0;
        size = Instance.size();

        int [] A = new int[size-opt];
        // NGounp = Integer.parseInt((String)Instance.get(0));
        NGounp = (Integer)Instance.get(0); // NOTE: NGounp = 0 always, as it is NOT used for any purpose
                                           // It is just the first "0" in the first line before instance starts.
        for (int i = opt; i< size;i++ ){
            A[i-opt] = (Integer)Instance.get(i);
        }
        int Size =A.length;
        if (NGounp >Size ){
            return (-1);
        }
        else {
            //Size
            int R = LongestBubble(A,Size);
            return(R);
        }
    }

    public static String Test;


    public static void main(String[] args) {
        Test = args[0];
        int opt = 2;
        String pathname = "dataTwo.txt";
        if (Test.equals("-opt1")) {
            opt = 1;
            pathname = "dataOne.txt";
        }


        ArrayList Datalist = new ArrayList();
        Datalist = ReadData(pathname);
        ArrayList AIS = DataWash(Datalist);

        int Nins = AIS.size();
        int NGounp = 0;
        int size = 0;
        if (Test.equals("-opt1")) {
            for (int t = 0; t < Nins; t++) {
                int Correct = 0;
                int Result = 0;
                ArrayList Instance = (ArrayList) AIS.get(t);
                Result = Computation(Instance, opt);
                System.out.println(Result);
            }
        }
        else {
            int wrong_no = 0;
            int Correct = 0;
            int Result = 0;
            ArrayList Wrong = new ArrayList();
            for (int t = 0; t < Nins; t++) {
                ArrayList Instance = (ArrayList) AIS.get(t);
                Result = Computation(Instance, opt);
                System.out.println(Result);
                Correct = (Integer) Instance.get(1);
                if (Correct != Result) {
                    Wrong.add(t+1);
                    //Wrong.add(Instance);
                    wrong_no=wrong_no+1;
                }
            }
            if (Wrong.size() > 0) {System.out.println("Index of wrong instance(s):");}
            for (int j = 0; j < Wrong.size(); j++) {
                System.out.print(Wrong.get(j));
                System.out.print(",");

                /*ArrayList Instance = (ArrayList)Wrong.get(j);
                for (int k = 0; k < Instance.size(); k++){
                    System.out.println(Instance.get(k));
                }*/
            }
            System.out.println("");
            System.out.println("Percentage of correct answers:");
            System.out.println(((double)(Nins-wrong_no) / (double)Nins)*100);
   
        }

    }
}