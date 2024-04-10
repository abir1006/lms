package dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade.StorageType;
import dataaccess.User;


public class aa implements DataAccess {

    enum StorageType {
        BOOKS, MEMBERS, USERS;
    }

    // For Mac Users path can use /
    public static final String OUTPUT_DIR = System.getProperty("user.dir")
            + "/SwingProject/src/dataaccess/storage";


    @Override
    public HashMap<String, Book> readBooksMap() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserMap() {
        //Returns a Map with name/value pairs being
        //   userId -> User
        return (HashMap<String, User>)readFromStorage(dataaccess.DataAccessFacade.StorageType.USERS);
    }

    @Override
    public HashMap<String, LibraryMember> readMemberMap() {
        return null;
    }

    @Override
    public void saveNewMember(LibraryMember member) {

    }

    static void loadUserMap(List<User> userList) {
        HashMap<String, User> users = new HashMap<String, User>();
        userList.forEach(user -> users.put(user.getId(), user));
    }


    public static Object readFromStorage(dataaccess.DataAccessFacade.StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(Exception e) {}
            }
        }
        return retVal;
    }

    public static void main(String[] args) {
        aa c = new aa();
        Object user = c.readFromStorage(DataAccessFacade.StorageType.USERS);
        System.out.println(user);
    }
}
