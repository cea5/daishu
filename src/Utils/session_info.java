package Utils;


public class session_info {
	
	public static String getSessionId (String session){
		String[] sessionArray = session.split(";");
		return sessionArray[0].substring(11); 
	}
}
