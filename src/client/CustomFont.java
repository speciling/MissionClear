package client;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * resource 폴더에 있는 폰트 객체 클래스
 */
public class CustomFont {
    private Font font;

    public CustomFont() {
        try (InputStream fontStream = Files.newInputStream(Path.of("./resource/NanumGothic.ttf"))) {
            // InputStream으로부터 Font 객체 생성
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return font
     */
    public Font getFont() {
        return font;
    }

    /**
     * 폰트 스타일과 크기를 입력하는 메소드
     * @param style
     * @param size
     *
     */
    public Font deriveFont(int style, float size) {
        return font.deriveFont(style, size);
    }

}
