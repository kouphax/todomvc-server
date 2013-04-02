package controllers;

import com.google.common.base.Optional;
import data.Store;
import models.Todo;
import org.codehaus.jackson.JsonNode;
import play.*;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

public class Todos extends Controller {

    public static Store store = new Store();

    public static Result index() {
        return ok(Json.toJson(store.getAll()));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create(){
        JsonNode json = request().body().asJson();
        models.Todo data = Json.fromJson(json, models.Todo.class);
        models.Todo todo = store.save(data);
        return ok(Json.toJson(todo));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
        JsonNode json = request().body().asJson();
        models.Todo data = Json.fromJson(json, models.Todo.class);
        Optional<models.Todo> todo = store.save(id, data);
        return ok(Json.toJson(todo));
    }

    public static Result delete(Long id) {
        Optional<models.Todo> todo = store.remove(id);
        return ok(Json.toJson(todo));
    }

}
