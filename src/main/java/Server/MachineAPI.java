package Server;

import Controller.ContractController;
import Controller.MachineController;
import Model.Machine;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class MachineAPI {

    public static void addMachine(Context context){
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        BigInteger companyId = jsonObject.getBigInteger("companyId");
        String contractAddress = context.cookieStore(privateKey);
        if (contractAddress == null) {
            context.result("Cookie not existing, deploy or load a contract first!");
            return;
        }
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
        BigInteger id = MachineController.addMachine(name, description, companyId, smartContract);
        if (id == null) {
            context.result("Something went wrong on machine creation!");
            return;
        }
        Machine machine = new Machine(id, name, description, companyId);
        context.json(machine);
    }

    public static void getMachine(Context context){
        String privateKey = context.pathParam("privateKey");
        BigInteger machineId = new BigInteger(context.pathParam("id"));
        String contractAddress = context.cookieStore(privateKey);
        if (contractAddress == null) {
            context.result("Cookie not existing, deploy or load a contract first!");
            return;
        }
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
        ICESmartContract.Machine machineReturned = MachineController.getMachineById(machineId, smartContract);
        if (machineReturned == null) {
            context.result("No machine found with that id");
        } else {
            Machine machine = new Machine(machineReturned.id,
                    machineReturned.name,
                    machineReturned.description,
                    machineReturned.companyId);
            context.json(machine);
        }
    }

}
