// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/muchmore/chatbot-java/conf/routes
// @DATE:Fri Jun 08 03:36:34 BRT 2018

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseBotController BotController = new controllers.ReverseBotController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseHomeController HomeController = new controllers.ReverseHomeController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseChatController ChatController = new controllers.ReverseChatController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseBotController BotController = new controllers.javascript.ReverseBotController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseHomeController HomeController = new controllers.javascript.ReverseHomeController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseChatController ChatController = new controllers.javascript.ReverseChatController(RoutesPrefix.byNamePrefix());
  }

}
