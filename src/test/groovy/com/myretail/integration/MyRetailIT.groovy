package com.myretail.integration

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.cassandraunit.CQLDataLoader
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet
import org.cassandraunit.utils.EmbeddedCassandraServerHelper
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.groovy.test.embed.GroovyEmbeddedApp
import ratpack.http.HttpMethod
import ratpack.http.client.internal.DefaultReceivedResponse
import ratpack.test.embed.EmbeddedApp
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static ratpack.jackson.Jackson.json

class MyRetailIT extends Specification {

  JsonSlurper slurper = new JsonSlurper()
  ObjectMapper mapper = new ObjectMapper()
  CQLDataLoader dataLoader


  void setup() {
    EmbeddedCassandraServerHelper.startEmbeddedCassandra()
    dataLoader = new CQLDataLoader(EmbeddedCassandraServerHelper.getSession())
    System.setProperty('ratpack.cassandraConfig.keyspace', 'product')
    System.setProperty('ratpack.cassandraConfig.seeds', '127.0.0.1:9142')
    System.setProperty('ratpack.cassandraConfig.clustername', 'Test Cluster')
    System.setProperty('ratpack.cassandraConfig.createKeySpaceIfNotExists', 'false')
  }

  void cleanup() {
    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra()
  }

  @AutoCleanup
  @Shared
  GroovyRatpackMainApplicationUnderTest groovyScriptApplicationunderTest = new GroovyRatpackMainApplicationUnderTest()

  @Unroll
  def 'Get Product Detail'() {
    setup:
    dataLoader.load(new ClassPathCQLDataSet("getProductDetail.cql", 'product'))
    EmbeddedApp add = GroovyEmbeddedApp.of {
      handlers {
       all {
          render json(readInJsonFile('13860428.json'))
        }
      }
    }
    System.setProperty('ratpack.productConfig.host', add.address.toString())


    when:
    DefaultReceivedResponse actualResponse = groovyScriptApplicationunderTest.httpClient.get("/product/13860428")

    then:
    def responseMap = slurper.parseText(actualResponse.body.text)
    responseMap.current_price.value == 110.0
    responseMap.current_price.currencyCode == 'USD'

  }

  @Unroll
  def 'Get Product Detail Error Case #scenario'() {
    setup:
    dataLoader.load(new ClassPathCQLDataSet("getProductDetailError.cql", 'product'))
    EmbeddedApp add = GroovyEmbeddedApp.of {
      handlers {
        prefix("v2/pdp/tcin/13860428") {
          get {
            render json(readInJsonFile('13860428.json'))
          }
          all {
            render 404
          }
        }
      }
    }
    System.setProperty('ratpack.productConfig.host', add.address.toString())

    when:
    DefaultReceivedResponse actualResponse = groovyScriptApplicationunderTest.httpClient.get("/product/${productId}")

    then:
    actualResponse.status.code == statusCode
    def responseMap = slurper.parseText(actualResponse.body.text)
    responseMap.errorMessage.contains(errorMessage)

    where:

    scenario            | insertProduct | productId  | errorMessage                              | statusCode
    "Product not found" | false         | '1380001'  | 'My Retail Error for product id=1380001'  | 400
    "Price not found"   | true          | '13860428' | 'My Retail Error for product id=13860428' | 400

  }

  @Unroll
  def 'Put Product Detail'() {
    setup:
    dataLoader.load(new ClassPathCQLDataSet("getProductDetail.cql", 'product'))
    EmbeddedApp add = GroovyEmbeddedApp.of {
      handlers {
        all {
          render json(readInJsonFile('13860428.json'))
        }
      }
    }
    System.setProperty('ratpack.productConfig.host', add.address.toString())

    when:
    DefaultReceivedResponse actualResponse = groovyScriptApplicationunderTest.httpClient.request("/product/13860428", { requestSpec ->
      requestSpec.body.type("application/json")
      requestSpec.method(HttpMethod.PUT)
      requestSpec.body.text(JsonOutput.toJson(value: 1619.99, currencyCode: 'USD'))
    })

    then:
    def responseMap = slurper.parseText(actualResponse.body.text)
    responseMap.current_price.value == 1619.99
    responseMap.current_price.currencyCode == 'USD'

  }

  public Map readInJsonFile(String fileName) throws IOException {
    Resource resource = new ClassPathResource(fileName);
    BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line);
    }
    br.close();
    String responseString =  sb.toString();
    return mapper.readValue(responseString,HashMap.class)
  }
}
