
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
        <div id="on" class="watson">
            <div class="mensagens">
                <div class="area" id="chat">
		</div>
                <div id="chat-messages">
        </div>
            </div>
            <form id="mensagem" class="mensagem">
                <input type="text" id="texto" name="texto" placeholder="Digite sua mensagem"/>
                <button type="submit" id="mensagem">Enviar</button>
            </form>
        </div>
        """),_display_(/*25.10*/helper/*25.16*/.javascriptRouter("jsRoutes")/*25.45*/(routes.javascript.BotController.socket)),format.raw/*25.85*/("""
        """),format.raw/*26.9*/("""<script type="text/javascript">

        </script>

        <script type='text/javascript' src='"""),_display_(/*30.46*/routes/*30.52*/.Assets.versioned("javascripts/components.js")),format.raw/*30.98*/("""'></script>
        <script type='text/javascript' src='"""),_display_(/*31.46*/routes/*31.52*/.Assets.versioned("javascripts/app.js")),format.raw/*31.91*/("""'></script>

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
                  DATE: Wed May 30 01:29:41 BRT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/chat.scala.html
                  HASH: 195b5469ad0f9dfa11a4c84cb38df9bbd3f73ef8
                  MATRIX: 1029->0|1266->211|1280->217|1342->258|1434->324|1448->330|1508->369|2280->1114|2295->1120|2333->1149|2394->1189|2431->1199|2559->1300|2574->1306|2641->1352|2726->1410|2741->1416|2801->1455
                  LINES: 33->1|38->6|38->6|38->6|39->7|39->7|39->7|57->25|57->25|57->25|57->25|58->26|62->30|62->30|62->30|63->31|63->31|63->31
                  -- GENERATED --
              */
          