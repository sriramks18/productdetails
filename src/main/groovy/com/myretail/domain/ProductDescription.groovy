package com.myretail.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class ProductDescription {
  String title

  @JsonProperty("bullet_description")
  List<String> bulletDescription

  @JsonProperty("general_description")
  String generalDescription
}
