package Model;

import java.math.BigInteger;

public class Recipe {
    public BigInteger id;
    public String name;
    public String description;
    public BigInteger companyId;

    public Recipe(BigInteger id, String name, String description, BigInteger companyId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.companyId = companyId;
    }

    public Recipe() {
    }
}
