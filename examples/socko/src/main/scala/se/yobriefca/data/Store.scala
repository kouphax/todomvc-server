package se.yobriefca.data

import java.util.concurrent.atomic.AtomicLong
import spray.json._
import scala.Some
import scala.collection.mutable

case class Todo(id: Option[Long] = None, title: String, order: Int, completed: Boolean)

object DefaultProtocolWithTodo extends DefaultJsonProtocol {
  implicit val todoFormat = jsonFormat4(Todo)
}


object Store {

  private val idGenerator = new AtomicLong(1)
  private val store = new mutable.HashMap[Long, Todo]

  def save(data: Todo): Todo = {
    val id = idGenerator.getAndIncrement
    val todo = data.copy(id = Some(id))
    store.put(id, todo)
    todo
  }

  def get(id: Long) = store.get(id)

  def getAll = store.values.toList

  def save(id: Long, data: Todo) = {
    store.get(id).map { _ =>
      val updated = data.copy(id = Some(id))
      store.put(id, updated)
      updated
    }
  }

  def remove(id: Long) = store.remove(id)
}