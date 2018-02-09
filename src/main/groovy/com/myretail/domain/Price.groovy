package com.myretail.domain

import com.datastax.driver.mapping.annotations.ClusteringColumn
import com.datastax.driver.mapping.annotations.Column
import com.datastax.driver.mapping.annotations.PartitionKey
import com.datastax.driver.mapping.annotations.Table
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.logging.Slf4j

@EqualsAndHashCode(includes = ['id'])
@ToString(includeNames = true)
@Slf4j
@Table(name = "PRICE")
@JsonIgnoreProperties(ignoreUnknown = true)
class Price {

  @PartitionKey
  @Column(name = "PARTITION_ID")
  @JsonIgnore
  Integer partitionId

  @ClusteringColumn(0)
  @Column(name = "PRD_ID")
  @JsonIgnore
  Integer id

  @Column(name = "VALUE")
  Double value

  @Column(name = "CURRENCY_CODE")
  String currencyCode

}
