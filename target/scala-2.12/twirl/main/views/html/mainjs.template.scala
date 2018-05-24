
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

object mainjs extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[Boolean,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(secured: Boolean):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.20*/("""
"""),format.raw/*2.1*/("""(function (d) """),format.raw/*2.15*/("""{"""),format.raw/*2.16*/("""
"""),format.raw/*3.1*/("""var iframe = d.createElement('iframe'), div = d.createElement('div'), body = d.getElementsByTagName('body')[0];
iframe.src = """"),_display_(/*4.16*/routes/*4.22*/.BotController.socket().absoluteURL(secured)),format.raw/*4.66*/("""";
    iframe.width = 320;
    iframe.height = 480;
    iframe.frameBorder = 0;
    iframe.id = "mmChat";
    div.style = "position: fixed; bottom: 0; right: 5px; z-index: 999";
    div.appendChild(iframe);
    body.insertBefore(div, body.firstChild);

    function resizeFrame(x, y) """),format.raw/*13.32*/("""{"""),format.raw/*13.33*/("""
    """),format.raw/*14.5*/("""iframe.width = x;
    iframe.height = y;
    """),format.raw/*16.5*/("""}"""),format.raw/*16.6*/("""

    """),format.raw/*18.5*/("""window.addEventListener('message', function (e) """),format.raw/*18.53*/("""{"""),format.raw/*18.54*/("""
    """),format.raw/*19.5*/("""try """),format.raw/*19.9*/("""{"""),format.raw/*19.10*/("""
    """),format.raw/*20.5*/("""var data = JSON.parse(e.data);
    if (data.size) """),format.raw/*21.20*/("""{"""),format.raw/*21.21*/("""
    """),format.raw/*22.5*/("""resizeFrame(data.size.width, data.size.height);
    """),format.raw/*23.5*/("""}"""),format.raw/*23.6*/("""
    """),format.raw/*24.5*/("""}"""),format.raw/*24.6*/(""" """),format.raw/*24.7*/("""catch (err) """),format.raw/*24.19*/("""{"""),format.raw/*24.20*/("""
    """),format.raw/*25.5*/("""// ignored
    """),format.raw/*26.5*/("""}"""),format.raw/*26.6*/("""
    """),format.raw/*27.5*/("""}"""),format.raw/*27.6*/(""", false);
    """),format.raw/*28.5*/("""}"""),format.raw/*28.6*/(""")(document);"""))
      }
    }
  }

  def render(secured:Boolean): play.twirl.api.HtmlFormat.Appendable = apply(secured)

  def f:((Boolean) => play.twirl.api.HtmlFormat.Appendable) = (secured) => apply(secured)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed May 23 16:30:43 GFT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/mainjs.scala.html
                  HASH: 1f2d63cb2bf8b0118ca6232805ac79ad551ecbaa
                  MATRIX: 950->1|1063->19|1091->21|1132->35|1160->36|1188->38|1342->166|1356->172|1420->216|1741->509|1770->510|1803->516|1877->563|1905->564|1940->572|2016->620|2045->621|2078->627|2109->631|2138->632|2171->638|2250->689|2279->690|2312->696|2392->749|2420->750|2453->756|2481->757|2509->758|2549->770|2578->771|2611->777|2654->793|2682->794|2715->800|2743->801|2785->816|2813->817
                  LINES: 28->1|33->1|34->2|34->2|34->2|35->3|36->4|36->4|36->4|45->13|45->13|46->14|48->16|48->16|50->18|50->18|50->18|51->19|51->19|51->19|52->20|53->21|53->21|54->22|55->23|55->23|56->24|56->24|56->24|56->24|56->24|57->25|58->26|58->26|59->27|59->27|60->28|60->28
                  -- GENERATED --
              */
          