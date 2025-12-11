package pages;


import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import tests.TestData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ProfilePage {
    private final SelenideElement userName = $("#userName-value"),
            deleteBooks = $("#delete-record-undefined"),
            confirmDeleteBooks = $("#closeSmallModal-ok"),
            booksList = $(".ReactTable");


    @Step("Открытие страницы /profile")
    public  ProfilePage openPage() {
        open("/profile");

        return this;
    }

    @Step("Проверка отображения User Name")
    public ProfilePage checkLogin() {
        userName.shouldHave(text(TestData.userName));

        return this;
    }

    @Step("Проверка наличия добавленной книги")
    public ProfilePage checkBookAdded() {
        booksList.shouldHave(text("Speaking JavaScript"));

        return this;
    }

    @Step("Удаление книг")
    public ProfilePage deleteBooks() {
        deleteBooks.click();
        confirmDeleteBooks.click();

        return this;
    }

    @Step("Проверка отсутствия книги в списке")
    public void checkBooksDeleted() {
        booksList.shouldNotHave(text("Speaking JavaScript"));
    }

}
