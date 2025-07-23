package chatting;

import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/**
 * DB와 연결하여 새로운 채팅방을 생성하는 클래스
 */
public class AddNewChatRoomInDB {
	// DB 연결 정보
    private final String DB_URL = "jdbc:mysql://localhost:3306/userinform";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "0328";
    
    private String userId;
    private String chatRoomName;
    private int chatRoomId;
    private RoomsPanel roomsPanel;
    
	//생성자
    public AddNewChatRoomInDB(String userId, int chatRoomId, RoomsPanel roomsPanel) {
    	this.userId = userId;
    	this.chatRoomId = chatRoomId;
    	this.roomsPanel = roomsPanel;
    }
    //오버로드
    public AddNewChatRoomInDB(String userId, String chatRoomName, int chatRoomId, RoomsPanel roomsPanel) {
    	this.userId = userId;
    	this.chatRoomName = chatRoomName;
    	this.chatRoomId = chatRoomId;
    	this.roomsPanel = roomsPanel;
    }
    
    /**
     * 새로운 방을 생성하여 패널에 추가하는 메서드
     */
    protected void addNewChatRoom() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

	            // JDBC 연결, password는 root 계정 패스워드 
	            System.out.println("DB 연결 완료");
	            
	            // 새로운 채팅방 추가하는 쿼리
	            String query = "INSERT INTO userchatroom VALUES (?,?,?);";
	            PreparedStatement pstmt = conn.prepareStatement(query);
         
	            pstmt.setString(1, this.userId); // userId를 첫 번째 파라미터에 대입
	            pstmt.setString(2, this.chatRoomName); // RoomName을 두 번째 파라미터에 대입
	            pstmt.setInt(3, this.chatRoomId); // RoomId을 세 번째 파라미터에 대입
	         
	            int rowsAffected = pstmt.executeUpdate(); // 실행된 행 수를 반환

	            if (rowsAffected > 0) {
	                System.out.println("채팅방 생성 완료");
	            } else {
	                System.out.println("채팅방 생성 실패");
	            }

	            pstmt.close();
	            conn.close();
	            
		}catch (ClassNotFoundException e1) {
            System.out.println("JDBC 드라이버 로드 에러");
        } catch (SQLException e2) {
            System.out.println("SQL 실행 에러");
        }
    }
    
    /**
     * 방 참가 버튼으로 방을 추가할때 사용하는 메서드.
     * parameter 없는 버전의 addNewChatRoom을 메서드 오버로드함
     * @param chatRoomName
     */
    protected void addNewChatRoom(int intRoomId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

	            // JDBC 연결, password는 root 계정 패스워드 
	            System.out.println("DB 연결 완료");
	            
	            String roomName = ""; // roomName 변수 초기화

	            String query = "SELECT chatRoomName FROM userchatroom WHERE chatRoomId = ?";
	            PreparedStatement pstmt = conn.prepareStatement(query);
	            pstmt.setInt(1, intRoomId); // roomId를 첫 번째 파라미터에 대입

	            ResultSet resultSet = pstmt.executeQuery(); // SELECT 쿼리 실행

	            if (resultSet.next()) {
	                roomName = resultSet.getString("chatRoomName"); // 결과를 roomName 변수에 저장
	            } 
	            // argument로 받은 roomId에 해당하는 roomName을 찾는 쿼리
	            String query2 = "INSERT INTO userchatroom VALUES (?,?,?);";
	            PreparedStatement pstmt2 = conn.prepareStatement(query2);
         
	            pstmt2.setString(1, this.userId); // userId를 첫 번째 파라미터에 대입
	            pstmt2.setString(2, roomName); // RoomName을 두 번째 파라미터에 대입
	            pstmt2.setInt(3, intRoomId); // RoomId을 세 번째 파라미터에 대입
	         
	            int rowsAffected = pstmt2.executeUpdate(); // 실행된 행 수를 반환
	            roomsPanel.addChatRoom(roomName, intRoomId);

	            if (rowsAffected > 0) {
	                System.out.println("채팅방 참가 완료");
	            } else {
	                System.out.println("채팅방 참가 실패");
	            }

	            pstmt.close();
	            conn.close();
	            
		}catch (ClassNotFoundException e1) {
            System.out.println("JDBC 드라이버 로드 에러");
        } catch (SQLException e2) {
            System.out.println("SQL 실행 에러: " + e2.getMessage());
            e2.printStackTrace(); 
        }

    }
    
    /**
     * 방이 존재하는지 체크. DB에 연결하여 
     * @return boolean
     */
    protected boolean checkRoomInDB() {
    	boolean userPwdExists = false;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT EXISTS (SELECT * FROM userchatroom WHERE chatRoomId = ?) AS isData;";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoomId);

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
}
