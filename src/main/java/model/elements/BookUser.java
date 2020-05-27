package model.elements;

import lombok.Data;
import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Data
@Table(name = "book_user")
public class BookUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_user_id")
    private int id;
    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "date")
    // @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "rating")
    private float rating;

    public void saveBookUser() {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(this);
        tx.commit();
        session.close();
    }

    public BookUser() {
        this.rating = -1;
        this.date = new Date();
    }

    public BookUser(Book book, float rating) {
        this.book = book;
        this.rating = rating;
    }

    public BookUser(Book book, User user) {
        this.book = book;
        this.user = user;
        this.date = new Date();
        System.out.println("bookUserDate" + date);
    }

    public BookUser(Book book, User user, float rating) {
        this.book = book;
        this.user = user;
        this.date = new Date();
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "";
    }
}
