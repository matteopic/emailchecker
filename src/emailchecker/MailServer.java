package emailchecker;
import javax.mail.*;
import java.util.*;

public class MailServer{

	public void connect(String protocol, String username, String password, String host, int port)throws MessagingException{
		url = new URLName(protocol, host, port, null, username, password);
		Folder folder = null;

		Properties props = new Properties();
		props.put("mail." + url.getProtocol() + ".host", url.getHost());
		props.put("mail.user", url.getUsername());
		props.put("mail."+ url.getProtocol() +".timeout", "20000");

		Session session = Session.getInstance(props, null);
		session.setDebug(false);
		store = session.getStore(url);
		store.connect();
	}

	/**Chiude una connessione se è già attiva*/
	public void disconnect(){
		try{
			if (store != null && store.isConnected()){
				store.close();
				store = null;
			}
		}catch(Exception e){}
	}

	/**Restituisce se una connessione è attiva oppure no*/
	public boolean isConnected()throws MessagingException{
		if (store != null) return store.isConnected();
		return false;
	}

	public int getNewMessage()throws MessagingException{
		if (url.getProtocol().equals("pop3")){
			return getUnreadMessageCount();
		}

		else if (url.getProtocol().equals("imap")){
			return getNewMessageCount();
		}

		else return -1;
	}

	public int getNewMessageCount()throws MessagingException{
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		int numMex = folder.getNewMessageCount();
		folder.close(false);
		return numMex;
	}

	public int getUnreadMessageCount()throws MessagingException{
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		int numMex = folder.getUnreadMessageCount();
		folder.close(false);
		return numMex;
	}

	private Store store;
	private URLName url;
}