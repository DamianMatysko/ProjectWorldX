package sk.itsovy.matysko.projectWorldX.Block;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BlockMethods {
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
        return connection;
    }

    public void calculation() throws Exception {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM input;");
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String name = null;
            int a = 0, b = 0, c = 0, p, o;
            while (rs.next()) {
                name = rs.getString("Name");
                a = rs.getInt("a");
                b = rs.getInt("b");
                c = rs.getInt("c");
            }
            //calculation
            o = a * b * c;
            p = (a * b) * 2 + (b * c) * 2 + (c * a) * 2;
            rs.close();
            stmt = getConnection().prepareStatement("INSERT INTO output (Name,p,o) VALUES (?,?,?)");
            stmt.setString(1, name);
            stmt.setInt(2, p);
            stmt.setInt(3, o);
            stmt.executeUpdate();
            //INSERT INTO input (Name,a,b,c) VALUES ("jozko",5,5,5);
            System.out.println("Update successful");
            stmt = getConnection().prepareStatement("DELETE FROM input");
            stmt.executeUpdate();
        } else {
            System.out.println("Nothing to upade");
        }
    }
}
