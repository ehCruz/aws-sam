package ehcruz.s3.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.time.Clock;

public class ClockConfig {

    @Produces
    @ApplicationScoped
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }
}
