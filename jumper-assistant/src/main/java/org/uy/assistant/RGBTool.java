package org.uy.assistant;

import org.uy.assistant.entity.RGBInfo;

import java.awt.image.BufferedImage;

public class RGBTool {

    public RGBInfo getRGB(BufferedImage image,int x,int y){
        int rgb = image.getRGB(x,y);
        RGBInfo info = new RGBInfo();
        info.setR((rgb & 0xff0000) >> 16);
        info.setG((rgb & 0xff00) >> 8);
        info.setB(rgb & 0xff);
        return info;
    }
}
