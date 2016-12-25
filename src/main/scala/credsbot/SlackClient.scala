package credsbot

import java.util.regex.Pattern
import net.ceedubs.ficus.Ficus._
import slack.SlackUtil
import slack.models.Message
import slack.rtm.SlackRtmClient

/**
  * Created by jahangir on 12/24/16.
  */
class SlackClient {

  val token = config.as[String]("api.token")

  val vault = new VaultClient()

  val client = SlackRtmClient(token)

  val selfId = client.state.self.id

  client.onMessage { message =>
      handleMessage(message)
  }

  def handleMessage(message: Message): Unit = {

    val mentionedIds = SlackUtil.extractMentionedIds(message.text)

    val toAnswer = mentionedIds.contains(selfId) && !message.user.isEmpty

    message.text match {
      case s if s.contains("sitedetails") && toAnswer => {
        val p = Pattern.compile("sitedetails(.*)");
        val m = p.matcher(s)
        val env = if(m.find()) m.group(1) else ""
        val siteDetails = vault.siteDetails(env)
        client.sendMessage(message.channel, s"<@${message.user}> - sitedetails for $env: \n url: ${siteDetails.url} \n user: ${siteDetails.user} " +
          s"\n password: ${siteDetails.password}")
      }
      case s if s.contains("help") && toAnswer => client.sendMessage(message.channel, s"<@${message.user}> - Usage: sitedetails cg-demo" + "\n" +
      "sitedetails mojo-demo")
      case s if s.contains("coffee") && toAnswer => client.sendMessage(message.channel, s"<@${message.user}> - Not yet. Keep tuned!")
      case s if s.contains("awake") && toAnswer => client.sendMessage(message.channel,
        s"""
           |<@${message.user}> - Yes, please type: "@credsbot help" to see what I can do for you.
         """.stripMargin)
      case _ =>
        if(toAnswer)
          client.sendMessage(message.channel, s"<@${message.user}> - Sorry, I don't understand.")
    }
  }

}

case class SiteDetails(url: String, user: String, password: String)


