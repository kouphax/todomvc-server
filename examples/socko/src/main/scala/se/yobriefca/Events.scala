package se.yobriefca

import org.mashupbots.socko.events.HttpRequestEvent

trait HasHttpSource {
  val originator: HttpRequestEvent
}

case class ListTodosEvent(originator: HttpRequestEvent) extends HasHttpSource

case class CreateTodoEvent(originator: HttpRequestEvent)  extends HasHttpSource

case class UpdateTodoEvent(originator: HttpRequestEvent, id: Long) extends HasHttpSource

case class DeleteTodoEvent(originator: HttpRequestEvent, id: Long) extends HasHttpSource
