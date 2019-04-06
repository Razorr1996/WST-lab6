package ru.basa62.wst.lab4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class BooksEntity {
    private Long id;
    private String name;
    private String author;
    private Date publicDate;
    private String isbn;
}
