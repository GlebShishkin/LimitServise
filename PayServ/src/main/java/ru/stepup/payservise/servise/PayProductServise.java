package ru.stepup.payservise.servise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stepup.limitservise.dto.SetLimitDto;
import ru.stepup.limitservise.dto.UserLimitDto;
import ru.stepup.payservise.config.properties.ApplicationProperties;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class PayProductServise {

    private RestTemplate restTemplate;
    private static ApplicationProperties applicationProperties;

    @Autowired
    public PayProductServise(RestTemplate restTemplate, ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
    }

    // запрос лимитов пользователей
    public List<UserLimitDto> getUserLimits() {

        ResponseEntity<UserLimitDto[]> response =
                restTemplate.getForEntity("/api/userlimits", UserLimitDto[].class);
        return Arrays.asList(response.getBody());
    }

    // запрос лимита пользователя
    public UserLimitDto getUserLimit(Integer userId) {

        ResponseEntity<UserLimitDto> response =
                restTemplate.getForEntity("/api/userlimit?userid=" + userId, UserLimitDto.class);
        return response.getBody();
    }

    // списание (SetLimitDto.typ = 0)/восстановление (SetLimitDto.typ = 1) лимита
    public void setLimit(SetLimitDto setLimitDto)  {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SetLimitDto> request = new HttpEntity<>(setLimitDto, headers);

//        log.info("########## Servise: setLimitDto.amount() = " + setLimitDto.amount());
        restTemplate.exchange(
                "/api/setlimit",
                HttpMethod.PUT,
                request,
                SetLimitDto.class);
    }
}
