package Server;

import Controller.ContractController;
import Controller.MeasureConstraintController;
import Model.MeasureConstraint;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class MeasureContraintAPI {

    public static void addMeasureConstraint(Context context) {
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        String name = jsonObject.getString("name");
        BigInteger maxMeasure = jsonObject.getBigInteger("maxMeasure");
        BigInteger minMeasure = jsonObject.getBigInteger("minMeasure");
        String unitOfMeasure = jsonObject.getString("unitOfMeasure");
        BigInteger recipeStepId = jsonObject.getBigInteger("recipeStepId");
        BigInteger machineId = jsonObject.getBigInteger("machineId");
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        BigInteger id = MeasureConstraintController.addMeasureConstraint(name,
                maxMeasure,
                minMeasure,
                unitOfMeasure,
                recipeStepId,
                machineId,
                smartContract);
        if (id == null) {
            context.result("Something went wrong on adding the measure constraint!");
            return;
        }
        MeasureConstraint measureConstraint = new MeasureConstraint(id,
                name,
                maxMeasure,
                minMeasure,
                unitOfMeasure,
                recipeStepId,
                machineId);
        context.json(measureConstraint);
    }

    public static void getMeasureConstraint(Context context) {
        String privateKey = context.pathParam("privateKey");
        BigInteger measureConstraintId = new BigInteger(context.pathParam("id"));
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        ICESmartContract.MeasureConstraint measureConstraintReturned;
        measureConstraintReturned = MeasureConstraintController.getMeasureConstraint(measureConstraintId, smartContract);
        if (measureConstraintReturned == null) {
            context.result("No recipe step found with that id");
        } else {
            MeasureConstraint measureConstraint = new MeasureConstraint(measureConstraintReturned.id,
                    measureConstraintReturned.name,
                    measureConstraintReturned.maxMeasure,
                    measureConstraintReturned.minMeasure,
                    measureConstraintReturned.unitOfMeasure,
                    measureConstraintReturned.recipeStepId,
                    measureConstraintReturned.machineId);
            context.json(measureConstraint);
        }
    }
}
