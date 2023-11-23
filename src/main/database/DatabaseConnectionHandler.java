package main.database;

import main.model.Contract;
import main.model.Player;
import main.model.TeamStaff;
import main.util.PrintablePreparedStatement;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");

            databaseSetup();

            System.out.println("\nDatabase set up!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        } catch (FileNotFoundException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    public Contract getPlayerContract(int pid) {


        try {
            String query = "SELECT * FROM signed_contract";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Contract model = new Contract(rs.getInt("bonus"),
                        rs.getInt("pid"),
                        rs.getInt("length"),
                        rs.getInt("value"),
                        rs.getObject("signed_date", LocalDateTime.class),
                        rs.getInt("cid"));

                if (model.getPid() == pid) return model;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        Contract c = new Contract(-1, -1,-1, -1,LocalDateTime.of(2019, 03, 28, 14, 33, 48, 640000) ,-1);
        return c;
    }

    public boolean deletePlayer(int pid) {
        String query = "DELETE FROM Player_Plays_for_Team WHERE PID = ?";
        String query2 = "DELETE FROM Signed_Contract WHERE PID = ?";
        try {
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            PrintablePreparedStatement ps2 = new PrintablePreparedStatement(connection.prepareStatement(query2), query2, false);

            connection.setAutoCommit(false);

            ps.setInt(1, pid);
            ps2.setInt(1, pid);

            int rowsAffected2 = ps2.executeUpdate();
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0 && rowsAffected2 > 0) {
                connection.commit();
                System.out.println("Player and contract deleted successfully.");
            } else {
                connection.rollback();
                System.out.println("Player not found or couldn't be deleted.");
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
        return false;
    }

    public Player[] getPlayerInfo() {
        ArrayList<Player> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM Player_Plays_for_Team";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocalDateTime debut = rs.getObject("Debut_Year", LocalDateTime.class);
                LocalDateTime dob = rs.getObject("Date_of_Birth", LocalDateTime.class);
                int height = rs.getInt("Height");
                String name = rs.getString("Name");
                int jersey = rs.getInt("Jersey#");
                int pid = rs.getInt("PID");
                String team = rs.getString("TName");
                String city = rs.getString("City");

//                Player model = new Player(
//                        rs.getObject("Debut_Year", LocalDateTime.class),
//                        rs.getObject("Date_of_Birth", LocalDateTime.class),
//                        rs.getInt("Height"),
//                        rs.getString("Name"),
//                        rs.getInt("Jersey#"),
//                        rs.getInt("PID"),
//                        rs.getString("TName"),
//                        rs.getString("City"));


                Player model = new Player(
                        debut,
                        dob,
                        height,
                        name,
                        jersey,
                        pid,
                        team,
                        city,
                        getPlayerContract(pid));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            e.printStackTrace();
        }

        return result.toArray(new Player[result.size()]);
    }

    public Contract[] getContractInfo() {
        ArrayList<Contract> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM signed_contract";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Contract model = new Contract(rs.getInt("bonus"),
                        rs.getInt("pid"),
                        rs.getInt("length"),
                        rs.getInt("value"),
                        rs.getObject("signed_date", LocalDateTime.class),
                        rs.getInt("cid"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Contract[result.size()]);
    }

    public Contract getHigherThanAvgContractByLength(int length) {
        ArrayList<Contract> result = new ArrayList<>();

        try {
            String query = "SELECT c1.cid, c1.pid, c1.bonus, c1.signed_date, AVG(c1.value) " +
                            "FROM signed_contract c1 " +
                            "GROUP BY c1.length " +
                            "HAVING  c1.value > AVG(Select AVG(c2.value) " +
                                                    "From signed_contract c2)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Contract model = new Contract(rs.getInt("bonus"),
                        rs.getInt("pid"),
                        length,
                        rs.getInt("value"),
                        rs.getObject("signed_date", LocalDateTime.class),
                        rs.getInt("cid"));
                rs.close();
                ps.close();
                return model;
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return null;
    }

    public boolean updateContract(int id, int newBonus, int newLength) {
        try {
            //String query = "UPDATE signed_contract SET bonus = ?, length = ? WHERE pid = ?";
            String query = "UPDATE signed_contract SET bonus = ?, length = ? WHERE cid = ?";

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, newBonus);
            ps.setInt(2, newLength);
            ps.setInt(3, id);
            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Contract " + id + " does not exist!");
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            return false;
        }
        return true;
    }

    public TeamStaff[] getTeamStaffInfo(int salary) {
        ArrayList<TeamStaff> result = new ArrayList<>();

        try {
            String query = "SELECT t.tname, t.city, s.name, s.salary " +
                            "FROM team t, staff s, works_for w " +
                            "WHERE s.salary >= ? AND s.stid = w.stid AND w.tname = t.tname AND w.city = t.city";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, salary);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                TeamStaff model = new TeamStaff(rs.getString("TName"),
                        rs.getString("City"),
                        rs.getString("Name"),
                        rs.getInt("Salary"));
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new TeamStaff[result.size()]);
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void databaseSetup() throws FileNotFoundException {
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setStopOnError(false);
        scriptRunner.runScript(new FileReader("./src/main/sql_scripts/dropTables.sql"));
        scriptRunner.setStopOnError(true);
        scriptRunner.runScript(new FileReader("./src/main/sql_scripts/databaseSetup.sql"));
    }


}
