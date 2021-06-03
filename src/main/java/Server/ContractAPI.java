package Server;

import Controller.ContractController;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.web3j.crypto.Credentials;

public class ContractAPI {

    public static void deployContract(Context context){
        String privateKey = context.pathParam("privateKey");
        ICESmartContract smartContract;
        smartContract = ContractController.deployNewContrat(Credentials.create(privateKey));
        context.cookieStore(privateKey, smartContract.getContractAddress());
    }

    public static void loadContract(Context context){
        String privateKey = context.pathParam("privateKey");
        String contractAddress = context.pathParam("address");
        ICESmartContract smartContract;
        smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
        context.cookieStore(privateKey, smartContract.getContractAddress());
    }

}
