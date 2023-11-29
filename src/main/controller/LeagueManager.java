package main.controller;

import main.Exception.InvalidBonusException;
import main.Exception.InvalidLengthException;
import main.Exception.InvalidSalaryException;
import main.Exception.NullContractException;
import main.database.DatabaseConnectionHandler;
import main.delegates.LoginWindowDelegate;
import main.delegates.TerminalOperationDelegate;
import main.model.*;
import main.ui.LoginWindow;
import main.ui.TerminalWindow;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class LeagueManager  implements LoginWindowDelegate, TerminalOperationDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public LeagueManager() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

//            TerminalTransactions transaction = new TerminalTransactions();
//            transaction.setupDatabase(this);
//            transaction.showMainMenu(this);
            new TerminalWindow(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) {
        LeagueManager leagueManager = new LeagueManager();
        leagueManager.start();
    }

    @Override
    public Contract[] getContractInfo() {
        return dbHandler.getContractInfo();
    }

    @Override
    public Player[] getPlayerInfo() { return dbHandler.getPlayerInfo(); }

    /**
     * TerminalOperationDelegate Implementation
     *
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */
    @Override
    public void databaseSetup() throws FileNotFoundException {
//        dbHandler.databaseSetup();
    }

    @Override
    public boolean updateContract(Contract contract, String length, String bonus) throws NullContractException, InvalidBonusException, InvalidLengthException {
        if (contract == null) {
            throw new NullContractException();
        }
        int newBonus = 0;
        int newLength = 0;

        try {
            newLength = Integer.parseInt(length);
            if(newLength <= 0 || newLength > 5) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new InvalidLengthException();
        }
        try {
            newBonus = Integer.parseInt(bonus);
            if(newBonus < 0 || newBonus > 75000) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new InvalidBonusException();
        }
        return dbHandler.updateContract(contract.getID(), newLength, newBonus);
    }

    @Override
    public TeamStaff[] getTeamStaffInfo(String input) throws InvalidSalaryException {
        try {
            int salary = Integer.parseInt(input);
            return dbHandler.getTeamStaffInfo(salary);
        } catch(NumberFormatException e) {
            throw new InvalidSalaryException();
        }
    }

    public boolean insertPlayer(LocalDateTime debutYear, LocalDateTime dob, int height, String name, int jerseyNum, int pid, String tName, String City) {
        // insert check for conflicting pid
        return dbHandler.insertPlayer(debutYear, dob, height, name, jerseyNum, pid, tName, City);
    }

    @Override
    public boolean deletePlayer(int pid) {
        return dbHandler.deletePlayer(pid);
    }

    @Override
    public int getHigherThanAvgContractByLength(int length) {
        return dbHandler.getHigherThanAvgContractByLength(length);
    }

    @Override
    public Sponsor[] getSponsors() {
        return dbHandler.getSponsors();
    }

    @Override
    public Team[] getTeamSponsoredByAll() {
        return dbHandler.getTeamSponsoredByAll();
    }
}
