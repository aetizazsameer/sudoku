import java.io.*;
import java.util.*;
import static java.lang.System.*;

// Thanks to Pradeep Mondal P of GeeksForGeeks and vkostyukov of Github, whose work this program is largely based on
public class Sudoku {
   public static void main(String[] args) throws IOException {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      int[][] grid = new int[9][9];
      
      out.println("Enter each row of the Sudoku you'd like to solve, using 0's for blank squares.\n" +
         "9 numbers per row will be read in. Numbers should be in the range 0-9, inclusive.\n" +
         "Example row: 0 1 7 3 0 0 5 0 9\n");
      
      for(int i = 0; i < 9; i++) {
         out.print("Row " + (i+1) + ": ");
         StringTokenizer st = new StringTokenizer(in.readLine());
         
         for(int j = 0; j < 9; j++) {
            if (!st.hasMoreTokens()) {
               out.println("Error: too few numbers entered for row " + (i+1) + ". Rerun and enter 9 numbers per row.");
               exit(1);
            }
            
            try {
               grid[i][j] = Integer.parseInt(st.nextToken());
               if(grid[i][j] < 0 || grid[i][j] > 9) {
                  out.println("Error: numbers out of range entered for row " + (i+1) + ". Rerun and enter 9 integers in the range 0-9, inclusive.");
                  exit(1);
               }
            }
            
            catch(NumberFormatException e) {
               out.println("Error: non-integer entered for row " + (i+1) + ". Rerun and enter 9 numbers per row.");
               exit(1);
            }
            
            if(j==8 && st.hasMoreTokens())
               out.println("Note: too many characters entered for row " + (i+1) + ". Only the first 9 numbers will be reflected in the Sudoku grid.");
         }
      }
      out.println();
      
      if(solveSudoku(grid, 0, 0))
         for(int[] i : grid) {
            for(int j : i)
               out.print(j + " ");
            out.println();
         }
      else
         out.println("Error: no solution exists to the given Sudoku puzzle.");
      in.close();
   }
   
   private static boolean solveSudoku(int[][] grid, int r, int c) {
      if(c == 9) {
         if(r == 8)
            return true;
         r++;
         c = 0;
      }
      
      if(grid[r][c] != 0)
         return solveSudoku(grid, r, c + 1);
      
      for(int n = 1; n <= 9; n++) {
         if(isLegal(grid, r, c, n)) {
            grid[r][c] = n;
            if(solveSudoku(grid, r, c + 1))
               return true;
         }
         grid[r][c] = 0;
      }
      return false;
   }
   
   private static boolean isLegal(int[][] grid, int r, int c, int n) {
      for(int i = 0; i <= 8; i++)
         if(grid[r][i] == n || grid[i][c] == n)
            return false;
         
      int startRow = r - r % 3, startCol = c - c % 3;
      for(int i = 0; i < 3; i++)
         for(int j = 0; j < 3; j++)
            if(grid[i + startRow][j + startCol] == n)
               return false;
            
      return true;
   }
}
