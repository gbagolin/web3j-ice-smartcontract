package Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.get("/:id", ctx -> ctx.result("Hello: " + ctx.pathParam("id")));
    }
}
