package im.jeanfrancois.servicediscoverychallenge.service

import im.jeanfrancois.servicediscoverychallenge.{ServiceMethod, Service}
import com.google.inject.Singleton;

/**
 * Document me!
 *
 * @author jfim
 */
@Singleton
class MachineNameService extends Service {
  @ServiceMethod
  def localMachineName() : String = {
    System.getenv("COMPUTERNAME");
  }
}