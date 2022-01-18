package epam.project.app.infra.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.catalina.startup.Tomcat;

public class Server {
    @Getter
    private final Tomcat tomcat;

    public Server() {
        tomcat = new Tomcat();
    }

    @SneakyThrows
    public void start(){
        tomcat.getServer();
        tomcat.getConnector();
        tomcat.getServer().start();
        tomcat.getServer().await();
    }
}
