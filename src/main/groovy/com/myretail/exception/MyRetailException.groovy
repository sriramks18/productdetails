package com.myretail.exception

class MyRetailException extends Exception {

  MyRetailExceptionEnum type
  String message

  MyRetailException(MyRetailExceptionEnum type, String message) {
    this.type = type
    this.message = message
  }
}
