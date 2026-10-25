package library.infrastructure;

import java.util.ArrayList;
import java.util.List;

import library.domain.Book;
import library.domain.Copy;

public class CopyRepository {

    private final List<Copy> copies = new ArrayList<>();

    public List<Copy> findAll() {
        return new ArrayList<>(copies);
    }

    public List<Copy> findByBook(Book book) {
        List<Copy> result = new ArrayList<>();
        for (Copy copy : copies) {
            if (copy.getBook() == book) {
                result.add(copy);
            }
        }
        return result;
    }

    public void save(Copy copy) {
        copies.add(copy);
    }

    public void update(Copy copy) {
        // Nothing to do in memory: the object in the list is the one that was changed.
        // A database would write the row here.
    }

    public void deleteAll() {
        copies.clear();
    }
}
