package Controller;

import org.ice.smart.contract.ICESmartContract;
import org.ice.smart.contract.event.EventId;
import org.ice.smart.contract.event.EventManager;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public class MeasureConstraintController {

    public static BigInteger addMeasureConstraint(String name,
                                                  BigInteger maxMeasure,
                                                  BigInteger minMeasure,
                                                  String unitOfMeasure,
                                                  BigInteger recipeStepId,
                                                  BigInteger machineId,
                                                  ICESmartContract smartContract) {
        try {
            TransactionReceipt transactionReceipt = smartContract.addMeasureConstraint(recipeStepId,
                    machineId,
                    name,
                    maxMeasure,
                    minMeasure,
                    unitOfMeasure).send();
            EventManager eventManager = new EventManager(transactionReceipt, new EventId());
            List<Type> eventParameters = eventManager.getEventParameters();
            return (BigInteger) eventParameters.get(0).getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ICESmartContract.MeasureConstraint getMeasureConstraint(BigInteger id, ICESmartContract smartContract) {
        try {
            return smartContract.getMeasureConstraintById(id).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
