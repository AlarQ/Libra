package model_t.elements_t;
import model.elements.Book;
import model.elements.BookUser;
import model.elements.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class topRatedBooksTest
{
    @Test
    public void shouldReturnTopRatedBooks() {
        List<Book> expected = prepareSortedBookData();
        List<Book> actual = prepareUserData().topRatedBooks();
        Assert.assertEquals(expected,actual);
    }

    public User prepareUserData() {
        User user = new User();
        user.setBooks(Arrays.asList(
                new BookUser(new Book("a"),6),
                new BookUser(new Book("b"),1),
                new BookUser(new Book("c"),4),
                new BookUser(new Book("d"),2),
                new BookUser(new Book("e"),9)
        ));
        return user;
    }

    public List<Book> prepareSortedBookData()
    {
        List<Book> books = new ArrayList<>();
        books.add(new Book("e"));
        books.add(new Book("a"));
        books.add(new Book("c"));
        books.add(new Book("d"));
        books.add(new Book("b"));
        return  books;

    }
}
