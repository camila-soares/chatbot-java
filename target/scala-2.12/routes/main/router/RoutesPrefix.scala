// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/muchmore/chatbot-java/conf/routes
// @DATE:Mon May 28 17:07:05 GFT 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
