// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/muchmore/chatbot-java/conf/routes
// @DATE:Fri Jun 08 03:36:34 BRT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  HomeController_0: controllers.HomeController,
  // @LINE:7
  ChatController_2: controllers.ChatController,
  // @LINE:9
  BotController_3: controllers.BotController,
  // @LINE:14
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_0: controllers.HomeController,
    // @LINE:7
    ChatController_2: controllers.ChatController,
    // @LINE:9
    BotController_3: controllers.BotController,
    // @LINE:14
    Assets_1: controllers.Assets
  ) = this(errorHandler, HomeController_0, ChatController_2, BotController_3, Assets_1, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, ChatController_2, BotController_3, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """chat""", """controllers.ChatController.chat"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ws""", """controllers.BotController.socket"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_0.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_ChatController_chat1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("chat")))
  )
  private[this] lazy val controllers_ChatController_chat1_invoker = createInvoker(
    ChatController_2.chat,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ChatController",
      "chat",
      Nil,
      "GET",
      this.prefix + """chat""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_BotController_socket2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ws")))
  )
  private[this] lazy val controllers_BotController_socket2_invoker = createInvoker(
    BotController_3.socket,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.BotController",
      "socket",
      Nil,
      "GET",
      this.prefix + """ws""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_Assets_versioned3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned3_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_0.index)
      }
  
    // @LINE:7
    case controllers_ChatController_chat1_route(params@_) =>
      call { 
        controllers_ChatController_chat1_invoker.call(ChatController_2.chat)
      }
  
    // @LINE:9
    case controllers_BotController_socket2_route(params@_) =>
      call { 
        controllers_BotController_socket2_invoker.call(BotController_3.socket)
      }
  
    // @LINE:14
    case controllers_Assets_versioned3_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned3_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
