package chatting;

import java.awt.*;


import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

import Client.ClientGui;
import Server.ChatServer;

import java.sql.*;


import calendar.CalendarPanel;
import friend.FriendInformPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * 채팅방과 방생성, 방 참가등의 버튼이 위치할 패널 클래스.
 * 채팅 패널의 틀을 구성한다.
 */
public class ChatRoomPanel extends JFrame{
	
	private String myId;
	private RoomsPanel roomsPanel = new RoomsPanel(); // RoomsPanel 인스턴스를 필드로 선언
	
	/**
	 * 채팅 로비 메인패널
	 * @param myId
	 */
	public ChatRoomPanel(String myId) {
		this.myId = myId;
		// 타이틀과 프로그램 이름
        setTitle("홍톡 2.0");
        
		setBounds(300, 300, 600, 500);
        setResizable(false);
        setVisible(true);
        
        // 타이틀 패널 설정
        JLabel title = new JLabel("  채팅");
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Dialog", Font.BOLD, 20));
        
        // 방 생성 버튼 스타일 설정
        JButton room = new JButton("방 생성");
        room.setBackground(Color.WHITE);
        room.setForeground(Color.BLACK);
        //방 생성 버튼 눌렀을 때 이벤트 처리
        room.addActionListener(new ActionListener() {            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		/*
        		 * AddNewChatRoomPanel 객체 생성
        		 */
        		AddNewChatRoomPanel newRoom = new AddNewChatRoomPanel(myId, roomsPanel);
        		newRoom.createPanels();
            }
        });
        
        // 방 참가 버튼 스타일 설정
        JButton enterRoom = new JButton("방 참가");
        enterRoom.setBackground(Color.WHITE);
        enterRoom.setForeground(Color.BLACK);
        //방 참가 버튼 눌렀을 때 이벤트 처리(미완)
        enterRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * JoinChatRoomPanel 객체 생성
                 */
                JoinChatRoomPanel joinRoom = new JoinChatRoomPanel(myId, roomsPanel);
                joinRoom.joinPanels();
            }
        });

      
        // 타이틀과 방 생성, 방 참가 버튼을 담을 패널
        JPanel titleAndRoomPanel = new JPanel(new BorderLayout());
        // 타이틀은 패널의 왼쪽에 배치
        titleAndRoomPanel.add(title, BorderLayout.WEST);
        // 패널의 오른쪽에 위치할 패널 생성
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // 방 생성 버튼 추가
        buttonsPanel.add(room);
        // 방 참가 버튼 추가
        buttonsPanel.add(enterRoom);

        // 오른쪽에 위치할 패널을 패널의 오른쪽에 배치
        titleAndRoomPanel.add(buttonsPanel, BorderLayout.EAST);
  
        // 친구 정보 버튼 스타일 설정
        JButton inform = new JButton("친구");
        inform.setBackground(Color.WHITE);
        inform.setForeground(Color.BLACK);
        // 친구 버튼 눌렀을 때 이벤트 처리
        inform.addActionListener(new ActionListener() {            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		handleinform();
            }
        });

        // 채팅 패널  버튼 스타일 설정
        JButton chatting = new JButton("채팅");
        chatting.setBackground(Color.WHITE);
        chatting.setForeground(Color.BLACK);
        // 채팅 버튼 눌렀을 때 이벤트 처리
        chatting.addActionListener(new ActionListener() {            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		handlechat();
            }
        });
        
        
        // 캘린더 패널  버튼 스타일 설정
        JButton calender= new JButton("캘린더");
        calender.setBackground(Color.WHITE);
        calender.setForeground(Color.BLACK);
        // 캘린더 버튼 눌렀을 때 이벤트 처리
        calender.addActionListener(new ActionListener() {            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		handlecalender();
            }
        });
        
        //패널에 버튼추가 
        JPanel buttonPanel = createButtonPanel(inform, chatting, calender);
    
        add(titleAndRoomPanel, BorderLayout.NORTH);
        /*
         * 처음 패널 생성할때, 유저가 포함된 채팅방을 업데이트
         */
        addUserInRoomsPanel();
        add(roomsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

	}
	
	/*
	 * 버튼 추가하는 메서드
	 */
	private JPanel createButtonPanel(JButton inform, JButton chatting, JButton calender) {
        JPanel panel = new JPanel();
        panel.add(inform);
        panel.add(chatting);
        panel.add(calender);
        return panel;
    }
	
	/**
	 * "친구"버튼 눌렀을때 친구 패널을 생성하고 현재 창 종료
	 */
	private void handleinform() {
        new FriendInformPanel(myId);
        dispose(); // 현재의 frame을 종료시키는 메서드.
    }
	
	/**
	 * "채팅"버튼 눌렀을때 친구 패널을 생성하고 현재 창 종료
	 */
	
	private void handlechat() {
        new ChatRoomPanel(myId);
        dispose(); // 현재의 frame을 종료시키는 메서드.
    }
	/**
	 * "캘린더"버튼 눌렀을때 친구 패널을 생성하고 현재 창 종료
	 */
	private void handlecalender() {
        new CalendarPanel(myId);
        dispose(); // 현재의 frame을 종료시키는 메서드.
    }
	
	
	/**
	 * 사용자 채팅방에 추가하는 메소드. 사용자가 로그인했을때나, 창을 바꿀 때 호출되어서,
	 * 현재 채팅창에 유저가 포함된 채팅방을 보여주도록 업데이트한다
	 */
	public void addUserInRoomsPanel() {
	    
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rs = null;

	        try {
	            // JDBC 드라이버 로드
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // 데이터베이스 URL, 사용자 이름, 암호 설정
	            String url = "jdbc:mysql://localhost:3306/userinform";
	            String username = "joonsang";
	            String password = "0328";

	            // 데이터베이스 연결
	            conn = DriverManager.getConnection(url, username, password);

	            // SQL 쿼리 실행
	            stmt = conn.createStatement();
	            String sqlQuery = "SELECT chatRoomName, chatRoomId FROM userchatroom WHERE userId = ?;";
	            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
	            pstmt.setString(1, myId);
	            ResultSet rs1 = pstmt.executeQuery();


	            // 결과 처리
	            while (rs1.next()) {
	                String chatRoomName = rs1.getString("chatRoomName");
	                int chatRoomId = rs1.getInt("chatRoomId");
	                // 여기서 chatRoomName과 chatRoomId 변수에 결과를 저장할 수 있음
	                roomsPanel.addChatRoom(chatRoomName, chatRoomId);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            // 자원 해제
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}

