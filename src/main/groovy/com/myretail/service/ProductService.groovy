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
import ratpack.func.Function
import ratpack.func.Pair
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

    priceDao.getPriceForId(id).left(productDao.getProductById(id)).map{Pair<Product, Price> pair ->
      Product product = pair.left
      Price price = pair.right
      if (price) {
        log.debug("Updating Price price=${price.value}  of product id=${id} to response")
        product.currentPrice = price
        return product
      } else {
        log.error("My Retail Error for product id=${id} while getting Price Data")
        throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "My Retail Error for product id=${id} while getting Price Data")
      }
    }

//    Promise<Price> pricePromise = priceDao.getPriceForId(id)
//    Promise<Product> productPromise =productDao.getProductById(id)
//    pricePromise.left(productPromise).map{Pair<Product, Price> pair ->
//      Product product = pair.left
//      Price price = pair.right
//      if (price) {
//        log.debug("Updating Price price=${price.value}  of product id=${id} to response")
//        product.currentPrice = price
//        return product
//      } else {
//        log.error("My Retail Error for product id=${id} while getting Price Data")
//        throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "My Retail Error for product id=${id} while getting Price Data")
//      }
//    }
//    pricePromise.flatMap(new Function<Price, Promise<Product>>() {
//      @Override
//      Promise<Product> apply(Price price) throws Exception {
//        productPromise.map { product ->
//          product.currentPrice = price
//        }
//        return productPromise
//      }
//    })
//    pricePromise.map(new Function<Price, Product>() {
//      @Override
//      Product apply(Price price) throws Exception {
//        return null
//      }
//    })

//        { Pair<Price, Product> pair ->
//          Price price = pair.left
//          Product product = pair.right
//          if (price) {
//            log.debug("Updating Price price=${price.value}  of product id=${id} to response")
//            product.currentPrice = price
//            return product
//          } else {
//            log.error("My Retail Error for product id=${id} while getting Price Data")
//            throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "My Retail Error for product id=${id} while getting Price Data")
//          }
//
//        }

  }

//  Promise<Product> updateProductWithPrice(Integer id, Product product) {
//    priceDao.getPriceForId(id).flatMap(new Function<Price, Promise>() {
//      @Override
//      Promise apply(Price price) throws Exception {
//        return null
//      }
//    })) { price ->
//      if (price) {
//        log.debug("Updating Price price=${price.value}  of product id=${id} to response")
//        product.currentPrice = price
//      } else {
//        log.error("My Retail Error for product id=${id} while getting Price Data")
//        throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "My Retail Error for product id=${id} while getting Price Data")
//      }
//    }
//  }

  Promise<Void> addPriceById(Integer id, Price price) {
    log.debug("Updating Price price=${price.value}  of product id=${id} to DB")
    priceDao.addPriceById(id, price)

  }
}
