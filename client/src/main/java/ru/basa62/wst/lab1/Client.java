package ru.basa62.wst.lab1;

import ru.basa62.wst.lab1.ws.client.BooksEntity;
import ru.basa62.wst.lab1.ws.client.BooksService_Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException {
        URL url = new URL(args[0]);
        BooksService_Service booksService = new BooksService_Service(url);

        List<BooksEntity> books1 = booksService.getBooksServicePort().findAll();
        for (BooksEntity book : books1) {
            System.out.println(printBook(book));
        }

        System.out.println("Добро пожаловать в библиотеку.");
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Поищем книги:");
            System.out.print("ID: ");
            String idStr = checkEmpty(in.nextLine());
            Long id = null;
            if (idStr != null) {
                id = Long.parseLong(idStr);
            }
            System.out.print("Название: ");
            String name = checkEmpty(in.nextLine());

            System.out.print("Автор: ");
            String author = checkEmpty(in.nextLine());

            System.out.print("Дата публикации (yyyy-MM-dd): ");
            String publicDateStr = checkEmpty(in.nextLine());
            XMLGregorianCalendar calendar = null;
            if (publicDateStr != null) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(publicDateStr);
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(date);
                    calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
                } catch (Exception e) {
                    System.out.println("Wrong format");
                }
            }

            System.out.print("ISBN: ");
            String isbn = checkEmpty(in.nextLine());

            List<BooksEntity> books2 = booksService.getBooksServicePort().filter(id, name, author, calendar, isbn);

            for (BooksEntity book : books2) {
                System.out.println(printBook(book));
            }
        }

    }

    public static String printBook(BooksEntity b) {
        Formatter fmt = new Formatter();
        return fmt.format("ID: %d, Book: %s, Author: %s, PublicDate: %s, ISBN: %s", b.getId(), b.getName(), b.getAuthor(), b.getPublicDate().toString(), b.getIsbn()).toString();
    }

    public static String checkEmpty(String s) {
        return s.length() == 0 ? null : s;
    }
}
