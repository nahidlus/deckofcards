package com.decks.response;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;


@Getter
public class GoResponse {


    private Response response;
    private String schemaPath;

    public void setResponse(Response response) {
        this.response = response;
    }
    public Response getResponse(){return this.response;}

    public void setSchemaPath(String schemaPath) {
        this.schemaPath = schemaPath;
    }
    public String getSchemaPath() {
        return this.schemaPath;
    }

    public int getHttpStatusCode(){
        return this.response.getStatusCode();
    }

    public String getResponseAsString(){
        return this.response.asString();
    }

    public void validateSchema(){
        if(this.getSchemaPath()!=null){
            File f = new File(this.getClass().getClassLoader().getResource(this.getSchemaPath()).getFile());
            this.response.then().body(matchesJsonSchema(f));
        }
    }

    public JsonPath jsonPath() {
        return this.response.jsonPath();
    }

    public void validateResponse(Response ExpectedResponse){


    }

}
