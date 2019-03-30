package ru.basa62.wst.lab2.db;

public interface Condition {
    String build();

    Object getValue();

    Class<?> getType();

    String getColumnName();
}
