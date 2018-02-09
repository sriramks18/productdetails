package com.myretail.config

import com.myretail.exception.MyRetailException
import com.myretail.exception.MyRetailExceptionEnum

class ProductConfig {
  String host
  String getProductDataPath
  Integer poolSize
  Long readTimeout
  Long connectTimeout
  String getProductParams

  void setPoolSize(String poolSize) {
    if(poolSize) {
      try {
        this.poolSize = Integer.parseInt(poolSize)
      }catch(NumberFormatException e) {
        throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "Product Client Config pool size is not a number")
      }
    }
  }

  void setReadTimeout(String readTimeout) {
    if(readTimeout) {
      try {
        this.readTimeout = Long.parseLong(readTimeout)
      }catch(NumberFormatException e) {
        throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "Product Client Config readTimeout is not a number")
      }
    }
  }

  void setConnectTimeout(String connectTimeout) {
    if(connectTimeout) {
      try {
        this.connectTimeout = Long.parseLong(connectTimeout)
      }catch(NumberFormatException e) {
        throw new MyRetailException(MyRetailExceptionEnum.CHECKED, "Product Client Config connectTimeout is not a number")
      }
    }
  }

}
