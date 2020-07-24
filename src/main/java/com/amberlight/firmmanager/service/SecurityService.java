package com.amberlight.firmmanager.service;

/**
 * Used as a facade so all controllers have a single point of
 * entry in the security functions of the persistence level.
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
