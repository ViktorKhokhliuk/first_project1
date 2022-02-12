package epam.project.app.logic.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Filter {
    @Getter
    private final Map<String, String> filterParameters;
}
