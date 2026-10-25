package library.application;

import java.util.List;

import library.domain.Member;
import library.infrastructure.MemberRepository;
import library.infrastructure.Notifier;

/**
 * Paying fines. Also the questions the screens ask about members and the messages sent to them.
 */
public class MemberService {

    private final MemberRepository memberRepository;
    private final Notifier notifier;

    public MemberService(MemberRepository memberRepository, Notifier notifier) {
        this.memberRepository = memberRepository;
        this.notifier = notifier;
    }

    // ---- operations ----

    /**
     * Returns the amount paid.
     */
    public double pay(Member member) {
        if (member.getDebt() == 0) {
            throw new LibraryException(member.getName() + " owes nothing.");
        }

        double paid = member.getDebt();
        member.setDebt(0);
        memberRepository.update(member);
        return paid;
    }

    // ---- questions the screens ask ----

    public List<Member> members() {
        return memberRepository.findAll();
    }

    public List<String> notifications() {
        return notifier.sent();
    }
}
