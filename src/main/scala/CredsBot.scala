import java.io.File
import java.util.regex.Pattern

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import slack.SlackUtil
import slack.rtm.SlackRtmClient
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

/**
  * Created by jahangir on 12/3/16.
  */
object CredsBot extends App {

  val config = ConfigFactory.parseFile(new File("src/main/resources/all.creds"));

  val token = config.as[String]("api.token")

  implicit val system = ActorSystem("slack")

  implicit val ec = system.dispatcher

  val client = SlackRtmClient(token)

  val selfId = client.state.self.id

  client.onMessage { message =>
    val mentionedIds = SlackUtil.extractMentionedIds(message.text)

    if(mentionedIds.contains(selfId)) {
      client.sendMessage(message.channel, s"<@${message.user}>: Hey!")
    }

    message.text match {
      case s if s.startsWith("sitedetails") => {
        val p = Pattern.compile("sitedetails(.*)");
        val m = p.matcher(s)
        val env = m.group(1)
        val siteDetails = config.as[SiteDetails](
          s"""
             |$env.sitedetails
           """.stripMargin)
        client.sendMessage(message.channel, s"<@${message.user}> - sitedetails for $env: \n url: ${siteDetails.url} \n user: ${siteDetails.user} " +
          s"\n password: ${siteDetails.password}")
      }
      case s if s.startsWith("help") => client.sendMessage(message.channel, s"<@${message.user}> - Usage: ")
      case _ => client.sendMessage(message.channel, s"<@${message.user}> - I don't understand, please type help: ")
    }
  }

}

case class SiteDetails(password: String, url: String, user: String)
