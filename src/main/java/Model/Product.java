package Model;

import java.math.BigInteger;

public class Product {

    public BigInteger id;
    public String name;
    public String description;
    public BigInteger companyId;
    public BigInteger recipeId;

    public Product(BigInteger id,
                   String name,
                   String description,
                   BigInteger companyId,
                   BigInteger recipeId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.companyId = companyId;
        this.recipeId = recipeId;
    }
}
