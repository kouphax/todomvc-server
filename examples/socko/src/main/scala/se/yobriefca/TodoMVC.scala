package se.yobriefca

import org.mashupbots.socko.infrastructure.Logger
import akka.actor.{Props, ActorSystem}
import org.mashupbots.socko.routes._
import org.mashupbots.socko.webserver.{WebServerConfig, WebServer}
import org.mashupbots.socko.handlers.{StaticContentHandler, StaticFileRequest}
import com.typesafe.config.ConfigFactory
import java.io.File
import akka.routing.FromConfig
import org.mashupbots.socko.handlers.StaticContentHandlerConfig
import se.yobriefca.handlers.TodoHandler

object TodoMVC extends Logger with App {

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

  val routes = Routes({
    case HttpRequest(request) => request match {
      case GET(Path("/todos")) => {
        actorSystem.actorOf(Props[TodoHandler]) ! ListTodosEvent(request)
      }
      case POST(Path("/todos")) => {
        actorSystem.actorOf(Props[TodoHandler]) ! CreateTodoEvent(request)
      }
      case PUT(PathSegments("todos" :: id :: Nil)) => {
        actorSystem.actorOf(Props[TodoHandler]) ! UpdateTodoEvent(request, id.toLong)
      }
      case DELETE(PathSegments("todos" :: id :: Nil)) => {
        actorSystem.actorOf(Props[TodoHandler]) ! DeleteTodoEvent(request, id.toLong)
      }
      case GET(Path(file)) => {
        val path = if(file == "" || file == "/") "/index.htm" else file
        staticContentHandlerRouter ! new StaticFileRequest(request, new File(s"$staticPath$path"))
      }
    }
  })

  val webServer = new WebServer(WebServerConfig(), routes, actorSystem)

  webServer.start()

  Runtime.getRuntime.addShutdownHook(new Thread {
    override def run { webServer.stop() }
  })

  System.out.println("Open your browser and navigate to http://localhost:8888")

}
