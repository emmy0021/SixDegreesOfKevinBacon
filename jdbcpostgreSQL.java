import java.sql.*;
import java.util.*; 
import java.util.Arrays; 
import java.util.Scanner;
//import java.sql.DriverManager;
/*
Tim McGuire, adapted from Robert lightfoot
CSCE 315
9-27-2019
 */
public class jdbcpostgreSQL {
  public static void main(String args[]) {
    //Building the connection
    Scanner input = new Scanner(System.in);
     Connection conn = null;
     try {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://db-315.cse.tamu.edu/data",
           dbSetup.user, dbSetup.pswd);
     } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
     }//end try catch
     System.out.println("Opened database successfully");

     try{
       /*System.out.println("Inpur first actor name: ");
       String act = input.next();
       System.out.println("Input second actor name: ");
       String act2 = input.next();*/
       String act = "Tom Cruise";
       String act2 = "Simon Pegg";
       String actid = "";
       String actid2 = "";
       String yr = "1880";
       String endyr = "2020";
       Vector<String> movieslist = new Vector<String>();


       //create a statement object
       Statement stmt = conn.createStatement();
        ResultSetMetaData resultmd = null;
        //StringBuilder str = new StringBuilder();
       //create an SQL statement
       String sqlStatement = "SELECT principals.tconst, principals.nconst, titlebasics.primaryTitle, namebasics.primaryName FROM principals " + 
       "INNER JOIN titlebasics ON titlebasics.tconst = principals.tconst AND titlebasics.titleType = 'movie' " +
       "INNER JOIN namebasics ON namebasics.nconst = principals.nconst " +
       "WHERE principals.category = 'actor' AND titlebasics.startYear > " + yr + " AND titlebasics.startYear < " + endyr + ";";
       //send statement to DBMS
       ResultSet result = stmt.executeQuery(sqlStatement);
        resultmd = result.getMetaData();
        int columns = resultmd.getColumnCount();
        int rows = 0;
        String[][] mat = new String[976749][4];

        for (int i = 0; i < 976749; i++)
        {
          for (int j = 0; j < 4; j++)
          {
            mat[i][j] = "";
          }
        }

       //OUTPUT
       System.out.println("Data");
       System.out.println("______________________________________");
       while (result.next()) {
        for (int i = 0; i < columns; i++)
        {
            /*if (i > 1) {
              //str.append(", ");
            }*/
            String temp = result.getString(i+1);
            //str.append(temp);
            mat[rows][i] = temp;
            /*if (i + 1 >= columns + 1)
            {
              //str.append(" ");
              //str.append("\n");
            }*/

            if (i == 3 && actid.length() == 0 && act.equals(temp))
            {
              actid = mat[rows][1];
            }
            if (i == 3 && actid2.length() == 0 && act2.equals(temp))
            {
              actid2 = mat[rows][1];
            }
            if (i == 1 && actid.length() != 0 && mat[rows][i].equals(actid))
            {
              movieslist.add(mat[rows][0]);
            }
          }
        rows++;
       }
      //System.out.println(act.equals("Tom Cruise"));
       //System.out.println(str.toString());
      // System.out.println(mat[0][0]);
      System.out.println("movieslist size = " + movieslist.size());

      Vector<String> finalpath = new Vector<String>();
      Vector<String> temppath = new Vector<String>();
      int finalDist = 0;
      int tempDist = 0;

      
       // 1st degree only
      /*for (int i = 0; i < movieslist.size(); i++)
      {
        for (int j = 0; j < rows; j++)
        {
          if (mat[j][1].equals(actid2) && mat[j][0].equals(movieslist.get(i)))
          {
              System.out.println("Result: " + mat[j][2]);
              i = movieslist.size();
              j = rows;
          }
        }
      }*/

      /*for (int i = 0; i < movieslist.size(); i++)     // degree 1
      {
        for (int j = 0; j < rows; j++)
        {
          if (movieslist.get(i).equals(mat[j][0]))
          {
            String tempact = mat[j][1];       // holds current actor
            if (tempact.equals(actid2))
            {
              finalpath.add(mat[j][0]);
              finalDist = 1;
              i = movieslist.size();
              j = rows;
            }
            /*else
            {
              for (int i2 = 0; i2 < rows; i2++)       // degree 2
              {
                if (mat[i2][1].equals(tempact))
                {
                  String tempmovie = mat[i2][0];    // holds current movie
                  for (int j2 = 0; j2 < rows; j2++)
                  {
                    if (tempmovie.equals(mat[j2][0]))
                    {
                      if (mat[j2][1].equals(actid2))
                      {
                        finalpath.add(mat[j2][0]);
                        // variables below are temp, just for testing, delete this
                        i = movieslist.size();
                        j = rows;
                        i2 = rows;
                        j2 = rows;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }*/


      System.out.println("Result:" + finalpath);


    
   } catch (Exception e){
     System.out.println("Error accessing Database.");
   }

    //closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch(Exception e) {
      System.out.println("Connection NOT Closed.");
    }//end try catch
  }//end main
}//end Class
