package im.jeanfrancois.servicediscoverychallenge.server

import com.google.inject.Guice
import im.jeanfrancois.servicediscoverychallenge.endpointdiscovery.MulticastEndpointDiscoveryService

/**
 * @author ${user.name}
 */
object ServiceServer {
  def main(args : Array[String]) {
    val injector = Guice.createInjector(new ServiceServerModule);

    val discoveryService = new MulticastEndpointDiscoveryService
    discoveryService.makeThisEndpointDiscoverable()

    val serviceRegistry = injector.getInstance(classOf[ServiceRegistry]);

    serviceRegistry.serviceList.foreach(service => {
      println("Starting service " + service.getClass.getName);
      service.start
    })

    val serviceRequestProcessor = injector.getInstance(classOf[ServiceRequestProcessor]);
    serviceRequestProcessor.start

    println("Service request processor started");
  }
}
