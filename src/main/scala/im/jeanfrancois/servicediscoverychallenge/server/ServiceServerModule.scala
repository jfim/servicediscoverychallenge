package im.jeanfrancois.servicediscoverychallenge.server

import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import service.{ServiceDiscoveryService, MachineNameService}

/**
 * Document me!
 *
 * @author jfim
 */

class ServiceServerModule extends AbstractModule{
  def configure: Unit = {
    bind(classOf[ServiceRegistry]).to(classOf[ServiceRegistryImpl]);
    val multibinder = Multibinder.newSetBinder(binder, classOf[Service]);
    multibinder.addBinding.to(classOf[ServiceDiscoveryService]);
    multibinder.addBinding.to(classOf[MachineNameService]);
  }
}