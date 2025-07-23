package chatting;

import javax.swing.*;

import javax.swing.text.NumberFormatter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;
/**
 * 새로운 방 추가하는 버튼 관리하는 클래스
 */
public class AddNewChatRoomPanel extends JFrame {

    private String myId;
    private RoomsPanel roomsPanel;
        
    //생성자
    public AddNewChatRoomPanel(String myId, RoomsPanel roomsPanel) {
        this.myId = myId;
        this.roomsPanel =roomsPanel;
        // 타이틀과 프로그램 이름
        setTitle("홍톡 2.0 방 생성");

        // 생성 메서드 호출
        createPanels();

        setBounds(300, 300, 400, 300);
        setResizable(false);
        setVisible(true);
        setResizable(false); // 패널 크기 조정 불가능하도록 설정
    }
    
    protected void createPanels() {
        JPanel title = new JPanel();
        JLabel titleName = new JLabel("홍톡 2.0");
        titleName.setForeground(new Color(5, 0, 153));
        titleName.setFont(new Font("Dialog", Font.BOLD, 30));
        title.add(titleName);
        
        // 입력 필드 패널 설정
        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(3, 2));
        
        /* 
         * 채팅방 이름 레이블 생성,추가
         */
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel jlb1 = new JLabel("채팅방 이름 : ", JLabel.CENTER);    
        namePanel.add(jlb1);
        // 채팅방 이름 텍스트필드 생성,추가
        JPanel RoomNamePanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField jtf1 = new JTextField(16);
        RoomNamePanel2.add(jtf1);
        // 채팅방 이름 패널에 추가
        jp1.add(namePanel);
        jp1.add(RoomNamePanel2);
   
        
        // 채팅방 생성 버튼 스타일 설정
        JPanel makeRoomButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jmakeRoom = new JButton("채팅방 생성");
        jmakeRoom.setBackground(Color.WHITE);
        jmakeRoom.setForeground(Color.BLACK); // 버튼 텍스트 색상
        makeRoomButton.add(jmakeRoom);
        //방 번호 입력하고 enter누르면 실행되게함
        jtf1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "채팅방 생성" 버튼 누르기
                jmakeRoom.doClick();
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
        jmakeRoom.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                String RoomName = jtf1.getText();
                Random random = new Random();
                while(true) {
                	try {
                    	// 방 아이디 생성. 1000 이상 5000 이하 임의의 랜덤한 숫자로 설정
                    	int intRoomId = random.nextInt(4001) + 1000;
                    	// AddNewChatRoom 클래스의 객체 생성
                        AddNewChatRoomInDB chatRoom = new AddNewChatRoomInDB(myId, RoomName, intRoomId, roomsPanel);
                      
                    	//방 번호 조건 검사. 	
                    	//1. 방 번호가 존재하는지 체크 -> 번호 다시 생성하여 방만들기
                    	if(chatRoom.checkRoomInDB()) {
                    		System.out.println("해당번호 방존재");
                    	}
                    	else {               		
                            // 생성된 객체를 통해 addNewUser 메서드 호출
                    		chatRoom.addNewChatRoom();
                            roomsPanel.addChatRoom(RoomName, intRoomId);
                            dispose();      
                            break;
                    	}
    				} catch (Exception e2) {
    					// TODO: handle exception
    					e2.printStackTrace();
    				}
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
