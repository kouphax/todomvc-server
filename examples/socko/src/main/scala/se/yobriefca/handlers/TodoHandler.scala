package se.yobriefca.handlers

import akka.actor.Actor
import se.yobriefca._
import se.yobriefca.data.{Todo, Store}
import spray.json.{JsValue, JsNull}
import se.yobriefca.CreateTodoEvent
import se.yobriefca.DeleteTodoEvent
import se.yobriefca.UpdateTodoEvent
import se.yobriefca.ListTodosEvent
import spray.json._
import data.DefaultProtocolWithTodo._

class TodoHandler extends Actor {
  def receive = {
    case event: ListTodosEvent => {
      write(event, Store.getAll.toJson)
    }
    case event: CreateTodoEvent => {
      val data = bodyAsTodo(event)
      val todo = Store.save(data)
      write(event, todo.toJson)
    }
    case event: UpdateTodoEvent => {
      val data = bodyAsTodo(event)
      val todo = Store.save(event.id, data)
      write(event, todo.toJson)
    }
    case event: DeleteTodoEvent => {
      Store.remove(event.id)
      write(event, JsNull)
    }
  }

  def bodyAsTodo(source: HasHttpSource) = source.originator.request.content.toString.asJson.convertTo[Todo]
  def write(source: HasHttpSource, json: JsValue) {
    source.originator.response.write(json.toString, "application/json")
    context.stop(self)
  }
}
