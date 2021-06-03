package Controller;

import org.ice.smart.contract.ICESmartContract;
import org.ice.smart.contract.event.EventId;
import org.ice.smart.contract.event.EventManager;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public class MeasureController {

    public static BigInteger addMeasure(String name,
                                        BigInteger measure,
                                        String unitOfMeasure,
                                        BigInteger measureStartTime,
                                        BigInteger measureEndTime,
                                        BigInteger phaseId,
                                        BigInteger measureConstraintId,
                                        ICESmartContract smartContract) {
        try {
            TransactionReceipt transactionReceipt = smartContract.addMeasure(phaseId,
                    measureConstraintId,
                    name,
                    measure,
                    unitOfMeasure,
                    measureStartTime,
                    measureEndTime).send();
            EventManager eventManager = new EventManager(transactionReceipt, new EventId());
            List<Type> eventParameters = eventManager.getEventParameters();
            return (BigInteger) eventParameters.get(0).getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ICESmartContract.Measure getMeasure(BigInteger id, ICESmartContract smartContract) {
        try {
            return smartContract.getMeasureById(id).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ICESmartContract.Measure> getAllMeasureByPhaseId(BigInteger id, ICESmartContract smartContract) {
        try {
            return smartContract.getMeasuresByPhaseId(id).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
