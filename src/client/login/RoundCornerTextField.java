package client.login;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;



/**
 * JTextField의 모서리를 둥글게 하기 위해서 만든 클래스
 */
public class RoundCornerTextField extends JTextField {
    private int arc = 20; // 원하는 모서리 반경 설정
    private int padding=5;

    public RoundCornerTextField(int columns) {
        super(columns);
        setOpaque(false); // 불투명 설정 해제
        setMargin(new Insets(padding, padding, padding, padding));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque()) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(getBackground());
            g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arc, arc));

            super.paintComponent(g2d);
            g2d.dispose();
        } else {
            super.paintComponent(g);
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do nothing to skip drawing the border
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("둥근 텍스트 필드");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        RoundCornerTextField textField = new RoundCornerTextField(20);
        textField.setPreferredSize(new Dimension(200, 30));
        frame.add(textField);

        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}



