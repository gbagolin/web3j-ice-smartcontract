package Controller;

import org.ice.smart.contract.ICESmartContract;
import org.ice.smart.contract.event.EventId;
import org.ice.smart.contract.event.EventManager;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public class RecipeController {

    public static BigInteger addRecipe(String name, String description, BigInteger companyId, ICESmartContract smartContract) {
        try {
            TransactionReceipt transactionReceipt = smartContract.addRecipe(companyId, name,description).send();
            EventManager eventManager = new EventManager(transactionReceipt, new EventId());
            List<Type> eventParameters = eventManager.getEventParameters();
            return (BigInteger) eventParameters.get(0).getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ICESmartContract.Recipe getRecipe(BigInteger id, ICESmartContract smartContract) {
        try {
            return smartContract.getRecipebyId(id).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
