package ru.basa62.wst.lab2.ws;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.basa62.wst.lab2.BooksDAO;
import ru.basa62.wst.lab2.BooksEntity;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@WebService(serviceName = "BooksService")
public class BooksService {
    @Inject
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
                                    @WebParam(name = "publicDate") Date publicDate, @WebParam(name = "isbn") String isbn) {
        return booksDAO.filter(id, name, author, publicDate, isbn);
    }
}
