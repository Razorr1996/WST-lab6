package ru.basa62.wst.lab3.ws;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.basa62.wst.lab3.BooksDAO;
import ru.basa62.wst.lab3.BooksEntity;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@WebService(serviceName = "BooksService")
public class BooksService {
    @Inject
    @NonNull
    private BooksDAO booksDAO;

    @WebMethod
    @SneakyThrows
    public List<BooksEntity> findAll() {
        return booksDAO.findAll();
    }

    @WebMethod
    @SneakyThrows
    public List<BooksEntity> filter(@WebParam(name = "id") Long id, @WebParam(name = "name") String name,
                                    @WebParam(name = "author") String author,
                                    @WebParam(name = "publicDate") String publicDate, @WebParam(name = "isbn") String isbn) {
        return booksDAO.filter(id, name, author, getDate(publicDate), isbn);
    }

    @WebMethod
    @SneakyThrows
    public Long create(@WebParam(name = "name") String name,
                       @WebParam(name = "author") String author,
                       @WebParam(name = "publicDate") String publicDate, @WebParam(name = "isbn") String isbn) {
        return booksDAO.create(name, author, getDate(publicDate), isbn);
    }

    @WebMethod
    @SneakyThrows
    public int delete(@WebParam(name = "id") long id) {
        return booksDAO.delete(id);
    }

    @WebMethod
    @SneakyThrows
    public int update(@WebParam(name = "id") Long id,
                       @WebParam(name = "name") String name,
                       @WebParam(name = "author") String author,
                       @WebParam(name = "publicDate") String publicDate, @WebParam(name = "isbn") String isbn) {
        return booksDAO.update(id, name, author, getDate(publicDate), isbn);
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
