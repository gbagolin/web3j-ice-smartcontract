package Model;

import java.math.BigInteger;

public class MeasureConstraint {
    public BigInteger id;
    public String name;
    public BigInteger maxMeasure;
    public BigInteger minMeasure;
    public String unitOfMeasure;
    public BigInteger recipeStepId;
    public BigInteger machineId;

    public MeasureConstraint(BigInteger id,
                             String name,
                             BigInteger maxMeasure,
                             BigInteger minMeasure,
                             String unitOfMeasure,
                             BigInteger recipeStepId,
                             BigInteger machineId) {
        this.id = id;
        this.name = name;
        this.maxMeasure = maxMeasure;
        this.minMeasure = minMeasure;
        this.unitOfMeasure = unitOfMeasure;
        this.recipeStepId = recipeStepId;
        this.machineId = machineId;
    }
}
