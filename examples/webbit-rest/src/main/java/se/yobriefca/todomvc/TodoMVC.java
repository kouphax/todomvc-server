package se.yobriefca.todomvc;

import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;
import org.webbitserver.*;
import org.webbitserver.handler.StaticFileHandler;
import org.webbitserver.netty.NettyWebServer;
import org.webbitserver.rest.Rest;
import se.yobriefca.todomvc.data.Store;
import se.yobriefca.todomvc.models.Todo;

import java.util.List;

public class TodoMVC {
    public static void main(String[] args) throws Exception {

        final ObjectMapper mapper = new ObjectMapper();
        final Store store = new Store();

        WebServer server = new NettyWebServer(9991)
                .add(new StaticFileHandler("./public"))
                .add("/", new StaticFileHandler("./public/index.htm"));

        Rest rest = new Rest(server);

        rest.GET("/todos", new HttpHandler() {
          @Override
          public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
              List<Todo> todos = store.getAll();
              String json = mapper.writeValueAsString(todos);
              response.header("Content-Type", "application/json")
                      .content(json)
                      .end();          }
        });

        rest.POST("/todos", new HttpHandler() {
          @Override
          public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
              Todo data = mapper.readValue(request.body(), Todo.class);
              Todo todo = store.save(data);
              String json = mapper.writeValueAsString(todo);
              response.header("Content-Type", "application/json")
                      .content(json)
                      .end();
          }
        });

        rest.PUT("/todos/{id}", new HttpHandler() {
          @Override
          public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
              final long id = Rest.intParam(request, "id");
              final Todo data = mapper.readValue(request.body(), Todo.class);
              final Optional<Todo> todo = store.save(id, data);
              String json = mapper.writeValueAsString(todo);
              response.header("Content-Type", "application/json")
                      .content(json)
                      .end();
          }
        });

        rest.DELETE("/todos/{id}", new HttpHandler() {
          @Override
          public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
              final long id =  Rest.intParam(request, "id");
              final Optional<Todo> todo = store.remove(id);
              String json = mapper.writeValueAsString(todo);
              response.header("Content-Type", "application/json")
                      .content(json)
                      .end();
          }
        });

        server.start().get();
        System.out.println("Listening on " + server.getUri());
  }
}
