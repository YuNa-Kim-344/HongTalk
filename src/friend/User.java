package friend;

public class User {
	
	private static User currentUser; // 현재 사용자 정보를 저장하는 정적 변수

    private String userId;
    private String userName;
    private String userEmail;
    private String userPassword;

    // Constructors, getters, and setters

    // Constructor
    public User(String userId, String userPassword, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword; 
        
        // 새 인스턴스가 생성될 때 자신을 현재 사용자로 설정
        setCurrentUser(this);

   }
    
 // 현재 사용자를 설정하는 정적 메서드
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // 현재 사용자를 가져오는 정적 메서드
    public static User getCurrentUser() {
        return currentUser;
    }

    
    // 아이디 반환 메서드
    public String getUserId() {
        return userId;
    }

    // 사용자 id 설정하기 위한 메서드
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 이름 반환 메서드
    public String getUserName() {
        return userName;
    }
    
    //사용자 이름 설정하기 위한 메서드
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // 이메일 반환 메서드
    public String getUserEmail() {
        return userEmail;
    }

    //이메일 설정하기 위한 메서드
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    // 비번 반환 메서드
    public String getUserPassword() {
        return userPassword;
    }
    
    // 비번을 설정하기 위한 메서드
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

