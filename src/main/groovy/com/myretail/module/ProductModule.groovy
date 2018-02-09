package com.myretail.module

import com.google.inject.AbstractModule
import com.google.inject.Scopes

import com.myretail.service.CassandraService
import com.myretail.dao.PriceDao
import com.myretail.dao.ProductDao
import com.myretail.rest.ProductResource
import com.myretail.service.ProductService

class ProductModule extends AbstractModule {

  String profile

  @Override
  protected void configure() {
    bind(ProductService.class).in Scopes.SINGLETON
    bind(ProductResource.class).in Scopes.SINGLETON
    bind(CassandraService.class).in(Scopes.SINGLETON)
    bind(ProductDao.class).in(Scopes.SINGLETON)
    bind(PriceDao.class).in(Scopes.SINGLETON)
  }


}
