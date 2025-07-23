package friend;

import java.sql.*;


public class UpdateInformDB {

    // DB 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/userinform";
    private static final String DB_USER = "joonsang";
    private static final String DB_PASSWORD = "0328";
    
    private String privateuerId;
    private String userId;
    private String userPwd; 
    private String userName;
    private String userEmail;
    
    public UpdateInformDB(String userId, String newId, String newPwd, String newName, String newEmail) {
    	
    	this.privateuerId = userId;
		this.userId = newId;
		this.userPwd = newPwd;
		this.userName = newName;
		this.userEmail = newEmail;
	}
    
    protected void updateUserInfo() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            // JDBC 연결, password는 root 계정 패스워드 
            System.out.println("DB 연결 완료");
            
            // 쿼리문 작성
            String query = "UPDATE userinform SET userId=?, userPwd=?, userName=?, userEmail=? WHERE userId=?;";
          
            // PreparedStatement 생성
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, this.userId); // userId를 첫 번째 파라미터에 대입
            pstmt.setString(2, this.userPwd); // userPwd를 두 번째 파라미터에 대입 
            pstmt.setString(3, this.userName); // userName을 세 번째 파라미터에 대입
            pstmt.setString(4, this.userEmail); // userEmail을 네 번째 파라미터에 대입
            pstmt.setString(5, this.privateuerId);
                                                                                                                                                                                        
            int rowsAffected = pstmt.executeUpdate(); // 실행된 행 수를 반환
            

            if (rowsAffected > 0) {
                System.out.println("업데이트 성공 ");
            } else {
                System.out.println("업데이트 실패");
            }
            
            
            pstmt.close();
            conn.close();
            
        } catch (SQLException e) {
            System.out.println("SQL 실행 에러: " + e.getMessage()); // SQL 실행 오류 처리
        }
    }
    
}
