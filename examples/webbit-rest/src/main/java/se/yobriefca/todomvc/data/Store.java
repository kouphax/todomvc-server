package se.yobriefca.todomvc.data;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import se.yobriefca.todomvc.models.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Store {

    private final AtomicLong idGenerator = new AtomicLong(1);
    private final List<Todo> store = new ArrayList<Todo>();

    public Todo save(Todo data) {
        Todo todo = new Todo( idGenerator.getAndIncrement(), data.getTitle(),
                data.getOrder(), data.isCompleted());
        store.add(todo);
        return todo;
    }

    public Optional<Todo> get(final long id) {
        Todo todo = Iterables.find(store, new Predicate<Todo>() {
            @Override
            public boolean apply(Todo todo) {
                return todo.getId() == id;
            }
        });

        return Optional.fromNullable(todo);
    }

    public List<Todo> getAll() {
        return Lists.newArrayList(store);
    }

    public Optional<Todo> save(long id, Todo data) {
        Optional<Todo> maybeTodo = get(id);

        if(maybeTodo.isPresent()) {
            // remove the old td
            final Todo oldTodo = maybeTodo.get();
            store.remove(maybeTodo.get());

            // create a new td with the same value
            final Todo newTodo = new Todo(oldTodo.getId(), data.getTitle(),
                    data.getOrder(), data.isCompleted());
            store.add(newTodo);

            return Optional.of(newTodo);
        } else {
            return Optional.absent();
        }
    }

    public Optional<Todo> remove(long id) {
        Optional<Todo> maybeTodo = get(id);

        if(maybeTodo.isPresent()) {
            Todo todo = maybeTodo.get();
            store.remove(maybeTodo.get());
        }

        return maybeTodo;
    }
}
