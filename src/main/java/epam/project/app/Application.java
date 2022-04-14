package epam.project.app;


import epam.project.app.infra.web.Server;
import epam.project.app.infra.config.app.ConfigApp;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Application {
    private final Server server;

    public static void main(String[] args) {
        ConfigApp configApp = new ConfigApp();
        Application app = configApp.config();
        app.run();
    }

    private void run() {
        server.start();
    }
}
