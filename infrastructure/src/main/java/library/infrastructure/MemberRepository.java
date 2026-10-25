package library.infrastructure;

import java.util.ArrayList;
import java.util.List;

import library.domain.Member;

public class MemberRepository {

    private final List<Member> members = new ArrayList<>();

    public List<Member> findAll() {
        return new ArrayList<>(members);
    }

    public void save(Member member) {
        members.add(member);
    }

    public void update(Member member) {
        // Nothing to do in memory: the object in the list is the one that was changed.
        // A database would write the row here.
    }

    public void deleteAll() {
        members.clear();
    }
}
