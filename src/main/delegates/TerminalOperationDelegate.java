package main.delegates;

import main.model.Contract;
import main.model.TeamStaff;

import java.io.FileNotFoundException;

public interface TerminalOperationDelegate {
    public Contract[] getContractInfo();
    public void databaseSetup() throws FileNotFoundException;
    public boolean updateContract(int id, int newBonus, int newLength);

    public TeamStaff[] getTeamStaffInfo(int salary);
}
