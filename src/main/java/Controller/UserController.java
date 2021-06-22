package Controller;

import org.ice.smart.contract.ICESmartContract;
import org.ice.smart.contract.event.EventId;
import org.ice.smart.contract.event.EventManager;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public class UserController {

    public static Model.Response addAdmin(String adminAddress, ICESmartContract smartContract) {
        try {
            TransactionReceipt transactionReceipt = smartContract.addAdmin(adminAddress).send();
            return new Model.Response("admin added", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Model.Response addDataProvider(String adminAddress, ICESmartContract smartContract) {
        try {
            TransactionReceipt transactionReceipt = smartContract.addDataProvider(adminAddress).send();
            return new Model.Response("data provider added", 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BigInteger getUserType(ICESmartContract smartContract) {
        try {
            return smartContract.getUserType().send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
