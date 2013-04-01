package controllers

import play.api.mvc.{Result, Action, Controller}
import play.api.libs.json.Json._
import models.Todo
import data.Store

object Todos extends Controller {

  def index = Action {
    Ok(toJson(Store.getAll))
  }

  def create = TodoAction { todo =>
    implicit val writes = Todo.todoWrites
    Ok(toJson(Some(Store.save(todo))))
  }

  def update(id: Int) = TodoAction { todo =>
    implicit val writes = Todo.todoWrites
    Ok(toJson(Some(Store.save(id, todo))))
  }

  def delete(id: Int) = Action {
    Ok(toJson(Store.remove(id)))
  }

  private def TodoAction(operation: Todo => Result) = Action(parse.json) { request =>
    Todo.form.bind(request.body).fold(
      errors => BadRequest(errors.errorsAsJson),
      todo => operation(todo)
    )
  }
}
