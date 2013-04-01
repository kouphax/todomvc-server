package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Writes, Json}
case class Todo(id: Long, title: String, order: Int, completed: Boolean)

object Todo {

  val form = Form(
    mapping(
      "id"        -> ignored(0L),
      "title"     -> nonEmptyText,
      "order"     -> number,
      "completed" -> boolean
    )(Todo.apply)
      (Todo.unapply)
  )

  implicit object todoWrites extends Writes[Todo]{
    def writes(o: Todo): JsValue = {
      Json.obj(
        "id"        -> o.id,
        "title"     -> o.title,
        "order"     -> o.order,
        "completed" -> o.completed
      )
    }
  }
}
