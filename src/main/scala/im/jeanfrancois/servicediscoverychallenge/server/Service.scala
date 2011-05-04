package im.jeanfrancois.servicediscoverychallenge.server

import actors.Actor
import java.lang.reflect.Method

/**
 * Document me!
 *
 * @author jfim
 */

trait Service extends Actor {
  def serviceMethods: List[Method] = {
    getClass.getDeclaredMethods.toList.filter(method => method.isAnnotationPresent(classOf[ServiceMethod]));
  }

  def act {
    while (true) {
      receive {
        case ProcessServiceMethod(methodName, methodArgs) => {
          // Check if the method is in the list of allowed service methods
          var filteredMethods = serviceMethods.filter(method => method.getName == methodName);

          // If it is, invoke it
          if (filteredMethods.size == 1) {
            reply {
              filteredMethods.head.invoke(this, methodArgs.toArray.asInstanceOf[Array[Object]] : _*);
            }
          }
        }
        case Stop => exit();
      }
    }
  }
}