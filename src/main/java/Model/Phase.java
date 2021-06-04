package Model;

import java.math.BigInteger;

public class Phase {

    public BigInteger id;
    public String name;
    public String description;
    public BigInteger productId;
    public BigInteger machineId;
    public BigInteger recipeStepId;


    public Phase(BigInteger id,
                 String name,
                 String description,
                 BigInteger productId,
                 BigInteger machineId,
                 BigInteger recipeStepId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productId = productId;
        this.machineId = machineId;
        this.recipeStepId = recipeStepId;
    }

    public Phase() {
    }
}
