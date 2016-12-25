import java.io.File

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
  * Created by jahangir on 12/25/16.
  */
package object credsbot {

  implicit val system = ActorSystem("slack")

  implicit val ec = system.dispatcher

  val config = ConfigFactory.parseFile(new File("src/main/resources/all.creds"));

}
