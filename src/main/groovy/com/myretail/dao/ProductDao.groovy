package com.myretail.dao

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.myretail.config.ProductConfig
import com.myretail.domain.Product
import com.myretail.exception.MyRetailException
import com.myretail.exception.MyRetailExceptionEnum
import io.netty.util.CharsetUtil
import ratpack.exec.Blocking
import ratpack.exec.Downstream
import ratpack.exec.Promise
import ratpack.exec.Upstream
import ratpack.func.Action
import ratpack.func.Factory
import ratpack.func.Function
import ratpack.http.Status
import ratpack.http.client.HttpClient
import ratpack.http.client.HttpClientSpec
import ratpack.http.client.ReceivedResponse
import ratpack.http.client.RequestSpec
import ratpack.service.Service

import java.time.Duration

class ProductDao implements Service {

  HttpClient productDetailsHttpClient
  String host
  String getProductDataPath
  String getProductParams
  ObjectMapper mapper

  // For Spock Mock
  ProductDao() {
  }

  @Inject
  ProductDao(ProductConfig productConfig) {
    productDetailsHttpClient = HttpClient.of(createAction(productConfig))
    this.host = productConfig.host
    this.getProductDataPath = productConfig.getProductDataPath
    this.getProductParams = productConfig.getProductParams
    this.mapper = new ObjectMapper()
  }

  Action<HttpClientSpec> createAction(ProductConfig productConfig) {
    return { httpClientSpec ->
      httpClientSpec.poolSize(productConfig.poolSize)
      httpClientSpec.readTimeout(Duration.ofMillis(productConfig.readTimeout))
      httpClientSpec.connectTimeout(Duration.ofMillis(productConfig.connectTimeout))
    }
  }

  Promise<Product> getProductById(Integer id) {
    Action<RequestSpec> requestHeaders = { requestSpec ->
      requestSpec.headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
    }
    String url = host + getProductDataPath + id
    URI uri = new URI(url + "?" + getProductParams)
    return productDetailsHttpClient.get(uri, requestHeaders).<Product> flatMap { receivedResponse ->
      return Blocking.get {
        if (receivedResponse.status == Status.OK) {
          mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true)
          return mapper.readValue(receivedResponse.body.text, Product.class)
        } else {
          throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "My Retail Error for product id=${id} while getting Product Data ")
        }
      }
    }

  }

}
