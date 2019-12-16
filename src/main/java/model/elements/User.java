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

    private List<BookUser> books;

    public void addUser()
    {

    }

    public boolean doesUserExist()
    {
        if (getUser(this.userId) == null)
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

