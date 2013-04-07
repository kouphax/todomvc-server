package se.yobriefca.data

import java.util.concurrent.atomic.AtomicLong
import scala.collection.mutable.ArrayBuffer
import spray.json._
import scala.Some

case class Todo(id: Long, title: String, order: Int, completed: Boolean)

object Implicits {

  implicit object TodoWriter extends JsonWriter[Todo]{
    def write(obj: Todo): JsValue = {
      JsObject(
        "id" -> JsNumber(obj.id),
        "title" -> JsString(obj.title),
        "order" -> JsNumber(obj.order),
        "completed" -> JsBoolean(obj.completed)
      )
    }
  }

  implicit object TodoReader extends  JsonReader[Todo]{
    def read(json: JsValue): Todo = {
      json.asJsObject.getFields("title", "order", "completed") match {
        case Seq(JsString(title), JsNumber(order), JsBoolean(completed)) => {
          Todo(0L, title, order.toInt, completed)
        }
        case _ => throw new DeserializationException("Todo expected")
      }
    }
  }
}

object Store {

  private val idGenerator = new AtomicLong(1)
  private val store = new ArrayBuffer[Todo]

  def save(data: Todo): Todo = {
    val todo = data.copy(id = idGenerator.getAndIncrement)
    store += todo
    todo
  }

  def get(id: Long) = store.find(_.id == id)

  def getAll = store.toList

  def save(id: Long, data: Todo) = {
    store.indexWhere(_.id == id) match {
      case index if index != -1 => {
        val updated = data.copy(id = id)
        store.remove(index)
        store.insert(index, updated)
        Some(updated)
      }
      case _ => None
    }
  }

  def remove(id: Long) = store remove store.indexWhere(_.id == id)
}