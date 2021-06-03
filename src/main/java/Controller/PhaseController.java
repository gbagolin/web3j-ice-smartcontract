package Controller;

import org.ice.smart.contract.ICESmartContract;
import org.ice.smart.contract.event.EventId;
import org.ice.smart.contract.event.EventManager;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public class PhaseController {

    public static BigInteger addPhase(String name,
                                      String description,
                                      BigInteger productId,
                                      BigInteger machineId,
                                      BigInteger recipeStepId,
                                      ICESmartContract smartContract) {
        try {
            TransactionReceipt transactionReceipt = smartContract.addPhase(productId, machineId, recipeStepId, name, description).send();
            EventManager eventManager = new EventManager(transactionReceipt, new EventId());
            List<Type> eventParameters = eventManager.getEventParameters();
            return (BigInteger) eventParameters.get(0).getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ICESmartContract.Phase getPhase(BigInteger id, ICESmartContract smartContract) {
        try {
            return smartContract.getPhaseById(id).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ICESmartContract.Phase> getPhasesByProductId(BigInteger id, ICESmartContract smartContract) {
        try {
            return smartContract.getPhasesByProductId(id).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
