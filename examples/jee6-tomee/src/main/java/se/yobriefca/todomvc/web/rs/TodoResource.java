package se.yobriefca.todomvc.web.rs;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.yobriefca.todomvc.data.Store;
import se.yobriefca.todomvc.model.Todo;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

	private Store store;

	public TodoResource() {}
	
	@Inject
	public TodoResource(Store store) {
		this.store = store;
	}

	@GET
	public List<Todo> getAll() {
		return store.getAll();
	}
	
	@GET
	@Path("/{id}")
	public Todo get(@PathParam("id") long id) {
		return store.get(id).get();
	}	

	@POST
	public Todo create(@Valid Todo todo) {
		return store.save(todo);
	}

	@PUT
	@Path("/{id}")
	public Todo update(@PathParam("id") long id, @Valid Todo todo) {
		return store.save(id, todo).get();
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") long id) {
		store.remove(id);
	}

}
