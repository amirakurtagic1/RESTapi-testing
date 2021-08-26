import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestForGETMethod {

    private String uidOfFirstAddedCollection;
    JSONObject json;

    @Test
    public void doesGetMethodExist() {
        given().header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections").
                then().statusCode(200);
    }

    @Test
    public void getCollections() {

        Response response = given().header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections");
        System.out.println(response.getBody().asString());

    }

    @Test
    public void testResponseForGETMethod() {
        given().header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections").
                then().
                body("collections.owner", hasItems("11579037"));
    }

    @Test
    public void testResponseForGETMethod2() {
        given().header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("collections.size()", is(2));
    }

    @Test
    public void testIfResponseHasAllParameters() {
        Response response = given().header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections");

        response.then().body("collections.id", notNullValue());
        response.then().body("collections.name", notNullValue());
        response.then().body("collections.owner", notNullValue());
        response.then().body("collections.uid.size()", notNullValue());
    }

    @Test
    public void testIfResponseHasAllParametersValue() {
        Response response = given().header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections");

        response.then().body("collections.id", hasItems("25cf6b98-f24c-4e8b-8060-4d608562da0a", "11579037-1b13b97f-dfa7-436e-973f-51471c88fe66"));
        response.then().body("collections.name", hasItems("Collection for testing 1", "Sample Collection"));
        response.then().body("collections.owner", hasItems("11579037", "11579037"));
        response.then().body("collections.uid.size()", is(2));
    }

    @Test
    public void testIfAPIKeyIsNotSent() {

        given().
                get("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("error.name", equalTo("AuthenticationError")).
                body("error.message", equalTo("Invalid API Key. Every request requires a valid API Key to be sent."));
    }
    //GET single collection

    @Test
    public void testDoesResponseHaveCollection() {

        given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections/11579037-25cf6b98-f24c-4e8b-8060-4d608562da0a").
                then().
                assertThat().
                body("collection", notNullValue());
    }

    @Test
    public void testDoesResponseHaveCollectionItems1() {

        given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections/11579037-25cf6b98-f24c-4e8b-8060-4d608562da0a").
                then().
                assertThat().
                body("collection.info", notNullValue()).
                body("collection.item", notNullValue());
    }

    @Test
    public void testDoesResponseHaveCollectionItems2() {

        given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections/11579037-25cf6b98-f24c-4e8b-8060-4d608562da0a").
                then().
                assertThat().
                body("collection.info.name", notNullValue()).
                body("collection.info.description", notNullValue()).
                body("collection.info.schema", notNullValue());
    }

    @Test
    public void testDoesCollectionHaveRightItemsSize() {

        given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                get("https://api.getpostman.com/collections/11579037-25cf6b98-f24c-4e8b-8060-4d608562da0a").
                then().
                assertThat().
                body("collection.item.size()", is(1));
    }

    @Test
    public void testForPOSTMEthod() {
        String stringToParse = "{\n" +
                "    \"collection\": {\n" +
                "        \"variables\": [],\n" +
                "        \"info\": {\n" +
                "            \"_postman_id\": \"ac03df1d-90f0-401d-aa57-39c395253c80\",\n" +
                "            \"name\": \"Sample Collection\",\n" +
                "            \"description\": \"This is just a sample collection.\",\n" +
                "            \"schema\": \"https://schema.getpostman.com/json/collection/v2.0.0/collection.json\"\n" +
                "        },\n" +
                "        \"item\": [\n" +
                "            {\n" +
                "                \"name\": \"This is a folder\",\n" +
                "                \"description\": \"\",\n" +
                "                \"item\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Sample POST Request\",\n" +
                "                        \"request\": {\n" +
                "                            \"url\": \"echo.getpostman.com/post\",\n" +
                "                            \"method\": \"POST\",\n" +
                "                            \"header\": [\n" +
                "                                {\n" +
                "                                    \"key\": \"Content-Type\",\n" +
                "                                    \"value\": \"application/json\",\n" +
                "                                    \"description\": \"\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"body\": {\n" +
                "                                \"mode\": \"raw\",\n" +
                "                                \"raw\": \"{\\n\\t\\\"data\\\": \\\"123\\\"\\n}\"\n" +
                "                            },\n" +
                "                            \"description\": \"This is a sample POST Request\"\n" +
                "                        },\n" +
                "                        \"response\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Sample GET Request\",\n" +
                "                \"request\": {\n" +
                "                    \"url\": \"echo.getpostman.com/get\",\n" +
                "                    \"method\": \"GET\",\n" +
                "                    \"header\": [],\n" +
                "                    \"body\": {\n" +
                "                        \"mode\": \"formdata\",\n" +
                "                        \"formdata\": []\n" +
                "                    },\n" +
                "                    \"description\": \"This is a sample GET Request\"\n" +
                "                },\n" +
                "                \"response\": []\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONParser parser = new JSONParser();
        try {
             json = (JSONObject) parser.parse(stringToParse);
            System.out.println(json.toString());
                    given().
                    header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                    header("Content-Type", "application-json").contentType(ContentType.JSON).accept(ContentType.JSON).body(json).when().
                    post("https://api.getpostman.com/collections").then().statusCode(200);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testIfAPIKeyIsNotSentForPOSTMethod() throws ParseException {
        String stringToParse = "{\n" +
                "    \"collection\": {\n" +
                "        \"variables\": [],\n" +
                "        \"info\": {\n" +
                "            \"_postman_id\": \"ac03df1d-90f0-401d-aa57-39c395253c80\",\n" +
                "            \"name\": \"Sample Collection\",\n" +
                "            \"description\": \"This is just a sample collection.\",\n" +
                "            \"schema\": \"https://schema.getpostman.com/json/collection/v2.0.0/collection.json\"\n" +
                "        },\n" +
                "        \"item\": [\n" +
                "            {\n" +
                "                \"name\": \"This is a folder\",\n" +
                "                \"description\": \"\",\n" +
                "                \"item\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Sample POST Request\",\n" +
                "                        \"request\": {\n" +
                "                            \"url\": \"echo.getpostman.com/post\",\n" +
                "                            \"method\": \"POST\",\n" +
                "                            \"header\": [\n" +
                "                                {\n" +
                "                                    \"key\": \"Content-Type\",\n" +
                "                                    \"value\": \"application/json\",\n" +
                "                                    \"description\": \"\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"body\": {\n" +
                "                                \"mode\": \"raw\",\n" +
                "                                \"raw\": \"{\\n\\t\\\"data\\\": \\\"123\\\"\\n}\"\n" +
                "                            },\n" +
                "                            \"description\": \"This is a sample POST Request\"\n" +
                "                        },\n" +
                "                        \"response\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Sample GET Request\",\n" +
                "                \"request\": {\n" +
                "                    \"url\": \"echo.getpostman.com/get\",\n" +
                "                    \"method\": \"GET\",\n" +
                "                    \"header\": [],\n" +
                "                    \"body\": {\n" +
                "                        \"mode\": \"formdata\",\n" +
                "                        \"formdata\": []\n" +
                "                    },\n" +
                "                    \"description\": \"This is a sample GET Request\"\n" +
                "                },\n" +
                "                \"response\": []\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(stringToParse);
        given().
                header("Content-Type", "application-json").contentType(ContentType.JSON).accept(ContentType.JSON).body(json).when().
                post("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("error.name", equalTo("AuthenticationError")).
                body("error.message", equalTo("Invalid API Key. Every request requires a valid API Key to be sent."));
    }
    @Test
    public void testIfCollectionAlreadyExists() throws ParseException {
        String stringToParse = "{\n" +
                "    \"collection\": {\n" +
                "        \"variables\": [],\n" +
                "        \"info\": {\n" +
                "            \"_postman_id\": \"ac03df1d-90f0-401d-aa57-39c395253c80\",\n" +
                "            \"name\": \"Sample Collection\",\n" +
                "            \"description\": \"This is just a sample collection.\",\n" +
                "            \"schema\": \"https://schema.getpostman.com/json/collection/v2.0.0/collection.json\"\n" +
                "        },\n" +
                "        \"item\": [\n" +
                "            {\n" +
                "                \"name\": \"This is a folder\",\n" +
                "                \"description\": \"\",\n" +
                "                \"item\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Sample POST Request\",\n" +
                "                        \"request\": {\n" +
                "                            \"url\": \"echo.getpostman.com/post\",\n" +
                "                            \"method\": \"POST\",\n" +
                "                            \"header\": [\n" +
                "                                {\n" +
                "                                    \"key\": \"Content-Type\",\n" +
                "                                    \"value\": \"application/json\",\n" +
                "                                    \"description\": \"\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"body\": {\n" +
                "                                \"mode\": \"raw\",\n" +
                "                                \"raw\": \"{\\n\\t\\\"data\\\": \\\"123\\\"\\n}\"\n" +
                "                            },\n" +
                "                            \"description\": \"This is a sample POST Request\"\n" +
                "                        },\n" +
                "                        \"response\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Sample GET Request\",\n" +
                "                \"request\": {\n" +
                "                    \"url\": \"echo.getpostman.com/get\",\n" +
                "                    \"method\": \"GET\",\n" +
                "                    \"header\": [],\n" +
                "                    \"body\": {\n" +
                "                        \"mode\": \"formdata\",\n" +
                "                        \"formdata\": []\n" +
                "                    },\n" +
                "                    \"description\": \"This is a sample GET Request\"\n" +
                "                },\n" +
                "                \"response\": []\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(stringToParse);
        given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                header("Content-Type", "application-json").contentType(ContentType.JSON).accept(ContentType.JSON).
                body(json).when().
                post("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("error.name", equalTo("instanceFoundError")).
                body("error.message", equalTo("The specified item already exists."));
    }
    @Test
    public void testIfAPIKeyIsNotRightForPOSTMethod() throws ParseException {
        String stringToParse = "{\n" +
                "    \"collection\": {\n" +
                "        \"variables\": [],\n" +
                "        \"info\": {\n" +
                "            \"_postman_id\": \"ac03df1d-90f0-401d-aa57-39c395253c80\",\n" +
                "            \"name\": \"Sample Collection\",\n" +
                "            \"description\": \"This is just a sample collection.\",\n" +
                "            \"schema\": \"https://schema.getpostman.com/json/collection/v2.0.0/collection.json\"\n" +
                "        },\n" +
                "        \"item\": [\n" +
                "            {\n" +
                "                \"name\": \"This is a folder\",\n" +
                "                \"description\": \"\",\n" +
                "                \"item\": [\n" +
                "                    {\n" +
                "                        \"name\": \"Sample POST Request\",\n" +
                "                        \"request\": {\n" +
                "                            \"url\": \"echo.getpostman.com/post\",\n" +
                "                            \"method\": \"POST\",\n" +
                "                            \"header\": [\n" +
                "                                {\n" +
                "                                    \"key\": \"Content-Type\",\n" +
                "                                    \"value\": \"application/json\",\n" +
                "                                    \"description\": \"\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"body\": {\n" +
                "                                \"mode\": \"raw\",\n" +
                "                                \"raw\": \"{\\n\\t\\\"data\\\": \\\"123\\\"\\n}\"\n" +
                "                            },\n" +
                "                            \"description\": \"This is a sample POST Request\"\n" +
                "                        },\n" +
                "                        \"response\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Sample GET Request\",\n" +
                "                \"request\": {\n" +
                "                    \"url\": \"echo.getpostman.com/get\",\n" +
                "                    \"method\": \"GET\",\n" +
                "                    \"header\": [],\n" +
                "                    \"body\": {\n" +
                "                        \"mode\": \"formdata\",\n" +
                "                        \"formdata\": []\n" +
                "                    },\n" +
                "                    \"description\": \"This is a sample GET Request\"\n" +
                "                },\n" +
                "                \"response\": []\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONParser parser = new JSONParser();
        json = (JSONObject) parser.parse(stringToParse);
        given().
                header("x-api-key", "keyIsNotOkay").
                header("Content-Type", "application-json").contentType(ContentType.JSON).accept(ContentType.JSON).
                body(json).when().
                post("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("error.name", equalTo("AuthenticationError")).
                body("error.message", equalTo("Invalid API Key. Every request requires a valid API Key to be sent."));
    }
    @Test
    public void testIfJsonObjectIsNotComplete1(){
        JSONObject json = new JSONObject();
        json.put("collection", "[]");
        given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                header("Content-Type", "application-json").contentType(ContentType.JSON).accept(ContentType.JSON).
                body(json).when().
                post("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("error.name", equalTo("malformedRequestError")).
                body("error.message", equalTo("Found 1 errors with the supplied collection."));
    }
    @Test
    public void testIfJsonObjectIsNotComplete2(){
        JSONObject req = new JSONObject();
        JSONObject item = new JSONObject();
        JSONObject itemOfCollection = new JSONObject();
        item.put("name", "GET Request");
        req.put("url", "https://reqres.in/api/users?page=2");
        req.put("method", "GET");
        req.put("header", "{}");
        req.put("description", "GET req for testing");

        item.put("request", req);
        itemOfCollection.put("name", "Folder");
        itemOfCollection.put("item", item);
        JSONObject collection = new JSONObject();
        collection.put("item", itemOfCollection);

        JSONObject json = new JSONObject();
        json.put("collection", collection);

        ArrayList<String> listOfErrors = new ArrayList<String>();
        listOfErrors.add(": must have required property 'info'");
        listOfErrors.add("item: must be array");

            given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                header("Content-Type", "application-json").contentType(ContentType.JSON).accept(ContentType.JSON).
                body(json).when().
                post("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("error.name", equalTo("malformedRequestError")).
                body("error.message", equalTo("Found 2 errors with the supplied collection.")).
                body("error.details", equalTo(listOfErrors));
    }
    @Test
    public void testWhenJsonObjectDoesNotHaveTheCorrectFormat(){
        JSONObject jsonObject = new JSONObject();
        given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                header("Content-Type", "application-json").contentType(ContentType.JSON).accept(ContentType.JSON).
                body(jsonObject).when().
                post("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("error.name", equalTo("paramMissingError")).
                body("error.message", equalTo("Parameter is missing in the request."));
    }
    @Test
    public void testIfJsonObjectWithWrongParamsIsSent(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("param1", "value1");
        given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                header("Content-Type", "application-json").contentType(ContentType.JSON).accept(ContentType.JSON).
                body(jsonObject).when().
                post("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("error.name", equalTo("paramMissingError")).
                body("error.message", equalTo("Parameter is missing in the request."));
    }
    @Test
    public void testWhenStringIsSentInsteadOfJsonObject(){
        String string = "Some basic string for testing.";
            given().
                header("x-api-key", "PMAK-6120cb3f64806900461701d3-8f58352ba485bc5feb5730fc3044847824").
                header("Content-Type", "application-json").contentType(ContentType.JSON).accept(ContentType.JSON).
                body(string).when().
                post("https://api.getpostman.com/collections").
                then().
                assertThat().
                body("error.name", equalTo("SyntaxError")).
                body("error.message", equalTo("Unexpected token S in JSON at position 0"));
    }


}