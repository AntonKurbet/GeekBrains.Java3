package ru.gb.java3.lesson2;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

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
            connection = DriverManager.getConnection("jdbc:sqlite:students.db");
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

    static void createTable(String tableName, HashMap<String, String> fields) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + "(";

        Set<Map.Entry<String,String>> fieldSet = fields.entrySet();
        Iterator<Map.Entry<String,String>> fieldIter = fieldSet.iterator();

        while (fieldIter.hasNext()) {
            Entry pair =fieldIter.next();
            query = String.format("%s %s %s,",query, pair.getKey(), pair.getValue());
        }
        query = query.substring(0,query.length() - 1) + ");";

        statement.execute(query);
    }

    static void insertRow(String tableName, LinkedHashMap<String, String> fields, String[] values) throws SQLException {
        String query = "INSERT INTO " + tableName + " (";

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            query = String.format("%s %s,",query,entry.getKey());
        }

        query = query.substring(0,query.length() - 1) + ") VALUES (";

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            query = String.format("%s ?,",query);
        }

        query = query.substring(0,query.length() - 1) + ");";

        preparedStatement = connection.prepareStatement(query);

        int i = 0;
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            switch (entry.getValue()) {
                case "varchar":
                    preparedStatement.setString(i + 1, values[i]);
                    break;
                case "int":
                    preparedStatement.setInt(i + 1, Integer.parseInt(values[i]));
            }
            i++;
        }

        preparedStatement.executeUpdate();
    }

    static String selectRows(String tableName, String[] fields, String where) throws SQLException {
        String query = "SELECT ";

        for (int i = 0; i < fields.length; i++) {
            query = String.format("%s %s,",query,fields[i]);
        }
        query = query.substring(0,query.length() - 1) + " FROM " + tableName;
        if (!where.isEmpty()) {
            query+= " WHERE " + where;
        }
        query+= ";";

        String result = "";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            for (int i = 0; i < fields.length; i++) {
                result = String.format("%s %s,",result,resultSet.getString(fields[i]));
            }
            result = result.substring(0,result.length() - 1) + "\n";
        }
        return result;
    }

    static void deleteRows(String tableName, String where) throws SQLException {
        String query = "DELETE FROM " + tableName;
        if (!where.isEmpty()) {
            query+= " WHERE " + where;
        }
        query+= ";";

        statement.execute(query);
    }

    static void dropTable(String tableName) throws SQLException {
        String query = "DROP TABLE IF EXISTS " + tableName;
        statement.execute(query);
    }

    private static void updateStudentsRow(String tableName, String[] values) throws SQLException {
        String query = "UPDATE " + tableName + " SET name = '" + values[1] + "',"
                + " score = " + values[2] + " WHERE id = " + values[0] + ";";
        statement.execute(query);
    }

    static void updateFromFile(String filename)  {
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename)))) {
            String s;
            while ((s = r.readLine()) != null) {
                String[] values = new String[3];
                values = s.split(" ");
                updateStudentsRow("students", values);
            }
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        connect();
        HashMap<String,String> fields = new HashMap<>();
        fields.put("id","INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE");
        fields.put("name","varchar(50)");
        fields.put("score","int");

        LinkedHashMap<String,String> fieldsToIns = new LinkedHashMap<>();
        fieldsToIns.put("name","varchar");
        fieldsToIns.put("score","int");

        try {
            // 1
            createTable("students", fields);
            insertRow("students",fieldsToIns, new String[]{"Anton", "100"});
            System.out.println(selectRows("students",
                    new String[]{"name", "score"},""));
            deleteRows("students","name = 'Anton'");
            System.out.println(selectRows("students",
                    new String[]{"name", "score"},""));
            dropTable("students");

            //2
            createTable("students", fields);
            fieldsToIns.put("id","int");
            for (int i = 1; i <= 3; i++) {
                insertRow("students",fieldsToIns,
                        new String[]{"Bob" + i, Integer.toString(100 * i), Integer.toString(i)});
            }
            updateFromFile("test.txt");
            System.out.println(selectRows("students",
                    new String[]{"id", "name", "score"},""));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

}
