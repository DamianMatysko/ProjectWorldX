package sk.itsovy.matysko.projectWorldX.databaseHelper;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static String option;

    public static void main(String[] args) {
        Database database = new Database();


        try {
            System.out.println("welcome to my application");
            printMenu();

            while (true) {
                if (option.equals("5")) {
                    System.out.println("bye..bye");
                    break;
                }

                switch (option) {
                    case "1":
                        database.selectSlovakCities();
                        printMenu();
                        break;
                    case "2":
                        database.insertSlovakCity();
                        printMenu();
                        break;
                    case "3":
                        database.updateSlovakCity();
                        printMenu();
                        break;
                    case "4":
                        database.deleteSlovakCity();
                        printMenu();
                        break;
                    case "6":
                        database.countryPopulation();
                        printMenu();
                        break;
                    default:
                        System.out.println();
                        System.out.println("Inncorrect opinion");
                        System.out.println();
                        printMenu();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void printMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("|Select action to do with the table city|");
        System.out.println("|1. Return list of the Slovak cities:   |");
        System.out.println("|2. Insert city into slovak cities      |");
        System.out.println("|3. Upade Slovak City                   |");
        System.out.println("|4. Delete Slovak City                  |");
        System.out.println("|5. Exit application                    |");
        System.out.println("|6. Population of country               |");
        System.out.println("-----------------------------------------");
        System.out.println("Select your action:");
        option = sc.nextLine();
    }
}
