package Controller;

import Server.ContractAddress;
import Web3jManagement.Web3jSingleton;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContractController {

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(1);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(50000000000L);
    private static final ContractGasProvider contractGasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

    public static ICESmartContract deployNewContrat(Credentials credential) {
        try {
            return ICESmartContract.deploy(Web3jSingleton.getWeb3j(), credential, contractGasProvider).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ICESmartContract loadExistingContract(Credentials credential, String contractAddress) {
        try {
            return ICESmartContract.load(contractAddress, Web3jSingleton.getWeb3j(), credential, contractGasProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeAddressToFile(String address) {
        JSONObject contract = new JSONObject();
        contract.put("address", address);
        ContractAddress.CONTRACT_ADDRESS = address;
        //Write JSON file
        try {
            Files.write(Path.of("src/main/resources/ContractAddress.json"), contract.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readContractAddress() {
        try {
            String text = Files.readString(Paths.get("src/main/resources/ContractAddress.json"));
            try {
                JSONObject contract = new JSONObject(text);
                ContractAddress.CONTRACT_ADDRESS = (String) contract.get("address");
            } catch (JSONException err) {
                err.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
