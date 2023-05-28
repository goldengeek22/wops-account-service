package com.wops.accountservice.web;

import com.wops.accountservice.config.WopsAccountPropertiesConfig;
import com.wops.accountservice.domain.WopsAccount;
import com.wops.accountservice.domain.WopsAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alexandre AMEVOR
 */

@RestController
@RequestMapping("wopsAccounts")
public class WopsAccountController {

    private final WopsAccountService wopsAccountService;

    private final WopsAccountPropertiesConfig propertiesConfig;

    public WopsAccountController(WopsAccountService wopsAccountService, WopsAccountPropertiesConfig propertiesConfig) {
        this.wopsAccountService = wopsAccountService;
        this.propertiesConfig = propertiesConfig;
    }

    @GetMapping("/hello")
    public String hello(){
        return propertiesConfig.getGreeting();
    }

    @GetMapping("/generateNumber")
    @ResponseStatus(HttpStatus.OK)
    public String generateWopsAccountNumber(){
        return wopsAccountService.generateAccountNumber();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<WopsAccount> getAll(){
        return wopsAccountService.getAll();
    }

    @GetMapping("{number}")
    @ResponseStatus(HttpStatus.OK)
    public WopsAccount findWopsAccountByNumber(@PathVariable String number){
        return wopsAccountService.findByNumber(number);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WopsAccount createWopsAccount(@Valid @RequestBody WopsAccount wopsAccount){
        return wopsAccountService.createWopsAccount(wopsAccount);
    }

    @PatchMapping("/lock/{number}")
    @ResponseStatus(HttpStatus.OK)
    public WopsAccount lockWopsAccount(@PathVariable String number){
        return wopsAccountService.lockWopsAccount(number);
    }

    @PatchMapping("/unlock/{number}")
    @ResponseStatus(HttpStatus.OK)
    public WopsAccount unlockWopsAccount(@PathVariable String number){
        return wopsAccountService.unlockWopsAccount(number);
    }

    @PatchMapping("/activate/{number}")
    @ResponseStatus(HttpStatus.OK)
    public WopsAccount activateAccount(@PathVariable String number){
        return wopsAccountService.activateWopsAccount(number);
    }

    @PatchMapping("/deactivate/{number}")
    @ResponseStatus(HttpStatus.OK)
    public WopsAccount deactivateAccount(@PathVariable String number){
        return wopsAccountService.deactivateWopsAccount(number);
    }
}
