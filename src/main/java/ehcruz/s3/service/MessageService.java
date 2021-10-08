package ehcruz.s3.service;

import ehcruz.s3.model.Message;
import ehcruz.s3.service.base.BaseService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Clock;
import java.time.LocalDate;

@ApplicationScoped
public class MessageService extends BaseService {

    @Inject
    Clock clock;

    public Message saveNewMessage() {
        return new Message("A message in the bottle", getLocalDate(), getLocalTime());
    }

    public LocalDate localDate() {
        return LocalDate.now(clock);
    }

}
