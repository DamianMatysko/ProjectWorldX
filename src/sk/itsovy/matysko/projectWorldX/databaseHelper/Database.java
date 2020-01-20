package sk.itsovy.matysko.projectWorldX.databaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Database implements DatabaseMethods {

    private Connection connection;

    @Override
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

        return connection;

    }

    @Override
    public void updateSlovakCity() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("name of upade city: ");
        String city = in.nextLine();
        System.out.println("Population: ");
        String population = in.nextLine();

        PreparedStatement stmt = getConnection().prepareStatement("UPDATE City SET Info = ? WHERE Name LIKE ?;");
        stmt.setString(1, "{\"Population\": " + population + "}");
        stmt.setString(2, city);
        stmt.execute();

        System.out.println("Population updated");
        connection.close();
    }

    @Override
    public void deleteSlovakCity() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter name of city to delete: ");
        String name = in.nextLine();
        System.out.println("are you sure<");
        String answer = in.nextLine();
        int count = 0;
        if (answer.equals("yes") || answer.equals("y")) {
            PreparedStatement stmt = getConnection().prepareStatement("SELECT Name AS City, countrycode AS Country, json_extract(city.Info, '$.Population') AS Population FROM city WHERE countryCode LIKE 'SVK';");
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("incorect input!");
            } else {
                count++;
                if (count > 0) {
                    PreparedStatement delete = getConnection().prepareStatement("DELETE FROM city where Name LIKE ? AND countryCode LIKE 'SVK'");
                    delete.setString(1, name);
                    delete.execute();
                    System.out.println("\nSuccessfuly removed");
                }
            }

        } else {
            System.out.println("not removed");
        }
        connection.close();
    }


    @Override
    public void insertSlovakCity() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("name of city");
        String name = sc.nextLine();
        System.out.println("district");
        String district = sc.nextLine();
        System.out.println("enter code:");
        String countryCode = sc.nextLine();
        System.out.println("enter poulation:");
        String population = sc.nextLine();

        PreparedStatement select = getConnection().prepareStatement("SELECT * FROM city WHERE Name LIKE ?");
        select.setString(1, name);

        int count = 0;
        ResultSet rs = select.executeQuery();

        if (rs.next()) {
            count++;
            System.out.println("city arely exist");
        }
        if (count == 0) {
            PreparedStatement insert = getConnection().prepareStatement("INSERT INTO city (Name, CountryCode, District, Info) VALUES (?,?,?,?)");
            insert.setString(1, name);
            insert.setString(2, countryCode);
            insert.setString(3, district);
            insert.setString(4, "{\"Population\": " + population + "}");
            insert.execute();
            System.out.println("/nSuccessfuly added");
        }
        connection.close();
    }

    @Override
    public void selectSlovakCities() throws Exception {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT Name AS City, district, countrycode AS Country, json_extract(city.Info, '$.Population') AS Population FROM City WHERE countryCode LIKE 'SVK';");

        ResultSet rs = stmt.executeQuery();
        System.out.println("\nList of SVK Courtnies: ");

        while (rs.next()) {
            System.out.println(rs.getString("City") + "  "
                    + rs.getString("district") + "   "
                    + rs.getString("Country") + "   "
                    + rs.getString("Population"));
        }
        connection.close();
    }

    @Override
    public void countryPopulation() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter country");
        String countryName = sc.nextLine();

        PreparedStatement select = getConnection().prepareStatement("SELECT country.Name, json_extract(doc, '$.demographics.Population') AS Population FROM country JOIN countryinfo ON countryinfo._id = country.code WHERE country.Name LIKE ?;");
        select.setString(1, countryName);

        ResultSet rs=select.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("Name") + " " + rs.getString("Population"));
        }
        connection.close();

    }


}
