package Server;

import Controller.ContractController;
import Controller.MeasureController;
import Model.Measure;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class MeasureAPI {

    public static void addMeasure(Context context) {
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        String name = jsonObject.getString("name");
        BigInteger measure = jsonObject.getBigInteger("measure");
        String unitOfMeasure = jsonObject.getString("unitOfMeasure");
        BigInteger measureStartTime = jsonObject.getBigInteger("measureStartTime");
        BigInteger measureEndTime = jsonObject.getBigInteger("measureEndTime");
        BigInteger phaseId = jsonObject.getBigInteger("phaseId");
        BigInteger measureConstraintId = jsonObject.getBigInteger("measureConstraintId");
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        BigInteger id = MeasureController.addMeasure(name,
                measure,
                unitOfMeasure,
                measureStartTime,
                measureEndTime,
                phaseId,
                measureConstraintId,
                smartContract);
        if (id == null) {
            context.result("Something went wrong on the measure creation!");
            return;
        }
        Measure measureObject = new Measure(id,
                name,
                measure,
                unitOfMeasure,
                measureStartTime,
                measureEndTime,
                phaseId,
                measureConstraintId
        );

        context.json(measureObject);
    }

    public static void getMeasure(Context context) {
        String privateKey = context.pathParam("privateKey");
        BigInteger measureId = new BigInteger(context.pathParam("id"));
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        ICESmartContract.Measure measureReturned;
        measureReturned = MeasureController.getMeasure(measureId, smartContract);
        if (measureReturned == null) {
            context.result("No found found with that id");
        } else {
            Measure measureObject = new Measure(measureReturned.id,
                    measureReturned.name,
                    measureReturned.measure,
                    measureReturned.unitOfMeasure,
                    measureReturned.measureStartTime,
                    measureReturned.measureEndTime,
                    measureReturned.phaseId,
                    measureReturned.measureConstraintId
            );
            context.json(measureObject);
        }
    }
}
