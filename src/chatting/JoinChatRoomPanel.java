package chatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import login.CheckUserInDB;

public class JoinChatRoomPanel extends JFrame {

	 private String myId;
	 private RoomsPanel roomsPanel;
	        
	 //생성자
	public JoinChatRoomPanel(String myId, RoomsPanel roomsPanel) {
		this.myId = myId;
        this.roomsPanel =roomsPanel;
        // 타이틀과 프로그램 이름
        setTitle("홍톡 2.0");

        // 생성 메서드 호출
        joinPanels();

        setBounds(300, 300, 400, 200);
        setResizable(false);
        setVisible(true);
        setResizable(false); // 패널 크기 조정 불가능하도록 설정
	}
	
	
	 protected void joinPanels() { 
	 	/**
	     * 방 참가 버튼 누를 시에 호출되는 함수
	     */
        JPanel title = new JPanel();
        JLabel titleName = new JLabel("채팅방 참가");
        titleName.setForeground(new Color(5, 0, 153));
        titleName.setFont(new Font("Dialog", Font.BOLD, 30));
        title.add(titleName);
        
        // 입력 필드 패널 설정
        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(2, 2));
        
        /* 
         * 채팅방 이름 레이블 생성,추가
         */
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jlb1 = new JLabel("채팅방 번호(1000~3000) : ", JLabel.CENTER);    
        namePanel.add(jlb1);
        // 채팅방 이름 텍스트필드 생성,추가
        JPanel RoomNamePanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField jtf1 = new JTextField(16);
        RoomNamePanel2.add(jtf1);
        // 채팅방 이름 패널에 추가
        jp1.add(namePanel);
        jp1.add(RoomNamePanel2);
        
        
        // 채팅방 참 버튼 스타일 설정
        JPanel makeRoomButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jJoinRoom = new JButton("채팅방 참여");
        jJoinRoom.setBackground(Color.WHITE);
        jJoinRoom.setForeground(Color.BLACK); // 버튼 텍스트 색상
        makeRoomButton.add(jJoinRoom);
        
        //방번호 입력하고 enter 누르면 "채팅방 참여" 버튼 눌러지게함
        jtf1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "채팅방 참여" 버튼 누르기
                jJoinRoom.doClick();
            }
         });

        
        
        //취소버튼
        JPanel cancelButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton jcancel = new JButton("취소");
        jcancel.setBackground(Color.WHITE);
        jcancel.setForeground(Color.BLACK); // 버튼 텍스트 색상
        cancelButton.add(jcancel);
            
        
        // 채팅방 생성 버튼 버튼 패널에 추가
        jp1.add(makeRoomButton);
        jp1.add(cancelButton);
        
        JPanel jp2 = new JPanel();
        jp2.setLayout(new FlowLayout());
        jp2.add(jp1);
        
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(jp2, BorderLayout.CENTER);
        
    
        // AddNewChatRoomPanel.java 파일 내의 방 생성 버튼 이벤트 처리 부분
        jJoinRoom.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomId = jtf1.getText();
                CheckUserInDB check = new CheckUserInDB();
                
                try {
                	//아이디 get
                	int intRoomId = Integer.parseInt(roomId);
                	/*
                	 *  AddNewChatRoom 클래스의 객체 생성
                	 */
                    AddNewChatRoomInDB chatRoom = new AddNewChatRoomInDB(myId, intRoomId, roomsPanel);    
                	/*
                	 * 방 번호 조건 검사. 
                	 * 1. 방 번호 범위 검사
                	 */
                	if(intRoomId >= 5001 || intRoomId <= 1000){
                		JOptionPane.showMessageDialog(null, "올바른 방 번호를 입력하세요.");
                	}
                	//2. 방 번호가 존재하는지 체크
                	else if(!chatRoom.checkRoomInDB()) {
                		JOptionPane.showMessageDialog(null, "해당 번호의 방이 존재하지 않습니다.");
                	}
                	//3. 만약 user가 이미 포함되어있다면 추가하지 않는다.
                	else if(check.checkUserInChatRoom(myId, String.valueOf(intRoomId))){
                		JOptionPane.showMessageDialog(null, "이미 방에 참여해 있습니다.");
                	}
                	// 조건 만족할경우 chatroom 객체를 통해 addNewChatRoom 메서드 호출
                	else {
                		chatRoom.addNewChatRoom(intRoomId);
                		JOptionPane.showMessageDialog(null, "참가 완료.");
                		dispose();
                	}
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "숫자를 입력하세요.");
					e2.printStackTrace();
				}
       
            }
        });
        
        jcancel.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
