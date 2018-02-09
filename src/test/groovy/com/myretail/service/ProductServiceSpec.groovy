package com.myretail.service

import com.myretail.dao.PriceDao
import com.myretail.dao.ProductDao
import com.myretail.domain.Item
import com.myretail.domain.Price
import com.myretail.domain.Product
import com.myretail.exception.MyRetailException
import com.myretail.exception.MyRetailExceptionEnum
import ratpack.exec.Blocking
import ratpack.exec.ExecResult
import ratpack.exec.Promise
import ratpack.test.exec.ExecHarness
import spock.lang.AutoCleanup
import spock.lang.Specification


class ProductServiceSpec extends Specification {

  @AutoCleanup
  ExecHarness execHarness = ExecHarness.harness()

  ProductService productService
  ProductDao productDao
  PriceDao priceDao


  def setup() {
    productDao = Mock(ProductDao.class)
    priceDao = Mock(PriceDao.class)
    productService = new ProductService(priceDao, productDao)
  }

  def "test getProduct"() {

    when:
    ExecResult<Promise<List<Product>>> result = execHarness.yield {productService.getProduct(1386028)}

    then:
    productDao.getProductById(1386028) >> Blocking.get {
      return new Product(item: new Item(tcin:'100'))
    }

    priceDao.getPriceForId(1386028) >> Blocking.get {
      return new Price(value: 100.00, currencyCode: 'USD')
    }

    result.value.item.tcin == '100'
    result.value.currentPrice.value == 100.00
    result.value.currentPrice.currencyCode == 'USD'

  }

  def "test getProduct error"() {

    when:
    ExecResult<Promise<List<Product>>> result = execHarness.yield {productService.getProduct(1386028)}

    then:
    productDao.getProductById(1386028) >> Blocking.get {
      throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "My Retail Error for product id=1386028 while getting Product Data ")
    }

    priceDao.getPriceForId(1386028) >> Blocking.get {
      return new Price(value: 100.00, currencyCode: 'USD')
    }

    result.error == true

  }

  def "test getProduct price error"() {

    when:
    ExecResult<Promise<List<Product>>> result = execHarness.yield {productService.getProduct(1386028)}

    then:
    productDao.getProductById(1386028) >> Blocking.get {
      return new Product(item: new Item(tcin:'100'))
    }

    priceDao.getPriceForId(1386028) >> Blocking.get {
      return null
    }

    result.error == true
  }

}
