package Server;

import Model.Company;
import Controller.CompanyController;
import io.javalin.Javalin;
import Controller.ContractController;
import org.ice.smart.contract.ICESmartContract;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class API {
    private static final Javalin app = Javalin.create();

    public static void start() {
        app.start();
        app.get("/deployContract/:privateKey", context -> {
            String privateKey = context.pathParam("privateKey");
            System.out.print(privateKey);
            ICESmartContract smartContract;
            smartContract = ContractController.deployNewContrat(Credentials.create(privateKey));
            context.cookieStore(privateKey, smartContract.getContractAddress());
        });

        app.get("/loadContract/:privateKey/:address/", context -> {
            String privateKey = context.pathParam("privateKey");
            String contractAddress = context.pathParam("address");
            ICESmartContract smartContract;
            smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
            context.cookieStore(privateKey, smartContract.getContractAddress());
        });
        app.get("/addCompany/:privateKey/:companyName", context -> {
            String privateKey = context.pathParam("privateKey");
            String contractAddress = context.cookieStore(privateKey);
            System.out.println(contractAddress);
            if (contractAddress == null) {
                context.result("Cookie not existing, deploy or load a contract first!");
                return;
            }
            ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
            String companyName = context.pathParam("companyName");
            BigInteger companyId = CompanyController.addCompany(companyName, smartContract);
            ICESmartContract.Company company = CompanyController.getCompanyById(companyId, smartContract);
            if (company == null){
                context.result("No company found with that id");
            }
            else{
                Company companyDataClass = new Company(company.id,company.name);
                context.json(companyDataClass);
            }
        });
    }
}
