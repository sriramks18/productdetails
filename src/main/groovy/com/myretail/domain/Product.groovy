package com.myretail.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "product")
class Product {

  @JsonProperty("item")
  Item item

  @JsonProperty("current_price")
  Price currentPrice
}
