package calendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import chatting.*;
import friend.*;

public class CalendarPanel extends JFrame{
	private String myId;
	public CalendarPanel(String myId) {
		this.myId = myId;
		// 타이틀과 프로그램 이름
        setTitle("홍톡 2.0 ");
		
		setBounds(300, 300, 600, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
		
		// 타이틀 패널 설정
        JLabel title = new JLabel("  캘린더");
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Dialog", Font.BOLD, 20));
		
        // 친구 정보 버튼 스타일 설정
        JButton inform = new JButton("친구");
        inform.setBackground(Color.WHITE);
        inform.setForeground(Color.BLACK);
        // 친구 버튼 눌렀을 때 이벤트 처리
        inform.addActionListener(new ActionListener() {            @Override
            public void actionPerformed(ActionEvent e) {
        		handleinform();
            }
        });
        

        // 채팅 패널  버튼 스타일 설정
        JButton chatting = new JButton("채팅");
        chatting.setBackground(Color.WHITE);
        chatting.setForeground(Color.BLACK);
        // 채팅 버튼 눌렀을 때 이벤트 처리
        chatting.addActionListener(new ActionListener() {            @Override
            public void actionPerformed(ActionEvent e) {
        		handlechat();
            }
        });
        
        
        // 캘린더 패널  버튼 스타일 설정
        JButton  calender= new JButton("캘린더");
        calender.setBackground(Color.WHITE);
        calender.setForeground(Color.BLACK);
        // 캘린더 버튼 눌렀을 때 이벤트 처리
        calender.addActionListener(new ActionListener() {            @Override
            public void actionPerformed(ActionEvent e) {
        		handlecalender();
            }
        });
        
        
        //패널에 버튼 추가
        JPanel buttonPanel = createButtonPanel(inform, chatting, calender);
    
        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
       
	}
	
	private JPanel createButtonPanel(JButton inform, JButton chatting, JButton calender) {
        JPanel panel = new JPanel();
        panel.add(inform);
        panel.add(chatting);
        panel.add(calender);
        return panel;
    }
	
	private void handleinform() {
        new FriendInformPanel(myId);
        dispose(); // 현재의 frame을 종료시키는 메서드.
    }
	
	private void handlechat() {
        new ChatRoomPanel(myId);
        dispose(); // 현재의 frame을 종료시키는 메서드.
    }
	
	private void handlecalender() {
        new CalendarPanel(myId);
        dispose(); // 현재의 frame을 종료시키는 메서드.
    }

}
