
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

object caht1 extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.1*/("""<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" media="screen" href=""""),_display_(/*6.54*/routes/*6.60*/.Assets.versioned("stylesheets/style.css")),format.raw/*6.102*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*7.59*/routes/*7.65*/.Assets.versioned("images/favicon.png")),format.raw/*7.104*/("""">
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
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
        """),_display_(/*23.10*/helper/*23.16*/.javascriptRouter("jsRoutes")/*23.45*/(routes.javascript.BotController.socket)),format.raw/*23.85*/("""
        """),format.raw/*24.9*/("""<script type="text/javascript">

        </script>

        <script type='text/javascript' src='"""),_display_(/*28.46*/routes/*28.52*/.Assets.versioned("javascripts/components.js")),format.raw/*28.98*/("""'></script>
        <script type='text/javascript' src='"""),_display_(/*29.46*/routes/*29.52*/.Assets.versioned("javascripts/build.js")),format.raw/*29.93*/("""'></script>


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
                  DATE: Fri Jun 08 04:31:44 BRT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/caht1.scala.html
                  HASH: ca1bf7f8d09358bd017b3b28d4cb392193217850
                  MATRIX: 1030->0|1254->198|1268->204|1331->246|1419->308|1433->314|1493->353|2181->1014|2196->1020|2234->1049|2295->1089|2332->1099|2460->1200|2475->1206|2542->1252|2627->1310|2642->1316|2704->1357
                  LINES: 33->1|38->6|38->6|38->6|39->7|39->7|39->7|55->23|55->23|55->23|55->23|56->24|60->28|60->28|60->28|61->29|61->29|61->29
                  -- GENERATED --
              */
          