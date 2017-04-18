package rk.service

import org.junit.Before
import org.junit.Test
import rk.dto.BookingRequestEntry

import java.time.LocalDateTime
import java.time.Month

import static org.junit.Assert.assertEquals

class BookingServiceImplTest {

    private BookingServiceImpl bookingService;

    @Before
    void setUp() throws Exception {
        bookingService = new BookingServiceImpl();
    }

    @Test
    void whenThereNoOverlappedNoRequestShouldNotIgnored() throws Exception {
        def requests = [new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 8, 0),
                "",
                LocalDateTime.of(0, Month.MARCH, 1, 12, 0),
                4),
                        new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 0, 0),
                                "",
                                LocalDateTime.of(0, Month.MARCH, 1, 4, 0),
                                4),
                        new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 9, 0),
                                "",
                                LocalDateTime.of(0, Month.MARCH, 1, 8, 0),
                                4),
        ]
        def result = bookingService.excludeOverlappedRequests(requests)

        assertEquals(3, result.size())
    }

    @Test
    void excludeOverlappedIgnoreHigher() throws Exception {
        def requests = [new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 7, 0),
                "EMP1",
                LocalDateTime.of(0, Month.MARCH, 1, 12, 0),
                4),
                        new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 8, 0),
                                "EMP2",
                                LocalDateTime.of(0, Month.MARCH, 1, 13, 0),
                                4)
        ]
        def result = bookingService.excludeOverlappedRequests(requests)

        assertEquals(1, result.size())
        assertEquals('EMP1', result[0].employerId)
    }

    @Test
    void notExcludeNotOverlappedHigherEntry() throws Exception {
        def requests = [new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 7, 0),
                "EMP1",
                LocalDateTime.of(0, Month.MARCH, 1, 16, 0),
                4),
                        new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 8, 0),
                                "EMP2",
                                LocalDateTime.of(0, Month.MARCH, 1, 12, 0),
                                4)
        ]
        def result = bookingService.excludeOverlappedRequests(requests)

        assertEquals(2, result.size())
        assertEquals('EMP2', result[0].employerId)
        assertEquals('EMP1', result[1].employerId)
    }

    @Test
    void excludeOverlappedIgnoreLess() throws Exception {
        def requests = [new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 7, 0),
                "EMP1",
                LocalDateTime.of(0, Month.MARCH, 1, 12, 0),
                4),
                        new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 8, 0),
                                "EMP2",
                                LocalDateTime.of(0, Month.MARCH, 1, 11, 0),
                                4)
        ]
        def result = bookingService.excludeOverlappedRequests(requests)

        assertEquals(1, result.size())
        assertEquals('EMP1', result[0].employerId)
    }

    @Test
    void notExcludeNotOverlappedLessEntry() throws Exception {
        def requests = [new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 7, 0),
                "EMP1",
                LocalDateTime.of(0, Month.MARCH, 1, 12, 0),
                4),
                        new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 8, 0),
                                "EMP2",
                                LocalDateTime.of(0, Month.MARCH, 1, 16, 0),
                                4)
        ]
        def result = bookingService.excludeOverlappedRequests(requests)

        assertEquals(2, result.size())
        assertEquals('EMP1', result[0].employerId)
        assertEquals('EMP2', result[1].employerId)
    }

    @Test
    void ifRequestsOverlapedTheEarliestShouldStayed() throws Exception {
        def requests = [new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 8, 0),
                "EMP1",
                LocalDateTime.of(0, Month.MARCH, 1, 11, 0),
                4),
                        new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 6, 0),
                                "EMP2",
                                LocalDateTime.of(0, Month.MARCH, 1, 4, 0),
                                4),
                        new BookingRequestEntry(LocalDateTime.of(0, Month.MARCH, 1, 7, 0),
                                "EMP3",
                                LocalDateTime.of(0, Month.MARCH, 1, 7, 0),
                                4),
        ]
        def result = bookingService.excludeOverlappedRequests(requests)

        assertEquals(2, result.size())
        assertEquals("EMP2", result[0].employerId)
        assertEquals("EMP1", result[1].employerId)
    }
}