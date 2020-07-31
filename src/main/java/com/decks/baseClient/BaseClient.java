package com.decks.baseClient;

import com.decks.SampleServiceClient;
import com.decks.constants.Headers;
import com.decks.constants.MethodType;
import com.decks.constants.PayloadType;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import com.decks.requet.GoRequest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

    public class BaseClient {

        public static Map<String, String> getContentAcceptsHeaders() {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(Headers.CONTENT_TYPE.toString(), Headers.CONTENT_TYPE.defaultValue());
            map.put(Headers.ACCEPT.toString(), Headers.ACCEPT.defaultValue());
            return map;
        }

        public static RequestSpecBuilder getRequestBuilder(boolean useDefaultHeaders) {
            RestAssured.useRelaxedHTTPSValidation();
            ConnectionConfig connectionConfig = new ConnectionConfig();
            RestAssured.config = RestAssured.config().connectionConfig(connectionConfig);
            RequestSpecBuilder tmp = new RequestSpecBuilder();
            tmp.setUrlEncodingEnabled(false);
            RestAssuredConfig config = RestAssured.config().encoderConfig(
                    (EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
            tmp.setConfig(config);
            if (useDefaultHeaders) {
                tmp.addHeaders(getContentAcceptsHeaders());
            }

            return tmp;
        }

        protected static RequestSpecBuilder validateBuilder(RequestSpecBuilder requestSpec, GoRequest request, Method method) {
            RequestSpecBuilder reqBuilder;
            if (requestSpec == null) {
                reqBuilder = getRequestBuilder(true);
            } else {
                reqBuilder = requestSpec;
            }
            if ((method == Method.POST || method == Method.PUT) && request.getBody() != null) {
                reqBuilder.setBody(request.getBody());
            }
            return reqBuilder;
        }

        public static Response get(String serviceName, String restAPI, RequestSpecBuilder reqBuilder,
                                   ResponseSpecification expectedResponse) {
            return fireRestCall(serviceName, restAPI, reqBuilder, expectedResponse, MethodType.GET);
        }

        public static Response options(String serviceName, String restAPI, RequestSpecBuilder reqBuilder,
                                       ResponseSpecification expectedResponse) {
            return fireRestCall(serviceName, restAPI, reqBuilder, expectedResponse, MethodType.OPTIONS);
        }


        public static Response post(String serviceName, String restAPI, RequestSpecBuilder reqBuilder,
                                    ResponseSpecification expectedResponse) {
            return fireRestCall(serviceName, restAPI, reqBuilder, expectedResponse, MethodType.POST);
        }

        public static Response put(String serviceName, String restAPI, RequestSpecBuilder reqBuilder,
                                   ResponseSpecification expectedResponse) {
            return fireRestCall(serviceName, restAPI, reqBuilder, expectedResponse, MethodType.PUT);
        }

        private static RequestSpecification prepRequest(String serviceName, RequestSpecBuilder reqBuilder) {
            RequestSpecification req=null;
            reqBuilder.setBaseUri(serviceName);
            req = reqBuilder.build();
            return req;
        }


        public static Response fireRestCall(String serviceName, String restAPI,
                                            RequestSpecBuilder reqBuilder, ResponseSpecification expectedResponse, MethodType method){

            RequestSpecification req = prepRequest(serviceName, reqBuilder);

            switch (method) {
                case DELETE:
                    return given(req).expect().spec(expectedResponse).when().delete(restAPI);
                case GET:
                    return given(req).expect().spec(expectedResponse).when().get(restAPI);
                case HEAD:
                    return given(req).expect().spec(expectedResponse).when().head(restAPI);
                case OPTIONS:
                    return given(req).expect().spec(expectedResponse).when().options(restAPI);
                case PATCH:
                    return given(req).expect().spec(expectedResponse).when().patch(restAPI);
                case POST:
                    return given(req).expect().spec(expectedResponse).when().post(restAPI);
                case PUT:
                    return given(req).expect().spec(expectedResponse).when().put(restAPI);
                case TRACE:
                    return given(req).expect().spec(expectedResponse).when().request(method.toString(), restAPI);
                default:
                    return null;
            }
        }

        public static void prepareBody(Object body, PayloadType payloadType,Map<String,String> bodyParams, RequestSpecBuilder reqSpecBuilder){
            if(payloadType == PayloadType.STRING || payloadType == PayloadType.OBJECT){
                reqSpecBuilder.setBody(body);
            }
            if(payloadType == PayloadType.JSON_FILE){
                File f = new File(SampleServiceClient.class.getClassLoader().getResource((String)body).getFile());
                try {
                    String s = readFile(f.getPath(), Charset.defaultCharset());
                    if(bodyParams!=null && !bodyParams.isEmpty()){
//                        s =  StringSubstitutor.replace(s,bodyParams);
                    }

                    reqSpecBuilder.setBody(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        public static String readFile(String path, Charset encoding) throws IOException, IOException {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        }

        public static void prepareGoRequest(GoRequest request){
            RequestSpecBuilder reqBuilder = getRequestBuilder(true);
            if(request != null && !request.getHeaders().isEmpty()){
                reqBuilder.addHeaders(request.getHeaders());
            }
            if(request.getMethod()!= MethodType.GET && request.getBody() != null){
                PayloadType payloadType = request.getPayloadType()==null ? PayloadType.STRING : request.getPayloadType();
                prepareBody(request.getBody(),payloadType,request.getBodyParams(),reqBuilder);
            }

            if(!request.getPathParams().isEmpty()){
                reqBuilder.addPathParams(request.getPathParams());
            }

            if(!request.getQueryParams().isEmpty()){
                reqBuilder.addQueryParams(request.getQueryParams());
            }
            request.setRequestSpecBuilder(reqBuilder);
        }
    }