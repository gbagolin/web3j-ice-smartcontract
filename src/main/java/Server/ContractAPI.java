package Server;

import Controller.ContractController;
import Model.Response;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

public class ContractAPI {

    public static void deployContract(Context context){
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        ICESmartContract smartContract;
        smartContract = ContractController.deployNewContrat(Credentials.create(privateKey));
        context.status(200);
        context.json(new Response("ok", 200));
    }
    @Deprecated
    public static void loadContract(Context context){
        String privateKey = context.pathParam("privateKey");
        String contractAddress = context.pathParam("address");
        ICESmartContract smartContract;
        smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
        context.cookieStore(privateKey, smartContract.getContractAddress());
        context.json(new Response("ok", 200));
    }

}
