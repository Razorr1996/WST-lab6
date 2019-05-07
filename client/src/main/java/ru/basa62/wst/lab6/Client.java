package ru.basa62.wst.lab6;

import java.io.IOException;
import java.net.URL;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class Client {
    static BooksResourceClient booksClient;

    public static void main(String[] args) throws IOException {
        URL url = new URL(args[0]);
        booksClient = new BooksResourceClient(url.toString());
        booksClient.setAuth("user", "pass");

        System.out.println("Добро пожаловать в библиотеку.");
        Scanner in = new Scanner(System.in);
        String choiceStr;
        while (true) {
            System.out.println("\nМеню:\n0 - Выход\n" +
                    "1 - filter\n" +
                    "2 - create\n" +
                    "3 - update\n" +
                    "4 - delete\n");
            System.out.print("Выбор: ");
            choiceStr = checkEmpty(in.nextLine());
            if (choiceStr != null) {
                int choice = Integer.parseInt(choiceStr);
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        filter();
                        break;
                    case 2:
                        create();
                        break;
                    case 3:
                        update();
                        break;
                    case 4:
                        delete();
                        break;
                    default:
                        System.out.println("Неверное значение");
                        break;
                }
            } else {
                System.out.println("Введите число");
            }
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

        Result<List<BooksEntity>> result = booksClient.filter(id, name, author, publicDate, isbn);

        if (result.isError()) {
            System.out.println(result.getMessage());
        } else {
            new Thread(() -> {
                try {
                    List<BooksEntity> books2 = result.getResult();
                    Thread.sleep(5000);
                    if (books2.size() == 0) {
                        System.out.println("Ничего не найдено");
                    } else {
                        System.out.println("Найдено:");
                        for (BooksEntity book : books2) {
                            System.out.println(printBook(book));
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void create() {
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

        new Thread(() -> {
            try {
                Result<String> result = booksClient.create(name, author, publicDate, isbn);
                Thread.sleep(5000);
                if (result.isError()) {
                    System.out.println(result.getMessage());
                } else {
                    System.out.printf("Новый ID: %s", result.getResult());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void update() {
        Scanner in = new Scanner(System.in);
        System.out.println("Обновим книгу:");
        System.out.print("ID: ");

        String idStr = checkEmpty(in.nextLine());
        Long id_ = null;
        if (idStr != null) {
            id_ = Long.parseLong(idStr);
        }
        Long id = id_;

        System.out.print("Название: ");
        String name = checkEmpty(in.nextLine());

        System.out.print("Автор: ");
        String author = checkEmpty(in.nextLine());

        System.out.print("Дата публикации (yyyy-MM-dd): ");
        String publicDate = checkEmpty(in.nextLine());

        System.out.print("ISBN: ");
        String isbn = checkEmpty(in.nextLine());

        new Thread(() -> {
            try {
                Result<String> result = booksClient.update(id, name, author, publicDate, isbn);
                Thread.sleep(5000);
                if (result.isError()) {
                    System.out.println(result.getMessage());
                } else {
                    System.out.printf("Обновлено: %s", result.getResult());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void delete() {
        Scanner in = new Scanner(System.in);
        System.out.println("Удалим книгу:");
        System.out.print("ID: ");
        String idStr = checkEmpty(in.nextLine());
        if (idStr != null) {
            long id = Long.parseLong(idStr);

            new Thread(() -> {
                try {
                    Result<String> result = booksClient.delete(id);
                    Thread.sleep(5000);
                    if (result.isError()) {
                        System.out.println(result.getMessage());
                    } else {
                        System.out.printf("Удалено: %s", result.getResult());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            System.out.println("Ничего не введено");
        }
    }

    private static String printBook(BooksEntity b) {
        Formatter fmt = new Formatter();
        return fmt.format("ID: %d, Book: %s, Author: %s, PublicDate: %s, ISBN: %s", b.getId(), b.getName(), b.getAuthor(), b.getPublicDate().toString(), b.getIsbn()).toString();
    }

    private static String checkEmpty(String s) {
        return s.length() == 0 ? null : s;
    }
}
