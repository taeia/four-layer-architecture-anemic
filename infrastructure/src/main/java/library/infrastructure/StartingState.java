package library.infrastructure;

import library.domain.Book;
import library.domain.Copy;
import library.domain.Member;

/**
 * The starting state of Chapter 0. In a real system this would be a SQL script.
 */
public class StartingState {

    public static void load(BookRepository bookRepository, CopyRepository copyRepository,
                            MemberRepository memberRepository, LoanRepository loanRepository,
                            ReservationRepository reservationRepository, Notifier notifier) {
        bookRepository.deleteAll();
        copyRepository.deleteAll();
        memberRepository.deleteAll();
        loanRepository.deleteAll();
        reservationRepository.deleteAll();
        notifier.clear();

        memberRepository.save(new Member("John"));
        memberRepository.save(new Member("Jane"));
        memberRepository.save(new Member("Mary"));

        Book hobbit = new Book("978-0-547-92822-7", "The Hobbit");
        Book pride = new Book("978-0-14-143951-8", "Pride and Prejudice");
        Book orwell = new Book("978-0-451-52493-5", "Nineteen Eighty-Four");
        bookRepository.save(hobbit);
        bookRepository.save(pride);
        bookRepository.save(orwell);

        copyRepository.save(new Copy("C1", hobbit));
        copyRepository.save(new Copy("C2", pride));
        copyRepository.save(new Copy("C3", pride));
        copyRepository.save(new Copy("C4", orwell));
        copyRepository.save(new Copy("C5", orwell));
    }
}
