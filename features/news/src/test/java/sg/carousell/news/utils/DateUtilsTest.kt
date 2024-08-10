package sg.carousell.news.utils

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import java.time.LocalDateTime
import java.time.ZoneId
import io.kotest.core.spec.style.FunSpec
import io.mockk.unmockkAll
import sg.carousell.news.presentation.utils.DateUtils
import java.time.temporal.ChronoUnit

class DateUtilsTest : FunSpec({

    beforeTest {
        mockkStatic(LocalDateTime::class)
    }

    afterTest {
        unmockkAll()
    }

    fun testRelativeTime(timeDifference: Long, unit: ChronoUnit, expectedString: String) {
        val now = LocalDateTime.of(2023, 5, 10, 12, 0)
        every { LocalDateTime.now() } returns now

        val pastTime = now.minus(timeDifference, unit)
        val timestamp = pastTime.atZone(ZoneId.systemDefault()).toEpochSecond()

        DateUtils.getRelativeTime(timestamp) shouldBe expectedString
    }

    test("getRelativeTime should return correct string for days") {
        testRelativeTime(5, ChronoUnit.DAYS, "5 days ago")
    }

    test("getRelativeTime should return correct string for 1 week") {
        testRelativeTime(1, ChronoUnit.WEEKS, "1 week ago")
    }

    test("getRelativeTime should return correct string for 4 weeks") {
        testRelativeTime(4, ChronoUnit.WEEKS, "4 weeks ago")
    }

    test("getRelativeTime should return correct string for 1 month") {
        testRelativeTime(5, ChronoUnit.WEEKS, "1 month ago")
    }

    test("getRelativeTime should return correct string for 1 year") {
        testRelativeTime(1, ChronoUnit.YEARS, "1 year ago")
    }
})
