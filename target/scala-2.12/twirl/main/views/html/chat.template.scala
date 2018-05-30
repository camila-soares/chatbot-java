
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
<html lang="en">
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
                <input type="text" name="message" class="message" id="message" placeholder="Message" maxlength="80" style="width:40%" />
                <button id="send-btn">Send</button>
            </div>
        </div>


        </div>
        """),_display_(/*23.10*/helper/*23.16*/.javascriptRouter("jsRoutes")/*23.45*/(routes.javascript.BotController.socket)),format.raw/*23.85*/("""
        """),format.raw/*24.9*/("""<script type="text/javascript">

        </script>

        <script type='text/javascript' src='"""),_display_(/*28.46*/routes/*28.52*/.Assets.versioned("javascripts/components.js")),format.raw/*28.98*/("""'></script>
        <script type='text/javascript' src='"""),_display_(/*29.46*/routes/*29.52*/.Assets.versioned("javascripts/app.js")),format.raw/*29.91*/("""'></script>

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
                  DATE: Wed May 30 00:28:49 BRT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/chat.scala.html
                  HASH: 9483f7859dcf1ad303ccbf42f98abd061004b55e
                  MATRIX: 1029->0|1266->211|1280->217|1342->258|1434->324|1448->330|1508->369|2214->1048|2229->1054|2267->1083|2328->1123|2365->1133|2493->1234|2508->1240|2575->1286|2660->1344|2675->1350|2735->1389
                  LINES: 33->1|38->6|38->6|38->6|39->7|39->7|39->7|55->23|55->23|55->23|55->23|56->24|60->28|60->28|60->28|61->29|61->29|61->29
                  -- GENERATED --
              */
          