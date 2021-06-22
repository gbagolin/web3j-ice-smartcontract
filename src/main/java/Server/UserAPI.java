package Server;

import Controller.CompanyController;
import Controller.ContractController;
import Controller.UserController;
import Model.Company;
import Model.UserType;
import io.javalin.http.Context;
import org.eclipse.jetty.server.Authentication;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class UserAPI {

    public static void addAdmin(Context context) {
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        String userAddress = jsonObject.getString("userAddress");
        Model.Response response = UserController.addAdmin(userAddress, smartContract);
        if (response == null) {
            context.result("Admin not added, please make sure to be the contract owner");
        } else {
            context.json(response);
        }
    }

    public static void addDataProvider(Context context) {
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        String userAddress = jsonObject.getString("userAddress");
        Model.Response response = UserController.addDataProvider(userAddress, smartContract);
        if (response == null) {
            context.result("Data provider not added, please make sure to be the company's admin");
        } else {
            context.json(response);
        }
    }

    public static void getUserType(Context context) {
        String privateKey = context.pathParam("privateKey");
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), ContractAddress.CONTRACT_ADDRESS);
        BigInteger userType = UserController.getUserType(smartContract);
        if (userType == null) {
            context.result("Something went wrong in retriving this information, please try again.");
        } else {
            context.json(new UserType(userType));
        }
    }
}
