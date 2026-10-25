package library.application;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import library.domain.Copy;
import library.domain.Loan;
import library.domain.Member;
import library.domain.Reservation;
import library.infrastructure.CopyRepository;
import library.infrastructure.LoanRepository;
import library.infrastructure.MemberRepository;
import library.infrastructure.Notifier;
import library.infrastructure.ReservationRepository;

/**
 * Borrowing, returning, and renewing. Also the questions the screens ask about loans and copies.
 */
public class LoanService {

    private final CopyRepository copyRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final Notifier notifier;

    public LoanService(CopyRepository copyRepository, MemberRepository memberRepository, LoanRepository loanRepository,
                       ReservationRepository reservationRepository, Notifier notifier) {
        this.copyRepository = copyRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
        this.notifier = notifier;
    }

    // ---- operations ----

    public Loan borrow(Member member, Copy copy) {
        if (copy.getStatus() == Copy.Status.ON_LOAN) {
            throw new LibraryException(copy.getNumber() + " is on loan.");
        }
        if (copy.getStatus() == Copy.Status.ON_HOLD && copy.getHeldFor() != member) {
            throw new LibraryException(copy.getNumber() + " is on hold for " + copy.getHeldFor().getName() + ".");
        }
        if (loanRepository.findByMember(member).size() >= 3) {
            throw new LibraryException(member.getName() + " has reached the loan limit of 3.");
        }
        if (member.getDebt() > 0) {
            throw new LibraryException(member.getName() + " owes "
                    + String.format(Locale.ENGLISH, "%.2f EUR", member.getDebt()) + ".");
        }

        Loan loan = new Loan(copy, member, Clock.today().plusDays(14));
        loanRepository.save(loan);
        copy.setStatus(Copy.Status.ON_LOAN);
        if (copy.getHeldFor() == member) {
            copy.setHeldFor(null);
            reservationRepository.delete(reservationRepository.findByBookAndMember(copy.getBook(), member));
        }
        copyRepository.update(copy);
        return loan;
    }

    /**
     * Returns the number of days the copy was late, or 0 if it was on time.
     */
    public long returnCopy(Loan loan) {
        Member member = loan.getMember();
        long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), Clock.today());
        if (daysLate > 0) {
            member.setDebt(member.getDebt() + daysLate * 0.50);
            memberRepository.update(member);
        }
        loanRepository.delete(loan);

        Copy copy = loan.getCopy();
        Reservation first = reservationRepository.findFirstByBook(copy.getBook());
        if (first != null) {
            copy.setStatus(Copy.Status.ON_HOLD);
            copy.setHeldFor(first.getMember());
            notifier.send(first.getMember(), copy.getBook().getTitle() + " is waiting for you.");
        } else {
            copy.setStatus(Copy.Status.AVAILABLE);
            copy.setHeldFor(null);
        }
        copyRepository.update(copy);
        return Math.max(daysLate, 0);
    }

    public void renew(Loan loan) {
        Member member = loan.getMember();
        if (member.getDebt() > 0) {
            throw new LibraryException(member.getName() + " owes "
                    + String.format(Locale.ENGLISH, "%.2f EUR", member.getDebt()) + ".");
        }
        if (reservationRepository.findFirstByBook(loan.getCopy().getBook()) != null) {
            throw new LibraryException(loan.getCopy().getBook().getTitle() + " is reserved.");
        }
        if (Clock.today().isAfter(loan.getDueDate())) {
            throw new LibraryException("The loan is overdue.");
        }

        loan.setDueDate(loan.getDueDate().plusDays(14));
        loanRepository.update(loan);
    }

    // ---- questions the screens ask ----

    public List<Copy> copies() {
        return copyRepository.findAll();
    }

    public List<Loan> loans() {
        return loanRepository.findAll();
    }

    public Loan loanOf(Copy copy) {
        return loanRepository.findByCopy(copy);
    }

    public int loanCount(Member member) {
        return loanRepository.findByMember(member).size();
    }
}
