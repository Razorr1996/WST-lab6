package ru.basa62.wst.lab3;

import ru.basa62.wst.lab3.ws.client.BooksEntity;
import ru.basa62.wst.lab3.ws.client.BooksService_Service;

import java.io.IOException;
import java.net.URL;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class Client {
    static BooksService_Service booksService;

    public static void main(String[] args) throws IOException {
        URL url = new URL(args[0]);
        booksService = new BooksService_Service(url);

        System.out.println("Добро пожаловать в библиотеку.");
        Scanner in = new Scanner(System.in);
        String choiceStr;
        while (true) {
            System.out.println("\nМеню:\n0 - Выход\n" +
                    "1 - findAll\n" +
                    "2 - filter\n" +
                    "3 - create\n" +
                    "4 - update\n" +
                    "5 - delete\n");
            System.out.print("Выбор: ");
            choiceStr = checkEmpty(in.nextLine());
            if (choiceStr != null) {
                int choice = Integer.parseInt(choiceStr);
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        findAll();
                        break;
                    case 2:
                        filter();
                        break;
                    case 3:
                        create();
                        break;
                    case 4:
                        update();
                        break;
                    case 5:
                        delete();
                        break;
                    default:
                        System.out.println("Неверное значение");
                        break;
                }
            } else {
                System.out.println("Введите число от 1 до 5");
            }
        }

    }

    private static void findAll() {
        System.out.println("Выведем все книги:");
        List<BooksEntity> books1 = booksService.getBooksServicePort().findAll();
        for (BooksEntity book : books1) {
            System.out.println(printBook(book));
        }
    }

    private static void filter() {
        Scanner in = new Scanner(System.in);
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
        String publicDate = checkEmpty(in.nextLine());

        System.out.print("ISBN: ");
        String isbn = checkEmpty(in.nextLine());

        List<BooksEntity> books2 = booksService.getBooksServicePort().filter(id, name, author, publicDate, isbn);

        if (books2.size() == 0) {
            System.out.println("Ничего не найдено");
        } else {
            System.out.println("Найдено:");
            for (BooksEntity book : books2) {
                System.out.println(printBook(book));
            }
        }
    }

    private static void create(){
        Scanner in = new Scanner(System.in);
        System.out.println("Создадим книгу:");

        System.out.print("Название: ");
        String name = checkEmpty(in.nextLine());

        System.out.print("Автор: ");
        String author = checkEmpty(in.nextLine());

        System.out.print("Дата публикации (yyyy-MM-dd): ");
        String publicDate = checkEmpty(in.nextLine());

        System.out.print("ISBN: ");
        String isbn = checkEmpty(in.nextLine());

        Long newId = booksService.getBooksServicePort().create(name, author, publicDate, isbn);
        System.out.printf("Новый ID: %d", newId);
    }

    private static void update(){
        Scanner in = new Scanner(System.in);
        System.out.println("Обновим книгу:");
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
        String publicDate = checkEmpty(in.nextLine());

        System.out.print("ISBN: ");
        String isbn = checkEmpty(in.nextLine());

        int count = booksService.getBooksServicePort().update(id, name, author, publicDate, isbn);
        System.out.printf("Обновлено: %d", count);
    }

    private static void delete() {
        Scanner in = new Scanner(System.in);
        System.out.println("Удалим книгу:");
        System.out.print("ID: ");
        String idStr = checkEmpty(in.nextLine());
        if (idStr != null) {
            long id = Long.parseLong(idStr);
            int count = booksService.getBooksServicePort().delete(id);
            System.out.printf("Удалено: %d", count);
        } else {
            System.out.println("Ничего не введено");
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
