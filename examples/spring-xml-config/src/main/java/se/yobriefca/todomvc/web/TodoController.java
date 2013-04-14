package se.yobriefca.todomvc.web;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import se.yobriefca.todomvc.data.Store;
import se.yobriefca.todomvc.model.Todo;

@Controller
@RequestMapping(value = "/todos")
public class TodoController {

	private final Store store;

	@Inject
	public TodoController(Store store) {
		this.store = store;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<Todo> getAll() {
		return store.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Todo get(@PathVariable("id") long id) {
		return store.get(id).get();
	}

	@RequestMapping(method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE }, 
			consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Todo create(@RequestBody @Valid Todo todo, HttpServletResponse response) {
		response.setStatus(HttpURLConnection.HTTP_CREATED);
		return store.save(todo);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Todo update(@PathVariable("id") long id, @RequestBody @Valid Todo todo) {
		return store.save(id, todo).get();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable("id") long id, HttpServletResponse response) {
		response.setStatus(HttpURLConnection.HTTP_NO_CONTENT);
		store.remove(id);
	}
}
