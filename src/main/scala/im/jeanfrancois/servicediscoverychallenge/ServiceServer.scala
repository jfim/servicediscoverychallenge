package im.jeanfrancois.servicediscoverychallenge

import com.google.inject.Guice

/**
 * @author ${user.name}
 */
object ServiceServer {
  def main(args : Array[String]) {
    val injector = Guice.createInjector(new ServiceServerModule);

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
