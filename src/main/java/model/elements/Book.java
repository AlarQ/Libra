package model.elements;

import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book
{
    private int bookId;
    private String author;
    private String title;
    private String publisher;
    private String genre;
    private String cover;
    private int yearOfPublication;
    private String ISBN;
    private float rating;
    private Date firstDate;
    private List<BookUser> users;
    private int numberOfReaders;
    public void addUser(User user)
    {
        if (users == null)
            users = new ArrayList<>();

        BookUser bookUser = new BookUser(this, user);
        users.add(bookUser);
    }

    //check!!!!!!!!!
    public void update(float rating)
    {
        float sum = this.rating*numberOfReaders++;
        this.rating = (sum+rating)/(numberOfReaders);
    }

    public void addBook()
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(this);
        tx.commit();
        session.close();
    }

    public static boolean isBookExistByTitle(String title)
    {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Book b " +
                "WHERE b.title=:title");
        query.setParameter("title", title);
        List result = query.getResultList();

        session.getTransaction().commit();
        return !result.isEmpty();
    }

    @OneToMany(
            mappedBy = "book",
            fetch = FetchType.EAGER
    )
    public List<BookUser> getUsers()
    {
        return users;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    public int getBookId()
    {
        return bookId;
    }

    @Column(name = "author")
    public String getAuthor()
    {
        return author;
    }

    @Column(name = "title")
    public String getTitle()
    {
        return title;
    }

    @Column(name = "publisher")
    public String getPublisher()
    {
        return publisher;
    }

    @Column(name = "genre")
    public String getGenre()
    {
        return genre;
    }

    @Column(name = "cover")
    public String getCover()
    {
        return cover;
    }

    @Column(name = "year_of_publication")
    public int getYearOfPublication()
    {
        return yearOfPublication;
    }

    @Column(name = "ISBN")
    public String getISBN()
    {
        return ISBN;
    }

    @Column(name = "rating")
    public float getRating()
    {
        return rating;
    }

    @Column(name = "first_date")
    public Date getFirstDate()
    {
        return firstDate;
    }

    @Column(name = "readers")
    public int getNumberOfReaders()
    {
        return numberOfReaders;
    }

    public Book()
    {
    }

    public Book(String author, String title, String publisher, int yearOfPublication,
                String genre, String ISBN, String cover, float rating)
    {
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

    public Book(String author, String title, String publisher, String genre, String cover, int yearOfPublication, String ISBN)
    {
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

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public void setYearOfPublication(int yearOfPublication)
    {
        this.yearOfPublication = yearOfPublication;
    }

    public void setISBN(String ISBN)
    {
        this.ISBN = ISBN;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }

    public void setFirstDate(Date firstDate)
    {
        this.firstDate = firstDate;
    }

    public void setUsers(List<BookUser> users)
    {
        this.users = users;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setNumberOfReaders(int numberOfReaders)
    {
        this.numberOfReaders = numberOfReaders;
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "bookId=" + bookId +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", genre='" + genre + '\'' +
                ", cover='" + cover + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", ISBN='" + ISBN + '\'' +
                ", rating=" + rating +
                ", firstDate=" + firstDate +
                ", numberOfReaders=" + numberOfReaders +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        if(this.ISBN == book.ISBN) return true;
        return bookId == book.bookId &&
                yearOfPublication == book.yearOfPublication &&
                Float.compare(book.rating, rating) == 0 &&
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
    public int hashCode()
    {
        return Objects.hash(bookId, author, title, publisher, genre, cover, yearOfPublication, ISBN, rating, firstDate, users);
    }

}
