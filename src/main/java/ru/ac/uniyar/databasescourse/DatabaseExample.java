package ru.ac.uniyar.databasescourse;


import ru.ac.uniyar.databasescourse.utils.SomeCsvDataLoader;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;


public class DatabaseExample {

    private static final String URL = "jdbc:mariadb://localhost:3306/secondpract";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {

        SomeCsvDataLoader.selectPath(Paths.get("data.csv"));

        try {
            SomeCsvDataLoader.loadRows();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        try {
            Connection con = DriverManager.getConnection(URL, user, password);
            Statement smt = con.createStatement();

            DBSolutionsManager.createNormalizedSolutionTables(smt);

            DBSolutionsManager.fillNormaliseodSolutionsTable(smt);

//            DBSolutionsManager.createTableForSolutions(smt);
//
//            DBSolutionsManager.fillSolutionsTable(smt, SomeCsvDataLoader.data);
//
//            DBSolutionsManager.noZachet(smt);


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }





    }


}

