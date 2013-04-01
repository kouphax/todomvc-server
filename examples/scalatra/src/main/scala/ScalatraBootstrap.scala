import se.yobriefca.todomvc._
import controllers.{TodoMVCServlet, StaticFileServlet}
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new TodoMVCServlet, "/todos")
    context.mount(new StaticFileServlet, "/")
  }
}
