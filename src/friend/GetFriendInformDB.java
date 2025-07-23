package friend;

import java.sql.*;

public class GetFriendInformDB {

    // DB 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/userinform";
    private static final String DB_USER = "joonsang";
    private static final String DB_PASSWORD = "0328";
    
    private String userId;
    
    public GetFriendInformDB(String userId) {
    	this.userId = userId;
    }
    
    protected UserFriend getInform() {
        UserFriend user = null;

        try {
            // 데이터베이스 연결
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            // 쿼리문 작성
            String query = "SELECT * FROM userinform WHERE userId = ?";
            // PreparedStatement 생성
            PreparedStatement pstmt = conn.prepareStatement(query);

            // 쿼리에 사용될 매개변수 설정
            pstmt.setString(1, this.userId);

            // 쿼리 실행
            ResultSet resultSet = pstmt.executeQuery();

            // 결과 처리
            if (resultSet.next()) {
            	String userId = resultSet.getString("userId");
                String userPassword = resultSet.getString("userPwd");
                String userName = resultSet.getString("userName");
                String userEmail = resultSet.getString("userEmail");

                // Create a User object with the retrieved information
                user = new UserFriend(userId, userPassword, userName, userEmail);
                
                // 출력
                System.out.println("정보를 가져왔습니다.");
                System.out.println("UserID: " + user.getUserId());
                System.out.println("UserName: " + user.getUserName());
                System.out.println("UserEmail: " + user.getUserEmail());
                System.out.println("UserPassword: " + user.getUserPassword());
            }

            // 자원 해제
            resultSet.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("SQL 실행 에러: " + e.getMessage()); // SQL 실행 오류 처리
        }

        return user;
    }

}
