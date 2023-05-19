package com.wops.accountservice.persistence;

import com.wops.accountservice.domain.WopsAccount;
import com.wops.accountservice.domain.WopsAccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexandre AMEVOR
 */

@Repository
public class InMemoryWopsAccountRepository implements WopsAccountRepository {

    private final Map<String, WopsAccount> wopsAccounts = new ConcurrentHashMap<>();

    @Override
    public Iterable<WopsAccount> getAll() {
        return wopsAccounts.values();
    }

    @Override
    public WopsAccount save(WopsAccount wopsAccount) {
        wopsAccounts.put(wopsAccount.number(), wopsAccount);
        return wopsAccount;
    }

    @Override
    public WopsAccount update(WopsAccount wopsAccount) {
        wopsAccounts.put(wopsAccount.number(), wopsAccount);
        return wopsAccount;
    }

    @Override
    public Optional<WopsAccount> findByNumber(String number) {
        return wopsAccounts.get(number) == null ? Optional.empty() : Optional.of(wopsAccounts.get(number));
    }

    @Override
    public void delete(String number) {
        wopsAccounts.remove(number);
    }

    @Override
    public boolean existsByNumber(String number) {
        return wopsAccounts.containsKey(number);
    }
}
