package ContractManagement;


import org.ice.smart.contract.ICESmartContract;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;

public class SingletonSmartContract {

    private static ICESmartContract smartContract = null;
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(1);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(50000000000L);
    private static final ContractGasProvider contractGasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
    private static final String CONTRACT_ADDRES = "0xC6A26530Bc820d59CC06F926CAB3B7C20A569F5E";

    private static void setSingletonSmartContract(Credentials credentials){
        smartContract = ICESmartContract.load(CONTRACT_ADDRES, Web3jSingleton.getWeb3j(), credentials, contractGasProvider);
    }

    public static ICESmartContract getSmartContract(Credentials credentials){
        if (smartContract == null){
            setSingletonSmartContract(credentials);
        }
        return smartContract;
    }

}
