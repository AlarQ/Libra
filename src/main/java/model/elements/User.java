package model.elements;

import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User
{
    private int userId;
    private String login;
    private String password;
    private String email;
    private Date addDate;
    private Byte admin;
    private List<BookUser> books;

    public void addUser()
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(this);
        tx.commit();
        session.close();
        System.out.println("user added");
    }

    public boolean doesUserExist()
    {
        return getUser(this.userId) != null;
    }

    public static User getUser(int id)
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        User user = session.get(User.class, id);
        session.getTransaction().commit();

        return user;
    }

    public static User getUserByEmail(String email)
    {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User u " +
                "WHERE u.email=:email");
        query.setParameter("email", email);
        List result = query.getResultList();

        session.getTransaction().commit();
        if (!result.isEmpty())
            return (User) result.get(0);
        else return null;
    }


    public void addBook(Book book)
    {
        if (books == null)
            books = new ArrayList<>();

    }

    public User()
    {
    }

    public User(String login, String password, String email)
    {
        this.login = login;
        this.password = password;
        this.email = email;
        this.addDate = new Date();
        this.admin = 0;
    }

    @OneToMany(
            mappedBy = "user",
            targetEntity = BookUser.class
    )
    public List<BookUser> getBooks()
    {
        return books;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public int getUserId()
    {
        return userId;
    }

    @Column(name = "admin")
    public Byte getAdmin()
    {
        return admin;
    }

    @Column(name = "email")
    public String getEmail()
    {
        return email;
    }

    @Column(name = "login")
    public String getLogin()
    {
        return login;
    }

    @Column(name = "add_date")
    public Date getAddDate()
    {
        return addDate;
    }

    @Column(name = "password")
    public String getPassword()
    {
        return password;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public void setUserId(int userID)
    {
        this.userId = userID;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setAddDate(Date addDate)
    {
        this.addDate = addDate;
    }

    public void setBooks(List<BookUser> books)
    {
        this.books = books;
    }

    public void setAdmin(Byte admin)
    {
        this.admin = admin;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userID=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", books=" + books +
                '}';
    }


}

