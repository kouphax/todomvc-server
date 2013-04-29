package se.yobriefca.todomvc.models;

import org.codehaus.jackson.annotate.JsonProperty;

public class Todo {

    @JsonProperty
    private long id;

    @JsonProperty
    private String title;

    @JsonProperty
    private long order;

    @JsonProperty
    private boolean completed;

    public Todo(){
        // default empty constructor to allow jackson to do its magic
    }

    public Todo(long id, String title, long order, boolean completed) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getOrder() {
        return order;
    }

    public boolean isCompleted() {
        return completed;
    }
}
