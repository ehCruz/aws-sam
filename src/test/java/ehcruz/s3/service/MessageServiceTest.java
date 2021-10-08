package ehcruz.s3.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@QuarkusTest
class MessageServiceTest {

    private final static LocalDate LOCAL_DATE = LocalDate.of(1995, 06, 03);

    @InjectMocks
    MessageService service;

    @Mock
    Clock clock;

    private Clock fixedClock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    @Test
    void testLocalDate() {
        assertEquals(LocalDate.now(clock), service.localDate());
    }

}