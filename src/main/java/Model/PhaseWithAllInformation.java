package Model;

import org.ice.smart.contract.ICESmartContract;

import java.math.BigInteger;
import java.util.List;

public class PhaseWithAllInformation extends Phase {

    public Machine machine;
    public List<MeasureWithConstraintInformation> measures;
    public RecipeStep recipeStep;

    public PhaseWithAllInformation(Phase phase,
                                   Machine machine,
                                   List<MeasureWithConstraintInformation> measures,
                                   RecipeStep recipeStep) {
        super(phase.id,
                phase.name,
                phase.description,
                phase.productId,
                phase.machineId,
                phase.recipeStepId);
        this.machine = machine;
        this.measures = measures;
        this.recipeStep = recipeStep;

    }
}
