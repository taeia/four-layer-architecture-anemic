package library.infrastructure;

import java.util.ArrayList;
import java.util.List;

import library.domain.Copy;
import library.domain.Loan;
import library.domain.Member;

public class LoanRepository {

    private final List<Loan> loans = new ArrayList<>();

    public List<Loan> findAll() {
        return new ArrayList<>(loans);
    }

    public List<Loan> findByMember(Member member) {
        List<Loan> result = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getMember() == member) {
                result.add(loan);
            }
        }
        return result;
    }

    public Loan findByCopy(Copy copy) {
        for (Loan loan : loans) {
            if (loan.getCopy() == copy) {
                return loan;
            }
        }
        return null;
    }

    public void save(Loan loan) {
        loans.add(loan);
    }

    public void update(Loan loan) {
        // Nothing to do in memory: the object in the list is the one that was changed.
        // A database would write the row here.
    }

    public void delete(Loan loan) {
        loans.remove(loan);
    }

    public void deleteAll() {
        loans.clear();
    }
}
