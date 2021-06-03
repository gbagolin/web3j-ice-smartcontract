package Server;

import Controller.CompanyController;
import Controller.ContractController;
import Model.Company;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class CompanyAPI {

    public static void addCompany(Context context) {
        JSONObject jsonObject = new JSONObject(context.body());
        String privateKey = jsonObject.getString("privateKey");
        String contractAddress = context.cookieStore(privateKey);
        if (contractAddress == null) {
            context.result("Cookie not existing, deploy or load a contract first!");
            return;
        }
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
        String companyName = jsonObject.getString("name");
        BigInteger companyId = CompanyController.addCompany(companyName, smartContract);
        ICESmartContract.Company companyReturned = CompanyController.getCompanyById(companyId, smartContract);
        if (companyReturned == null) {
            context.result("Something went wrong on company creation");
        } else {
            Company company = new Company(companyReturned.id, companyReturned.name);
            context.json(company);
        }
    }

    public static void getCompany(Context context){
        String privateKey = context.pathParam("privateKey");
        BigInteger companyId = new BigInteger(context.pathParam("companyId"));
        String contractAddress = context.cookieStore(privateKey);
        if (contractAddress == null) {
            context.result("Cookie not existing, deploy or load a contract first!");
            return;
        }
        ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
        ICESmartContract.Company companyReturned = CompanyController.getCompanyById(companyId, smartContract);
        if (companyReturned == null) {
            context.result("No company found with that id");
        } else {
            Company company = new Company(companyReturned.id, companyReturned.name);
            context.json(company);
        }
    }

}
