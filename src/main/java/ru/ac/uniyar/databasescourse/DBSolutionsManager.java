package ru.ac.uniyar.databasescourse;


import ru.ac.uniyar.databasescourse.utils.SomeCsvDataLoader;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DBSolutionsManager {

    public static void q(Statement smt){
        try {
            ResultSet rs = smt.executeQuery("SELECT * FROM cuntries");
            while(rs.next()){
                System.out.print(rs.getInt(1));
                System.out.println(rs.getString(2));

            }
        }
        catch (SQLException e){
            System.out.println("aaaaaa");
        }
    }

    public static void createTableForSolutions(Statement smt){
        try {
            smt.addBatch("DROP TABLE solutions");
            smt.addBatch("CREATE TABLE IF NOT EXISTS Solutions (\n" +
                    "    name VARCHAR(255),\n" +
                    "    surname VARCHAR(30),\n" +
                    "    card INT(6),\n" +
                    "    answer TEXT,\n" +
                    "    score FLOAT,\n" +
                    "    review TEXT,\n" +
                    "    has_pass VARCHAR(1)\n" +
                    ");");
            smt.executeBatch();

        }
        catch (SQLException e){
            System.out.println("aaaaaa");
        }
    }

    public static void fillSolutionsTable(Statement smt, List<List> data){
        try {
            String name;
            String surname;
            String card;
            String answer;
            String score;
            String review;
            String has_pass;

            for(int i = 0; i < data.size(); i++){
                List row = SomeCsvDataLoader.data.get(i);
                    name = row.get(0).toString();
                    surname = row.get(1).toString();
                    card = row.get(2).toString();
                    answer = row.get(3).toString();
                    score = row.get(4).toString();
                    review = row.get(5).toString();
                    has_pass = row.get(6).toString();

                smt.addBatch(
                        String.format("INSERT INTO solutions (name, surname, card, answer, score, review, has_pass)" +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                                name, surname, card, answer, score, review, has_pass
                        )
                );
            }

            smt.executeBatch();

        }
        catch (SQLException e){
            System.out.println("aaaaaa");
        }
    }

    public static void noZachet(Statement smt){
        try {
            ResultSet rs = smt.executeQuery("SELECT surname, answer from solutions where has_pass='F' OR has_pass='';");
            while(rs.next()){
                System.out.print(rs.getString(1) + " ");
                System.out.println(rs.getString(2));
            }
        }
        catch (SQLException e){
            System.out.println("aaaaaa");
        }
    }


    public static void createNormalizedSolutionTables(Statement smt){
        try {
            smt.addBatch("DROP TABLE IF EXISTS solutions");
            smt.addBatch("DROP TABLE IF EXISTS students");
            smt.addBatch("DROP TABLE IF EXISTS reviewers");
            smt.addBatch("DROP TABLE IF EXISTS scores");


            smt.addBatch("CREATE TABLE IF NOT EXISTS  students (" +
                    "studentID INT PRIMARY KEY," +
                    "studentName VARCHAR(50) NOT NULL," +
                    "StudentSurname VARCHAR(50))");

            smt.addBatch("CREATE TABLE IF NOT EXISTS  reviewers (" +
                    "reviewerID INT PRIMARY KEY AUTO_INCREMENT," +
                    "reviewerSurname VARCHAR(50) NOT NULL," +
                    "reviewerDepartment VARCHAR(50) NOT NULL)");

            smt.addBatch("CREATE TABLE IF NOT EXISTS  scores (" +
                    "score FLOAT PRIMARY KEY," +
                    " hasPassed VARCHAR(3))");

            smt.addBatch("CREATE TABLE IF NOT EXISTS  solutions (" +
                    "solutionID INT PRIMARY KEY AUTO_INCREMENT," +
                    "studentID INT," +
                    "fk_reviewerID INT," +

                    "FOREIGN KEY (studentID) REFERENCES students (studentID))");



            smt.executeBatch();
        }
        catch (SQLException e){
            System.out.println("ssss");
        }
    }

    public static void fillNormaliseodSolutionsTable(Statement smt){
        try {

            Set<List> uniqueUserDataSet = new HashSet<>();

            SomeCsvDataLoader.data.stream().forEach(row -> {
                List<String> allUserData = new ArrayList<String>();

                allUserData.add(row.get(0).toString());
                allUserData.add(row.get(1).toString());
                allUserData.add(row.get(2).toString());

                uniqueUserDataSet.add( allUserData );


            } );

            List uniqueUserDataList = new ArrayList<>(uniqueUserDataSet);

            for (int i = 0; i < uniqueUserDataList.size(); i++) {
                ArrayList<String> userRow = (ArrayList<String>) uniqueUserDataList.get(i);

                smt.addBatch(String.format
                        ("INSERT INTO students (studentId, studentName, studentSurname)" +
                                " VALUES ('%s', '%s', '%s')",userRow.get(0).toString(), userRow.get(1).toString(), userRow.get(2).toString())
                );
            }
            for (int i = 0; i < SomeCsvDataLoader.data.size(); i++) {

                List solutionRow = SomeCsvDataLoader.data.get(i);

                smt.addBatch(String.format
                        ("INSERT INTO solutions (solutionID, studentID, fk_reviewerID)" +
                                " VALUES ('%s', '%s', '%s')", solutionRow.get(3).toString(), solutionRow.get(0).toString(), solutionRow.get(4).toString())
                );
            }
                smt.executeBatch();


        }
        catch (SQLException e){
            System.out.println("asdasd");
        }

    }

}
