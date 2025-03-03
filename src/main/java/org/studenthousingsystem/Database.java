package org.studenthousingsystem;

import javafx.scene.control.Alert;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

import static org.studenthousingsystem.StudentAddController.alert;
import static org.studenthousingsystem.StudentHousingSystem.conn;

public class Database {

    static PreparedStatement preparedStatement;
    static ResultSet resultSet;


    public static Connection InitConn()
    {
        try
        {
            String jdbcPath = "jdbc:postgresql://localhost:5432/studenthousingsystem";
            String username = "postgres";
            String password = "123";

            // register postgresql driver
            Class.forName("org.postgresql.Driver");

            // connect to database
            Connection conn;
            conn = DriverManager.getConnection(jdbcPath, username, password);
            return conn;
        }
        catch (Exception e) {
            // connection failed to database or class not found
            System.out.println("[!] Error while connecting to the Database");
            return null;
        }
    }

    public static String MD5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashedPassword = no.toString(16);
            while (hashedPassword.length() < 32) {
                hashedPassword = "0" + hashedPassword;
            }
            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isRegEmail(String email) throws SQLException
    {
        String sql = "SELECT * FROM PERSON WHERE Email=?";
        try
        {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        finally {
            closeResources();
        }
    }


    public static boolean isSamePassword(String email, String pass) throws SQLException
    {
        String sql = "SELECT Password FROM PERSON WHERE Email=?";
        try
        {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String Password = resultSet.getString(1);
                return Password.equals(pass);
            }
        } finally {
            closeResources();
        }
        return false;
    }

    public static void insertPerson(String id, String email, String name, String password) throws SQLException
    {
        String insertPerson = "INSERT INTO PERSON (id, name, email, password) VALUES (?, ?, ?, ?)";

        try
        {
            preparedStatement = conn.prepareStatement(insertPerson);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }
    }

    public static void insertStudentPersonData(String student_id, String person_id) throws SQLException
    {
        String insertStudentPerson = "INSERT INTO STUDENT_PERSON_DATA (STUDENT_ID, PERSON_ID) VALUES (?, ?)";

        try
        {
            preparedStatement = conn.prepareStatement(insertStudentPerson);
            preparedStatement.setString(1, student_id);
            preparedStatement.setString(2, person_id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }
    }

    public static void insertStudent(Student student)
    {
        String insertStudent = "INSERT INTO STUDENT (ID, CITY, WARNINGS, PAYMENT, APPLIED_TO_ROOM) VALUES (?, ?, ?, ?, ?)";

        try
        {
            insertPerson(student.getId(), student.getEmail(), student.getName(), student.getPasswordHash());
            insertStudentPersonData(student.getStudentId(), student.getId());
            preparedStatement = conn.prepareStatement(insertStudent);
            preparedStatement.setString(1, student.getStudentId());
            preparedStatement.setString(2, student.getCity());
            preparedStatement.setInt(3, student.getWarnings());
            preparedStatement.setBoolean(4, student.isPaymentSuccessful());
            preparedStatement.setBoolean(5, student.isAppliedToRoom());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0)
            {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Student Added Successfully");
                alert.show();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }
    }

    public static void insertStaffPersonData(String staff_id, String person_id) throws SQLException
    {
        String insertStaffPerson = "INSERT INTO STAFF_PERSON_DATA (STAFF_ID, PERSON_ID) VALUES (?, ?)";

        try
        {
            preparedStatement = conn.prepareStatement(insertStaffPerson);
            preparedStatement.setString(1, staff_id);
            preparedStatement.setString(2, person_id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }
    }

    public static void insertStaff(Staff staff)
    {
        String insertStaff = "INSERT INTO STAFF (ID, SALARY) VALUES (?, ?)";
        try
        {
            insertPerson(staff.getId(), staff.getEmail(), staff.getName(), staff.getPasswordHash());
            preparedStatement = conn.prepareStatement(insertStaff);
            preparedStatement.setString(1, staff.getStaff_id());
            preparedStatement.setDouble(2, staff.getSalary());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0)
            {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Staff Added Successfully");
                alert.show();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }
    }

    public static void insertGatekeeperPersonData(String gatekeeper_id, String person_id) throws SQLException
    {
        String insertGatekeeperPerson = "INSERT INTO GATEKEEPER_PERSON_DATA (GATEKEEPER_ID, PERSON_ID) VALUES (?, ?)";

        try
        {
            preparedStatement = conn.prepareStatement(insertGatekeeperPerson);
            preparedStatement.setString(1, gatekeeper_id);
            preparedStatement.setString(2, person_id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }
    }

    public static void insertGatekeeper(Gatekeeper gatekeeper)
    {
        String insertGatekeeper = "INSERT INTO GATEKEEPER (ID, SALARY) VALUES (?, ?)";
        try
        {
            insertPerson(gatekeeper.getId(), gatekeeper.getEmail(), gatekeeper.getName(), gatekeeper.getPasswordHash());
            preparedStatement = conn.prepareStatement(insertGatekeeper);
            preparedStatement.setString(1, gatekeeper.getGatekeeper_id());
            preparedStatement.setDouble(2, gatekeeper.getSalary());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0)
            {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Gatekeeper Added Successfully");
                alert.show();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }
    }

    public static void insertRoomData(int roomNumber, int floorNumber, int isFilled) {
        String sql = "INSERT INTO ROOM (ROOM_NUMBER, FLOOR, OCCUPIED) VALUES (?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setInt(2, floorNumber);
            preparedStatement.setInt(3, isFilled);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Room Added Successfully");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }


    public static int isAppliedForDorm(String id) throws SQLException
    {
        String sql = "SELECT apllied_to_room FROM STUDENT WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } finally {
            closeResources();
        }
        return 0;
    }


    public static void setAppliedForDorm(String id, boolean applied)
    {
        try
        {
            String sql = "UPDATE STUDENT SET APPLIED_TO_ROOM = ? WHERE id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setBoolean(1, applied);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("[!] Error");
        }
        finally {
            closeResources();
        }
    }


    private static void closeResources() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Student getStudent(String id) throws SQLException
    {
        String selectStudent = """
                SELECT
                    s.id AS s_id,
                    city,
                    warnings,
                    payment,
                    applied_to_room,
                    p.id AS p_id,
                    email,
                    name,
                    password
                FROM
                    student AS s
                    INNER JOIN student_person_data AS spd ON spd.student_id = s.id
                    INNER JOIN person AS p ON spd.person_id = p.id
                WHERE
                    s.id = ?;
                """;

        try
        {
            preparedStatement = conn.prepareStatement(selectStudent);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                id = resultSet.getString(1);
                String city = resultSet.getString(2);
                int warnings = resultSet.getInt(3);
                boolean payment = resultSet.getBoolean(4);
                boolean applied_to_room = resultSet.getBoolean(5);
                String pId = resultSet.getString(6);
                String email = resultSet.getString(7);
                String name = resultSet.getString(8);
                String password = resultSet.getString(9);

                return new Student(id, name, email, city, password, warnings, payment, applied_to_room, pId);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }

        return null;
    }

    public static ArrayList<Student> getAllStudents() throws SQLException
    {
        String selectallStudents = """
                SELECT
                    *
                FROM
                    student;
                """;

        ArrayList<Student> allStudents = new ArrayList<>();
        try
        {
            preparedStatement = conn.prepareStatement(selectallStudents);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String id = resultSet.getString(1);
                String city = resultSet.getString(2);
                int warnings = resultSet.getInt(3);
                boolean payment = resultSet.getBoolean(4);
                boolean applied_to_room = resultSet.getBoolean(5);
                String pId = resultSet.getString(6);
                String email = resultSet.getString(7);
                String name = resultSet.getString(8);
                String password = resultSet.getString(9);
                allStudents.add(new Student(id, name, email, city, password, warnings, payment, applied_to_room, pId));
                return allStudents;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }

        return allStudents;
    }

    public static int personSize() throws SQLException
    {
        int totalCount = getAllStudents().size();
        String getLastGatekeeperId = """
                SELECT
                    max(id)
                FROM
                    gatekeeper;
                """;

        String getLastStaffId = """
                SELECT
                    max(id)
                FROM
                    staff;
                """;

        try
        {
            preparedStatement = conn.prepareStatement(getLastGatekeeperId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String id = resultSet.getString(1);
                totalCount += Integer.parseInt(id);
            }

            preparedStatement = conn.prepareStatement(getLastStaffId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String id = resultSet.getString(1);
                totalCount += Integer.parseInt(id);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }

        return totalCount;
    }
}
