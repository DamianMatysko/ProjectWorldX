package sk.itsovy.matysko.projectWorldX.testDatabase;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Functions {
    //private Connection connection;
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
        return connection;
    }
    public void insertNewUser(String firstName, String lastName, String email, Date date) throws SQLException {
        try {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO person (firstName,secondName, email, date) VALUES (?,?,?,?)");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setDate(4, sqlDate);
            stmt.executeUpdate();

            System.out.println("successful");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void search(String searchWord) throws Exception {
        Scanner sc= new Scanner(System.in);
        System.out.println("enter NAME");
        String name=sc.nextLine();

        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM person where firstName LIKE ? OR secondName like ?;");
        stmt.setString(1, "%"+name+"%");
        stmt.setString(2, "%"+name+"%");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            System.out.println(rs.getString("ID") + "  "
                    + rs.getString("firstName") + "   "
                    + rs.getString("secondName") + "   "
                    + rs.getString("email") + "   "
                    + rs.getString("date"));
        }
    }
}
