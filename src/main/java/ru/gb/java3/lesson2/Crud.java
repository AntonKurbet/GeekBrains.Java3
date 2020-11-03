package ru.gb.java3.lesson2;

import java.sql.*;
import java.util.*;

public class Crud {
//    1. Создать CRUD операции,
//      1 метод создани таблицы
//      2 метод для добавления записи
//      3 метод для получения записи
//      4 метод для удаления записи
//      5 удаление таблицы
//    2. Обновить данные в БД из файла (файл приложен test.txt)
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    synchronized static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:crud_test.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void createTable(String tableName, HashMap<String,String> fields) throws SQLException {
        String query = "CREATE TABLE " + tableName + "(";

        Set<Map.Entry<String,String>> fieldSet = fields.entrySet();
        Iterator<Map.Entry<String,String>> fieldIter = fieldSet.iterator();

        while (fieldIter.hasNext()) {
            Map.Entry pair =fieldIter.next();
            query = String.format("%s %s %s,",query, pair.getKey(), pair.getValue());
        }
        query = query.substring(0,query.length() - 1) + ");";

        statement.execute(query);
    }

    void insertRow(String tableName, LinkedHashMap<String,String> fields, Object[] values) throws SQLException {
        String query = "INSERT INTO " + tableName + "(";

        Set<Map.Entry<String,String>> fieldSet = fields.entrySet();
        Iterator<Map.Entry<String,String>> fieldIter = fieldSet.iterator();

        while (fieldIter.hasNext()) {
            Map.Entry pair = fieldIter.next();
            query = String.format("%s ?,",pair.getKey());
        }

        query = query.substring(0,query.length() - 1) + ") VALUES(";

        for (int i = 0; i < fields.size(); i++) {
            query = String.format("%s ?,",query);
        }
        query = query.substring(0,query.length() - 1) + ");";

        connection.prepareStatement(query);

//        int i = 0;
//        while (fieldIter.hasNext()) {
//            i++;
//            Map.Entry pair = fieldIter.next();
//            switch (pair.getValue().toString()) {
//                case "char":
//                    preparedStatement.setString(i, values[i].toString());
//                    break;
//                case "int":
//                    preparedStatement.setInt(i, Integer.parseInt(values[i]));
//            };
//        }

        //        preparedStatement.setString(1, name);
//        preparedStatement.setInt(2, price);

        int rows = preparedStatement.executeUpdate();
    }
}
