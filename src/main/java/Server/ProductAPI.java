package Server;

import Controller.*;
import Model.*;
import io.javalin.http.Context;
import org.ice.smart.contract.ICESmartContract;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ProductAPI {

    public static void addProduct(Context context) {
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
    }

    public static void getProduct(Context context) {
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
    }

    public static void getProductInformation(Context context) {
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
        Product productModel;
        productModel = new Product(productReturned.id,
                productReturned.name,
                productReturned.description,
                productReturned.companyId,
                productReturned.recipeId);
        if (productReturned == null) {
            context.result("No product with that id");
            return;
        }

        ICESmartContract.Company companyReturned;
        companyReturned = CompanyController.getCompanyById(productReturned.companyId,
                smartContract);

        Company companyModel = new Company(companyReturned.id, companyReturned.name);

        if (companyReturned == null) {
            context.result("No company with that id");
            return;
        }

        ICESmartContract.Recipe recipeReturned;
        recipeReturned = RecipeController.getRecipe(productReturned.recipeId, smartContract);

        Recipe recipeModel = new Recipe(recipeReturned.id,
                recipeReturned.name,
                recipeReturned.description,
                recipeReturned.companyId);


        if (recipeReturned == null) {
            context.result("No recipe with that id");
            return;
        }

        List<ICESmartContract.Phase> phases;
        phases = PhaseController.getPhasesByProductId(productId, smartContract);

        if (phases == null) {
            context.result("No phase with that id");
            return;
        }

        List<PhaseWithAllInformation> phaseWithAllInformations = new ArrayList<>();

        for (ICESmartContract.Phase phase : phases) {
            Phase modelPhase = new Phase(phase.id,
                    phase.name,
                    phase.description,
                    phase.productId,
                    phase.machineId,
                    phase.recipeStepId);

            ICESmartContract.Machine machineReturned = MachineController.getMachineById(phase.machineId, smartContract);

            if (machineReturned == null) {
                context.result("No machine with id: " + machineReturned.id);
                return;
            }
            Machine machineModel = new Machine(machineReturned.id,
                    machineReturned.name,
                    machineReturned.description,
                    machineReturned.companyId);

            List<ICESmartContract.Measure> allMeasure = MeasureController.getAllMeasureByPhaseId(phase.id, smartContract);

            if (allMeasure == null) {
                context.result("No measures with phase id: " + phase.id);
                return;
            }

            List<MeasureWithConstraintInformation> allMeasureModel = new ArrayList<>();
            for (ICESmartContract.Measure measure : allMeasure) {
                Measure measureModel = new Measure(measure.id,
                        measure.name,
                        measure.measure,
                        measure.unitOfMeasure,
                        measure.measureStartTime,
                        measure.measureEndTime,
                        measure.phaseId,
                        measure.measureConstraintId);
                ICESmartContract.MeasureConstraint measureConstraint;
                measureConstraint = MeasureConstraintController.getMeasureConstraint(measure.measureConstraintId, smartContract);

                MeasureConstraint measureConstraintModel = new MeasureConstraint(measureConstraint.id,
                        measureModel.name,
                        measureConstraint.maxMeasure,
                        measureConstraint.minMeasure,
                        measureConstraint.unitOfMeasure,
                        measureConstraint.recipeStepId,
                        measureConstraint.machineId);

                MeasureWithConstraintInformation measureToadd = new MeasureWithConstraintInformation(measureModel, measureConstraintModel);

                allMeasureModel.add(measureToadd);
            }
            ICESmartContract.RecipeStep recipeStepReturned = RecipeStepController.getRecipeStep(phase.recipeStepId, smartContract);

            if (recipeStepReturned == null) {
                context.result("No recipestep with id: " + phase.recipeStepId);
                return;
            }
            RecipeStep recipeStepModel = new RecipeStep(recipeStepReturned.id,
                    recipeStepReturned.name,
                    recipeStepReturned.description,
                    recipeStepReturned.recipeId,
                    recipeStepReturned.machineId);

            PhaseWithAllInformation phaseToAdd;
            phaseToAdd = new PhaseWithAllInformation(modelPhase,
                    machineModel,
                    allMeasureModel,
                    recipeStepModel);
            phaseWithAllInformations.add(phaseToAdd);

        }

        ProductInformation productInformation = new ProductInformation(productModel,
                companyModel,
                phaseWithAllInformations,
                recipeModel);
        context.json(productInformation);
    }
}
