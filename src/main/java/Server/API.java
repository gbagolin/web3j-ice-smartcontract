package Server;

import Model.*;
import io.javalin.Javalin;
import org.json.JSONObject;

import java.math.BigInteger;

public class API {
    private static final Javalin app = Javalin.create(config ->
    {
        config.contextPath = "/";
        config.enableCorsForAllOrigins();
    });

    public static void start() {
        app.start();
        app.get("/deployContract" +
                        "/:privateKey",
                context -> {
                    ContractAPI.deployContract(context);
                });

        app.get("/loadContract" +
                        "/:privateKey" +
                        "/:address",
                context -> {
                    ContractAPI.loadContract(context);
                });

        app.post("/getSchema", context -> {
            SchemaAPI.getSchema(context);
        });
        app.post("/addCompany",
                context -> {
                    CompanyAPI.addCompany(context);
                });
        app.get("/getCompany" +
                        "/:privateKey" +
                        "/:companyId",
                context -> {
                    CompanyAPI.getCompany(context);
                });
        app.post("/addMachine",
                context -> {
                    MachineAPI.addMachine(context);
                });
        app.get("/getMachine" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    MachineAPI.getMachine(context);
                });

        app.post("/addRecipe",
                context -> {
                    RecipeAPI.addRecipe(context);
                });

        app.get("/getRecipe" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    RecipeAPI.getRecipe(context);
                });

        app.post("/addRecipeStep",
                context -> {
                    RecipeStepAPI.addRecipeStep(context);
                });

        app.get("/getRecipeStep" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    RecipeStepAPI.getRecipeStep(context);
                });

        app.post("/addMeasureConstraint",
                context -> {
                    MeasureContraintAPI.addMeasureConstraint(context);
                });

        app.get("/getMeasureConstraint" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    MeasureContraintAPI.getMeasureConstraint(context);
                });

        app.post("/addProduct",
                context -> {
                    ProductAPI.addProduct(context);
                });

        app.get("/getProduct" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    ProductAPI.getProduct(context);
                });

        app.post("/addPhase",
                context -> {
                    PhaseAPI.addPhase(context);
                });

        app.get("/getPhase" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    PhaseAPI.getPhase(context);
                });

        app.post("/addMeasure",
                context -> {
                    MeasureAPI.addMeasure(context);
                });

        app.get("/getMeasure" +
                        "/:privateKey" +
                        "/:id",
                context -> {
                    MeasureAPI.getMeasure(context);
                });

        app.get("/getProductInformation/:privateKey/:id", context -> {
            ProductAPI.getProductInformation(context);
        });
    }
}
