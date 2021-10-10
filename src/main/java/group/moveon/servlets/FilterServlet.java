package group.moveon.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/filter/*")
public class FilterServlet extends HttpServlet {
    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tail = req.getPathInfo().replaceFirst("/", "");
        String[] params = tail.split("/");
        String[] operation = new String[params.length];


        String[][] queryDetails = new String[params.length][3];
        for (int i = 0; i < params.length; i++) {
            queryDetails[i][0] = params[i].split("&")[0];
            queryDetails[i][1] = params[i].split("&")[1];
            queryDetails[i][2] = params[i].split("&")[2];
        }

        for (int i = 0; i < params.length; i++) {
            if (queryDetails[i][1].equals("g")) {
                operation[i] = ">";
            } else if (queryDetails[i][1].equals("ge")) {
                operation[i] = ">=";
            } else if (queryDetails[i][1].equals("l")) {
                operation[i] = "<";
            } else if (queryDetails[i][1].equals("le")) {
                operation[i] = "<=";
            } else {
                operation[i] = "=";
            }
        }


        try {
            Class.forName("org.postgresql.Driver");
            StringBuilder queryString = new StringBuilder("SELECT * FROM ORGANIZATION WHERE ");
            for (int i = 0; i < params.length; i++) {
                queryString.append("(");
                queryString.append(queryDetails[i][0]);
                queryString.append(operation[i]);
                queryString.append("'");
                queryString.append(queryDetails[i][2]);
                queryString.append("'");
                queryString.append(")");
                queryString.append("AND");
            }
            queryString.deleteCharAt(queryString.length() - 1);
            queryString.deleteCharAt(queryString.length() - 1);
            queryString.deleteCharAt(queryString.length() - 1);
            System.out.println(queryString);
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12032001");
            System.out.println("Successfully set!");
            PreparedStatement statement = connection.prepareStatement(String.valueOf(queryString));
            statement.execute();
            ResultSet rs = statement.getResultSet();
            rs.next();

            StringBuilder xmlStr = new StringBuilder("<data>");
            while (!rs.isLast()) {
                xmlStr.append("<element>");
                xmlStr.append("<id>").append(String.valueOf(rs.getLong("id"))).append("</id>");
                xmlStr.append("<name>").append(rs.getString("name")).append("</name>");
                xmlStr.append("<coordinatex>").append(String.valueOf(rs.getFloat("coordinatex"))).append("</coordinatex>");
                xmlStr.append("<coordinatey>").append(String.valueOf(rs.getLong("coordinatey"))).append("</coordinatey>");
                xmlStr.append("<creationdate>").append(String.valueOf(rs.getDate("creationdate"))).append("</creationdate>");
                xmlStr.append("<annualturnover>").append(String.valueOf(rs.getFloat("annualturnover"))).append("</annualturnover>");
                xmlStr.append("<type>").append(rs.getString("organizationtype")).append("</type>");
                xmlStr.append("<street>").append(rs.getString("street")).append("</street>");
                xmlStr.append("<town>").append(rs.getString("town")).append("</town>");
                xmlStr.append("</element>");
                rs.next();
            }
            xmlStr.append("<element>");
            xmlStr.append("<id>").append(String.valueOf(rs.getLong("id"))).append("</id>");
            xmlStr.append("<name>").append(rs.getString("name")).append("</name>");
            xmlStr.append("<coordinatex>").append(String.valueOf(rs.getFloat("coordinatex"))).append("</coordinatex>");
            xmlStr.append("<coordinatey>").append(String.valueOf(rs.getLong("coordinatey"))).append("</coordinatey>");
            xmlStr.append("<creationdate>").append(String.valueOf(rs.getDate("creationdate"))).append("</creationdate>");
            xmlStr.append("<annualturnover>").append(String.valueOf(rs.getFloat("annualturnover"))).append("</annualturnover>");
            xmlStr.append("<type>").append(rs.getString("organizationtype")).append("</type>");
            xmlStr.append("<street>").append(rs.getString("street")).append("</street>");
            xmlStr.append("<town>").append(rs.getString("town")).append("</town>");
            xmlStr.append("<locationx>").append(String.valueOf(rs.getFloat("coordinatex"))).append("</locationx>");
            xmlStr.append("<locationy>").append(String.valueOf(rs.getLong("coordinatey"))).append("</locationy>");
            xmlStr.append("</element>");

            xmlStr.append("</data>");

            resp.setContentType("application/xml");
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(String.valueOf(xmlStr));
            printWriter.close();


            connection.close();
        } catch (SQLException |ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }
}
