package Controller;

import org.ice.smart.contract.ICESmartContract;
import org.ice.smart.contract.event.EventId;
import org.ice.smart.contract.event.EventManager;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public class RecipeStepController {

    public static BigInteger addRecipeStep(String name, String description, BigInteger recipeId, BigInteger machineId, ICESmartContract smartContract) {
        try {
            TransactionReceipt transactionReceipt = smartContract.addRecipeStep(recipeId, machineId, name, description).send();
            EventManager eventManager = new EventManager(transactionReceipt, new EventId());
            List<Type> eventParameters = eventManager.getEventParameters();
            return (BigInteger) eventParameters.get(0).getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ICESmartContract.RecipeStep getRecipeStep(BigInteger id, ICESmartContract smartContract) {
        try {
            return smartContract.getRecipeStepbyId(id).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
