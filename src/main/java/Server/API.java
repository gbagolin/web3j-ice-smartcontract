package Server;

import Controller.*;
import Model.*;
import io.javalin.Javalin;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
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
        app.post("/addCompany",
                context -> {
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

        app.post("/addMachine",
                context -> {
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
                        Machine machine = new Machine(machineReturned.id,
                                machineReturned.name,
                                machineReturned.description,
                                machineReturned.companyId);
                        context.json(machine);
                    }
                });

        app.post("/addRecipe",
                context -> {
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
                        context.result("No recipe found with that id");
                    } else {
                        Recipe recipe = new Recipe(recipeReturned.id, recipeReturned.name, recipeReturned.description, recipeReturned.companyId);
                        context.json(recipe);
                    }
                });

        app.post("/addRecipeStep",
                context -> {
                    JSONObject jsonObject = new JSONObject(context.body());
                    String privateKey = jsonObject.getString("privateKey");
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    BigInteger recipeId = jsonObject.getBigInteger("recipeId");
                    BigInteger machineId = jsonObject.getBigInteger("machineId");
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    BigInteger id = RecipeStepController.addRecipeStep(name, description, recipeId, machineId, smartContract);
                    if (id == null) {
                        context.result("Something went wrong on recipe step creation!");
                        return;
                    }
                    RecipeStep recipeStep = new RecipeStep(id, name, description, recipeId, machineId);
                    context.json(recipeStep);
                });

        app.get("/getRecipeStep" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    BigInteger recipeStepId = new BigInteger(context.pathParam("id"));
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    ICESmartContract.RecipeStep recipeStepReturned = RecipeStepController.getRecipeStep(recipeStepId, smartContract);
                    if (recipeStepReturned == null) {
                        context.result("No recipe step found with that id");
                    } else {
                        RecipeStep recipeStep = new RecipeStep(recipeStepReturned.id,
                                recipeStepReturned.name,
                                recipeStepReturned.description,
                                recipeStepReturned.recipeId,
                                recipeStepReturned.machineId);
                        context.json(recipeStep);
                    }
                });

        app.post("/addMeasureConstraint",
                context -> {
                    JSONObject jsonObject = new JSONObject(context.body());
                    String privateKey = jsonObject.getString("privateKey");
                    String name = jsonObject.getString("name");
                    BigInteger maxMeasure = jsonObject.getBigInteger("maxMeasure");
                    BigInteger minMeasure = jsonObject.getBigInteger("minMeasure");
                    String unitOfMeasure = jsonObject.getString("unitOfMeasure");
                    BigInteger recipeStepId = jsonObject.getBigInteger("recipeStepId");
                    BigInteger machineId = jsonObject.getBigInteger("machineId");
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    BigInteger id = MeasureConstraintController.addMeasureConstraint(name,
                            maxMeasure,
                            minMeasure,
                            unitOfMeasure,
                            recipeStepId,
                            machineId,
                            smartContract);
                    if (id == null) {
                        context.result("Something went wrong on adding the measure constraint!");
                        return;
                    }
                    MeasureConstraint measureConstraint = new MeasureConstraint(id,
                            name,
                            maxMeasure,
                            minMeasure,
                            unitOfMeasure,
                            recipeStepId,
                            machineId);
                    context.json(measureConstraint);
                });

        app.get("/getMeasureConstraint" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    BigInteger measureConstraintId = new BigInteger(context.pathParam("id"));
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    ICESmartContract.MeasureConstraint measureConstraintReturned;
                    measureConstraintReturned = MeasureConstraintController.getMeasureConstraint(measureConstraintId, smartContract);
                    if (measureConstraintReturned == null) {
                        context.result("No recipe step found with that id");
                    } else {
                        MeasureConstraint measureConstraint = new MeasureConstraint(measureConstraintReturned.id,
                                measureConstraintReturned.name,
                                measureConstraintReturned.maxMeasure,
                                measureConstraintReturned.minMeasure,
                                measureConstraintReturned.unitOfMeasure,
                                measureConstraintReturned.recipeStepId,
                                measureConstraintReturned.machineId);
                        context.json(measureConstraint);
                    }
                });
        app.post("/addProduct",
                context -> {
                    JSONObject jsonObject = new JSONObject(context.body());
                    String privateKey = jsonObject.getString("privateKey");
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    BigInteger companyId = jsonObject.getBigInteger("companyId");
                    BigInteger recipeId = jsonObject.getBigInteger("recipeId");
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    BigInteger id = ProductController.addProduct(name,
                            description,
                            companyId,
                            recipeId,
                            smartContract);
                    if (id == null) {
                        context.result("Something went wrong on the creation of the product!");
                        return;
                    }
                    Product product = new Product(id,
                            name,
                            description,
                            companyId,
                            recipeId);

                    context.json(product);
                });

        app.get("/getProduct" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    BigInteger productId = new BigInteger(context.pathParam("id"));
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    ICESmartContract.Product productReturned;
                    productReturned = ProductController.getProduct(productId, smartContract);
                    if (productReturned == null) {
                        context.result("No found found with that id");
                    } else {
                        Product product = new Product(productReturned.id, productReturned.name,
                                productReturned.description,
                                productReturned.companyId,
                                productReturned.recipeId);
                        context.json(product);
                    }
                });

        app.post("/addPhase",
                context -> {
                    JSONObject jsonObject = new JSONObject(context.body());
                    String privateKey = jsonObject.getString("privateKey");
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    BigInteger productId = jsonObject.getBigInteger("productId");
                    BigInteger machineId = jsonObject.getBigInteger("machineId");
                    BigInteger recipeStepId = jsonObject.getBigInteger("recipeStepId");
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    BigInteger id = PhaseController.addPhase(name,
                            description,
                            productId,
                            machineId,
                            recipeStepId,
                            smartContract);
                    if (id == null) {
                        context.result("Something went wrong on the creation of the phase!");
                        return;
                    }
                    Phase phase = new Phase(id,
                            name,
                            description,
                            productId,
                            machineId,
                            recipeStepId
                    );

                    context.json(phase);
                });

        app.get("/getPhase" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    BigInteger phaseId = new BigInteger(context.pathParam("id"));
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    ICESmartContract.Phase phaseReturned;
                    phaseReturned = PhaseController.getPhase(phaseId, smartContract);
                    if (phaseReturned == null) {
                        context.result("No found found with that id");
                    } else {
                        Phase phase = new Phase(phaseReturned.id,
                                phaseReturned.name,
                                phaseReturned.description,
                                phaseReturned.productId,
                                phaseReturned.machineId,
                                phaseReturned.recipeStepId);
                        context.json(phase);
                    }
                });

        app.post("/addMeasure",
                context -> {
                    JSONObject jsonObject = new JSONObject(context.body());
                    String privateKey = jsonObject.getString("privateKey");
                    String name = jsonObject.getString("name");
                    BigInteger measure = jsonObject.getBigInteger("measure");
                    String unitOfMeasure = jsonObject.getString("unitOfMeasure");
                    BigInteger measureStartTime = jsonObject.getBigInteger("measureStartTime");
                    BigInteger measureEndTime = jsonObject.getBigInteger("measureEndTime");
                    BigInteger phaseId = jsonObject.getBigInteger("phaseId");
                    BigInteger measureConstraintId = jsonObject.getBigInteger("measureConstraintId");
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    BigInteger id = MeasureController.addMeasure(name,
                            measure,
                            unitOfMeasure,
                            measureStartTime,
                            measureEndTime,
                            phaseId,
                            measureConstraintId,
                            smartContract);
                    if (id == null) {
                        context.result("Something went wrong on the measure creation!");
                        return;
                    }
                    Measure measureObject = new Measure(id,
                            name,
                            measure,
                            unitOfMeasure,
                            measureStartTime,
                            measureEndTime,
                            phaseId,
                            measureConstraintId
                    );

                    context.json(measureObject);
                });

        app.get("/getMeasure" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    String privateKey = context.pathParam("privateKey");
                    BigInteger measureId = new BigInteger(context.pathParam("id"));
                    String contractAddress = context.cookieStore(privateKey);
                    if (contractAddress == null) {
                        context.result("Cookie not existing, deploy or load a contract first!");
                        return;
                    }
                    ICESmartContract smartContract = ContractController.loadExistingContract(Credentials.create(privateKey), contractAddress);
                    ICESmartContract.Measure measureReturned;
                    measureReturned = MeasureController.getMeasure(measureId, smartContract);
                    if (measureReturned == null) {
                        context.result("No found found with that id");
                    } else {
                        Measure measureObject = new Measure(measureReturned.id,
                                measureReturned.name,
                                measureReturned.measure,
                                measureReturned.unitOfMeasure,
                                measureReturned.measureStartTime,
                                measureReturned.measureEndTime,
                                measureReturned.phaseId,
                                measureReturned.measureConstraintId
                        );
                        context.json(measureObject);
                    }
                });
    }
}
