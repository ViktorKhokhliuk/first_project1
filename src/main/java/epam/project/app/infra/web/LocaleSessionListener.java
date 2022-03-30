package epam.project.app.infra.web;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Locale;

@Log4j2
@RequiredArgsConstructor
public class LocaleSessionListener implements HttpSessionListener {

    private final List<Locale> locales;
    private final Locale selectedLocale;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        session.setAttribute("locales", locales);
        session.setAttribute("selectedLocale", selectedLocale);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.info("session destroyed");
    }
}

