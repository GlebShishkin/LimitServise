package ru.stepup.payservise.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.stepup.limitservise.dto.SetLimitDto;
import ru.stepup.limitservise.dto.UserLimitDto;
import ru.stepup.payservise.servise.PayProductServise;
//import ru.stepup.prodservisejpa.dto.PaymentDto;
//import ru.stepup.prodservisejpa.dto.AccountSaldoDto;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PayProductController {

    private PayProductServise payProductServise;

    public PayProductController(PayProductServise payProductServise) {
        this.payProductServise = payProductServise;
    }

    // запрос лимитов пользователей
    @GetMapping("/userlimits")
    public List<UserLimitDto> getUserLimits() {
        return payProductServise.getUserLimits();
    }

    // запрос лимита пользователя
    @GetMapping("/userlimit")
    public UserLimitDto getUserLimitById(@RequestParam Integer userid) throws SQLException {
        return payProductServise.getUserLimit(userid);
    }

    // списание (SetLimitDto.typ = 0)/восстановление (SetLimitDto.typ = 1) лимита
    @PostMapping(value = "/setlimit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void setLimit(@RequestBody SetLimitDto setLimitDto) {
        payProductServise.setLimit(setLimitDto);
    }
}
