package apiEngine;

import org.apache.http.HttpStatus;

import apiEngine.model.requests.AddBooksRequest;
import apiEngine.model.requests.AuthorizationRequest;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.response.Books;
import apiEngine.model.response.Token;
import apiEngine.model.response.UserAccount;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndPoints {

    private final RequestSpecification request;

    public EndPoints(String baseUrl) {
        System.out.println("Base URI " + baseUrl);
        RestAssured.baseURI = baseUrl;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
    }

    public void authenticateUser(AuthorizationRequest authRequest) {
        Response response = request.body(authRequest).post(Route.generateToken());
        if (response.statusCode() != HttpStatus.SC_OK)
            throw new RuntimeException("Authentication Failed. Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());

        Token tokenResponse = response.body().jsonPath().getObject("$", Token.class);
        request.header("Authorization", "Bearer " + tokenResponse.token);
    }

    public IRestResponse<Books> getBooks() {
        System.out.println("API : " + Route.books());
        Response response = request.get(Route.books());
//        Response response = request.get("https://bookstore.toolsqa.com/BookStore/v1/Books");
        System.out.println(response.statusCode());
        System.out.println(response.getBody().asString());
        return new RestResponse(Books.class, response);
    }

    public IRestResponse<UserAccount> addBook(AddBooksRequest addBooksRequest) {
        Response response = request.body(addBooksRequest).post(Route.books());
        System.out.println("addBook response code : " + response.getStatusCode());
        System.out.println("addBook response body : " + response.getBody().asString());
        return new RestResponse(UserAccount.class, response);
    }

    public Response removeBook(RemoveBookRequest removeBookRequest) {
        return request.body(removeBookRequest).delete(Route.book());
    }

    public IRestResponse<UserAccount> getUserAccount(String userId) {
        Response response = request.get(Route.userAccount(userId));
        return new RestResponse(UserAccount.class, response);
    }

}