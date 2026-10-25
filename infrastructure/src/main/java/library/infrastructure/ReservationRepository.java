package library.infrastructure;

import java.util.ArrayList;
import java.util.List;

import library.domain.Book;
import library.domain.Member;
import library.domain.Reservation;

public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    public List<Reservation> findByBook(Book book) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getBook() == book) {
                result.add(reservation);
            }
        }
        return result;
    }

    public Reservation findFirstByBook(Book book) {
        for (Reservation reservation : reservations) {
            if (reservation.getBook() == book) {
                return reservation;
            }
        }
        return null;
    }

    public Reservation findByBookAndMember(Book book, Member member) {
        for (Reservation reservation : reservations) {
            if (reservation.getBook() == book && reservation.getMember() == member) {
                return reservation;
            }
        }
        return null;
    }

    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void deleteAll() {
        reservations.clear();
    }
}
