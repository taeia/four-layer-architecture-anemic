package library.infrastructure;

import java.util.ArrayList;
import java.util.List;

import library.domain.Book;

public class BookRepository {

    private final List<Book> books = new ArrayList<>();

    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    public void save(Book book) {
        books.add(book);
    }

    public void deleteAll() {
        books.clear();
    }
}
