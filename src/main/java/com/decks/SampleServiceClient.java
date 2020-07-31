package com.decks;

import com.decks.requet.GoRequest;
import com.decks.response.GoResponse;
import com.decks.types.ExpectedResponseTypes;

public class SampleServiceClient {


  protected static void prepareDefaultHeaders(GoRequest request) {
  }

  public static GoResponse getClientData(String serviceType, GoRequest request,
                                         ExpectedResponseTypes expectedResponseType) {
    prepareDefaultHeaders(request);
    return request.execute(serviceType,expectedResponseType);
  }

}



