package friend;

import java.sql.*;


public class UserFriendDB {
	// DB 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/userinform";
    private static final String DB_USER = "joonsang";
    private static final String DB_PASSWORD = "0328";

    public static void saveFriendship(Friendship friendship) {
        try {
            // 데이터베이스 연결
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // JDBC 연결, password는 root 계정 패스워드 
            System.out.println("DB 연결 완료");
            
            // 친구 정보를 저장하기 위한 쿼리문 작성
            String query = "INSERT INTO userfriend (user1, user2, friendName, friendEmail) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            // 쿼리에 사용될 매개변수 설정
            pstmt.setString(1, friendship.getUser1());
            pstmt.setString(2, friendship.getUser2().getUserId());
            pstmt.setString(3, friendship.getUser2().getUserName()); // 친구의 이름을 사용
            pstmt.setString(4, friendship.getUser2().getUserEmail()); // 친구의 이메일을 사용
            
            // 출력
            System.out.println("정보를 가져왔습니다.");
            System.out.println("UserID: " + friendship.getUser1());
            System.out.println("UserFriendId: " + friendship.getUser2().getUserId());
            System.out.println("UserFriendName: " + friendship.getUser2().getUserName());
            System.out.println("UserFriendEmail: " + friendship.getUser2().getUserEmail());

            // 쿼리 실행
            pstmt.executeUpdate();

            // 자원 해제
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("SQL 실행 에러: " + e.getMessage());
        }
    }
}
