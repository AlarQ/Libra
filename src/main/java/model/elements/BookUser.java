package model.elements;

import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity()
@Table(name = "book_user")
public class BookUser
{
    private int id;
    private Book book;
    private User user;
    private Date date;
    private float rating;

    public void saveBookUser()
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(this);
        tx.commit();
        session.close();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_user_id")
    public int getId()
    {
        return id;
    }

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    public Book getBook()
    {
        return book;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User getUser()
    {
        return user;
    }

    @Column(name = "date")
   // @Temporal(TemporalType.DATE)
    public Date getDate()
    {
        return date;
    }

    @Column(name = "rating")
    public float getRating()
    {
        return rating;
    }

    public BookUser()
    {
        this.rating = -1;
        this.date = new Date();
    }

    public BookUser(Book book, User user)
    {
        this.book = book;
        this.user = user;
        this.date = new Date();
        System.out.println("bookUserDate" + date);
    }

    public BookUser(Book book, User user, float rating)
    {
        this.book = book;
        this.user = user;
        this.date = new Date();
        this.rating = rating;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setBook(Book book)
    {
        this.book = book;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setDate(Date addDate)
    {
        this.date = addDate;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookUser bookUser = (BookUser) o;
        return Objects.equals(id, bookUser.id) &&
                Objects.equals(book, bookUser.book) &&
                Objects.equals(user, bookUser.user) &&
                Objects.equals(date, bookUser.date);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, book, user, date);
    }

}
