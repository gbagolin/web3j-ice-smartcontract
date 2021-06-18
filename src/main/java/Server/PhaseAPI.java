package Server;

import Controller.ContractController;
import Controller.PhaseController;
import Model.Phase;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class PhaseAPI {

    public static void addPhase(Context context) {
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        BigInteger productId = jsonObject.getBigInteger("productId");
        BigInteger machineId = jsonObject.getBigInteger("machineId");
        BigInteger recipeStepId = jsonObject.getBigInteger("recipeStepId");
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        BigInteger id = PhaseController.addPhase(name,
                description,
                productId,
                machineId,
                recipeStepId,
                smartContract);
        if (id == null) {
            context.result("Something went wrong on the creation of the phase!");
            return;
        }
        Phase phase = new Phase(id,
                name,
                description,
                productId,
                machineId,
                recipeStepId
        );

        context.json(phase);
    }

    public static void getPhase(Context context) {
        String privateKey = context.pathParam("privateKey");
        BigInteger phaseId = new BigInteger(context.pathParam("id"));
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        ICESmartContract.Phase phaseReturned;
        phaseReturned = PhaseController.getPhase(phaseId, smartContract);
        if (phaseReturned == null) {
            context.result("No found found with that id");
        } else {
            Phase phase = new Phase(phaseReturned.id,
                    phaseReturned.name,
                    phaseReturned.description,
                    phaseReturned.productId,
                    phaseReturned.machineId,
                    phaseReturned.recipeStepId);
            context.json(phase);
        }
    }
}
