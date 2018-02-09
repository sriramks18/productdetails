import com.myretail.config.CassandraConfig
import com.myretail.config.ProductConfig
import com.myretail.exception.ErrorHandler
import com.myretail.module.ProductModule
import com.myretail.rest.ProductResource
import ratpack.error.ServerErrorHandler

import static ratpack.groovy.Groovy.ratpack


ratpack {
  serverConfig {
    props("application.properties")
    sysProps()
    require("/cassandraConfig", CassandraConfig)
    require("/productConfig", ProductConfig)
  }

  bindings {
    module ProductModule
    bindInstance ServerErrorHandler, new ErrorHandler(
    )
  }

  handlers {
    prefix("product") {
      all chain(registry.get(ProductResource))
    }
  }
}