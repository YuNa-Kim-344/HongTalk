package friend;

public class UserFriend {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPassword;

    // Constructors, getters, and setters

    // Constructor
    public UserFriend(String userId, String userPassword, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword; 

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

