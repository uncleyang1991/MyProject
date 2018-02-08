package org.uy.assistant;

import org.uy.assistant.entity.RGBInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    private String PNG_URL = System.getProperty("user.dir")+"\\jumper-assistant\\png\\";

    private final int height = 1920;
    private final int width = 1080;
    private final double pressTimeCoefficient = 1.37;

    private ScreenShell screen = new ScreenShell();

    private RGBTool rgbt = new RGBTool();

    private PositionSearch positionSearch = new PositionSearch();

    public void start(){
        try {
            int count = 0;
            while(true){
                if(screen.screen()){
                    BufferedImage image = ImageIO.read(new File(PNG_URL+"screen.png"));
                    RGBInfo q_one = rgbt.getRGB(image,995,85);
                    if(q_one.getR()==255&&q_one.getG()==255&&q_one.getB()==255){
                        RGBInfo q_two = rgbt.getRGB(image,395,1565);
                        if(q_two.getR()==0&&q_two.getG()==199&&q_two.getB()==119){
                            System.out.println("游戏结束");
                            break;
                        }
                    }
                    System.out.print("第"+(++count)+"次\t");

                    int[] me = positionSearch.mePosition(image);
                    int[] target = positionSearch.targetPosition(image);
                    int x = me[0];
                    int y = me[1];
                    int t_x = target[0];
                    int t_y = target[1];
                    double distance = Math.sqrt(Math.pow(x-t_x,2)+Math.pow(y-t_y,2));

                    int pressTime = (int) Math.max(distance * pressTimeCoefficient, 200);

                    screen.getRunTime().exec("adb shell input swipe 500 1300 500 1300 "+pressTime).waitFor();
                }
                Thread.sleep(2500);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
