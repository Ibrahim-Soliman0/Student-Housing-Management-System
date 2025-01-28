//package org.studenthousingsystem;
//
//import javafx.scene.control.Alert;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.sql.*;
//
//import static org.studenthousingsystem.StudentAddController.alert;
//import static org.studenthousingsystem.StudentHousingSystem.conn;
//
//public class Database {
//
//    static PreparedStatement preparedStatement;
//    static ResultSet resultSet = null;
//    static StringBuilder s;
//
//
//    public static Connection InitConn() {
//        try {
//            Connection conn ;
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "STUDENTHOUSINGSYSTEM", "123456");
//            return conn;
//        } catch (Exception e) {
//            System.out.println("[!] Error while connecting to the Database");
//            return null;
//        }
//    }
//
//    public static String MD5Hash(String password) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] messageDigest = md.digest(password.getBytes());
//            BigInteger no = new BigInteger(1, messageDigest);
//            String hashedPassword = no.toString(16);
//            while (hashedPassword.length() < 32) {
//                hashedPassword = "0" + hashedPassword;
//            }
//            return hashedPassword;
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static Student getStudent(String email, String password) throws SQLException {
//        String sql = "SELECT * FROM STUDENT WHERE Email = ? and Password = ?";
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, email);
//            preparedStatement.setString(2, MD5Hash(password));
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String ID = resultSet.getString(1);
//                String City = resultSet.getString(2);
//                String password1 = resultSet.getString(3);
//                int warnings = resultSet.getInt(4);
//                boolean payment = resultSet.getBoolean(5);
//                int applied = resultSet.getInt(6);
//                String Email = resultSet.getString(7);
//                String Name = resultSet.getString(8);
//                return new Student(Name, Email, ID, City);
//            }
//        } finally {
//            closeResources();
//        }
//        return null;
//    }
//
//
//    public static boolean isRegEmail(String email) throws SQLException {
//        String sql = "SELECT * FROM STUDENT WHERE Email=?";
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, email);
//            resultSet = preparedStatement.executeQuery();
//            return resultSet.next();
//        } finally {
//            closeResources();
//        }
//    }
//
//
//    public static boolean isSamePassword(String email, String pass) throws SQLException {
//        String sql = "SELECT Password FROM STUDENT WHERE Email=?";
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, email);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                String Password = resultSet.getString(1);
//                return Password.equals(pass);
//            }
//        } finally {
//            closeResources();
//        }
//        return false;
//    }
//
//
//    static void insertStudentData(String id, String name, String city, String email, String hashedPassword, int warnings, boolean payment, int applied) {
//        String sql = "INSERT INTO STUDENT (ID, CITY, PASSWORD, WARNINGS, PAYMENT_SUCCESSFUL, APPLIED, EMAIL, NAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, id);
//            preparedStatement.setString(2, city);
//            preparedStatement.setString(3, hashedPassword);
//            preparedStatement.setInt(4, warnings);
//            preparedStatement.setBoolean(5, payment);
//            preparedStatement.setInt(6, applied);
//            preparedStatement.setString(7, email);
//            preparedStatement.setString(8, name);
//            int rowsInserted = preparedStatement.executeUpdate();
//            if (rowsInserted > 0 ) {
//                alert = new Alert(Alert.AlertType.CONFIRMATION, "Student Added Successfully");
//                alert.show();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeResources();
//        }
//    }
//
//    static void insertAdmintData(String id, String email, String password) {
//        String sql = "INSERT INTO STUDENT (ID, PASSWORD, EMAIL) VALUES (?, ?, ?)";
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, id);
//            preparedStatement.setString(2, email);
//            preparedStatement.setString(3, MD5Hash(password));
//            int rowsInserted = preparedStatement.executeUpdate();
//            if (rowsInserted > 0) {
//                alert = new Alert(Alert.AlertType.CONFIRMATION, "Admin Added Successfully");
//                alert.show();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeResources();
//        }
//    }
//
//    static void insertRoomData(int roomNumber, int floorNumber, int isFilled) {
//        String sql = "INSERT INTO ROOM (ROOM_NUMBER, FLOOR, OCCUPIED) VALUES (?, ?, ?)";
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setInt(1, roomNumber);
//            preparedStatement.setInt(2, floorNumber);
//            preparedStatement.setInt(3, isFilled);
//            int rowsInserted = preparedStatement.executeUpdate();
//            if (rowsInserted > 0) {
//                alert = new Alert(Alert.AlertType.CONFIRMATION, "Room Added Successfully");
//                alert.show();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeResources();
//        }
//    }
//
//
//    public static int isAppliedForDorm(String id) throws SQLException {
//        String sql = "SELECT Applied FROM STUDENT WHERE id=?";
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, id);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getInt(1);
//            }
//        } finally {
//            closeResources();
//        }
//        return 0;
//    }
//
//
//    public static void setAppliedForDorm(String id, int v) {
//        try {
//            String sql = "UPDATE STUDENT SET Applied = ? WHERE id = ?";
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setInt(1, v);
//            preparedStatement.setString(2, id);
//            preparedStatement.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("[!] Error");
//        } finally {
//            closeResources();
//        }
//    }
//
//
//    private static void closeResources() {
//        try {
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//            if (resultSet != null) {
//                resultSet.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    static void insertAdmin(String Email, String Password) {
//        String sql = "INSERT INTO ADMIN (EMAIL, PASSWORD) VALUES (?,?)";
//        try {
//            preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1,Email);
//            preparedStatement.setString(2, Password);
//            int rowsInserted = preparedStatement.executeUpdate();
//            if (rowsInserted > 0) {
//                alert = new Alert(Alert.AlertType.CONFIRMATION, "Admin Added Successfully");
//                alert.show();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeResources();
//        }
//    }
//}
