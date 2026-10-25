package library.infrastructure;

import java.util.ArrayList;
import java.util.List;

import library.domain.Member;

/**
 * Sends messages to members. Evans puts sending messages in the infrastructure layer,
 * next to persistence, so this is where it lives. Sending still means adding a line
 * to a list that the main window shows; a real channel would go here.
 */
public class Notifier {

    private final List<String> sent = new ArrayList<>();

    public void send(Member member, String message) {
        sent.add(member.getName() + ": " + message);
    }

    public List<String> sent() {
        return new ArrayList<>(sent);
    }

    public void clear() {
        sent.clear();
    }
}
