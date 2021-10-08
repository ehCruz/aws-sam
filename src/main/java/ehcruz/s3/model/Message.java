package ehcruz.s3.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Message {

    private String content;
    private LocalDate sentDate;
    private LocalTime sentTime;

    public Message(String content, LocalDate sentDate, LocalTime sentTime) {
        this.content = content;
        this.sentDate = sentDate;
        this.sentTime = sentTime;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public LocalTime getSentTime() {
        return sentTime;
    }
}
