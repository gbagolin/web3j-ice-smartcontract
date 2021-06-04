package Server;

import Model.*;
import io.javalin.http.Context;
import org.json.JSONObject;

public class SchemaAPI {

    public static void getSchema(Context context) {
        JSONObject jsonObject = new JSONObject(context.body());
        String schemaName = jsonObject.getString("schema");
        Schema schema = Schema.valueOf(schemaName);
        switch (schema) {
            case COMPANY:
                context.json(new Company());
                break;
            case MACHINE:
                context.json(new Machine());
                break;
            case RECIPE:
                context.json(new Recipe());
                break;
            case RECIPE_STEP:
                context.json(new RecipeStep());
                break;
            case MEASURE_CONSTRAINT:
                context.json(new MeasureConstraint());
                break;
            case PRODUCT:
                context.json(new Product());
                break;
            case PHASE:
                context.json(new Phase());
                break;
            case MEASURE:
                context.json(new Measure());
                break;
        }
    }
}
