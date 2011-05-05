package im.jeanfrancois.servicediscoverychallenge.client

import im.jeanfrancois.servicediscoverychallenge.server.service.ServiceDiscoveryService
import im.jeanfrancois.servicediscoverychallenge.common.{Stop, ServiceRequestMessage}
import im.jeanfrancois.servicediscoverychallenge.endpointdiscovery.MulticastEndpointDiscoveryService

/**
 * Document me!
 *
 * @author jfim
 */

object ServiceClient {
  def main(args: Array[String]) {
    println("Discovering nodes")
    val remoteNodes = new MulticastEndpointDiscoveryService().findEndpoints
    println(remoteNodes.size + " node(s) found")
    println()

    remoteNodes foreach(remoteNode => {
      println("=== " + remoteNode + " =================================================")
      val remoteServiceRequestProcessor = new RemoteServiceRequestProcessor(remoteNode);
      remoteServiceRequestProcessor.start

      // Discover remote services
      println("Sending service discovery message")
      val serviceList = (remoteServiceRequestProcessor !? ServiceRequestMessage(classOf[ServiceDiscoveryService].getName, "listServices", List[Object]())).asInstanceOf[List[String]];

      // Display available services
      println()
      println("Available services")
      serviceList.foreach(println);

      // Display service methods
      println()
      println("Methods available per service")
      serviceList.foreach(serviceclass => {
        println(serviceclass)
        var methodList = (remoteServiceRequestProcessor !? ServiceRequestMessage(classOf[ServiceDiscoveryService].getName, "listServiceMethods", List[Object](serviceclass))).asInstanceOf[List[String]];
        methodList.foreach(str => println("\t" + str))
        println()
      })

      remoteServiceRequestProcessor !? Stop
    })
  }
}