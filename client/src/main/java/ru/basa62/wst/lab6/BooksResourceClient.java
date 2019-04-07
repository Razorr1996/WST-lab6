package ru.basa62.wst.lab6;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BooksResourceClient {
    @NonNull
    private final String baseUrl;

    public List<BooksEntity> filter(Long id, String name, String author, String  publicDate, String isbn) {
        Client client = Client.create();
        WebResource resource = client.resource(baseUrl);
        resource = addParam(resource, "id", id);
        resource = addParam(resource, "name", name);
        resource = addParam(resource, "author", author);
        resource = addParam(resource, "publicDate", publicDate);
        resource = addParam(resource, "isbn", isbn);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<List<BooksEntity>> type = new GenericType<List<BooksEntity>>() {
        };
        return response.getEntity(type);
    }

    public String create(String name, String author, String  publicDate, String isbn) {
        Client client = Client.create();
        WebResource resource = client.resource(baseUrl);
        resource = addParam(resource, "name", name);
        resource = addParam(resource, "author", author);
        resource = addParam(resource, "publicDate", publicDate);
        resource = addParam(resource, "isbn", isbn);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return response.getEntity(type);
    }

    public String update(Long id, String name, String author, String  publicDate, String isbn) {
        Client client = Client.create();
        WebResource resource = client.resource(baseUrl);
        resource = addParam(resource, "id", id);
        resource = addParam(resource, "name", name);
        resource = addParam(resource, "author", author);
        resource = addParam(resource, "publicDate", publicDate);
        resource = addParam(resource, "isbn", isbn);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON_TYPE).put(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return response.getEntity(type);
    }

    public String delete(Long id) {
        Client client = Client.create();
        WebResource resource = client.resource(baseUrl);
        resource = addParam(resource, "id", id);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new IllegalStateException("Request failed");
        }
        GenericType<String> type = new GenericType<String>() {
        };
        return response.getEntity(type);
    }

    private WebResource addParam(WebResource webResource, String name, Object param) {
        if (param != null) {
            return webResource.queryParam(name, param + "");
        }
        return webResource;
    }
}
