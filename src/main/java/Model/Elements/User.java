package Model.Elements;

import Model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userID;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "book_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    public void addUser()
    {
        if (this.doesUserExist())
        {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            session.save(this);
            tx.commit();
            session.close();
        } else
            System.out.println("User already exists!");
    }

    public boolean doesUserExist()
    {
        if (getUser(this.userID) == null)
            return false;
        return true;
    }

    public static User getUser(int id)
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        User user = session.get(User.class, id);
        session.getTransaction().commit();

        return user;
    }

    public void addBook(Book book)
    {
        if (books == null)
            books = new ArrayList<>();

        books.add(book);
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        tx.commit();
        session.close();
    }

    public User()
    {
    }

    public User(String userName, String password, String email)
    {
        this.login = userName;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userID=" + userID +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", books=" + books +
                '}';
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public int getUserID()
    {
        return userID;
    }
}

