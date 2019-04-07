package ru.basa62.wst.lab6.rs;

import lombok.SneakyThrows;
import ru.basa62.wst.lab6.BooksDAO;
import ru.basa62.wst.lab6.BooksEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
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
    @SneakyThrows
    public List<BooksEntity> filter(@QueryParam("id") Long id, @QueryParam("name") String name,
                                    @QueryParam("author") String author,
                                    @QueryParam("publicDate") String publicDate, @QueryParam("isbn") String isbn) {
        return booksDAO.filter(id, name, author, getDate(publicDate), isbn);
    }

    @POST
    @SneakyThrows
    public String create(@QueryParam("name") String name,
                         @QueryParam("author") String author,
                         @QueryParam("publicDate") String publicDate, @QueryParam("isbn") String isbn) {
        return booksDAO.create(name, author, getDate(publicDate), isbn) + "";
    }

    @PUT
    @SneakyThrows
    public String update(@QueryParam("id") Long id, @QueryParam("name") String name,
                         @QueryParam("author") String author,
                         @QueryParam("publicDate") String publicDate, @QueryParam("isbn") String isbn) {
        return booksDAO.update(id, name, author, getDate(publicDate), isbn) + "";
    }

    @DELETE
    @SneakyThrows
    public String delete(@QueryParam("id") Long id) {
        return booksDAO.delete(id) + "";
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
