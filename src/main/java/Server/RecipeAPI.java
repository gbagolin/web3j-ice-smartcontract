package Server;

import Controller.ContractController;
import Controller.RecipeController;
import Model.Recipe;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class RecipeAPI {

    public static void addRecipe(Context context) {
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        BigInteger companyId = jsonObject.getBigInteger("companyId");
        String contractAddress = context.cookieStore(privateKey);
        if (contractAddress == null) {
            context.result("Cookie not existing, deploy or load a contract first!");
            return;
        }
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
        BigInteger id = RecipeController.addRecipe(name, description, companyId, smartContract);
        if (id == null) {
            context.result("Something went wrong on recipe creation!");
            return;
        }
        Recipe recipe = new Recipe(id, name, description, companyId);
        context.json(recipe);
    }

    public static void getRecipe(Context context) {
        String privateKey = context.pathParam("privateKey");
        BigInteger recipeId = new BigInteger(context.pathParam("id"));
        String contractAddress = context.cookieStore(privateKey);
        if (contractAddress == null) {
            context.result("Cookie not existing, deploy or load a contract first!");
            return;
        }
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
        ICESmartContract.Recipe recipeReturned = RecipeController.getRecipe(recipeId, smartContract);
        if (recipeReturned == null) {
            context.result("No recipe found with that id");
        } else {
            Recipe recipe = new Recipe(recipeReturned.id, recipeReturned.name, recipeReturned.description, recipeReturned.companyId);
            context.json(recipe);
        }
    }

}
