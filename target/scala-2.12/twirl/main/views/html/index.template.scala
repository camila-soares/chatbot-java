
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.17*/("""
"""),format.raw/*2.1*/("""<!doctype html>

<html lang="pt-br">
  <head>
    <title>"""),_display_(/*6.13*/title),format.raw/*6.18*/("""</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">

  </head>
  <body style="background-color: darkgray;">

    <script type="text/javascript">
            (function (d, t, b) """),format.raw/*13.33*/("""{"""),format.raw/*13.34*/("""
              """),format.raw/*14.15*/("""var a = d.createElement(t), e = d.getElementsByTagName(b)[0];
              a.type = 'text/javascript';
              a.src = '/chat.js';
              e.insertBefore(a, e.firstChild);
            """),format.raw/*18.13*/("""}"""),format.raw/*18.14*/(""")(document, 'script', 'body');
    </script>

  </body>
</html>
"""))
      }
    }
  }

  def render(title:String): play.twirl.api.HtmlFormat.Appendable = apply(title)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (title) => apply(title)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Thu May 31 10:56:16 BRT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/index.scala.html
                  HASH: b4488e551c4f78670e3edb167a73e3fcc2740dc2
                  MATRIX: 948->1|1058->16|1086->18|1174->80|1199->85|1475->333|1504->334|1548->350|1777->551|1806->552
                  LINES: 28->1|33->1|34->2|38->6|38->6|45->13|45->13|46->14|50->18|50->18
                  -- GENERATED --
              */
          