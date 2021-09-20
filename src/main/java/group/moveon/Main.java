package group.moveon;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12032001");
            System.out.println("Successfully set!");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORGANIZATION");
            statement.execute();
            ResultSet rs = statement.getResultSet();
            rs.next();
            System.out.println(rs.getString("name"));
            connection.close();
        } catch (
                SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
