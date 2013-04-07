package se.yobriefca

import org.mashupbots.socko.infrastructure.Logger
import akka.actor.{Actor, Props, ActorSystem}
import org.mashupbots.socko.routes._
import org.mashupbots.socko.webserver.{WebServerConfig, WebServer}
import org.mashupbots.socko.events.HttpRequestEvent
import org.mashupbots.socko.handlers.{StaticContentHandler, StaticFileRequest}
import com.typesafe.config.ConfigFactory
import java.io.File
import akka.routing.FromConfig
import org.mashupbots.socko.handlers.StaticContentHandlerConfig
import spray.json._
import DefaultJsonProtocol._
import se.yobriefca.data.{Todo, Store}
import data.Implicits._

case class ListTodosRequest(request: HttpRequestEvent)
case class CreateTodoRequest(request: HttpRequestEvent)
case class UpdateTodoRequest(request: HttpRequestEvent, id: Long)
case class DeleteTodoRequest(request: HttpRequestEvent, id: Long)

class TodoHandler extends Actor {
  def receive = {
    case event: ListTodosRequest => {
      event.request.response.write(Store.getAll.map(_.toJson).toJson.toString, "application/json")
      context.stop(self)
    }
    case event: CreateTodoRequest => {
      val data = event.request.request.content.toString.asJson.convertTo[Todo]
      val todo = Store.save(data)
      event.request.response.write(todo.toJson.toString, "application/json")
      context.stop(self)
    }
    case event: UpdateTodoRequest => {
      val data = event.request.request.content.toString.asJson.convertTo[Todo]
      val todo = Store.save(event.id, data)
      event.request.response.write(todo.map(_.toJson.toString).getOrElse(""), "application/json")
      context.stop(self)
    }
    case event: DeleteTodoRequest => {
      Store.remove(event.id)
      event.request.response.write("", "application/json")
      context.stop(self)
    }
  }
}

object TodoMVC extends Logger with App {
  //
  // STEP #1 - Define Actors and Start Akka
  // See `HelloHandler`
  //

  val actorConfig = """
      my-pinned-dispatcher {
        type=PinnedDispatcher
        executor=thread-pool-executor
      }
      akka {
        event-handlers = ["akka.event.slf4j.Slf4jEventHandler"]
        loglevel=DEBUG
        actor {
          deployment {
            /static-file-router {
              router = round-robin
              nr-of-instances = 5
            }
          }
        }
      }"""

  val actorSystem = ActorSystem("HelloExampleActorSystem", ConfigFactory.parseString(actorConfig))

  val staticPath = new File("./public").getAbsolutePath

  val handlerConfig = StaticContentHandlerConfig(Seq(staticPath))

  val staticContentHandlerRouter = actorSystem.actorOf(Props(new StaticContentHandler(handlerConfig))
    .withRouter(FromConfig()).withDispatcher("my-pinned-dispatcher"), "static-file-router")

  //
  // STEP #2 - Define Routes
  // Dispatch all HTTP GET events to a newly instanced `HelloHandler` actor for processing.
  // `HelloHandler` will `stop()` itself after processing each request.
  //
  val routes = Routes({
    case HttpRequest(request) => request match {
      case GET(Path("/todos")) => {
        actorSystem.actorOf(Props[TodoHandler]) ! ListTodosRequest(request)
      }
      case POST(Path("/todos")) => {
        actorSystem.actorOf(Props[TodoHandler]) ! CreateTodoRequest(request)
      }
      case PUT(PathSegments("todos" :: id :: Nil)) => {
        actorSystem.actorOf(Props[TodoHandler]) ! UpdateTodoRequest(request, id.toLong)
      }
      case DELETE(PathSegments("todos" :: id :: Nil)) => {
        actorSystem.actorOf(Props[TodoHandler]) ! DeleteTodoRequest(request, id.toLong)
      }
      case GET(Path(file)) => {
        val path = if(file == "" || file == "/") "/index.htm" else file
        staticContentHandlerRouter ! new StaticFileRequest(request, new File(s"$staticPath$path"))
      }
    }
  })

  //
  // STEP #3 - Start and Stop Socko Web Server
  //
  val webServer = new WebServer(WebServerConfig(), routes, actorSystem)

  webServer.start()

  Runtime.getRuntime.addShutdownHook(new Thread {
    override def run { webServer.stop() }
  })

  System.out.println("Open your browser and navigate to http://localhost:8888")

}
