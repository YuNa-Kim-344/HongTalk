package friend;

import java.sql.*;

public class DeleteInformDB {

    // DB 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/userinform";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Java1111";

    // 사용자 정보 삭제 메서드
    public static void deleteInform(String userId) {
        try {
            // 데이터베이스 연결
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            System.out.println("DB 연결 완료");

            // SQL 쿼리 작성
            String sql = "DELETE FROM userInform WHERE userId = ?";

            // PreparedStatement를 사용하여 SQL 쿼리 실행
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // 바인딩 변수에 값 할당
                pstmt.setString(1, userId);

                // 쿼리 실행
                int affectedRows = pstmt.executeUpdate();

                // 결과 확인
                if (affectedRows > 0) {
                    System.out.println("사용자 정보가 성공적으로 삭제되었습니다.");
                } else {
                    System.out.println("사용자를 찾을 수 없거나 삭제에 실패했습니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}