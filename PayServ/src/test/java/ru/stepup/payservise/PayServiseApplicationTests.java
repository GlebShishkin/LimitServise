package ru.stepup.payservise;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.stepup.limitservise.dto.SetLimitDto;
import ru.stepup.limitservise.dto.UserLimitDto;
import ru.stepup.limitservise.enumerator.DirectionType;
import java.math.BigDecimal;
import java.net.URISyntaxException;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PayServiseApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Тест восстановления лимита")
    void restoreLimit() throws URISyntaxException {
        final String postUrl = "http://localhost:8080/api/setlimit";
        final String getUrl = "http://localhost:8080/api/userlimit?userid=";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final Integer userId = 1;
        final BigDecimal sumAmount = new BigDecimal(500);

        // запрашиваем начальныую сумму лимита
        BigDecimal sumInitLimit = new BigDecimal(String.valueOf(restTemplate.getForEntity(getUrl + userId, UserLimitDto.class).getBody().limit()));
        log.info("начальныая сумма лимита = " + sumInitLimit);

        // списываем лимит (на сумму sumAmount)
        SetLimitDto debitLimitDto = new SetLimitDto(userId, sumAmount, DirectionType.debiting);
        restTemplate.exchange(postUrl, HttpMethod.PUT, new HttpEntity<>(debitLimitDto, headers), SetLimitDto.class);

        // проверяем изменение лимита после списания (на сумму sumAmount)
        Assertions.assertEquals(sumInitLimit.subtract(sumAmount)
                , restTemplate.getForEntity(getUrl + userId, UserLimitDto.class).getBody().limit());

        // восстанавливаем лимит (на сумму sumAmount)
        SetLimitDto creditLimitDto = new SetLimitDto(userId, sumAmount, DirectionType.crediting);
        restTemplate.exchange(postUrl, HttpMethod.PUT, new HttpEntity<>(creditLimitDto, headers), SetLimitDto.class);

        // проверяем восстановление лимита после списания (на сумму sumAmount)
        Assertions.assertEquals(sumInitLimit
                , restTemplate.getForEntity(getUrl + userId, UserLimitDto.class).getBody().limit());
    }
}
