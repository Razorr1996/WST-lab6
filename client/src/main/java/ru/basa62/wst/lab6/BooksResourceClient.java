package ru.basa62.wst.lab6;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BooksResourceClient {
    @NonNull
    private final String baseUrl;

    private String authHeader = "";

    public Result<List<BooksEntity>> filter(Long id, String name, String author, String publicDate, String isbn) {
        Client client = Client.create();
        WebResource resource = client.resource(baseUrl);
        resource = addParam(resource, "id", id);
        resource = addParam(resource, "name", name);
        resource = addParam(resource, "author", author);
        resource = addParam(resource, "publicDate", publicDate);
        resource = addParam(resource, "isbn", isbn);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON_TYPE).header("Authorization", authHeader).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            GenericType<String> type = new GenericType<String>() {
            };
            return Result.left(response.getEntity(type));
        }
        GenericType<List<BooksEntity>> type = new GenericType<List<BooksEntity>>() {
        };
        return Result.right(response.getEntity(type));
    }

    public Result<String> create(String name, String author, String publicDate, String isbn) {
        Client client = Client.create();
        WebResource resource = client.resource(baseUrl);
        resource = addParam(resource, "name", name);
        resource = addParam(resource, "author", author);
        resource = addParam(resource, "publicDate", publicDate);
        resource = addParam(resource, "isbn", isbn);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON_TYPE).header("Authorization", authHeader).post(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            GenericType<String> type = new GenericType<String>() {
            };
            return Result.left(response.getEntity(type));
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return Result.right(response.getEntity(type));
    }

    public Result<String> update(Long id, String name, String author, String publicDate, String isbn) {
        Client client = Client.create();
        WebResource resource = client.resource(baseUrl);
        resource = addParam(resource, "id", id);
        resource = addParam(resource, "name", name);
        resource = addParam(resource, "author", author);
        resource = addParam(resource, "publicDate", publicDate);
        resource = addParam(resource, "isbn", isbn);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON_TYPE).header("Authorization", authHeader).put(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            GenericType<String> type = new GenericType<String>() {
            };
            return Result.left(response.getEntity(type));
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return Result.right(response.getEntity(type));
    }

    public Result<String> delete(Long id) {
        Client client = Client.create();
        WebResource resource = client.resource(baseUrl);
        resource = addParam(resource, "id", id);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON_TYPE).header("Authorization", authHeader).delete(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            GenericType<String> type = new GenericType<String>() {
            };
            return Result.left(response.getEntity(type));
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return Result.right(response.getEntity(type));
    }

    private WebResource addParam(WebResource webResource, String name, Object param) {
        if (param != null) {
            return webResource.queryParam(name, param + "");
        }
        return webResource;
    }

    public void setAuth(String user, String password) {
        String authString = user + ":" + password;
        authHeader = "Basic " + new BASE64Encoder().encode(authString.getBytes());
    }
}
