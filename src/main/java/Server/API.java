package Server;

import Controller.MachineController;
import Controller.RecipeController;
import Model.Company;
import Controller.CompanyController;
import Model.Machine;
import Model.Recipe;
import io.javalin.Javalin;
import Controller.ContractController;
import org.ice.smart.contract.ICESmartContract;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class API {
    private static final Javalin app = Javalin.create();

    public static void start() {
        app.start();
        app.get("/deployContract" +
                        "/:privateKey",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    ICESmartContract smartContract;
                    smartContract = ContractController.deployNewContrat(Credentials.create(privateKey));
                    context.cookieStore(privateKey, smartContract.getContractAddress());
                });

        app.get("/loadContract" +
                        "/:privateKey" +
                        "/:address",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    String contractAddress = context.pathParam("address");
                    ICESmartContract smartContract;
                    smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    context.cookieStore(privateKey, smartContract.getContractAddress());
                });
        app.get("/addCompany" +
                        "/:privateKey" +
                        "/:companyName",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    String companyName = context.pathParam("companyName");
                    BigInteger companyId = CompanyController.addCompany(companyName, smartContract);
                    ICESmartContract.Company companyReturned = CompanyController.getCompanyById(companyId, smartContract);
                    if (companyReturned == null) {
                        context.result("Something went wrong on company creation");
                    } else {
                        Company company = new Company(companyReturned.id, companyReturned.name);
                        context.json(company);
                    }
                });

        app.get("/getCompany" +
                        "/:privateKey" +
                        "/:companyId",
                context -> {
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
                });

        app.get("/addMachine" +
                        "/:privateKey" +
                        "/:name" +
                        "/:description" +
                        "/:companyId",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    String name = context.pathParam("name");
                    String description = context.pathParam("description");
                    BigInteger companyId = new BigInteger(context.pathParam("companyId"));
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
                });

        app.get("/getMachine" +
                        "/:privateKey" +
                        "/:id",
                context -> {
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
                        Machine machine = new Machine(machineReturned.id, machineReturned.name, machineReturned.description, machineReturned.companyId);
                        context.json(machine);
                    }
                });

        app.get("/addRecipe" +
                        "/:privateKey" +
                        "/:name" +
                        "/:description" +
                        "/:companyId",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    String name = context.pathParam("name");
                    String description = context.pathParam("description");
                    BigInteger companyId = new BigInteger(context.pathParam("companyId"));
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    BigInteger id = RecipeController.addRecipe(name, description, companyId, smartContract);
                    if (id == null) {
                        context.result("Something went wrong on recipe creation!");
                        return;
                    }
                    Recipe recipe = new Recipe(id, name, description, companyId);
                    context.json(recipe);
                });

        app.get("/getRecipe" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    BigInteger recipeId = new BigInteger(context.pathParam("id"));
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    ICESmartContract.Recipe recipeReturned = RecipeController.getRecipe(recipeId, smartContract);
                    if (recipeReturned == null) {
                        context.result("No machine found with that id");
                    } else {
                        Recipe recipe = new Recipe(recipeReturned.id, recipeReturned.name, recipeReturned.description, recipeReturned.companyId);
                        context.json(recipe);
                    }
                });

    }
}
