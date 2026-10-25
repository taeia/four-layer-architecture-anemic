package library.domain;

public class Copy {

    private final String number;
    private final Book book;
    private Status status = Status.AVAILABLE;
    private Member heldFor;

    public Copy(String number, Book book) {
        this.number = number;
        this.book = book;
    }

    public String getNumber() {
        return number;
    }

    public Book getBook() {
        return book;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Member getHeldFor() {
        return heldFor;
    }

    public void setHeldFor(Member heldFor) {
        this.heldFor = heldFor;
    }

    @Override
    public String toString() {
        return number + " (" + book.getTitle() + ")";
    }

    public enum Status {
        AVAILABLE("available"), ON_LOAN("on loan"), ON_HOLD("on hold");

        private final String label;

        Status(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
