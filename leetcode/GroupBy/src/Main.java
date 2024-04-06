import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class Main {
    public static void main(String[] args) {
        var list = List.of(
                new Book("book_a", "author_a", "genre_a"),
                new Book("book_b", "author_a", "genre_a"),
                new Book("book_c", "author_a", "genre_b"),
                new Book("book_d", "author_b", "genre_b"),
                new Book("book_e", "author_b", "genre_a")
        );

        System.out.println(list.stream()
                .collect(groupingBy(Book::author, groupingBy(Book::genre, mapping(Book::name, toSet())))));
        System.out.println(list.stream()
                .collect(groupingBy(Book::author, groupingBy(Book::genre, mapping(Book::name, toList())))));
        System.out.println(list.stream()
                .collect(groupingBy(Book::author, groupingBy(Book::genre, mapping(Book::name, toList())))));
    }
}