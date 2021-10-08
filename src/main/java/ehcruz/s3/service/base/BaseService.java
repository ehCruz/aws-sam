package ehcruz.s3.service.base;

import javax.inject.Inject;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class BaseService {

    @Inject
    Clock clock;

    protected LocalDate getLocalDate() {
        return LocalDate.now(clock);
    }

    protected LocalTime getLocalTime() {
        return LocalTime.now(clock);
    }
}
