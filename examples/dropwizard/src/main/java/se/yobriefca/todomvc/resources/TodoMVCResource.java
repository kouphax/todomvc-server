package se.yobriefca.todomvc.resources;

import com.google.common.base.Optional;
import se.yobriefca.todomvc.core.Todo;
import se.yobriefca.todomvc.data.Store;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class TodoMVCResource {

    private final Store store;

    public TodoMVCResource(Store store) {
        this.store = store;
    }

    @GET
    public List<Todo> getAll() {
        return store.getAll();
    }

    @POST
    public Todo create(@Valid Todo todo) {
        return store.save(todo);
    }

    @PUT
    @Path("/{id}")
    public Optional<Todo> update(@PathParam("id") long id, @Valid Todo todo){
        return store.save(id, todo);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id){
        store.remove(id);
    }

}
