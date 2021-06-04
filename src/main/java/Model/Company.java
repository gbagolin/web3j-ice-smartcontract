package Model;

import java.math.BigInteger;

public class Company {
    public BigInteger id;
    public String name;

    public Company(BigInteger id, String name){
        this.id = id;
        this.name = name;
    }

    public Company() {
    }
}
