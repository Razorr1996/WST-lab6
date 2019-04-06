package ru.basa62.wst.lab4;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import ru.basa62.wst.lab4.rs.BooksResource;
import ru.basa62.wst.lab4.util.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class App {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration("config.properties");
        String scheme = conf.get("scheme", "http");
        String host = conf.get("host", "localhost");
        String port = conf.get("port", "8081");
        String baseUrl = scheme + "://" + host + ":" + port;

        String appName = conf.get("app.name", "standalone-lab4");
        String appUrl = baseUrl + "/" + appName;

        DataSource dataSource = initDataSource();
        BooksResource.STATIC_DAO = new BooksDAO(dataSource);

        ClassNamesResourceConfig resourceConfig = new ClassNamesResourceConfig(BooksResource.class);

        log.info("Starting");
        HttpServer httpServer = GrizzlyServerFactory.createHttpServer(appUrl, resourceConfig);
        httpServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(httpServer::stop));
        log.info("All started");

        System.in.read();
    }

    @SneakyThrows
    private static DataSource initDataSource() {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream("datasource.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        HikariConfig hikariConfig = new HikariConfig(properties);
        return new HikariDataSource(hikariConfig);
    }
}
