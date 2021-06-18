package Server;

import Controller.ContractController;
import Controller.RecipeStepController;
import Model.RecipeStep;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class RecipeStepAPI {
    public static void addRecipeStep(Context context) {
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        BigInteger recipeId = jsonObject.getBigInteger("recipeId");
        BigInteger machineId = jsonObject.getBigInteger("machineId");
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        BigInteger id = RecipeStepController.addRecipeStep(name, description, recipeId, machineId, smartContract);
        if (id == null) {
            context.result("Something went wrong on recipe step creation!");
            return;
        }
        RecipeStep recipeStep = new RecipeStep(id, name, description, recipeId, machineId);
        context.json(recipeStep);
    }

    public static void getRecipeStep(Context context) {
        String privateKey = context.pathParam("privateKey");
        BigInteger recipeStepId = new BigInteger(context.pathParam("id"));
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        ICESmartContract.RecipeStep recipeStepReturned = RecipeStepController.getRecipeStep(recipeStepId, smartContract);
        if (recipeStepReturned == null) {
            context.result("No recipe step found with that id");
        } else {
            RecipeStep recipeStep = new RecipeStep(recipeStepReturned.id,
                    recipeStepReturned.name,
                    recipeStepReturned.description,
                    recipeStepReturned.recipeId,
                    recipeStepReturned.machineId);
            context.json(recipeStep);
        }
    }
}
