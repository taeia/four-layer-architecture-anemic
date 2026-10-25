package library.domain;

public class Member {

    private final String name;
    private double debt;

    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    @Override
    public String toString() {
        return name;
    }
}
