package epam.project.app.infra.web;

import epam.project.app.infra.web.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DispatcherRequest {
    private final List<Placeholder> placeholders;

    public ModelAndView processingRequest(HttpServletRequest req) {
        return placeholders.stream()
                .filter(placeholder -> placeholder.getMethod().equals(req.getMethod()))
                .filter(placeholder -> placeholder.getUrl().equals(req.getHttpServletMapping().getMatchValue()))
                .findFirst()
                .map(Placeholder::getProcess)
                .map(function -> function.apply(req))
                .orElseThrow(() -> new AppException("cannot process request: " + req.getRequestURL()));
    }

}
