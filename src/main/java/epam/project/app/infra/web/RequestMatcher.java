package epam.project.app.infra.web;

import epam.project.app.logic.entity.user.User;
import epam.project.app.logic.entity.user.UserRole;

import java.util.Arrays;
import java.util.List;

public class RequestMatcher {
    String pathRegex;
    List<UserRole> accessRoles;

    public RequestMatcher(String pathRegex, UserRole... roles) {
        this.pathRegex = pathRegex;
        this.accessRoles = Arrays.asList(roles);
    }

    public boolean pathMatch(String path) {
        return path.matches(pathRegex);
    }

    public boolean hasRole(User user) {
        return user != null && accessRoles.contains(user.getUserRole());
    }
}
