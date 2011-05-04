package im.jeanfrancois.servicediscoverychallenge.client

import actors.remote.Node
import actors.remote.RemoteActor._
import actors.Actor
import im.jeanfrancois.servicediscoverychallenge.server.service.ServiceDiscoveryService
import im.jeanfrancois.servicediscoverychallenge.common.{Stop, ServiceRequestMessage}

/**
 * Document me!
 *
 * @author jfim
 */

object ServiceClient {
  def main(args: Array[String]) {
    val remoteNode = Node("127.0.0.1", 1234)
    val remoteServiceRequestProcessor = new RemoteServiceRequestProcessor(remoteNode);
    remoteServiceRequestProcessor.start

    println("Sending service discovery message")
    val serviceList = (remoteServiceRequestProcessor !? ServiceRequestMessage(classOf[ServiceDiscoveryService].getName, "listServices", List[Object]())).asInstanceOf[List[String]];
    println(serviceList)
    serviceList.foreach(serviceclass => println(remoteServiceRequestProcessor !? ServiceRequestMessage(classOf[ServiceDiscoveryService].getName, "listServiceMethods", List[Object](serviceclass))))

    remoteServiceRequestProcessor !? Stop
  }
}