package im.jeanfrancois.servicediscoverychallenge.server.service

import com.google.inject.Singleton
import im.jeanfrancois.servicediscoverychallenge.server.{ServiceMethod, Service}
;

/**
 * Document me!
 *
 * @author jfim
 */
@Singleton
class MachineNameService extends Service {
  @ServiceMethod
  def localMachineName(): String = {
    System.getenv("COMPUTERNAME");
  }
}