package sk.itsovy.matysko.projectWorldX.databaseHelper;

import java.sql.Connection;

public interface DatabaseMethods {
    Connection getConnection() throws  Exception;
    public void updateSlovakCity() throws Exception;
    public void deleteSlovakCity() throws Exception;
    public void insertSlovakCity() throws Exception;
    public void selectSlovakCities() throws Exception;
    public void countryPopulation() throws Exception;
}
