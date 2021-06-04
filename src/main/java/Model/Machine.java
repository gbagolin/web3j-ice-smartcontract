package Model;

import java.math.BigInteger;

public class Machine {
    public BigInteger id;
    public String name;
    public String description;
    public BigInteger companyId;

    public Machine(BigInteger id, String name, String description, BigInteger companyId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.companyId = companyId;
    }

    public Machine() {
    }
}
