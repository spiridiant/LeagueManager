package main.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Contract {
    private int bonus;
    private int pid;
    private int length;
    private int value;
    private LocalDateTime signedDate;
    private int cid;
    public Contract(int bonus, int pid, int length, int value, LocalDateTime signedDate, int cid) {
        this.bonus = bonus;
        this.pid = pid;
        this.length = length;
        this.value = value;
        this.signedDate = signedDate;
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "ID: " + cid +
                ", Player: " + pid +
                ", Bonus: " + bonus +
                ", Length: " + length + " years" +
                ", Value: " + value +
                ", Signed Date: " + signedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public int getID() {
        return cid;
    }

    public int getBonus() {
        return bonus;
    }

    public int getLength() {
        return length;
    }
}
