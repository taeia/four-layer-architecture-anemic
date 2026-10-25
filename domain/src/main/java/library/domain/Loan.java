package library.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan {

    private final Copy copy;
    private final Member member;
    private LocalDate dueDate;

    public Loan(Copy copy, Member member, LocalDate dueDate) {
        this.copy = copy;
        this.member = member;
        this.dueDate = dueDate;
    }

    public Copy getCopy() {
        return copy;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return copy.getNumber() + " (" + copy.getBook().getTitle() + "), " + member.getName()
                + ", due " + dueDate.format(DateTimeFormatter.ofPattern("d MMMM"));
    }
}
