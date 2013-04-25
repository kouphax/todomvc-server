package se.yobriefca.todomvc;

import static spark.Spark.*;

import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;
import se.yobriefca.todomvc.data.Store;
import se.yobriefca.todomvc.models.Todo;
import spark.*;
import spark.utils.IOUtils;

import java.io.*;
import java.util.List;

public class TodoMVC {


    public static void main(String[] args) {

        final ObjectMapper mapper = new ObjectMapper();
        final Store store = new Store();

        // serve up all static files in the public directory
        externalStaticFileLocation("./public");

        // add a filter to capture the root and render out the index.htm
        // from the public folder / -> ./public/index.htm.  This is the only
        // route override needed to serve the static content
        before(new Filter("/") {
            @Override
            public void handle(Request request, Response response){
                try (InputStream stream = new FileInputStream("./public/index.htm")) {
                    halt(200, IOUtils.toString(stream));
                } catch (IOException e) {
                    halt(500);
                }
            }
        });

        get(new Route("/todos"){
            @Override
            public Object handle(Request request, Response response) {
                try {
                    List<Todo> todos = store.getAll();
                    String json = mapper.writeValueAsString(todos);

                    response.status(200);
                    response.header("Content-Type", "application/json");

                    return json;
                } catch(Exception e) {
                    halt(500);
                    return null;
                }
            }
        });

        post(new Route("/todos") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Todo data = mapper.readValue(request.body(), Todo.class);
                    Todo todo = store.save(data);
                    String json = mapper.writeValueAsString(todo);

                    response.status(200);
                    response.header("Content-Type", "application/json");
                    return json;
                } catch(Exception e) {
                    halt(500);
                    return null;
                }
            }
        });

        put(new Route("/todos/:id"){
            @Override
            public Object handle(Request request, Response response) {
                try{
                    long id = Long.parseLong(request.params(":id"));
                    Todo data = mapper.readValue(request.body(), Todo.class);
                    Optional<Todo> todo = store.save(id, data);
                    String json = mapper.writeValueAsString(todo);

                    response.status(200);
                    response.header("Content-Type", "application/json");
                    return json;
                } catch(Exception e) {
                    e.printStackTrace();
                    halt(500);
                    return null;
                }
            }
        });

        delete(new Route("/todos/:id"){
            @Override
            public Object handle(Request request, Response response) {
                try{
                    long id = Long.parseLong(request.params(":id"));
                    Optional<Todo> todo = store.remove(id);
                    String json = mapper.writeValueAsString(todo);

                    response.status(200);
                    response.header("Content-Type", "application/json");
                    return json;
                } catch(Exception e) {
                    e.printStackTrace();
                    halt(500);
                    return null;
                }
            }
        });

    }
}