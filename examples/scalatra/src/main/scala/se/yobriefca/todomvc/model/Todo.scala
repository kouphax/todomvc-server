package se.yobriefca.todomvc.model

import java.util.concurrent.atomic.AtomicLong
import collection.mutable.ArrayBuffer

case class Todo(id: Long = 0, title: String, order: Int, completed: Boolean)

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