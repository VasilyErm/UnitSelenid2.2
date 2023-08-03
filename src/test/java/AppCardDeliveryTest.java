
import org.junit.jupiter.api.Test;


import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AppCardDeliveryTest {
    @Test
    public void appCardDeliveryPositiveTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
//        $("[data-test-id='date'] .input__control").setValue("06082023");
        $("[data-test-id='name'] .input__control").setValue("Иванов Иван");
        $("[data-test-id='phone'] .input__control").setValue("+79111231212");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button__text").click();
        $(byText("Успешно!")).shouldBe(visible,Duration.ofSeconds(15));
        $(byText("Встреча успешно забронирована на")).shouldBe(visible);
    }
}
