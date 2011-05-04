package im.jeanfrancois.servicediscoverychallenge.client

import actors.Actor
import actors.remote.Node
import actors.remote.RemoteActor._
import im.jeanfrancois.servicediscoverychallenge.common.{Stop, ServiceRequestMessage}

/**
 * Document me!
 *
 * @author jfim
 */

class RemoteServiceRequestProcessor (val remoteNode : Node) extends Actor {
  def act() {
    val serviceRequestProcessor = select(remoteNode, 'ServiceRequestProcessor)

    while(true) {
      receive {
        case msg : ServiceRequestMessage => {
          reply(serviceRequestProcessor !? msg)
        }
        case Stop => exit()
      }
    }
  }
}