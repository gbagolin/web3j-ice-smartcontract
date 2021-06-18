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
            case Company:
                context.json(new Company());
                break;
            case Machine:
                context.json(new Machine());
                break;
            case Recipe:
                context.json(new Recipe());
                break;
            case RecipeStep:
                context.json(new RecipeStep());
                break;
            case MeasureConstraint:
                context.json(new MeasureConstraint());
                break;
            case Product:
                context.json(new Product());
                break;
            case Phase:
                context.json(new Phase());
                break;
            case Measure:
                context.json(new Measure());
                break;
        }
    }
}
