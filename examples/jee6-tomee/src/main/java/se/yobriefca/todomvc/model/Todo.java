package se.yobriefca.todomvc.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Todo {

	private long id;

	@NotNull
	@Size(min = 1)
	private String title;

	private long order;

	private boolean completed;

	public Todo() {
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

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
