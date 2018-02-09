package com.myretail.dao

import com.datastax.driver.mapping.Mapper
import com.datastax.driver.mapping.MappingManager
import com.google.inject.Inject
import com.myretail.service.CassandraService
import com.myretail.domain.Price
import ratpack.exec.Promise
import ratpack.service.Service

class PriceDao implements Service {

  CassandraService cassandraService

  Mapper<Price> priceMapper

  public static final int PRICE_PARTITION_ID = 1

  // For Spock Mock
  PriceDao() {
  }

  @Inject
  PriceDao(CassandraService cassandraService) {
    this.cassandraService = cassandraService
  }

  Promise<Price> getPriceForId(int id) {
    verifyPriceMapper()
    Promise.<Price> async { upstream ->
      upstream.accept(priceMapper.getAsync(PRICE_PARTITION_ID, id))
    }
  }

  Promise<Void> addPriceById(int id, Price price) {
    verifyPriceMapper()
    price.partitionId = PRICE_PARTITION_ID
    price.id = id
    Promise.<Void> async{ downstream ->
        downstream.accept(priceMapper.saveAsync(price))
      }
  }

  void verifyPriceMapper() {
    if (!priceMapper) {
      this.priceMapper = new MappingManager(cassandraService.session).mapper(Price)
    }
  }
}
