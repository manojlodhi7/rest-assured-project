package stepDefinitions;

import apiEngine.IRestResponse;
import apiEngine.model.Book;
import apiEngine.model.requests.AddBooksRequest;
import apiEngine.model.requests.ISBN;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.response.Books;
import apiEngine.model.response.UserAccount;
import cucumber.TestContext;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import enums.Context;
import io.restassured.response.Response;

public class BooksSteps extends BaseSteps {

    public BooksSteps(TestContext testContext) {
        super(testContext);
    }

    @Given("^A list of books are available$")
    public void listOfBooksAreAvailable() {
        IRestResponse<Books> booksResponse = getEndPoints().getBooks();
        System.out.println("booksResponse "  + booksResponse);
        Books bookss = booksResponse.getBody();
        System.out.println("bookss : " + bookss.books.get(0));
        Book book = bookss.getBooks().get(0);
        getScenarioContext().setContext(Context.BOOK, book);
    }

    @When("^I add a book to my reading list$")
    public void addBookInList() {
        Book book = (Book) getScenarioContext().getContext(Context.BOOK);
        String userId = (String) getScenarioContext().getContext(Context.USER_ID);

        ISBN isbn = new ISBN(book.isbn);
        AddBooksRequest addBooksRequest = new AddBooksRequest(userId, isbn);

        IRestResponse<UserAccount> userAccountResponse = getEndPoints().addBook(addBooksRequest);
        getScenarioContext().setContext(Context.USER_ACCOUNT_RESPONSE, userAccountResponse);
    }

    @When("^I remove a book from my reading list$")
    public void removeBookFromList() {
        Book book = (Book) getScenarioContext().getContext(Context.BOOK);
        String userId = (String) getScenarioContext().getContext(Context.USER_ID);

        RemoveBookRequest removeBookRequest = new RemoveBookRequest(userId, book.isbn);

        Response response = getEndPoints().removeBook(removeBookRequest);
        getScenarioContext().setContext(Context.BOOK_REMOVE_RESPONSE, response);
    }
}
