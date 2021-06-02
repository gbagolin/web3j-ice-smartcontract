package Controller;

import org.ice.smart.contract.ICESmartContract;
import org.ice.smart.contract.event.EventId;
import org.ice.smart.contract.event.EventManager;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public class CompanyController {
    /**
     * Add a company returning the id;
     * @param name
     * @param smartContract
     * @return
     */
    public static BigInteger addCompany(String name, ICESmartContract smartContract) {
        try {
            TransactionReceipt transactionReceipt = smartContract.addCompany(name).send();
            EventManager eventManager = new EventManager(transactionReceipt, new EventId());
            List<Type> eventParameters = eventManager.getEventParameters();
            return (BigInteger) eventParameters.get(0).getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ICESmartContract.Company getCompanyById(BigInteger id, ICESmartContract smartContract) {
        try {
            return smartContract.getCompanyById(id).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
