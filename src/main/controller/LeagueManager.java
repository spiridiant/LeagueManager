package main.controller;

import main.database.DatabaseConnectionHandler;
import main.delegates.LoginWindowDelegate;
import main.ui.LoginWindow;
import main.ui.TerminalWindow;

public class LeagueManager  implements LoginWindowDelegate{
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
            TerminalWindow main = new TerminalWindow();
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
}
