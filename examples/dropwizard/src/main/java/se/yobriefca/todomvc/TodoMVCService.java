package se.yobriefca.todomvc;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import se.yobriefca.todomvc.data.Store;
import se.yobriefca.todomvc.resources.TodoMVCResource;

public class TodoMVCService extends Service<TodoMVCConfiguration> {

    public static void main(String[] args) throws Exception {
        new TodoMVCService().run(args);
    }

    @Override
    public void initialize(Bootstrap<TodoMVCConfiguration> bootstrap) {
        bootstrap.setName("todomvc-server");
        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    }

    @Override
    public void run(TodoMVCConfiguration configuration, Environment environment) throws Exception {
        final Store store = new Store();
        environment.addResource(new TodoMVCResource(store));
    }
}
