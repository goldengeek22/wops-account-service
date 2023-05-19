package com.wops.accountservice.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Alexandre AMEVOR
 */

@Service
public class WopsAccountService {

    private final WopsAccountRepository wopsAccountRepository;

    public WopsAccountService(WopsAccountRepository wopsAccountRepository) {
        this.wopsAccountRepository = wopsAccountRepository;
    }

    public Iterable<WopsAccount> getAll(){
        return wopsAccountRepository.getAll();
    }

    public WopsAccount findByNumber(String number){
        return wopsAccountRepository.findByNumber(number).orElseThrow(()-> new WopsAccountNotFoundException(number));
    }

    public String generateAccountNumber(){
        return UUID.randomUUID().toString();
    }
    public WopsAccount createWopsAccount(WopsAccount wopsAccount){
        if(wopsAccountRepository.existsByNumber(wopsAccount.number())){
            throw new WopsAccountAlreadyExistsException(wopsAccount.number());
        }
        return wopsAccountRepository.save(wopsAccount);
    }

    public WopsAccount lockWopsAccount(String accountNumber){
        WopsAccount wopsAccount = wopsAccountRepository.findByNumber(accountNumber).orElseThrow(()->new WopsAccountNotFoundException(accountNumber));
        WopsAccount lockedAccount = new WopsAccount(accountNumber,wopsAccount.creationDate(), wopsAccount.active(), true);
        wopsAccountRepository.update(lockedAccount);
        return lockedAccount;
    }

    public WopsAccount unlockWopsAccount(String accountNumber){
        WopsAccount wopsAccount = wopsAccountRepository.findByNumber(accountNumber).orElseThrow(()->new WopsAccountNotFoundException(accountNumber));
        WopsAccount unlockedAccount = new WopsAccount(accountNumber,wopsAccount.creationDate(), wopsAccount.active(), false);
        wopsAccountRepository.update(unlockedAccount);
        return unlockedAccount;
    }

    public WopsAccount deactivateWopsAccount(String accountNumber){
        WopsAccount wopsAccount = wopsAccountRepository.findByNumber(accountNumber).orElseThrow(()->new WopsAccountNotFoundException(accountNumber));
        WopsAccount deactivatedAccount = new WopsAccount(accountNumber,wopsAccount.creationDate(),false, wopsAccount.locked());
        wopsAccountRepository.update(deactivatedAccount);
        return deactivatedAccount;
    }

    public WopsAccount activateWopsAccount(String accountNumber){
        WopsAccount wopsAccount = wopsAccountRepository.findByNumber(accountNumber).orElseThrow(()->new WopsAccountNotFoundException(accountNumber));
        WopsAccount activatedAccount = new WopsAccount(accountNumber,wopsAccount.creationDate(),true, wopsAccount.locked());
        wopsAccountRepository.update(activatedAccount);
        return activatedAccount;
    }
}
