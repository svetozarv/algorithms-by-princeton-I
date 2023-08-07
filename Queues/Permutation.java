import edu.princeton.cs.algs4.StdIn;
import java.lang.Integer;

public class Permutation {
   public static void main(String[] args) {
      if (args.length == 0) {
         System.out.println("No arguments given");
      } else {
         int k = Integer.parseInt(args[0]);
      }

      String input = StdIn.readLine();
      String[] arr = input.split(" ");
      
      for (String s : arr) {
         System.out.println(s);
      }
   }
}