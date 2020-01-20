package sk.itsovy.matysko.projectWorldX;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static final String QUERYCOUNTRIES="SELECT name FROM country" ;
    public static final String ITALYCITIES="SELECT name FROM city WHERE countryCode LIKE 'ITA'";
    public static final String CITIES="SELECT name FROM city WHERE countryCode LIKE ? ORDER BY name";
    public static final String INSERTCITY="INSERT INTO city(Name, CountryCode, District, Info)" +" VALUES(?,?,?,?)";


    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");

            PreparedStatement stmt=connection.prepareStatement(QUERYCOUNTRIES);
            ResultSet rs=stmt.executeQuery();
            System.out.println("List of countries: ");
            int count=0;
            while (rs.next()) {
                String data = rs.getString("Name");
                System.out.print(data+"  ");
                count++;
                if (count%5==0){
                    System.out.println();
                }
            }

            System.out.println();

            stmt=connection.prepareStatement(ITALYCITIES);
            rs=stmt.executeQuery();
            System.out.println("List of countries: ");
            while (rs.next()) {
                String data = rs.getString("Name");
                System.out.print(data+"  ");
                count++;
                if (count%5==0){
                    System.out.println();
                }
            }

            System.out.println();
            System.out.println();

            stmt=connection.prepareStatement(CITIES);
            stmt.setString(1,"USA");
            rs=stmt.executeQuery();
            System.out.println("List of cities: ");
            while (rs.next()) {
                String data = rs.getString("Name");
                System.out.print(data+"  ");
                count++;
                if (count%5==0){
                    System.out.println();
                }
            }
            System.out.println();

            String TRYCITY = "SELECT * FROM City WHERE City.Name = ?";
            stmt = connection.prepareStatement(TRYCITY);
            stmt.setString(1,"Michalovce");//!!!
            ResultSet rs1=stmt.executeQuery();
            if(rs1.next()){
                System.out.println("already exists");
            }else {
                stmt = connection.prepareStatement(INSERTCITY);
                stmt.setString(1, "Michalovce");//!!!
                stmt.setString(2, "SVK");
                stmt.setString(3, "Košice");
                stmt.setString(4, "{\"Population\":4550}");
                stmt.executeUpdate();
                System.out.println("successfully added");

            }

/*
            Scanner in = new Scanner(System.in);
            System.out.println("Input the city: ");
            String cityName = in.nextLine();
            stmt = connection.prepareStatement(sql2);
            stmt.setString(1,cityName);
            ResultSet rs2=stmt.executeQuery();
*/
            Scanner in = new Scanner(System.in);
            System.out.println("Input the city: ");
            String cityName = in.nextLine();
            PreparedStatement preparedStatement = connection.prepareCall("SELECT city.Name, country.Name AS Country_Name, json_extract(Info, '$.Population') AS Population FROM city JOIN country ON country.code = city.CountryCode WHERE city.Name = ?");
            preparedStatement.setString(1, cityName);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                String cityName2 = result.getString("city.Name");
                String countryName = result.getString("Country_Name");
                String population = result.getString("Population");
                System.out.println(cityName2 + " " + countryName + " " + population + "\n");
            }


            // top 20 miest s obivatelmi

            preparedStatement = connection.prepareCall("SELECT city.Name, country.Name AS Country_Name, json_extract(Info, '$.Population') AS Population FROM city JOIN country ON country.code = city.CountryCode ORDER BY json_extract(Info, '$.Population') DESC LIMIT 20");
            result = preparedStatement.executeQuery();

            while (result.next()) {
                String cityName2 = result.getString("city.Name");
                String countryName = result.getString("Country_Name");
                String population = result.getString("Population");
                System.out.println(cityName2 + " " + countryName + " " + population + "\n");
            }






            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
