package sk.itsovy.matysko.projectWorldX.testDatabase;

import javax.security.sasl.SaslClient;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;
import java.util.Scanner;

public class Main {
    //private Connection connection;

    public static void main(String[] args) {
        try {
            Functions f1 = new Functions();

            PreparedStatement stmt = f1.getConnection().prepareStatement("SELECT * FROM person");

            ResultSet rs = stmt.executeQuery();
            System.out.println("Person table: ");

            while (rs.next()) {
                System.out.println(rs.getString("ID") + "  "
                        + rs.getString("firstName") + "   "
                        + rs.getString("secondName") + "   "
                        + rs.getString("email") + "   "
                        + rs.getString("date"));
            }

            stmt = f1.getConnection().prepareStatement(" SELECT * FROM person WHERE email LIKE '%@gmail.com%';");
            rs = stmt.executeQuery();
            System.out.println("@gmail.com");

            while (rs.next()) {
                System.out.println(rs.getString("ID") + "  "
                        + rs.getString("firstName") + "   "
                        + rs.getString("secondName") + "   "
                        + rs.getString("email") + "   "
                        + rs.getString("date"));
            }

            System.out.println("enter a moth");
            Scanner in = new Scanner(System.in);
            //String moth = in.nextLine();

            stmt = f1.getConnection().prepareStatement(" SELECT * FROM person WHERE date LIKE ?");
            stmt.setString(1, "%-05-%");
            rs = stmt.executeQuery();
            System.out.println("moth");

            while (rs.next()) {
                System.out.println(rs.getString("ID") + "  "
                        + rs.getString("firstName") + "   "
                        + rs.getString("secondName") + "   "
                        + rs.getString("email") + "   "
                        + rs.getString("date"));
            }


            System.out.println("Input first name for the new user: ");
            String firstName = in.nextLine();
            System.out.println("Input last name for the new user: ");
            String lastName = in.nextLine();
            System.out.println("Input email for the new user: ");
            String email = in.nextLine();
            System.out.println("Input the date of birth dd/MM/yyyy: ");
            String sDate1 = in.nextLine(); //"31/12/1998";
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            f1.insertNewUser(firstName, lastName, email, date);

            f1.search("B");


            f1.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
