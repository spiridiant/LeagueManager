package main.database;

import main.model.Contract;
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

    public boolean updateContract(int id, int newBonus, int newLength) {
        try {
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
