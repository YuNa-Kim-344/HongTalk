package Canvas;

import java.awt.image.BufferedImage;
import java.io.Serializable;

//BufferedImage를 Serializable 인터페이스를 구현하여 직렬화
//이미지를 전송하기 위해 SerializableImage 객체를 ObjectOutputStream을 통해 전송
public class SerializableImage implements Serializable {
  private static final long serialVersionUID = 1L;
  private transient BufferedImage image;

  public SerializableImage(BufferedImage image) {
      this.image = image;
  }

  public BufferedImage getImage() {
      return image;
  }

}