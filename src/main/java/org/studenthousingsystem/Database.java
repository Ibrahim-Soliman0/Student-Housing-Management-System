package org.studenthousingsystem;

import javafx.scene.control.Alert;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.studenthousingsystem.StudentHousingSystem.conn;
import static org.studenthousingsystem.StudentHousingSystem.staff;

@SuppressWarnings("DuplicatedCode")
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

    private static void closeResources() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean isRegEmail(String email)
    {
        String sql = "SELECT * FROM PERSON WHERE Email=?";
        try
        {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
        return false;
    }

    public static boolean isSamePassword(String email, String pass)
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
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
        return false;
    }

    public static String isStudent(String email)
    {
        String sql = """
                SELECT
                    s.id AS s_id
                 FROM
                    student AS s
                    INNER JOIN student_person_data AS spd ON spd.student_id = s.id
                    INNER JOIN person AS p ON spd.person_id = p.id
                 WHERE
                    email = ?;
                """;
        try
        {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getString("s_id");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally
        {
            closeResources();
        }

        return "0";
    }

    public static String isStaff(String email)
    {
        String sql = """
                SELECT
                    s.id AS s_id
                FROM
                    staff AS s
                    INNER JOIN staff_person_data AS spd ON spd.staff_id = s.id
                    INNER JOIN person AS p ON spd.person_id = p.id
                WHERE
                    email = ?;
                """;
        try
        {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getString(1);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally
        {
            closeResources();
        }

        return "0";
    }

    public static String isGatekeeper(String email)
    {
        String sql = """
                SELECT
                    gk.id AS gk_id
                FROM
                    gatekeeper AS gk
                    INNER JOIN gatekeeper_person_data AS gpd ON gpd.gatekeeper_id = gk.id
                    INNER JOIN person AS p ON gpd.person_id = p.id
                WHERE
                    email = ?;
                """;
        try
        {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getString(1);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally
        {
            closeResources();
        }

        return "0";
    }

    public static void insertPerson(String id, String email, String name, String password)
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
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
    }

    public static void insertStudentPersonData(String student_id, String person_id)
    {
        String insertStudentPerson = "INSERT INTO STUDENT_PERSON_DATA (STUDENT_ID, PERSON_ID) VALUES (?, ?)";

        try
        {
            preparedStatement = conn.prepareStatement(insertStudentPerson);
            preparedStatement.setString(1, student_id);
            preparedStatement.setString(2, person_id);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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
            String personId = Database.getNextPersonId();
            insertPerson(personId, student.getEmail(), student.getName(), student.getPasswordHash());
            insertStudentPersonData(student.getStudentId(), personId);
            preparedStatement = conn.prepareStatement(insertStudent);
            preparedStatement.setString(1, student.getStudentId());
            preparedStatement.setString(2, student.getCity());
            preparedStatement.setInt(3, student.getWarnings());
            preparedStatement.setBoolean(4, student.isPaymentSuccessful());
            preparedStatement.setBoolean(5, student.isAppliedToRoom());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Student Added Successfully");
                alert.show();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
    }

    public static void insertStaffPersonData(String staff_id, String person_id)
    {
        String insertStaffPerson = "INSERT INTO STAFF_PERSON_DATA (STAFF_ID, PERSON_ID) VALUES (?, ?)";

        try
        {
            preparedStatement = conn.prepareStatement(insertStaffPerson);
            preparedStatement.setString(1, staff_id);
            preparedStatement.setString(2, person_id);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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
            String staffId = Database.getNextStaffId();
            String personId = Database.getNextPersonId();
            insertPerson(personId, staff.getEmail(), staff.getName(), staff.getPasswordHash());
            insertStaffPersonData(staffId, personId);
            preparedStatement = conn.prepareStatement(insertStaff);
            preparedStatement.setString(1, staffId);
            preparedStatement.setDouble(2, staff.getSalary());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Staff Added Successfully");
                alert.show();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
    }

    public static void insertGatekeeperPersonData(String gatekeeper_id, String person_id)
    {
        String insertGatekeeperPerson = "INSERT INTO GATEKEEPER_PERSON_DATA (GATEKEEPER_ID, PERSON_ID) VALUES (?, ?)";

        try
        {
            preparedStatement = conn.prepareStatement(insertGatekeeperPerson);
            preparedStatement.setString(1, gatekeeper_id);
            preparedStatement.setString(2, person_id);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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
            String gatekeeperId = Database.getNextGatekeeperId();
            String personId = Database.getNextPersonId();
            insertPerson(personId, gatekeeper.getEmail(), gatekeeper.getName(), gatekeeper.getPasswordHash());
            insertGatekeeperPersonData(gatekeeperId, personId);
            preparedStatement = conn.prepareStatement(insertGatekeeper);
            preparedStatement.setString(1, gatekeeperId);
            preparedStatement.setDouble(2, gatekeeper.getSalary());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Gatekeeper Added Successfully");
                alert.show();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
    }

    public static void insertRoomData(Room room)
    {
        String insertRoom = "INSERT INTO ROOM (ID, ROOM_NUMBER, BUILDING, FLOOR, OCCUPIED) VALUES (?, ?, ?, ?, ?)";
        try
        {
            preparedStatement = conn.prepareStatement(insertRoom);
            preparedStatement.setString(1, Database.getNextRoomId());
            preparedStatement.setString(2, room.getRoomNumber());
            preparedStatement.setString(3, room.getBuilding());
            preparedStatement.setString(4, room.getFloor());
            preparedStatement.setBoolean(5, room.isOccupied());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Room Added Successfully");
                alert.show();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
    }

    public static void makeRoomRequest(RoomRequest roomRequest)
    {
        String makeRoomRequest = "INSERT INTO room_requests (room_id, student_id, staff_id, status) VALUES (?, ?, NULL, 'pending')";
        try
        {
            preparedStatement = conn.prepareStatement(makeRoomRequest);
            preparedStatement.setString(1, roomRequest.getRoom().getId());
            preparedStatement.setString(2, roomRequest.getStudent().getStudentId());
            preparedStatement.executeQuery();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
    }

    public static void approveRoomRequest(RoomRequest roomRequest)
    {
        String makeRoomRequest = """
                                UPDATE
                                    room_requests
                                SET
                                    staff_id = ?, status = 'approved'
                                WHERE
                                    room_id = ? AND student_id = ?""";
        try
        {
            preparedStatement = conn.prepareStatement(makeRoomRequest);
            preparedStatement.setString(1, roomRequest.getStaff().getStaff_id());
            preparedStatement.setString(2, roomRequest.getRoom().getId());
            preparedStatement.setString(3, roomRequest.getStudent().getStudentId());
            preparedStatement.executeQuery();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        addToOccupiedRooms(roomRequest.getStudent().getStudentId(), roomRequest.getRoom().getId());
    }

    public static void rejectRoomRequest(RoomRequest roomRequest)
    {
        String rejectRequest = """
                                UPDATE
                                    room_requests
                                SET
                                    staff_id = ?, status = 'rejected'
                                WHERE
                                    room_id = ? AND student_id = ?""";
        try
        {
            preparedStatement = conn.prepareStatement(rejectRequest);
            preparedStatement.setString(1, roomRequest.getStaff().getStaff_id());
            preparedStatement.setString(2, roomRequest.getRoom().getId());
            preparedStatement.setString(3, roomRequest.getStudent().getStudentId());
            preparedStatement.executeQuery();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        setAppliedForDorm(roomRequest.getStudent().getStudentId(), false);
    }

    public static void addToOccupiedRooms(String student_id, String room_id)
    {
        String occupyRoom = "INSERT INTO occupied_rooms (student_id, room_id) VALUES (?, ?)";
        try
        {
            preparedStatement = conn.prepareStatement(occupyRoom);
            preparedStatement.setString(1, student_id);
            preparedStatement.setString(2, room_id);
            preparedStatement.executeQuery();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
    }

    public static String isAppliedForDorm(String id)
    {
        String sql = "SELECT apllied_to_room FROM STUDENT WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
        return "0";
    }

    public static void setAppliedForDorm(String id, boolean applied)
    {
        String sql = "UPDATE STUDENT SET APPLIED_TO_ROOM = ? WHERE id = ?";
        try
        {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setBoolean(1, applied);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
    }

    public static void giveWarningToStudent(Student student, int warnings)
    {
        String giveWarning = "UPDATE STUDENT SET warnings = ? WHERE id = ?";
        try
        {
            preparedStatement = conn.prepareStatement(giveWarning);
            preparedStatement.setInt(1, warnings);
            preparedStatement.setString(2, student.getStudentId());
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }
    }

    public static boolean isADormStudent(Student student)
    {
        String isADormStudent = "SELECT * FROM occupied_rooms WHERE student_id = ?";
        try
        {
            preparedStatement = conn.prepareStatement(isADormStudent);
            preparedStatement.setString(1, student.getStudentId());
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return false;
    }

    public static Student getStudent(String id)
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
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return null;
    }

    public static ArrayList<Student> getAllStudents()
    {
        String selectallStudents = """
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
                    INNER JOIN person AS p ON spd.person_id = p.id;
                """;

        ArrayList<Student> allStudents = new ArrayList<>();
        try
        {
            preparedStatement = conn.prepareStatement(selectallStudents);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
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
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return allStudents;
    }

    public static Staff getStaff(String id)
    {
        String selectStaff = """
                SELECT
                    s.id AS s_id,
                    salary,
                    p.id AS p_id,
                    email,
                    name,
                    password
                FROM
                    staff AS s
                    INNER JOIN staff_person_data AS spd ON spd.staff_id = s.id
                    INNER JOIN person AS p ON spd.person_id = p.id
                WHERE
                    s.id = ?;
                """;

        try
        {
            preparedStatement = conn.prepareStatement(selectStaff);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                id = resultSet.getString(1);
                double salary = resultSet.getDouble(2);
                String pId = resultSet.getString(3);
                String email = resultSet.getString(4);
                String name = resultSet.getString(5);
                String password = resultSet.getString(6);

                return new Staff(name, email, salary, pId, password, id);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return null;
    }

    public static Gatekeeper getGatekeeper(String id)
    {
        String selectGatekeeper = """
                SELECT
                    gk.id AS gk_id,
                    salary,
                    p.id AS p_id,
                    email,
                    name,
                    password
                FROM
                    gatekeeper AS gk
                    INNER JOIN gatekeeper_person_data AS gpd ON gpd.gatekeeper_id = gk.id
                    INNER JOIN person AS p ON gpd.person_id = p.id
                WHERE
                    gk.id = ?;
                """;

        try
        {
            preparedStatement = conn.prepareStatement(selectGatekeeper);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                id = resultSet.getString(1);
                double salary = resultSet.getDouble(2);
                String pId = resultSet.getString(3);
                String email = resultSet.getString(4);
                String name = resultSet.getString(5);
                String password = resultSet.getString(6);

                return new Gatekeeper(name, email, salary, pId, password, id);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return null;
    }

    public static Room getRoom(String id)
    {
        String selectRoom = """
                SELECT
                    *
                FROM
                    room
                WHERE
                    id = ?;
                """;

        try
        {
            preparedStatement = conn.prepareStatement(selectRoom);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                id = resultSet.getString(1);
                String floor = resultSet.getString(2);
                String building = resultSet.getString(3);
                boolean occupied = resultSet.getBoolean(4);
                String roomNumber = resultSet.getString(5);

                return new Room(roomNumber, floor, building, occupied, id);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return null;
    }

    public static ArrayList<Room> getAllNonOccupiedRooms()
    {
        String selectAllRooms = """
                SELECT
                    *
                FROM
                    room
                WHERE
                    occupied = false
                """;

        ArrayList<Room> allRooms = new ArrayList<>();
        try
        {
            preparedStatement = conn.prepareStatement(selectAllRooms);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String id = resultSet.getString(1);
                String floor = resultSet.getString(2);
                String building = resultSet.getString(3);
                boolean occupied = resultSet.getBoolean(4);
                String roomNumber = resultSet.getString(5);

                allRooms.add(new Room(roomNumber, floor, building, occupied, id));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return allRooms;
    }

    public static ArrayList<Room> getAllRooms()
    {
        String selectAllRooms = """
                SELECT
                    *
                FROM
                    room
                """;

        ArrayList<Room> allRooms = new ArrayList<>();
        try
        {
            preparedStatement = conn.prepareStatement(selectAllRooms);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String id = resultSet.getString(1);
                String floor = resultSet.getString(2);
                String building = resultSet.getString(3);
                boolean occupied = resultSet.getBoolean(4);
                String roomNumber = resultSet.getString(5);

                allRooms.add(new Room(roomNumber, floor, building, occupied, id));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return allRooms;
    }

    public static ArrayList<RoomRequest> getAllRoomRequests()
    {
        String selectAllRoomRequests = """
                SELECT
                    *
                FROM
                    room_requests
                """;

        ArrayList<RoomRequest> allRequests = new ArrayList<>();
        try
        {
            preparedStatement = conn.prepareStatement(selectAllRoomRequests);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                String room_id = resultSet.getString(1);
                String student_id = resultSet.getString(2);
                String staff_id = resultSet.getString(3);
                String status = resultSet.getString(4);

                allRequests.add(new RoomRequest(
                        Database.getRoom(room_id), Database.getStudent(student_id), Database.getStaff(staff_id), status));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return allRequests;
    }

    public static boolean insertStudentToEntranceLog(Student student, Gatekeeper gatekeeper, LocalDateTime time, String type)
    {
        String insertStudentToEntranceLog = """
                        INSERT INTO student_entrance_log (id, gatekeeper_id, student_id, time, type) VALUES(?,?,?,?,?);
                        """;

        try
        {
            preparedStatement = conn.prepareStatement(insertStudentToEntranceLog);
            preparedStatement.setString(1, Database.getNextEntranceLogId());
            preparedStatement.setString(2, gatekeeper.getId());
            preparedStatement.setString(3, student.getStudentId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(time));
            preparedStatement.setString(5, type);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return false;
    }

    public static String getNextPersonId()
    {
        String nextPersonId = """
                SELECT nextval('person_id_seq');
                """;

        try
        {
            preparedStatement = conn.prepareStatement(nextPersonId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return String.valueOf(resultSet.getInt(1));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return "0";
    }

    public static String getNextStaffId()
    {
        String nextStaffId = """
                SELECT nextval('staff_id_seq');
                """;

        try
        {
            preparedStatement = conn.prepareStatement(nextStaffId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return String.valueOf(resultSet.getInt(1));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return "0";
    }

    public static String getNextGatekeeperId()
    {
        String nextGatekeeperId = """
                SELECT nextval('gatekeeper_id_seq');
                """;

        try
        {
            preparedStatement = conn.prepareStatement(nextGatekeeperId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return String.valueOf(resultSet.getInt(1));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return "0";
    }

    public static String getNextRoomId()
    {
        String nextRoomId = """
                SELECT nextval('room_id_seq');
                """;

        try
        {
            preparedStatement = conn.prepareStatement(nextRoomId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return String.valueOf(resultSet.getInt(1));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return "0";
    }

    public static String getNextEntranceLogId()
    {
        String nextEntranceLogId = """
                SELECT nextval('entrance_log_id_seq');
                """;

        try
        {
            preparedStatement = conn.prepareStatement(nextEntranceLogId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return String.valueOf(resultSet.getInt(1));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            closeResources();
        }

        return "0";
    }
}
