package model;

import model.HibernateUtil;
import model.elements.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookUploader
{
    public static void main(String... args) {
     //   HibernateUtil.loadSessionFactory();
      //  readBooksFromCSV("C:/Users/Paula/libra/src/main/resources/images/books.csv");
    }

    private static void readBooksFromCSV(String fileName) {

        Path pathToFile = Paths.get(fileName);

        System.out.println(pathToFile.toAbsolutePath());
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.UTF_8)) {

            String line = br.readLine();
            while (line != null) {

                String[] a = line.split(",");
                //  a[5] = a[5].replaceAll("[^0-9]+", "");

                for (String s : a)
                    System.out.println(s);
                Book book = new Book(a[0], a[1], a[2], a[3], a[4], Integer.parseInt(a[5]), a[6]);
                Thread.sleep(2000);
                book.addBook();
                line = br.readLine();
            }

        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        }
    }
}
