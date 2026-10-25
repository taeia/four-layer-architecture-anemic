package library.domain;

public class Reservation {

    private final Book book;
    private final Member member;

    public Reservation(Book book, Member member) {
        this.book = book;
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }
}
