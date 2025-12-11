package tests;

import api.ApiTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

public class DeleteBooksTest extends TestBase{
    ApiTests apiTests = new ApiTests();
    ProfilePage profilePages = new ProfilePage();

    @Test
    @DisplayName("Проверка удаления товара из списка книг")
    void deleteBooksTest() {
     apiTests.authorizeRequest();
     apiTests.setCookie()
             .makeDeleteAllBooksRequest()
             .makeBookPostRequest("9781449365035");
     profilePages.openPage()
             .checkLogin()
             .checkBookAdded()
             .deleteBooks()
             .checkBooksDeleted();
    }
}
