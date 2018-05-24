
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
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <meta http-equiv="Content-Security-Policy" content="connect-src http://localhost:9000; default-src *; style-src 'self' http://* 'unsafe-inline'; script-src 'self' http://* 'unsafe-inline' 'unsafe-eval'" />
            <link rel="stylesheet" media="screen" href=""""),_display_(/*9.58*/routes/*9.64*/.Assets.versioned("stylesheets/main.css")),format.raw/*9.105*/("""">
            <link rel="shortcut icon" type="image/png" href=""""),_display_(/*10.63*/routes/*10.69*/.Assets.versioned("images/favicon.png")),format.raw/*10.108*/("""">
            <title>ChatBot</title>
        </head>
    <body>
        <div id="watson" class="watson">
            <div class="mensagens">
                <div class="area" id="chat">
		</div>
            </div>
            <form id="mensagem" class="mensagem">
                <input type="text" id="texto" name="texto" placeholder="Digite sua mensagem"/>
                <button type="submit">Enviar</button>
            </form>
        </div>


        <script type='text/javascript' src='"""),_display_(/*26.46*/routes/*26.52*/.Assets.versioned("javascripts/components.js")),format.raw/*26.98*/("""'></script>
        <script type='text/javascript' src='"""),_display_(/*27.46*/routes/*27.52*/.Assets.versioned("javascripts/app.js")),format.raw/*27.91*/("""'></script>
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
                  DATE: Thu May 24 15:53:50 GFT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/chat.scala.html
                  HASH: d602a565e00adaf65673d65d7176c698ed28a50b
                  MATRIX: 1029->2|1567->514|1581->520|1643->561|1736->627|1751->633|1812->672|2351->1184|2366->1190|2433->1236|2518->1294|2533->1300|2593->1339
                  LINES: 33->2|40->9|40->9|40->9|41->10|41->10|41->10|57->26|57->26|57->26|58->27|58->27|58->27
                  -- GENERATED --
              */
          