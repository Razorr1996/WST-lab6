package ru.basa62.wst.lab4.beans;

import ru.basa62.wst.lab4.rs.BooksResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class Deploy extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(BooksResource.class));
    }
}
