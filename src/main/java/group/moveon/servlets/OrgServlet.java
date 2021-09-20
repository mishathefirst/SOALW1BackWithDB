package group.moveon.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/organizations")
public class OrgServlet extends HttpServlet {
    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "pwd");
            System.out.println("Successfully set!");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORGANIZATION");
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "pwd");
            System.out.println("Successfully set!");
            PreparedStatement preStatement = connection.prepareStatement("SELECT COUNT(*) FROM ORGANIZATION");
            preStatement.execute();
            ResultSet preRS = preStatement.getResultSet();
            preRS.next();
            String index = String.valueOf(Integer.valueOf(preRS.getString(1)) + 1);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ORGANIZATION VALUES (" + index + ", \'" + req.getParameter("name") + "\', "
                    + req.getParameter("coordinatex") + ", " + req.getParameter("coordinatey") + ", \'" + req.getParameter("creationDate") + "\', "
                    + req.getParameter("annualTurnover") + ", \'" + req.getParameter("type") + "\', \'" + req.getParameter("street") + "\', \'"
                    + req.getParameter("town") + "\')");
            statement.execute();

            connection.close();
        } catch (SQLException |ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }
}
