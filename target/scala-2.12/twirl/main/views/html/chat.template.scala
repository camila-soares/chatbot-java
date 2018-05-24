
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
            <meta http-equiv="Content-Security-Policy" content="connect-src 'self' * data: gap: https://ssl.gstatic.com 'unsafe-eval'; style-src 'self'; script-src 'self' 'unsafe-inline'; media-src *;">
            <link rel="stylesheet" media="screen" href=""""),_display_(/*8.58*/routes/*8.64*/.Assets.versioned("stylesheets/main.css")),format.raw/*8.105*/("""">
            <link rel="shortcut icon" type="image/png" href=""""),_display_(/*9.63*/routes/*9.69*/.Assets.versioned("images/favicon.png")),format.raw/*9.108*/("""">
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
        <script type='text/javascript' src='"""),_display_(/*23.46*/routes/*23.52*/.Assets.versioned("javascripts/components.js")),format.raw/*23.98*/("""'></script>
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
                  DATE: Thu May 24 17:02:33 GFT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/chat.scala.html
                  HASH: fef2d2e099c5ddfae68ad7b9e2612cd1f86307c8
                  MATRIX: 1029->2|1470->417|1484->423|1546->464|1638->530|1652->536|1712->575|2247->1083|2262->1089|2329->1135|2414->1193|2429->1199|2489->1238
                  LINES: 33->2|39->8|39->8|39->8|40->9|40->9|40->9|54->23|54->23|54->23|55->24|55->24|55->24
                  -- GENERATED --
              */
          