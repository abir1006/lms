package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public String login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
        return currentAuth.toString();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

    @Override
    public List<String> allBooks() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }

    @Override
    public HashMap<String,Book> getAllBooks() {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> retval = da.readBooksMap();
        return retval;

    }

    @Override
    public HashMap<String, LibraryMember> getAllMembers() {
        DataAccess da = new DataAccessFacade();
        HashMap<String, LibraryMember> retval = da.readMemberMap();
        return retval;
    }

    @Override
    public boolean checkRecord(String memberId, String Isbn) {
        DataAccess da = new DataAccessFacade();
        boolean IsbnFound = false;
        boolean memberIdFound = false;

        for(Map.Entry<String, Book> h: da.readBooksMap().entrySet()){
//            System.out.println(h.getValue());
            if(h.getValue().getIsbn().equals(Isbn)){
                IsbnFound = true;
            }
        }
        //48-56882 ====1004
        for(Map.Entry<String, LibraryMember> h: da.readMemberMap().entrySet()){
            if(h.getValue().getMemberId().equals(memberId)){
                memberIdFound = true;
            }
        }
        if(IsbnFound && memberIdFound)
            return true;
        return false;
    }


}
