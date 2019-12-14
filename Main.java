import java.util.*;
import java.io.*;
/* Notice that this code is not perfect. For example, indexes is an object, so the creation of ArrayList "ticks" is useless. However, to resolve this, it would mean that I'd have to tap into the code once again...No :)
*/
class Main {
  public static void main(String[] args) throws IOException{
    //Intializing the base variables.
    boolean run = true;
    while (run) {
      Scanner input = new Scanner(new File("students.txt"));

      ArrayList<String> classroom = new ArrayList<String>();
      int index = 0;

      while (input.hasNextLine()){
        classroom.add(input.nextLine());
        index++;
      }
      Scanner userInput = new Scanner(System.in);
      System.out.print("\nHow many students per group?  ");
      int groupSize = userInput.nextInt();
      int groupNum;
      System.out.println();

      //Using base variables to create the dimensions of the group.

      if (classroom.size()%groupSize != 0){
        groupNum = (classroom.size()/groupSize) +1;
      }

      else{
        groupNum = classroom.size()/groupSize;
      }

      //Calling methods for the "finalGroup" ArrayList.
      String[][] finalGroups = createGroup(classroom, groupSize, index, groupNum);

      //Prints out all students in their specific group. 

      for (int i = 1; i <= groupNum; i++){
        //A string for each group that gets reset after every iteration. Cut's last two characters to add a "and" and period at the end.
        String table = "";
        for (int z = 0; z < groupSize-1; z++){
          table += finalGroups[i-1][z] + ", ";
        }
        if (groupSize != 1){
          table += "and " + finalGroups[i-1][finalGroups[i-1].length-1] + ".";
        }
        else{
          table += finalGroups[i-1][finalGroups[i-1].length-1] + ".";
        }

        System.out.println("Group #" + (i) + ": " + table);
      }

      //Restart function.
      Scanner ask = new Scanner(System.in);
      System.out.print("\n______________________________________\nWould you like to re-run? You can add more students within the file \"students.txt\"...[y/etc]  ");

      String s = ask.next();
      if (s.equalsIgnoreCase("y")){
        /* **Method for adding names on to "student.txt". **
        ____________________________________________________
        System.out.print("\nWould you like to add more students? [y/etc]  ");
        s = ask.next();
        if (s.equalsIgnoreCase("y")){
          BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt", true));  

          System.out.print("\nHow many new students?  ");
          int studNum = ask.nextInt();
          System.out.println();
          String stud = "";

          for (int i = 0; i < studNum; i++){
            System.out.print("\nEnter a name: ");
            stud = ask.next();
            writer.write(stud);
            writer.newLine();
          }
        }
          */
      }
      else{
        input.close();
        userInput.close();
        ask.close();
        //fr.close();
        //writer.close();
        break;
      }

    }
  }
  public static String[][] createGroup(ArrayList<String> classroom, int groupSize, int index, int groupNum){
    String[][] studentsWithIndex = new String[groupNum][groupSize]; //Sets 2D Array for muti-groups.
    
    //Base ArrayFor randomization.
    int[] indexes = new int[index];
    for (int i = 0; i < index; i++){
      indexes[i] = i;
    }
    //Shuffle(Random) methods and tests.    
    ArrayList<Integer> ticks = shuffle(indexes); //Key component. Randomized positions of students.
    //Setting up a new array(group) for the students.
    int counter = 0; 
    for (int i = 0; i < groupSize; i++){
      for (int x = 0; x < groupNum; x++){
        if (counter > index-1){ //Checks if there are no more students.
          studentsWithIndex[x][i] = "[EMPTY SPOT!]";
        }
        else{ //If not, add them.
          int value = ticks.get(counter);
          studentsWithIndex[x][i] = classroom.get(value);
        }
        counter++;
      }
    }
    return studentsWithIndex;

  }
  public static ArrayList<Integer> shuffle(int[] indexes){
    boolean run = true;
    int i = 0;

    //While loop to "randomize" the index arrangements of the student positions.
    while (run) {
      if (i == indexes.length-1){
        break;
      }
      //This is the "hold on to, remove, change, add" method.
      int randPos = (int) (Math.random() * 21); 
      int holder = indexes[i]; 
      indexes[i] = indexes[randPos];
      indexes[randPos] = holder; 
      i++;
    }
    //Creating a new ArrayList to serve as the addition of students in to groups.
    ArrayList<Integer> counterNums = new ArrayList<Integer>();
    for (int x = 0; x < indexes.length; x++){
      counterNums.add(indexes[x]);
    }
    return counterNums;
  }
}
