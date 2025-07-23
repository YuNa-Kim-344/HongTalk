package friend;

public class Friendship {
	private String myId;
	public UserFriend user2;
	

    

    public Friendship(String myId, UserFriend user2) {
    	System.out.println(myId);
    	System.out.println(user2.getUserId());
    	System.out.println(user2.getUserName());
    	System.out.println(user2.getUserEmail());
    	this.myId = myId;     
        this.user2 = user2;
    }

    public String getUser1() {
        return myId;
        
    }

    public void setUser1(String myId) {
        this.myId = myId;
    }

    public UserFriend getUser2() {
        return user2;
    }

    public void setUser2(UserFriend user2) {
        this.user2 = user2;
    }
    
    
}
