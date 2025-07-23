package friend;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import friend.User;

public class AddFriendInformPanel extends JFrame {
   
   private String myId;
   private JTextField friendIdField;
   private CenterPanel centerPanel; 
   
   public AddFriendInformPanel(String myId) {
      
      // 타이틀과 프로그램 이름
        setTitle("홍톡 2.0");
      
        setBounds(300, 300, 300, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        //타이틀 패널 설정
        JLabel title = new JLabel("친구 추가", JLabel.CENTER);
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Dialog", Font.BOLD, 30));
        
        add(title, BorderLayout.NORTH);
        
        // 추가  버튼 스타일 설정
        JButton friendadd = new JButton("추가");
        friendadd.setBackground(Color.WHITE);
        friendadd.setForeground(Color.BLACK);


        // 취소 버튼 스타일 설정
        JButton cancel = new JButton("취소");
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(Color.BLACK);

        JPanel buttonPanel = createButtonPanel(friendadd, cancel);
        add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel jp = new JPanel();
        
        // 아이디 레이블 생성,추가
        JPanel friendidPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel friendIdLabel = new JLabel("친구 아이디 : ", JLabel.CENTER);   
        friendidPanel.add(friendIdLabel);
        //아이디 텍스트필드 생성,추가
        JPanel friendIdft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField ft = new JTextField(16);
        friendIdft.add(ft);
        // 아이디 패널에 추가
        jp.add(friendidPanel);
        jp.add(friendIdft);
        
        add(jp, BorderLayout.CENTER);
        
        // CenterPanel 인스턴스 생성
        centerPanel = new CenterPanel();
        System.out.println("2");
        attachActionListeners(friendadd, ft, cancel);  
   }
   
   // 버튼 패널 생성 메서드
   private JPanel createButtonPanel(JButton friendadd, JButton cancel) {
       JPanel panel = new JPanel();
       panel.add(friendadd);
       panel.add(cancel);
       return panel;
   }
   
    private void attachActionListeners(JButton friendadd, JTextField ft, JButton cancel) {
           friendadd.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   String friendId = ft.getText().trim();
                   
                   try {
                	   
                	   // 데이터베이스에서 friendId의 정보 가져오기
                       GetInformDB friends = new GetInformDB(friendId);
                       User friendInfo = friends.getInform();
                       
                       
                       if (friendInfo != null) {
                           // 친구 정보를 찾았을 경우 CenterPanel을 업데이트
                           centerPanel.updateFriendPanel(friendInfo);
                           JOptionPane.showMessageDialog(null, "친구 추가 성공", "확인", JOptionPane.INFORMATION_MESSAGE);
                           dispose();
                       } else {
                           // 친구 정보를 찾지 못했을 경우 경고창 표시
                           JOptionPane.showMessageDialog(null, "존재하지 않는 ID 입니다.", "경고", JOptionPane.WARNING_MESSAGE);
                       }
                   }catch (Exception e1) {
					// TODO: handle exception
                	   e1.printStackTrace();
                   }
                   
               }
           });
           cancel.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   dispose();
               }
           });
           
    }
   

}