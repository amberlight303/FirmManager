package com.amberlight.firmmanager.service;

/**
 * Used as a facade so all controllers have a single point of
 * entry in the security functions of the persistence level.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
