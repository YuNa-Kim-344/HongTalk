package login;
import java.sql.*;

public class AddNewUserInDB {
    // DB 연결 정보
    private final String DB_URL = "jdbc:mysql://localhost:3306/userinform";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "0328";
    
    private String userId;
    private String userPwd; 
    private String userName;
    private String userEmail;
    
    public AddNewUserInDB(String userId, String userPwd, String userName, String userEmail) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.userEmail = userEmail;
	}

    protected void addNewUser() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // JDBC 연결, password는 root 계정 패스워드 
            System.out.println("DB 연결 완료");
            
            // 새로운 사용자 추가하는 쿼리
            String query = "INSERT INTO userinform VALUES (?,?,?,?);";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.userId); // userId를 첫 번째 파라미터에 대입
            pstmt.setString(2, this.userPwd); // userPwd를 두 번째 파라미터에 대입
            pstmt.setString(3, this.userName); // userName을 세 번째 파라미터에 대입
            pstmt.setString(4, this.userEmail); // userEmail을 네 번째 파라미터에 대입
            
            int rowsAffected = pstmt.executeUpdate(); // 실행된 행 수를 반환

            if (rowsAffected > 0) {
                System.out.println("사용자 추가 완료");
            } else {
                System.out.println("사용자 추가 실패");
            }
            
            pstmt.close();
            conn.close();
            
        } catch (ClassNotFoundException e1) {
            System.out.println("JDBC 드라이버 로드 에러");
        } catch (SQLException e2) {
            System.out.println("SQL 실행 에러");
        }
    }

}