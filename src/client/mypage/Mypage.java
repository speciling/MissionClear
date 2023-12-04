package client.mypage;

import java.awt.*;

import client.CustomFont;
import client.db.ClientDBManager;
import client.net.ClientSocket;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

import javax.swing.*;
import client.recruitpage.Group;
import org.json.simple.JSONArray;
import server.service.Request;
import server.service.RequestType;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.simple.JSONObject;

/**
 * @author 최지원
 * 
 * 사각형의 바텀부분에만 검정색 선을 더하기 위해 JPanel을 커스텀한 클래스
 *
 */
class CustomPanel extends JPanel {

   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 현재 패널의 너비와 높이 구하기
        int width = getWidth();
        int height = getHeight();

        // 아래쪽에 1px의 검정색 선 그리기
        g.setColor(Color.BLACK);
        g.drawLine(0, height - 1, width, height - 1);
    }
}
   
/**
 * 사각형의 모서리를 둥글게 만들기 위해 JPanel 커스텀한 클래스
 * */
class RoundedPanel2 extends JPanel {
    private int radius;

    public RoundedPanel2(int radius) {
        this.radius = radius;
        setOpaque(false); // 패널 배경을 투명하게 설정
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 모서리가 둥근 사각형을 그림
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, radius, radius));
    }
}

/**
 * 
 * mypage 클래스
 *
 */
public class Mypage {

    CustomFont customFont = new CustomFont();
    public JPanel box;
    public CustomPanel missionProgressPanel;
    public CustomPanel missionEndProgressPanel;
    public RoundedPanel2 missionInProgress;
    public RoundedPanel2 endMissionInProgress;
    private JTextField nicknameField;
    private String newNickname;
    private String filePath;
    private int uid;
    private JScrollPane scrollMissionInProgress;
    private JScrollPane scrollEndMissionInProgress;

    public JPanel get() {
       return box;
    }
   
    /**
     * 화면에 보이게 하기 위한 생성자
     *
     */
    public Mypage(int uid, String nickname, String picPath) {
        this.uid=uid;
        box = new JPanel();       
        box.setBackground(new Color(246, 246, 246));
        box.setBounds(0,0,943,781);
        box.setLayout(null);
        
        missionInProgress = new RoundedPanel2(32);
        missionInProgress.setForeground(new Color(255, 255, 255));
        scrollMissionInProgress = new JScrollPane(missionInProgress);
        scrollMissionInProgress.setBounds(29, 249, 420, 462);
        scrollMissionInProgress.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollMissionInProgress.setBorder(null);

        box.add(scrollMissionInProgress);
        missionInProgress.setLayout(null);

        
        JLabel lblNewLabel_2_1 = new JLabel("진행중인 미션");
        lblNewLabel_2_1.setFont(customFont.deriveFont(Font.BOLD, 20));
        lblNewLabel_2_1.setBounds(135, 10, 121, 24);
        missionInProgress.add(lblNewLabel_2_1);
       
        showOngoingMission();

        endMissionInProgress = new RoundedPanel2(32);
        endMissionInProgress.setForeground(new Color(255, 255, 255));
        scrollEndMissionInProgress = new JScrollPane(endMissionInProgress);
        scrollEndMissionInProgress.setBounds(476, 249, 420, 462);
        scrollEndMissionInProgress.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollEndMissionInProgress.setBorder(null);

        box.add(scrollEndMissionInProgress);
        endMissionInProgress.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("종료된 미션");
        lblNewLabel_2.setFont(customFont.deriveFont(Font.BOLD, 20));
        lblNewLabel_2.setBounds(158, 10, 102, 35);
        endMissionInProgress.add(lblNewLabel_2);

        showFinishedMission();

        JButton PFPButton = new JButton();
        PFPButton.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        PFPButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               changePFP();
           }
        });
        ImageIcon icon = new ImageIcon(picPath);
        Image img = icon.getImage();
        Image updateImg = img.getScaledInstance(149,149,Image.SCALE_SMOOTH);
        ImageIcon updateIcon = new ImageIcon(updateImg);

        PFPButton.setIcon(updateIcon);
        PFPButton.setBounds(388, 10, 149, 149);
        PFPButton.setHorizontalAlignment(PFPButton.CENTER);
        PFPButton.setContentAreaFilled(false);
        PFPButton.setBorderPainted(false);
        box.add(PFPButton);

        JLabel lblNewLabel_1 = new JLabel(nickname+" 님");
        lblNewLabel_1.setBounds(384, 171, 124, 35);
        lblNewLabel_1.setFont(customFont.deriveFont(Font.BOLD, 20));
        box.add(lblNewLabel_1);

        JButton btnNewButton = new JButton("");
        btnNewButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              changeNickName();
           }
        });
        btnNewButton.setBounds(517, 176, 25, 25);
        btnNewButton.setIcon(new ImageIcon(Mypage.class.getResource("/mypage/pencil.png")));
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setBorderPainted(false);
        box.add(btnNewButton);

   }

    /**서버에 닉네임 변경을 요청하는 함수*/
    public void changeNickName() {
        //서버에 닉네임 변경을 요청하는 함수
      JFrame changeNickNamePopUp = new JFrame();
      changeNickNamePopUp.setSize(355,240);
      changeNickNamePopUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      changeNickNamePopUp.setLocation(570, 230);
      
      RoundedPanel2 inputNickNamePanel = new RoundedPanel2(20);
      inputNickNamePanel.setBounds(0,0,339,199);
      inputNickNamePanel.setForeground(new Color(255,255,255));
      inputNickNamePanel.setBackground(new Color(255,255,255));
      changeNickNamePopUp.getContentPane().add(inputNickNamePanel);
      inputNickNamePanel.setLayout(null);
      
      JLabel nicknameLabel = new JLabel("닉네임 변경하기");
      nicknameLabel.setFont(customFont.deriveFont(Font.BOLD, 18));
      nicknameLabel.setBounds(106, 31, 130, 35);
      inputNickNamePanel.add(nicknameLabel);
       
      nicknameField = new JTextField();
      nicknameField.setBounds(13, 74, 314, 35);
      nicknameField.setBackground(new Color(237, 237, 237));
      nicknameField.setBorder(null);
      inputNickNamePanel.add(nicknameField);
      nicknameField.setColumns(10);
       
       
        
      JButton enterButton = new JButton("");
      enterButton.setIcon(new ImageIcon("./resource/mypage/changeButton.png"));
      enterButton.setBounds(115, 150, 110, 38);
      enterButton.setContentAreaFilled(false);
      enterButton.setBorderPainted(false);
      inputNickNamePanel.add(enterButton);

      nicknameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            newNickname = nicknameField.getText();
                            JSONObject a = new JSONObject();
                            a.put("nickname", newNickname);
                            System.out.println(newNickname);
                            Request request = new Request(RequestType.CHANGENICKNAME,a);
                            ClientSocket.send(request);
                            changeNickNamePopUp.dispose();
                        }
                    });
                }
            }
        });

       changeNickNamePopUp.setVisible(true);
    }

    
    /**서버에 프로필 사진 변경을 요청하는 함수*/
    public void changePFP() {
        JFileChooser fileChooser = new JFileChooser();

        // Create a filter to show only image files (jpg and png)
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "png");
        fileChooser.setFileFilter(imageFilter);

        int result = fileChooser.showOpenDialog(null);

        // 파일을 선택하지 않은 경우 종료합니다.
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        // 선택한 파일을 가져옵니다.
        File selectedFile = fileChooser.getSelectedFile();

        // 선택한 파일이 사진 파일인지 확인합니다.
        if (!selectedFile.getName().toLowerCase().endsWith(".jpg") &&
                !selectedFile.getName().toLowerCase().endsWith(".png")) {
            JOptionPane.showMessageDialog(null, "사진 파일을 선택하세요.");
            return;
        }
        
        filePath = selectedFile.getAbsolutePath();

        JSONObject a = new JSONObject();
        a.put("filePath", filePath);
        Request request = new Request(RequestType.CHANGEPFP,a);
        ClientSocket.send(request);
    }
    
    /**진행중인 미션을 보여주는 함수*/
    public void showOngoingMission() {
       missionInProgress.removeAll();
       int y = 0;
       int panelHeight = 107;
       int verticalGap = 0;
       
       List<Group> groupList = ClientDBManager.getMyGroupList();
       for (Group group : groupList) {
            CustomPanel missionProgressPanel = new CustomPanel();
            missionProgressPanel.setBackground(Color.WHITE); 
            missionProgressPanel.setLayout(null); 

            missionProgressPanel.setPreferredSize(new Dimension(374, 107));
            missionProgressPanel.setMinimumSize(new Dimension(374, 107));
            missionProgressPanel.setMaximumSize(new Dimension(374, 107));

            JLabel ongoingGroupName = new JLabel();
            ongoingGroupName.setFont(customFont.deriveFont( Font.PLAIN, 25));
            ongoingGroupName.setText(group.getTitle());
            ongoingGroupName.setBounds(12, 10, 350, 25); 
            missionProgressPanel.add(ongoingGroupName);

            JProgressBar progressBar = new JProgressBar();
            progressBar.setStringPainted(true);
            progressBar.setBounds(12, 55, 350, 20);

            int cnt = 0;
            JSONArray a = ClientDBManager.getGroupProgress(group.getGid());
            for (int i = 0; i < a.size(); i++) {
                JSONObject o = (JSONObject) a.get(i);
                int uid = Integer.parseInt(o.get("uid").toString());
                if (uid == this.uid) {
                    cnt++;
                }
            }
            String startDate = group.getStartDateYear() + "-" + group.getStartDateMonth() + "-" + group.getStartDateDay();
            String endDate = group.getEndDateYear() + "-" + group.getEndDateMonth() + "-" + group.getEndDateDay();
            float period = (float) ChronoUnit.DAYS.between(LocalDate.parse(startDate), LocalDate.parse(endDate)) + 1;
            int progressValue = (int) (cnt / period * 100);
            progressBar.setValue(progressValue);

            missionProgressPanel.add(progressBar);

            missionInProgress.add(missionProgressPanel);
            missionInProgress.add(Box.createRigidArea(new Dimension(0, verticalGap))); 

            y += panelHeight + verticalGap;
        }

        if(missionEndProgressPanel!=null){
           missionInProgress.setPreferredSize(new Dimension(374, y));
            missionEndProgressPanel.revalidate();
            missionEndProgressPanel.repaint();
        }
    

    }



    /** 완료된 미션을 보여주는 함수*/
    public void showFinishedMission() {
        //완료된 미션을 보여주는 함수
        int x=470, y=56;
        List<Group> groupList = ClientDBManager.getMyEndedGroupList();
        for(Group group: groupList){
            missionEndProgressPanel = new CustomPanel();
            missionEndProgressPanel.setBackground(new Color(255, 255, 255));


            missionEndProgressPanel.setBounds(x, y, 374, 107);
            endMissionInProgress.add(missionEndProgressPanel);
            missionEndProgressPanel.setLayout(null);

            JLabel endedGroupName = new JLabel();
            endedGroupName.setFont(customFont.deriveFont( Font.PLAIN, 25));
            endedGroupName.setText(group.getTitle());

            endedGroupName.setBounds(12, 10, 265, 35);
            missionEndProgressPanel.add(endedGroupName);

            int cnt=0;
            JSONArray a = ClientDBManager.getGroupProgress(group.getGid());
            for(int i=0;i<a.size();i++){
                JSONObject o = (JSONObject) a.get(i);
                int uid= Integer.parseInt(o.get("uid").toString());

                if(uid==this.uid){
                    cnt++;
                }
            }
            String startDate = group.getStartDateYear()+"-"+group.getStartDateMonth()+"-"+group.getStartDateDay();
            String endDate = group.getEndDateYear()+"-"+group.getEndDateMonth()+"-"+group.getEndDateDay();
            int gid=group.getGid();
            float period=(float)ChronoUnit.DAYS.between(LocalDate.parse(startDate),LocalDate.parse(endDate))+1;
            int progressValue = (int)(cnt/period*100);

            JProgressBar progressBar = new JProgressBar();
            progressBar.setStringPainted(true);
            progressBar.setValue(progressValue);
            progressBar.setBounds(12, 55, 304, 30);

            missionEndProgressPanel.add(progressBar);

            y+=110;
        }
        if(missionEndProgressPanel!=null){
            missionEndProgressPanel.revalidate();
            missionEndProgressPanel.repaint();
        }
    }
}
