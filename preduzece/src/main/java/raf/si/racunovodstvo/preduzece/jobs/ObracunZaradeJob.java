package raf.si.racunovodstvo.preduzece.jobs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raf.si.racunovodstvo.preduzece.services.impl.ObracunZaradeService;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ObracunZaradeJob {
    private final ObracunZaradeService obracunZaradeService;

    /**
     * dayOfMonth from 1 to 31
     */
    @Getter @Setter
    private int dayOfMonth = JobConstants.DEFAULT_DAY_OF_MONTH;

    @Autowired
    public ObracunZaradeJob(ObracunZaradeService obracunZaradeService) {
        this.obracunZaradeService = obracunZaradeService;
        start();
    }

    public void start() {
        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ZonedDateTime now = ZonedDateTime.now();
                long delay = now.until(now.plusMonths(1), ChronoUnit.MILLIS);

                try {
                    // Provera sa specifikacijom mozda treba now.minusMonths(1) ako je potrebno za prosli mesec
                    obracunZaradeService.makeObracunZarade(Date.from(now.toInstant()));
                } finally {
                    executor.schedule(this, delay, TimeUnit.MILLISECONDS);
                }
            }
        };

        ZonedDateTime dateTime = ZonedDateTime.now();
        if (dateTime.getDayOfMonth() >= dayOfMonth) {
            dateTime = dateTime.plusMonths(1);
        }
        dateTime = dateTime.withDayOfMonth(dayOfMonth);
        executor.schedule(task,
                ZonedDateTime.now().until(dateTime, ChronoUnit.MILLIS),
                TimeUnit.MILLISECONDS);
    }
}
