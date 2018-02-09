package com.myretail.exception

import ratpack.error.ServerErrorHandler
import ratpack.handling.Context
import ratpack.jackson.Jackson

class ErrorHandler implements ServerErrorHandler {

  @Override
  void error(Context context, Throwable throwable) throws Exception {
      if(throwable instanceof MyRetailException) {
        context.response.status(400)
        context.response.contentType("application/json")
        context.render(Jackson.json(["errorMessage":throwable.message]))
      }else {
        context.response.status(500)
        context.response.contentType("application/json")
        context.render(Jackson.json(["errorMessage":"Something went wrong. We are working on it"]))
      }
  }

}
