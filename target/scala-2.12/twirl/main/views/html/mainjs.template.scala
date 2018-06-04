
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
"""),format.raw/*2.1*/("""var originServer = '"""),_display_(/*2.22*/routes/*2.28*/.BotController.socket().absoluteURL(secured)),format.raw/*2.72*/("""';
    (function (d) """),format.raw/*3.19*/("""{"""),format.raw/*3.20*/("""
    """),format.raw/*4.5*/("""var scriptInject = d.createElement('script'), body = d.getElementsByTagName('body')[0];
    scriptInject.type = 'text/javascript';
    scriptInject.src = '"""),_display_(/*6.26*/routes/*6.32*/.Assets.versioned("javascripts/app.js").absoluteURL(secured)),format.raw/*6.92*/("""';
    body.insertBefore(scriptInject, body.firstChild);
    """),format.raw/*8.5*/("""}"""),format.raw/*8.6*/(""")(document);"""))
      }
    }
  }

  def render(secured:Boolean): play.twirl.api.HtmlFormat.Appendable = apply(secured)

  def f:((Boolean) => play.twirl.api.HtmlFormat.Appendable) = (secured) => apply(secured)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Sun Jun 03 13:20:26 BRT 2018
                  SOURCE: C:/Users/muchmore/chatbot-java/app/views/mainjs.scala.html
                  HASH: c25fcdf7976f7a84bd9303b24f818fdc48b31574
                  MATRIX: 950->1|1063->19|1091->21|1138->42|1152->48|1216->92|1265->114|1293->115|1325->121|1509->279|1523->285|1603->345|1692->408|1719->409
                  LINES: 28->1|33->1|34->2|34->2|34->2|34->2|35->3|35->3|36->4|38->6|38->6|38->6|40->8|40->8
                  -- GENERATED --
              */
          