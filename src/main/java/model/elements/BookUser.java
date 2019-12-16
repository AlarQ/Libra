package model.elements;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity()
@Table(name = "book_user")
public class BookUser
{
    private int id;
    private Book book;
    private User user;
    private Date date = new Date();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_user_id")
    public int getId()
    {
        return id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
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
    @Temporal(TemporalType.DATE)
    public Date getDate()
    {
        return date;
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
