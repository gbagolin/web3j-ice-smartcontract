package Controller;

import Web3jManagement.Web3jSingleton;
import org.ice.smart.contract.ICESmartContract;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class ContractController {

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(1);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(50000000000L);
    private static final ContractGasProvider contractGasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

    public static ICESmartContract deployNewContrat(Credentials credential)  {
        try {
            return ICESmartContract.deploy(Web3jSingleton.getWeb3j(), credential, contractGasProvider).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ICESmartContract loadExistingContract(Credentials credential,String contractAddress)  {
        try {
           return ICESmartContract.load(contractAddress, Web3jSingleton.getWeb3j(), credential, contractGasProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
