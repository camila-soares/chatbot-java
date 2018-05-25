
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


Seq[Any](format.raw/*2.1*/("""<!DOCTYPE html>
<html lang="en">
        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <link rel="stylesheet" media="screen" href=""""),_display_(/*7.58*/routes/*7.64*/.Assets.versioned("stylesheets/main.css")),format.raw/*7.105*/("""">
            <link rel="shortcut icon" type="image/png" href=""""),_display_(/*8.63*/routes/*8.69*/.Assets.versioned("images/favicon.png")),format.raw/*8.108*/("""">
            <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
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
                <button type="submit">Enviar</button>
            </form>
        </div>
        <script type='text/javascript' src='"""),_display_(/*25.46*/routes/*25.52*/.Assets.versioned("javascripts/components.js")),format.raw/*25.98*/("""'></script>
        <script type='text/javascript' src='"""),_display_(/*26.46*/routes/*26.52*/.Assets.versioned("javascripts/app.js")),format.raw/*26.91*/("""'></script>
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
                  DATE: Fri May 25 13:44:01 GFT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/chat.scala.html
                  HASH: 33b4d269ed8a0e8a5839e40917c33e95a4a7ac5a
                  MATRIX: 1029->2|1266->213|1280->219|1342->260|1434->326|1448->332|1508->371|2201->1037|2216->1043|2283->1089|2368->1147|2383->1153|2443->1192
                  LINES: 33->2|38->7|38->7|38->7|39->8|39->8|39->8|56->25|56->25|56->25|57->26|57->26|57->26
                  -- GENERATED --
              */
          