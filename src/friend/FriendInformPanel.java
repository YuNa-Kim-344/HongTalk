package friend;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

import calendar.*;
import chatting.*;

import java.util.ArrayList;
import java.util.List;


public class FriendInformPanel extends JFrame{
   private String myId;
   
   /*
    * 친구 창 생성자
    */
   public FriendInformPanel(String myId) {
      this.myId = myId;
      
      // 타이틀과 프로그램 이름
        setTitle("홍톡 2.0");
      
        setBounds(300, 300, 600, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
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

        // 채팅 패널 버튼 스타일 설정
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
        JButton  calender= new JButton("캘린더");
        calender.setBackground(Color.WHITE);
        calender.setForeground(Color.BLACK);
        // 캘린더 버튼 눌렀을 때 이벤트 처리
        calender.addActionListener(new ActionListener() {            
           @Override
            public void actionPerformed(ActionEvent e) {
              handlecalender();
            }
        });
        
        // 패널에 버튼 추가
        JPanel buttonPanel = createButtonPanel(inform, chatting, calender);
        add(buttonPanel, BorderLayout.SOUTH);
        
        /*
         * 화면 상단에 위치할 패널 생성. 사용자의 정보를 담고있다.
         */
        try {
           NorthPanel north = new NorthPanel(this.myId, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handlemyinform();
                      
                }        
            });
            //화면 상단에 추가
            add(north, BorderLayout.NORTH);   
            
      } catch (Exception e) {
         // TODO: handle exception
         e.printStackTrace();
         System.out.print("상단패널오류");
      }
        
      /*
       * 화면 중앙에 올 Center패널 객체 생성
       */
        CenterPanel center = new CenterPanel();
        add(center, BorderLayout.CENTER);
            
   }
   
   // 버튼패널을 만드는 메서드. 친구, 채팅, 캘린더 버튼을 하단에 추가함
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
   
   
   /**
    * 화면 상단의 내정보 클릭했을때 발생되는 이벤트??
    */
   private void handlemyinform() {      
       // 사용자로부터 비밀번호를 다이얼로그를 통해 입력 받습니다 (JPasswordField를 사용하는 것이 보안상 좋습니다)
       String enteredPassword = JOptionPane.showInputDialog(null, "비밀번호를 입력하세요:");

       // 입력된 비밀번호를 확인합니다.
       if (validatePassword(myId, enteredPassword)) {
           // 비밀번호가 올바르면 CkeckInformPanel로 이동합니다.
           new ChangInformPanel(myId);
           dispose();
         
       } else {
           // 비밀번호가 일치하지 않으면 경고 메시지를 표시하고 현재 창은 유지됩니다.
           JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.", "경고", JOptionPane.WARNING_MESSAGE);
       }
       
    }
 
   /**
    * 비밀번호 비교 메서드
    * @param userId
    * @param enteredPassword
    * @return
    */
   private boolean validatePassword(String userId, String enteredPassword) {
       // GetInformDB를 사용하여 사용자 정보 가져오기
       GetInformDB getInformDB = new GetInformDB(userId);
       User userInfo = getInformDB.getInform();

       // 사용자 정보에서 저장된 비밀번호 가져오기
       String storedPassword = userInfo.getUserPassword();

       // 입력된 비밀번호와 저장된 비밀번호 비교
       return enteredPassword.equals(storedPassword);
   }
   

}

/**
 * 화면 상단에 오는 패널으로, 사용자의 이름과 내 정보 버튼등을 포함
 */
class NorthPanel extends JPanel {
   
   private String myId;
   private ActionListener myInformListener;
   

    public NorthPanel(String userId, ActionListener myInformListener) {
       
       this.myId = userId;
       this.myInformListener = myInformListener;
        setLayout(new BorderLayout());
        
        try {
            // GetInformDB를 사용하여 사용자 정보 가져오기
            GetInformDB getInformDB = new GetInformDB(userId);
            User userInfo = getInformDB.getInform();
            
            // 내 정보 이름 
            JLabel name = new JLabel(" " + userInfo.getUserName());
            name.setForeground(Color.BLACK);
            name.setFont(new Font("Dialog", Font.BOLD, 30));
            

            // 내 정보 버튼 
            JButton myinform = new JButton("내 정보");
            myinform.setBackground(Color.WHITE);
            myinform.setForeground(Color.BLACK);
            // 내 정보 버튼 눌렀을 때 이벤트 처리 
            myinform.addActionListener(new ActionListener() {            
               @Override
                public void actionPerformed(ActionEvent e) {
                  handlemyinform();
                }
            });
            
            // 친구 
            JLabel friend = new JLabel("  친구");
            friend.setForeground(Color.BLACK);
            friend.setFont(new Font("Dialog", Font.BOLD, 15));

            // 버튼 스타일 설정
            JButton addFriend = new JButton("+");
            addFriend.setBackground(Color.WHITE);
            addFriend.setForeground(Color.BLACK);
            // 친구 추가 버튼 눌렀을 때 이벤트 처리 
            addFriend.addActionListener(new ActionListener() {            
               @Override
                public void actionPerformed(ActionEvent e) {
                  handleaddfriend();
                }
            });

            //이름 과 내정보 버튼 담을 패널과
            JPanel nameAndmyinform = new JPanel(new BorderLayout());
            nameAndmyinform.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //레이블과 버튼추가
            nameAndmyinform.add(name, BorderLayout.WEST);
            nameAndmyinform.add(myinform, BorderLayout.EAST);
            
            //친구 와 + 버튼 담을 패널
            JPanel friendAndaddFriend = new JPanel(new BorderLayout());
            nameAndmyinform.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //레이블과 버튼 추가
            friendAndaddFriend.add(friend, BorderLayout.WEST);
            friendAndaddFriend.add(addFriend, BorderLayout.EAST);
            
            // North에 위치할 패널을 담을 All 패널
            JPanel All = new JPanel(new BorderLayout());
            All.add(nameAndmyinform, BorderLayout.NORTH);
            All.add(friendAndaddFriend, BorderLayout.SOUTH);
             
            add(All);
            
            
            
      } catch (Exception e) {
         // TODO: handle exception
         e.printStackTrace();
      }
      
    }
    
    private void handlemyinform() {
        // 부모 프레임에 대한 참조를 전달하는 이벤트 발생
        if (myInformListener != null) {
            myInformListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
    }
    private void handleaddfriend() {
        // 부모 프레임에 대한 참조를 전달하는 이벤트 발생
    	System.out.println("1");
    	AddFriendInformPanel addFriendPanel = new AddFriendInformPanel(myId);
        
    }
    
    
}


class CenterPanel extends JPanel {
    private JPanel friendInfoPanel;
    private List<JPanel> roomPanels;
    private JScrollPane scrollPane;
    private int maxRooms = 5;

    public CenterPanel() {
    	// 패널 레이아웃 및 스크롤 설정
        setLayout(new BorderLayout());
        friendInfoPanel = new JPanel(new GridLayout(0, 1));
        scrollPane = new JScrollPane(friendInfoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 마우스 휠 이벤트 추가
        scrollPane.addMouseWheelListener(e -> mouseWheelMoves(e));

        // 스크롤 패널을 중앙에 추가
        add(scrollPane, BorderLayout.CENTER);

        // 방 목록 및 빈 공간 패널 초기화
        roomPanels = new ArrayList<>();
        addEmptyPanels(maxRooms - 1);
    }
    
    // 친구  목록 업데이트
    protected void updateFriendPanel(User friendUser) {
    	try {
    		friendInfoPanel.removeAll();

            for (JPanel friendPanel : roomPanels) {
            	friendInfoPanel.add(friendPanel);
            }
            for (int i = roomPanels.size(); i < maxRooms; i++) {
            	friendInfoPanel.add(new JPanel());
            }
            scrollPane.setViewportView(friendInfoPanel);
            
            JLabel nameLabel = new JLabel(" " + friendUser.getUserName());
            nameLabel.setForeground(Color.BLACK);
            nameLabel.setFont(new Font("Dialog", Font.BOLD, 30));

            JLabel emailLabel = new JLabel(" " + friendUser.getUserEmail());

            JPanel friendPanel = new JPanel(new BorderLayout());
            friendPanel.add(nameLabel, BorderLayout.WEST);
            friendPanel.add(emailLabel, BorderLayout.EAST);

            friendInfoPanel.add(friendPanel);
            revalidate();
            repaint();
    	} catch(Exception e) {
    		e.printStackTrace();
    		System.out.print("에러");
    	}
    	
    }
    
    // 빈 공간에 패널 추가
    private void addEmptyPanels(int count) {
        for (int i = 0; i < count; i++) {
        	friendInfoPanel.add(new JPanel());
        }
    }
    
    // 마우스 휠 이벤트 처리
    private void mouseWheelMoves(MouseWheelEvent e) {
        int n = e.getWheelRotation();

        if (roomPanels.size() >= maxRooms) {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(n < 0 ? vertical.getValue() + vertical.getUnitIncrement()
                                    : vertical.getValue() - vertical.getUnitIncrement());
            revalidate();
            repaint();
        }
        revalidate();
        repaint();
    }


}