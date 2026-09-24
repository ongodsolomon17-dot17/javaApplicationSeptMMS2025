package hospital.services;

import hospital.dao.UserDAO;
import hospital.models.User;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.isEmpty()) {
            return null;
        }

        return userDAO.authenticate(username.trim(), password);
    }
}