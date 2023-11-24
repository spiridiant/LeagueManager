package main.delegates;

import main.Exception.InvalidBonusException;
import main.Exception.InvalidLengthException;
import main.Exception.InvalidSalaryException;
import main.Exception.NullContractException;
import main.model.*;

import java.io.FileNotFoundException;

public interface TerminalOperationDelegate {
    public Contract[] getContractInfo();

    public Player[] getPlayerInfo();
    public boolean deletePlayer(int pid);
    public void databaseSetup() throws FileNotFoundException;
    public boolean updateContract(Contract contract, String Length,String bonus) throws NullContractException, InvalidBonusException, InvalidLengthException;

    public TeamStaff[] getTeamStaffInfo(String input) throws InvalidSalaryException;
    public int getHigherThanAvgContractByLength(int length);
    public Sponsor[] getSponsors();
    public Team[] getTeamSponsoredByAll();
}
