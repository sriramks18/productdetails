package com.myretail.service

import com.google.inject.Inject
import com.myretail.dao.PriceDao
import com.myretail.dao.ProductDao
import com.myretail.domain.Price
import com.myretail.domain.Product
import com.myretail.exception.MyRetailException
import com.myretail.exception.MyRetailExceptionEnum
import groovy.util.logging.Slf4j
import ratpack.exec.Blocking
import ratpack.exec.Promise
import ratpack.service.Service

@Slf4j
class ProductService implements Service {

  ProductDao productDao

  PriceDao priceDao

  @Inject
  ProductService(PriceDao priceDao, ProductDao productDao) {
    this.productDao = productDao
    this.priceDao = priceDao
  }

  Promise<Product> getProduct(Integer id) {
    productDao.getProductById(id).flatMap{product ->
        updateProductWithPrice(id, product)
      }
  }

  Promise<Product> updateProductWithPrice(Integer id, Product product) {
    priceDao.getPriceForId(id).flatMap { price ->
      return Blocking.get {
        if(price) {
          log.debug("Updating Price price=${price.value}  of product id=${id} to response")
          product.currentPrice = price
          product
        }else {
          log.error("My Retail Error for product id=${id} while getting Price Data")
          throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "My Retail Error for product id=${id} while getting Price Data")
        }
      }
    }
  }

   Promise<Void> addPriceById(Integer id, Price price) {
     log.debug("Updating Price price=${price.value}  of product id=${id} to DB")
     priceDao.addPriceById(id, price)

  }
}
