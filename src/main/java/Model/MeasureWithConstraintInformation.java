package Model;

import java.math.BigInteger;

public class MeasureWithConstraintInformation extends Measure {

    public MeasureConstraint measureConstraint;

    public MeasureWithConstraintInformation(Measure measure, MeasureConstraint measureConstraint) {
        super(measure.id,
                measure.name,
                measure.measure,
                measure.unitOfMeasure,
                measure.measureStartTime,
                measure.measureEndTime,
                measure.phaseId,
                measure.measureConstraintId
                );
        this.measureConstraint = measureConstraint;
    }


}
