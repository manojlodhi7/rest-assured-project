package apiEngine.model.response;

import java.util.List;
import apiEngine.model.Book;

public class Books {
    public List<Book> books;

    public List<Book> getBooks(){
        return books;
    }

    public void setBooks(List<Book> books){
        this.books = books;
    }
}