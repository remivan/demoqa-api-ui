package api;

import io.qameta.allure.Step;
import models.BookDataModel;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.openqa.selenium.Cookie;
import tests.TestBase;
import tests.TestData;

import java.util.Collections;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static specs.BaseSpecs.*;


public class ApiTests {

    LoginResponseModel loginResponseModel = authorizeRequest();

    public String token = loginResponseModel.getToken(),
            username = loginResponseModel.getUsername(),
            userId = loginResponseModel.getUserId(),
            expires = loginResponseModel.getExpires();


    public  LoginResponseModel authorizeRequest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setUserName(TestData.userName);
        authData.setPassword(TestData.password);

        return given(requestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpecification(200))
                .extract().as(LoginResponseModel.class);
    }

    @Step("Set cookie")
    public ApiTests setCookie() {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));

        return this;
    }

    @Step("Make request to delete all books")
    public ApiTests makeDeleteAllBooksRequest() {
        given(requestBookSpec(token))
                .queryParams("UserId", userId)

                .when()
                .delete("/BookStore/v1/Books")

                .then()
                .spec(responseSpecification(204));
        return this;
    }

    @Step("Make request to add a book")
    public void makeBookPostRequest(String isbn) {
        BookDataModel bookData = new BookDataModel();
        BookDataModel.CollectionOfIsbns isbnCollection = new BookDataModel.CollectionOfIsbns();
        bookData.setUserId(userId);
        isbnCollection.setIsbn(isbn);
        bookData.setCollectionOfIsbns(Collections.singletonList(isbnCollection));

        given(requestBookSpec(token))
                .body(bookData)

                .when()
                .post("/BookStore/v1/Books")

                .then()
                .spec(responseSpecification(201));
    }

}
