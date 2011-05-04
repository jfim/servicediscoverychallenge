package im.jeanfrancois.servicediscoverychallenge.server

/**
 * Document me!
 *
 * @author jfim
 */

case class ServiceRequestMessage(className : String, methodName : String, methodArgs : List[Object])