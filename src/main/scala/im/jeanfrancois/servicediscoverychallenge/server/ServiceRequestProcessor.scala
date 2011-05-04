package im.jeanfrancois.servicediscoverychallenge.server

import actors.Actor
import scala.actors.remote.RemoteActor._
import com.google.inject.{Singleton, Inject}

/**
 * Document me!
 *
 * @author jfim
 */

@Singleton
class ServiceRequestProcessor @Inject() (val serviceRegistry : ServiceRegistry) extends Actor {
  def act() {
    alive(1234);
    register('ServiceRequestProcessor, this);
    while(true) {
      receive {
        case ServiceRequestMessage(classname, methodName, methodArgs) => {
          serviceRegistry.serviceList.find(service => service.getClass.getName == classname) match {
            case Some(service) => {
              reply(service !? ProcessServiceMethod(methodName, methodArgs));
            }
            case None => reply(Nil);
          }
        }
      }
    }
  }
}