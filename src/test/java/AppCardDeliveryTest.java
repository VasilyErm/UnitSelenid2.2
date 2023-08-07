import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void appCardDeliveryPositiveTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        String currentDate = generateDate(3, "dd.MM.yyyy");
        sleep(2000);
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, Keys.HOME), Keys.DELETE);
        sleep(2000);
        $("[data-test-id='date'] .input__control").sendKeys(currentDate);
        sleep(2000);
        $("[data-test-id='name'] .input__control").setValue("Иванов Иван");
        $("[data-test-id='phone'] .input__control").setValue("+79111231212");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }

    @Test
    public void appCardDeliveryPositiveMeetingInAWeek() {
        open("http://localhost:9999");
        String city = "Санкт-Петербург";
        $("[data-test-id='city'] .input__control").setValue("Са");
        $$(".menu-item__control").findBy(text(city)).click();
        $(".icon-button__content").click();
        int minimalMeetDay = 3;
        int meetInNextWeek = 7;
        if (!generateDate(minimalMeetDay, "MM").equals(generateDate(meetInNextWeek, "MM"))) {
            $("[data-step=\"1\"]").click();
        }
        $$(".calendar__day").findBy(text(generateDate(meetInNextWeek, "d"))).click();
        $("[data-test-id='name'] .input__control").setValue("Иванов Иван");
        $("[data-test-id='phone'] .input__control").setValue("+79111231212");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + generateDate(meetInNextWeek, "dd.MM.yyyy")));
    }
}