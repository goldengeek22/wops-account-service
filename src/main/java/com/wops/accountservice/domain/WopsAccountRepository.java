package com.wops.accountservice.domain;

import java.util.Optional;

/**
 * @author Alexandre AMEVOR
 */
public interface WopsAccountRepository {
    Iterable<WopsAccount> getAll();
    WopsAccount save(WopsAccount wopsAccount);
    WopsAccount update(WopsAccount wopsAccount);
    Optional<WopsAccount> findByNumber(String number);
    void delete(String number);
    boolean existsByNumber(String number);
}
