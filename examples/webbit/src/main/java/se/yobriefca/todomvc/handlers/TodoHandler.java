package se.yobriefca.todomvc.handlers;

import org.codehaus.jackson.map.ObjectMapper;
import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;
import se.yobriefca.todomvc.data.Store;
import se.yobriefca.todomvc.models.Todo;

import java.util.List;

public class TodoHandler implements HttpHandler {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final Store store;

    public TodoHandler(Store store) {
        this.store = store;
    }

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        switch (request.method()) {
            case "GET": get(request, response); break;
            case "POST": post(request, response); break;
            case "PUT":
                break;
            case "DELETE":
                break;
        }
    }

    private void get(HttpRequest request, HttpResponse response) throws Exception {
        List<Todo> todos = store.getAll();
        String json = mapper.writeValueAsString(todos);
        response.header("Content-Type", "application/json")
                .write(json)
                .end();
    }

    private void post(HttpRequest request, HttpResponse response) throws Exception {
        Todo data = mapper.readValue(request.body(), Todo.class);
        Todo todo = store.save(data);
        String json = mapper.writeValueAsString(todo);
        response.header("Content-Type", "application/json")
                .write(json)
                .end();
    }
}
