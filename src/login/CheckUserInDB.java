package login;
import java.sql.*;

public class CheckUserInDB {
    // DB 연결 정보
    private final String DB_URL = "jdbc:mysql://localhost:3306/userinform";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "0328";

    /**
     * 사용자 Id 존재하는지 확인하는 메서드.
     * @param userId
     * @return boolean
     */
    public boolean checkUserID(String userId) {
        boolean userIdExists = false;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            /*
             * 사용자 Id가 DB에 존재하는지 확인하는 쿼리. userinform 테이블에서 userID 키를 사용하여 탐색
             */
            String query = "SELECT EXISTS (SELECT * FROM userinform WHERE userId = ?) AS isData;";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                userIdExists = resultSet.getBoolean("isData");
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("SQL 실행 에러: " + e.getMessage());
        }

        return userIdExists;
    }

    /**
     * 사용자 email 있는지 확인하는 메서드.
     * @param userEmail
     * @return boolean
     */
    public boolean checkUserEmail(String userEmail) {
        boolean userEmailExists = false;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT EXISTS (SELECT * FROM userinform WHERE userEmail = ?) AS isData;";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userEmail);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                userEmailExists = resultSet.getBoolean("isData");
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("SQL 실행 에러: " + e.getMessage());
        }

        return userEmailExists;
    }

    /**
     * 사용자 비밀번호 있는지 확인하는 메서드.   
     * @param userPwd
     * @return boolean
     */
    public boolean checkUserPwd(String userPwd) {
        boolean userPwdExists = false;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT EXISTS (SELECT * FROM userinform WHERE userPwd = ?) AS isData;";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userPwd);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                userPwdExists = resultSet.getBoolean("isData");
            }
            
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("SQL 실행 에러: " + e.getMessage());
        }
        return userPwdExists;
    }
    /**
     * 사용자 로그인 요청 시, 아이디와 비밀번호가 일치하는 사용자 정보가 존재하는지 확인
     * @param userId, userPwd
     * @return boolean
     */
    public boolean checkUserInform(String userId, String userPwd) {
    	boolean userPwdExists = false;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT EXISTS (SELECT * FROM userinform WHERE userId = ? AND userPwd = ?) AS isData;";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPwd);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                userPwdExists = resultSet.getBoolean("isData");
            }
            
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("SQL 실행 에러: " + e.getMessage());
        }
        return userPwdExists;
	}
    
    /**
     * 유저가 방에 존재하는지 검사하는 메소드
     * @param userId
     * @param userPwd
     * @return
     */
    public boolean checkUserInChatRoom(String userId, String roomId) {
        boolean userExistsInRoom = false;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //userchatroom 테이블에서 roomId에 해당하는 room에 userId가 존재하는지 확인하는 쿼리 작성
            String query = "SELECT COUNT(*) AS userCount FROM userchatroom WHERE userId = ? AND chatRoomId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, roomId);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int userCount = resultSet.getInt("userCount");
                // room에 userId가 존재하면 userCount가 1 이상이 될 것이므로 true 반환
                userExistsInRoom = userCount > 0;
            }
            
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("SQL 실행 에러: " + e.getMessage());
        }
        return userExistsInRoom;
    }
}
