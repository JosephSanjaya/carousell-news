package sg.carousell.news.presentation.utils

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import java.time.LocalDateTime
import java.time.ZoneId
import io.kotest.core.spec.style.FunSpec
import io.mockk.unmockkAll

class DateUtilsTest : FunSpec({

    beforeTest {
        mockkStatic(LocalDateTime::class)
    }

    afterTest {
        unmockkAll()
    }

    test("getRelativeTimeString should return correct string for days") {
        val now = LocalDateTime.of(2023, 5, 10, 12, 0)
        every { LocalDateTime.now() } returns now

        val fiveDaysAgo = now.minusDays(5)
        val timestamp = fiveDaysAgo.atZone(ZoneId.systemDefault()).toEpochSecond()

        DateUtils.getRelativeTimeString(timestamp) shouldBe "5 days ago"
    }

    test("getRelativeTimeString should return correct string for 1 week") {
        val now = LocalDateTime.of(2023, 5, 10, 12, 0)
        every { LocalDateTime.now() } returns now

        val oneWeekAgo = now.minusWeeks(1)
        val timestamp = oneWeekAgo.atZone(ZoneId.systemDefault()).toEpochSecond()

        DateUtils.getRelativeTimeString(timestamp) shouldBe "1 week ago"
    }

    test("getRelativeTimeString should return correct string for 4 weeks") {
        val now = LocalDateTime.of(2023, 5, 10, 12, 0)
        every { LocalDateTime.now() } returns now

        val fourWeeksAgo = now.minusWeeks(4)
        val timestamp = fourWeeksAgo.atZone(ZoneId.systemDefault()).toEpochSecond()

        DateUtils.getRelativeTimeString(timestamp) shouldBe "4 weeks ago"
    }

    test("getRelativeTimeString should return correct string for 1 month") {
        val now = LocalDateTime.of(2023, 5, 10, 12, 0)
        every { LocalDateTime.now() } returns now

        val fiveWeeksAgo = now.minusWeeks(5)
        val timestamp = fiveWeeksAgo.atZone(ZoneId.systemDefault()).toEpochSecond()

        DateUtils.getRelativeTimeString(timestamp) shouldBe "1 month ago"
    }

    test("getRelativeTimeString should return correct string for 1 year") {
        val now = LocalDateTime.of(2023, 5, 10, 12, 0)
        every { LocalDateTime.now() } returns now

        val oneYearAgo = now.minusYears(1)
        val timestamp = oneYearAgo.atZone(ZoneId.systemDefault()).toEpochSecond()

        DateUtils.getRelativeTimeString(timestamp) shouldBe "1 year ago"
    }
})