package ru.basa62.wst.lab3.db;

public interface Condition {
    String build();

    Object getValue();

    Class<?> getType();

    String getColumnName();
}
