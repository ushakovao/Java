package processing;

import image.BlurFilter;
import image.CrystallizeFilter;
import image.EdgeFilter;
import image.GrayscaleFilter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by ushakova on 14/07/16.
 */
public class Test extends JPanel{
    public static void main(String[] args) throws Throwable {
        BufferedImage bufferedImage = ImageIO.read(new File("assets/images/Lenna.png"));
        EdgeFilter e = new EdgeFilter();
        e.filter(bufferedImage, bufferedImage);
        ImageIO.write(bufferedImage, "png", new File("assets/images/out/CrystallizeFilter.png"));

    }
}
