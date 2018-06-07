
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import play.data._
import play.core.j.PlayFormsMagicForJava._

object chat extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.1*/("""<!DOCTYPE html>
<html lang="pt-br">
        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <link rel="stylesheet" media="screen" href=""""),_display_(/*6.58*/routes/*6.64*/.Assets.versioned("stylesheets/main.css")),format.raw/*6.105*/("""">
            <link rel="shortcut icon" type="image/png" href=""""),_display_(/*7.63*/routes/*7.69*/.Assets.versioned("images/favicon.png")),format.raw/*7.108*/("""">
            <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
            <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
            <title>ChatBot</title>
        </head>
    <body>
        <div class="chat_wrapper" id="chat_wrapper">
            <div class="message_box" id="message_box"></div>
            <div class="panel">
                <!--form class="chat-window-form" id="form-main"-->
                <input type="text" name="message" class="message" id="message" placeholder="Message" maxlength="80" style="width:40%" />
                <button  id="send-btn">Send</button>
                <!--/form-->
            </div>
        </div>
        """),_display_(/*22.10*/helper/*22.16*/.javascriptRouter("jsRoutes")/*22.45*/(routes.javascript.BotController.socket)),format.raw/*22.85*/("""
        """),format.raw/*23.9*/("""<script type='text/javascript' src='"""),_display_(/*23.46*/routes/*23.52*/.Assets.versioned("javascripts/components.js")),format.raw/*23.98*/("""'></script>
        <script type='text/javascript' src='"""),_display_(/*24.46*/routes/*24.52*/.Assets.versioned("javascripts/app.js")),format.raw/*24.91*/("""'></script>

    </body>
</html>"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jun 06 23:25:34 BRT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/chat.scala.html
                  HASH: d0e4cb13e3d97c8190d0ab29c7917ba19efb09c9
                  MATRIX: 1029->0|1269->214|1283->220|1345->261|1437->327|1451->333|1511->372|2297->1131|2312->1137|2350->1166|2411->1206|2448->1216|2512->1253|2527->1259|2594->1305|2679->1363|2694->1369|2754->1408
                  LINES: 33->1|38->6|38->6|38->6|39->7|39->7|39->7|54->22|54->22|54->22|54->22|55->23|55->23|55->23|55->23|56->24|56->24|56->24
                  -- GENERATED --
              */
          