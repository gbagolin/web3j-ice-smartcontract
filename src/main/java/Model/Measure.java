package Model;

import java.math.BigInteger;

public class Measure {

    public BigInteger id;
    public String name;
    public BigInteger measure;
    public String unitOfMeasure;
    public BigInteger measureStartTime;
    public BigInteger measureEndTime;
    public BigInteger phaseId;
    public BigInteger measureConstraintId;

    public Measure(BigInteger id,
                   String name,
                   BigInteger measure,
                   String unitOfMeasure,
                   BigInteger measureStartTime,
                   BigInteger measureEndTime,
                   BigInteger phaseId,
                   BigInteger measureConstraintId) {
        this.id = id;
        this.name = name;
        this.measure = measure;
        this.unitOfMeasure = unitOfMeasure;
        this.measureStartTime = measureStartTime;
        this.measureEndTime = measureEndTime;
        this.phaseId = phaseId;
        this.measureConstraintId = measureConstraintId;
    }

    public Measure() {
    }
}
