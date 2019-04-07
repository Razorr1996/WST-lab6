package ru.basa62.wst.lab6.rs;

import ru.basa62.wst.lab6.BooksDAO;
import ru.basa62.wst.lab6.BooksEntity;
import ru.basa62.wst.lab6.rs.exceptions.ServiceException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
                                    @QueryParam("publicDate") String publicDate, @QueryParam("isbn") String isbn) throws ServiceException {
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
                         @QueryParam("publicDate") String publicDate, @QueryParam("isbn") String isbn) throws ServiceException {
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
    public String update(@QueryParam("id") Long id, @QueryParam("name") String name,
                         @QueryParam("author") String author,
                         @QueryParam("publicDate") String publicDate, @QueryParam("isbn") String isbn) throws ServiceException {
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
    public String delete(@QueryParam("id") Long id) throws ServiceException {
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
}
