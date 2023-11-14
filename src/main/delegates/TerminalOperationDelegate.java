package main.delegates;

import java.io.FileNotFoundException;

public interface TerminalOperationDelegate {
    public void databaseSetup() throws FileNotFoundException;

    public void updateContract();
}
