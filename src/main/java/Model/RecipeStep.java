package Model;

import java.math.BigInteger;

public class RecipeStep {
    public BigInteger id;
    public String name;
    public String description;
    public BigInteger recipeId;
    public BigInteger machineId;

    public RecipeStep(BigInteger id,
                      String name,
                      String description,
                      BigInteger recipeId,
                      BigInteger machineId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.recipeId = recipeId;
        this.machineId = machineId;
    }

    public RecipeStep() {
    }
}
