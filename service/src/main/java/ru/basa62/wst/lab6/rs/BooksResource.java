package ru.basa62.wst.lab6.rs;

import lombok.extern.slf4j.Slf4j;
import ru.basa62.wst.lab6.BooksDAO;
import ru.basa62.wst.lab6.BooksEntity;
import ru.basa62.wst.lab6.rs.exceptions.ServiceException;
import sun.misc.BASE64Decoder;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@RequestScoped
@Path("/books")
@Produces({MediaType.APPLICATION_JSON})
public class BooksResource {
    public static BooksDAO STATIC_DAO;

    @Inject
    private BooksDAO booksDAO;

    public BooksResource() {
        booksDAO = STATIC_DAO;
    }

    @GET
    public List<BooksEntity> filter(@QueryParam("id") Long id, @QueryParam("name") String name,
                                    @QueryParam("author") String author,
                                    @QueryParam("publicDate") String publicDate,
                                    @QueryParam("isbn") String isbn) throws ServiceException {
        try {
            return booksDAO.filter(id, name, author, getDate(publicDate), isbn);
        } catch (SQLException e) {
            String message = "SQL error: " + e.getMessage() + "; State:" + e.getSQLState();
            throw new ServiceException(message);
        } catch (ParseException e) {
            String message = "Parse error: " + e.getMessage();
            throw new ServiceException(message);
        }
    }

    @POST
    public String create(@QueryParam("name") String name,
                         @QueryParam("author") String author,
                         @QueryParam("publicDate") String publicDate,
                         @QueryParam("isbn") String isbn,
                         @HeaderParam("Authorization") String authString) throws ServiceException {
        checkAuth(authString);
        try {
            return booksDAO.create(name, author, getDate(publicDate), isbn) + "";
        } catch (SQLException e) {
            String message = "SQL error: " + e.getMessage() + ". State: " + e.getSQLState();
            throw new ServiceException(message);
        } catch (ParseException e) {
            String message = "Parse error: " + e.getMessage();
            throw new ServiceException(message);
        }
    }

    @PUT
    public String update(@QueryParam("id") Long id,
                         @QueryParam("name") String name,
                         @QueryParam("author") String author,
                         @QueryParam("publicDate") String publicDate,
                         @QueryParam("isbn") String isbn,
                         @HeaderParam("Authorization") String authString) throws ServiceException {
        checkAuth(authString);
        try {
            int count = booksDAO.update(id, name, author, getDate(publicDate), isbn);
            if (count == 0) {
                String message = "Book with id=" + id + " doesn't exist.";
                throw new ServiceException(message);
            }
            return count + "";
        } catch (SQLException e) {
            String message = "SQL error: " + e.getMessage() + ". State: " + e.getSQLState();
            throw new ServiceException(message);

        } catch (ParseException e) {
            String message = "Parse error: " + e.getMessage();
            throw new ServiceException(message);

        }
    }

    @DELETE
    public String delete(@QueryParam("id") Long id,
                         @HeaderParam("authorization") String authString) throws ServiceException {
        checkAuth(authString);
        try {
            int count = booksDAO.delete(id);
            if (count == 0) {
                String message = "Book with id=" + id + " doesn't exist.";
                throw new ServiceException(message);
            }
            return count + "";
        } catch (SQLException e) {
            String message = "SQL error: " + e.getMessage() + ". State: " + e.getSQLState();
            throw new ServiceException(message);
        }
    }

    private Date getDate(String string) throws ParseException {
        if (string == null) {
            return null;
        } else {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            return new Date(simpleDateFormat.parse(string).getTime());
        }
    }

    private void checkAuth(String authString) throws ServiceException {
        if (authString == null || authString.equals("")) {
            throw new ServiceException("Empty auth");
        }

        String decodedAuth;
        // Header is in the format "Basic dXNlcjpwYXNz"
        // We need to extract data before decoding it back to original string
        String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        // Decode the data back to original string

        byte[] bytes;

        try {
            bytes = new BASE64Decoder().decodeBuffer(authInfo);
        } catch (IOException e) {
            String message = "BASE64 decode error: " + e.getMessage();
            throw new ServiceException(message);
        }

        decodedAuth = new String(bytes);
        log.info(decodedAuth);

        String[] up = decodedAuth.split(":");

        String user = up[0];
        String pass = up[1];

        if (!user.equals("user") || !pass.equals("pass")) {
            String message = "Wrong auth!";
            throw new ServiceException(message);
        }

    }

}
