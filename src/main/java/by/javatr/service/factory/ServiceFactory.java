package by.javatr.ibank.service.factory;

import by.javatr.ibank.service.AdminService;
import by.javatr.ibank.service.AuthenticationService;
import by.javatr.ibank.service.UserService;
import by.javatr.ibank.service.impl.AdminServiceImpl;
import by.javatr.ibank.service.impl.AuthenticationServiceImpl;
import by.javatr.ibank.service.impl.UserServiceImpl;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final AdminService adminService = new AdminServiceImpl();

    private final AuthenticationService authenticationService =
                new AuthenticationServiceImpl();

    private final UserService userService = new UserServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserService getUserService() {
        return userService;
    }
}
