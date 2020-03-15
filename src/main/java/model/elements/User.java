package model.elements;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.scene.control.Alert;
import model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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
    private List<BookUser> books = new ArrayList<>();

    public List<Book> lastAddedBooks()
    {
        Map<Book, Date> result = new HashMap<>();
        result = books.stream().collect(
                Collectors.toMap(BookUser::getBook, BookUser::getDate));

        Comparator<Map.Entry<Book, Date>> valueComparator = new Comparator<Map.Entry<Book,Date>>() {

            @Override
            public int compare(Map.Entry<Book, Date> e1, Map.Entry<Book, Date> e2) {
                Date v1 = e1.getValue();
                Date v2 = e2.getValue();
                return v2.compareTo(v1);
            }
        };

        Set<Map.Entry<Book, Date>> entries = result.entrySet();

        // Sort method needs a List, so let's first convert Set to List in Java
        List<Map.Entry<Book, Date>> listOfEntries = new ArrayList<Map.Entry<Book, Date>>(entries);

        // sorting HashMap by values using comparator
        Collections.sort(listOfEntries, valueComparator);

        LinkedHashMap<Book, Date> sortedByValue = new LinkedHashMap<Book, Date>(listOfEntries.size());

        // copying entries from List to Map
        List<Book> lastAdded = new ArrayList<>();

        for(Map.Entry<Book, Date> entry : listOfEntries){
            sortedByValue.put(entry.getKey(), entry.getValue());
            lastAdded.add(entry.getKey());
        }

        for (Book b: lastAdded)
            System.out.println(b);

        /**
        System.out.println("HashMap after sorting entries by values ");
        Set<Map.Entry<Book, Date>> entrySetSortedByValue = sortedByValue.entrySet();

        for(Map.Entry<Book, Date> mapping : entrySetSortedByValue){
            System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
        }
         */
         return lastAdded;
    }

    public boolean deleteUser(String login)
    {
        List result;

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query quer = session.createQuery("FROM User u WHERE u.login=:login");
        quer.setParameter("login", login);
        result = quer.getResultList();
        System.out.println(result.size());
        session.getTransaction().commit();
        session.close();
        if(result.size()>0) {
            Session session1 = HibernateUtil.getSession();
            session1.beginTransaction();
            Query query = session1.createQuery("delete FROM User u " +
                    "WHERE u.login='" + login + "'");
            int res = query.executeUpdate();
            session1.getTransaction().commit();
            session1.close();
            return res>0;
        }
        return false;
    }

    public List<Book> topRatedBooks()
    {
        Map<Book, Float> result = new HashMap<>();
        result = books.stream().collect(
                Collectors.toMap(BookUser::getBook, BookUser::getRating));

        for (Book b: result.keySet())
            System.out.println(b);

        Comparator<Map.Entry<Book, Float>> valueComparator = new Comparator<Map.Entry<Book,Float>>() {

            @Override
            public int compare(Map.Entry<Book, Float> e1, Map.Entry<Book, Float> e2) {
                Float v1 = e1.getValue();
                Float v2 = e2.getValue();
                return v2.compareTo(v1);

            }
        };

        Set<Map.Entry<Book, Float>> entries = result.entrySet();

        // Sort method needs a List, so let's first convert Set to List in Java
        List<Map.Entry<Book, Float>> listOfEntries = new ArrayList<Map.Entry<Book, Float>>(entries);

        // sorting HashMap by values using comparator
        Collections.sort(listOfEntries, valueComparator);

        LinkedHashMap<Book, Float> sortedByValue = new LinkedHashMap<Book, Float>(listOfEntries.size());

        // copying entries from List to Map
        List<Book> topRated = new ArrayList<>();

        for(Map.Entry<Book, Float> entry : listOfEntries){
            sortedByValue.put(entry.getKey(), entry.getValue());
            topRated.add(entry.getKey());
        }

         System.out.println("HashMap after sorting entries by values ");
         Set<Map.Entry<Book, Float>> entrySetSortedByValue = sortedByValue.entrySet();

         for(Map.Entry<Book, Float> mapping : entrySetSortedByValue){
         System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
         }

        return topRated;
    }

    public void addUser()
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(this);
        tx.commit();
        session.close();
        System.out.println("user added");
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

    public boolean isOwned(Book book)
    {
        books = this.getBooks();
        for (BookUser b : books) {
            //change it using equals
            if (b.getBook().getISBN().equals(book.getISBN()))
                return true;
        }
        return false;
    }

    public void addBook(Book book)
    {
        if (books == null)
            books = new ArrayList<>();
        {
            BookUser bookUser = new BookUser(book, this);
            books.add(bookUser);
            bookUser.saveBookUser();
        }
    }

    @OneToMany(
            mappedBy = "user",
            targetEntity = BookUser.class,
            fetch = FetchType.EAGER
    )
    public List<BookUser> getBooks()
    {
        return books;
    }

    //skoro nie pozwala na brak pól dla ktorych sa gettersy
    //to coś jest nie tak z budową klasy
    public int retNumberOfBooks()
    {
        return books.size();
    }

    public String retFavoriteAuthor()
    {
        //za pomocą stramu zrobić
        if (!books.isEmpty()) {
            List<String> authors = books.stream().map(b -> b.getBook().getAuthor()).collect(Collectors.toList());

            List<String> favorites = new ArrayList<>();
            Map<String, Long> occurrences =
                    authors.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));

            System.out.println(occurrences);
            int c = 0;
            long maxValueInMap = (Collections.max(occurrences.values()));
            for (Map.Entry<String, Long> entry : occurrences.entrySet()) {
                if (entry.getValue() == maxValueInMap & c<2) {
                    favorites.add(entry.getKey());
                    c++;
                }
            }
            if (!favorites.isEmpty()) {
                StringBuilder sB = new StringBuilder();
                for (String s : favorites) {
                    sB.append(s);
                    sB.append(" | ");
                }
                String result = sB.toString();
                return result;
            } else
                return "You don't have books on your shelf";
        }
        return "";
    }

    @Transient
    public boolean isAdmin()
    {
        return admin == 1;
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

    public User()
    {
        this.admin = 0;
    }

    public User(String login, String password, String email)
    {
        this.login = login;
        this.password = password;
        this.email = email;
        this.addDate = new Date();
        this.admin = 0;
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
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", addDate=" + addDate +
                ", admin=" + admin +
                ", books=" + books +
                '}';
    }
}

