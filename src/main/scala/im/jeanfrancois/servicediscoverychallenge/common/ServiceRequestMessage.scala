package im.jeanfrancois.servicediscoverychallenge.common

/**
 * Document me!
 *
 * @author jfim
 */

case class ServiceRequestMessage(className : String, methodName : String, methodArgs : List[Object])