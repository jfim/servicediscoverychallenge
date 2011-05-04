package im.jeanfrancois.servicediscoverychallenge

import com.google.inject.{Inject, Singleton}
import scala.collection.JavaConversions._

/**
 * Document me!
 *
 * @author jfim
 */

@Singleton
class ServiceRegistryImpl @Inject()(val services:java.util.Set[Service]) extends ServiceRegistry {
  var serviceList = List[Service]();

  serviceList ++= services.toList;
}