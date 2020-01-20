package sk.itsovy.matysko.projectWorldX.genericita;

import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        Box<Integer> box = new Box<>();
        box.setValue(1234678);
        Box<String> box2 =new Box<>();
        box2.setValue("hello");
        System.out.println(box2.getValue()+" "+box.getValue());

    }
}
