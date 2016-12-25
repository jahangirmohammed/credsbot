package credsbot

import com.bettercloud.vault.{Vault, VaultConfig}
import net.ceedubs.ficus.Ficus._
import scala.collection.JavaConverters._
import scala.reflect.ClassTag

/**
  * Created by jahangir on 12/24/16.
  */
class VaultClient {

  val BasePath = "secret/sites/"

  def createCaseClass[T](vals : Map[String, Object])(implicit cmf : ClassTag[T]) = {
    val ctor = cmf.runtimeClass.getConstructors().head
    val args = cmf.runtimeClass.getDeclaredFields().map( f => vals(f.getName) )
    ctor.newInstance(args : _*).asInstanceOf[T]
  }

  val vaultConfig =  new VaultConfig(config.as[String]("vault.address"),config.as[String]("vault.root-token")).build();

  val vault = new Vault(vaultConfig);

  def siteDetails(env: String): SiteDetails = {
    val path = BasePath+env.trim
    createCaseClass[SiteDetails](vault.logical().read(path).getData.asScala.toMap)
  }

}
