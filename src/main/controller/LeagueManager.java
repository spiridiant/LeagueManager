package main.controller;

import main.database.DatabaseConnectionHandler;
import main.delegates.LoginWindowDelegate;
import main.delegates.TerminalOperationDelegate;
import main.model.Contract;
import main.model.TeamStaff;
import main.ui.LoginWindow;
import main.ui.TerminalWindow;

import java.io.FileNotFoundException;

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
    public boolean updateContract(int id, int newBonus, int newLength) {
        return dbHandler.updateContract(id, newBonus, newLength);
    }

    @Override
    public TeamStaff[] getTeamStaffInfo(int salary) {
        return dbHandler.getTeamStaffInfo(salary);
    }
}
