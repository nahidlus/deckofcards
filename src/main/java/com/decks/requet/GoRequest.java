package com.decks.requet;

import com.decks.baseClient.BaseClient;
import com.decks.config.Config;
import com.decks.constants.MethodType;
import com.decks.constants.PayloadType;
import com.decks.response.GoResponse;
import com.decks.types.ExpectedResponseTypes;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class GoRequest extends BaseClient {

    private String apiPath;
    private MethodType method;
    private Object body;
    private Map<String, String> headers;
    private Map<String, String> pathParams;
    private Map<String,String> queryParams;
    private String schemaPath;
    private PayloadType payloadType;
    private Map<String,String> bodyParams;
    private RequestSpecBuilder requestSpecBuilder;



    public Map<String, String> getBodyParams() {
        return bodyParams;
    }

    public GoRequest setBodyParams(Map<String, String> bodyParams) {
        this.bodyParams = bodyParams;
        return this;
    }

    public RequestSpecBuilder getRequestSpecBuilder() {
        return requestSpecBuilder;
    }

    public GoRequest setRequestSpecBuilder(RequestSpecBuilder requestSpecBuilder) {
        this.requestSpecBuilder = requestSpecBuilder;
        return this;
    }

    private GoRequest(Builder builder) {
        this.apiPath = builder.apiPath;
        this.method = builder.method;
        this.body = builder.body;
        this.headers = builder.headers;
        this.pathParams = builder.pathParams;
        this.queryParams = builder.queryParams;
        this.schemaPath = builder.schemaPath;
        this.payloadType = builder.payloadType;
        this.bodyParams  = builder.bodyParams;
    }

    public GoResponse execute(String serviceType, ExpectedResponseTypes expectedResponseTypes){
        if(serviceType==null){
            throw new NullPointerException("Base URL Cannot be null");
        }
        else if(!serviceType.startsWith("http://") && !serviceType.startsWith("https://")){
            throw new RuntimeException("HTTP protocal missing from URL. Kindly add http or https before the base url");
        }
        else {
            GoResponse res = new GoResponse();

            res.setSchemaPath(this.schemaPath);
            prepareGoRequest(this);
            Response response = fireRestCall(serviceType, this.getApiPath(), this.requestSpecBuilder,
                    expectedResponseTypes.getResponseSpecification(this), this.method);
            res.setResponse(response);
            return res;
        }
    }

    public GoResponse execute(ExpectedResponseTypes expectedResponseTypes){
        String baseUrl = Config.getBaseUrl();
        return execute(baseUrl,expectedResponseTypes);
    }

    public String getApiPath() {
        return apiPath;
    }

    public MethodType getMethod() {
        return method;
    }

    public Object getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public PayloadType getPayloadType() {
        return payloadType;
    }

    public GoRequest setPayloadType(PayloadType payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    public Map<String, String> getPathParams() {
        return pathParams;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getSchemaPath() {
        return schemaPath;
    }

    public GoRequest setApiPath(String apiPath) {
        this.apiPath = apiPath;
        return this;
    }

    public GoRequest setMethod(MethodType method) {
        this.method = method;
        return this;
    }

    public GoRequest setBody(Object body) {
        this.body = body;
        return this;
    }

    public GoRequest setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public GoRequest setPathParams(Map<String, String> pathParams) {
        this.pathParams = pathParams;
        return this;
    }

    public GoRequest setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public GoRequest setSchemaPath(String schemaPath) {
        this.schemaPath = schemaPath;
        return this;
    }

    public GoRequest header(String key, String value){
        if(this.headers==null){
            this.headers = new HashMap<String, String>();
        }
        this.headers.put(key,value);
        return this;
    }

    public static final class Builder {
        private String apiPath;
        private MethodType method;
        private Object body;
        private PayloadType payloadType;
        private Map<String, String> headers = new HashMap<String, String>();
        private Map<String, String> pathParams= new HashMap<String, String>();
        private Map<String, String> queryParams= new HashMap<String, String>();
        private String schemaPath;
        private Map<String,String> bodyParams;

        public  Builder(String apiPath,MethodType method) {
            this.apiPath = apiPath;
            this.method = method;
        }

        public GoRequest build() {
            return new GoRequest(this);
        }

        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        public Builder body(Object body,Map<String,String> params){
                this.body = body;
                this.bodyParams = params;
                return this;
        }

        public Builder payLoadType(PayloadType payloadType){
            this.payloadType = payloadType;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Builder pathParams(Map<String, String> pathParams) {
            this.pathParams.putAll(pathParams);
            return this;
        }

        public Builder queryParams(Map<String, String> queryParams) {
            this.queryParams.putAll(queryParams);
            return this;
        }

        public Builder schemaPath(String schemaPath) {
            this.schemaPath = schemaPath;
            return this;
        }
        public Builder header(String key, String value){
            this.headers.put(key,value);
            return this;
        }

        public Builder pathParam(String key, String value){
                this.pathParams.put(key,value);
                return this;
            }

        public Builder queryParam(String key, String value){
            this.queryParams.put(key,value);
            return this;
        }
        }

}
