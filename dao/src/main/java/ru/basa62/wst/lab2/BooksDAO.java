package ru.basa62.wst.lab2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.basa62.wst.lab2.db.DefaultCondition;
import ru.basa62.wst.lab2.db.IgnoreCaseContainsCondition;
import ru.basa62.wst.lab2.db.Query;
import ru.basa62.wst.lab2.db.QueryBuilder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class BooksDAO {

    private final String TABLE_NAME = "books";
    private final String ID_COLUMN = "id";
    private final String NAME_COLUMN = "name";
    private final String AUTHOR_COLUMN = "author";
    private final String PUBLIC_DATE_COLUMN = "public_date";
    private final String ISBN_COLUMN = "isbn";

    private final DataSource dataSource;

    public List<BooksEntity> findAll() throws SQLException {
        log.debug("Find all query");
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("SELECT b.id, b.name, b.author, b.public_date, b.isbn from books as b");
            return rsToEntities(statement.getResultSet());
        }

    }

    public List<BooksEntity> filter(Long id, String name, String author, Date publicDate, String isbn) throws SQLException {
        log.debug("Filter with args: {} {} {} {} {}", id, name, author, publicDate, isbn);
        if (Stream.of(id, name, author, publicDate, isbn).allMatch(Objects::isNull)) {
            log.debug("Args are empty");
            return findAll();
        }
        Query query = new QueryBuilder()
                .tableName(TABLE_NAME)
                .selectColumns(ID_COLUMN, NAME_COLUMN, AUTHOR_COLUMN, PUBLIC_DATE_COLUMN, ISBN_COLUMN)
                .condition(DefaultCondition.defaultCondition(ID_COLUMN, id, Long.class))
                .condition(new IgnoreCaseContainsCondition(NAME_COLUMN, name))
                .condition(new IgnoreCaseContainsCondition(AUTHOR_COLUMN, author))
                .condition(DefaultCondition.defaultCondition(PUBLIC_DATE_COLUMN, publicDate, Date.class))
                .condition(new IgnoreCaseContainsCondition(ISBN_COLUMN, isbn))
                .buildPreparedStatementQuery();
        log.debug("Built query {}", query.getQueryString());
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query.getQueryString());
            query.initPreparedStatement(ps);
            ResultSet rs = ps.executeQuery();
            return rsToEntities(rs);
        }

    }

    private List<BooksEntity> rsToEntities(ResultSet rs) throws SQLException {
        List<BooksEntity> result = new ArrayList<>();
        while (rs.next()) {
            result.add(resultSetToEntity(rs));
        }
        log.debug("Result set was converted to entity list {}", result);
        return result;
    }

    private BooksEntity resultSetToEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong(ID_COLUMN);
        String name = rs.getString(NAME_COLUMN);
        String author = rs.getString(AUTHOR_COLUMN);
        Date publicDate = rs.getDate(PUBLIC_DATE_COLUMN);
        String isbn = rs.getString(ISBN_COLUMN);
        return new BooksEntity(id, name, author, publicDate, isbn);
    }
}
