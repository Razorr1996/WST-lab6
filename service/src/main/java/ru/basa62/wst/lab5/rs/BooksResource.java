package ru.basa62.wst.lab5.rs;

import lombok.SneakyThrows;
import ru.basa62.wst.lab5.BooksDAO;
import ru.basa62.wst.lab5.BooksEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
//    @Path("/filter")
    @SneakyThrows
    public List<BooksEntity> filter(@QueryParam("id") Long id, @QueryParam("name") String name,
                                    @QueryParam("author") String author,
                                    @QueryParam("publicDate") String publicDate, @QueryParam("isbn") String isbn) {
        return booksDAO.filter(id, name, author, getDate(publicDate), isbn);
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
