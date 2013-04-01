package se.yobriefca.todomvc.controllers

import org.scalatra.ScalatraServlet
import java.io.File

class StaticFileServlet extends ScalatraServlet {

  get("/") {
    contentType = "text/html"
    new File("src/main/webapp/index.html")
  }

}
