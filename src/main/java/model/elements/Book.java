package model.elements;

import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.Data;

@Entity
@Data
@Table(name = "book")
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;
    @Column(name = "author")
    private String author;
    @Column(name = "title")
    private String title;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "genre")
    private String genre;
    @Column(name = "cover")
    private String cover;
    @Column(name = "year_of_publication")
    private int yearOfPublication;
    @Column(name = "ISBN")
    private String ISBN;

    @Column(name = "rating")
    private float rating;
    @Column(name = "first_date")
    private Date firstDate;
    @OneToMany(
            mappedBy = "book",
            fetch = FetchType.EAGER
    )
    private List<BookUser> users;
    @Column(name = "readers")
    private int numberOfReaders;

    public void addUser(User user) {
        if (users == null)
            users = new ArrayList<>();

        BookUser bookUser = new BookUser(this, user);
        users.add(bookUser);
    }

    public void update(float rating) {
        float sum = this.rating * numberOfReaders++;
        this.rating = (sum + rating) / (numberOfReaders);
    }

    public void addBook() {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(this);
        tx.commit();
        session.close();
    }

    public static boolean isBookAdded(String title) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Book b " +
                "WHERE b.title=:title");
        query.setParameter("title", title);
        List result = query.getResultList();

        session.getTransaction().commit();
        return !result.isEmpty();
    }


    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String author, String title, String publisher, int yearOfPublication,
                String genre, String ISBN, String cover, float rating) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.genre = genre;
        this.cover = cover;
        this.yearOfPublication = yearOfPublication;
        this.ISBN = ISBN;
        this.rating = rating;
        this.firstDate = new Date();
        this.numberOfReaders = 1;
    }

    public Book(String author, String title, String publisher, String genre, String cover, int yearOfPublication, String ISBN) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.genre = genre;
        this.cover = cover;
        this.yearOfPublication = yearOfPublication;
        this.ISBN = ISBN;
        this.rating = 0;
        this.firstDate = new Date();
        this.numberOfReaders = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId &&
                yearOfPublication == book.yearOfPublication &&
                Float.compare(book.rating, rating) == 0 &&
                numberOfReaders == book.numberOfReaders &&
                Objects.equals(author, book.author) &&
                Objects.equals(title, book.title) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(genre, book.genre) &&
                Objects.equals(cover, book.cover) &&
                Objects.equals(ISBN, book.ISBN) &&
                Objects.equals(firstDate, book.firstDate) &&
                Objects.equals(users, book.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, author, title, publisher, genre, cover, yearOfPublication, ISBN, rating, firstDate, users, numberOfReaders);
    }
}
