package co.drytools.backend;

import java.util.Objects;

public class TestEmailMessage {
    private final String sender;
    private final String receiver;
    private final String subject;

    public TestEmailMessage(String sender, String receiver, String subject) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestEmailMessage that = (TestEmailMessage) o;

        if (!Objects.equals(sender, that.sender)) return false;
        if (!Objects.equals(receiver, that.receiver)) return false;
        return Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TestEmailMessage[" + "this.sender=" + this.sender + ", this.receiver=" + this.receiver + ", this.subject=" + this.subject + "]";
    }
}
