package se.yobriefca.todomvc;

import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.handler.StaticFileHandler;
import se.yobriefca.todomvc.data.Store;
import se.yobriefca.todomvc.handlers.TodoHandler;

import java.util.concurrent.ExecutionException;

public class TodoMVCApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebServer webServer = WebServers.createWebServer(9996)
                .add(new StaticFileHandler("./public"))
                .add("/", new StaticFileHandler("./public/index.htm"))
                .add("/todos", new TodoHandler(new Store()));
        webServer.start().get();
        System.out.println("Listening on " + webServer.getUri());
    }
}