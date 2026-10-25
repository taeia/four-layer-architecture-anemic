package library.application;

import java.util.List;

import library.domain.Book;
import library.domain.Copy;
import library.domain.Loan;
import library.domain.Member;
import library.domain.Reservation;
import library.infrastructure.BookRepository;
import library.infrastructure.CopyRepository;
import library.infrastructure.LoanRepository;
import library.infrastructure.ReservationRepository;

/**
 * Reserving. Also the questions the screens ask about books and reservations.
 */
public class ReservationService {

    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(BookRepository bookRepository, CopyRepository copyRepository,
                              LoanRepository loanRepository, ReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
    }

    // ---- operations ----

    /**
     * Returns the position of the new reservation in the queue, counting from 1.
     */
    public int reserve(Member member, Book book) {
        for (Copy copy : copyRepository.findByBook(book)) {
            if (copy.getStatus() == Copy.Status.AVAILABLE) {
                throw new LibraryException("A copy of " + book.getTitle() + " is available.");
            }
        }
        for (Loan loan : loanRepository.findByMember(member)) {
            if (loan.getCopy().getBook() == book) {
                throw new LibraryException(member.getName() + " has a copy of " + book.getTitle() + " on loan.");
            }
        }
        if (reservationRepository.findByBookAndMember(book, member) != null) {
            throw new LibraryException(member.getName() + " already has a reservation for " + book.getTitle() + ".");
        }

        reservationRepository.save(new Reservation(book, member));
        return reservationRepository.findByBook(book).size();
    }

    // ---- questions the screens ask ----

    public List<Book> books() {
        return bookRepository.findAll();
    }

    public List<Reservation> reservations() {
        return reservationRepository.findAll();
    }

    public int queuePosition(Reservation reservation) {
        return reservationRepository.findByBook(reservation.getBook()).indexOf(reservation) + 1;
    }
}
