package com.myretail.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.myretail.domain.Price
import com.myretail.domain.Product
import com.myretail.service.ProductService
import ratpack.exec.Promise
import ratpack.groovy.handling.GroovyChainAction

import static ratpack.jackson.Jackson.fromJson
import static ratpack.jackson.Jackson.json

class ProductResource extends GroovyChainAction {

  ProductService productService
  ObjectMapper mapper

  @Inject
  ProductResource(ProductService productService) {
    this.productService = productService
    this.mapper = new ObjectMapper()

  }

  @Override
  void execute() throws Exception {
    path(":id") {
      def id = pathTokens["id"]

      byMethod {
        get { ctx ->
          Promise<Product> product = productService.getProduct(Integer.parseInt(id))
          product.then { result ->
            if (result == null) {
              render 404
            } else {
              render json(result)
            }
          }
        }
        put { ctx ->
          parse(fromJson(Price.class, mapper)).
              flatMap { price ->
                productService.addPriceById(Integer.parseInt(id), price)
              }.
              flatMap {
                productService.getProduct(Integer.parseInt(id))
              }.then { Product product ->
            if (product == null) {
              render 404
            } else {
              render json(product)
            }
          }
        }
      }
    }
  }
}
