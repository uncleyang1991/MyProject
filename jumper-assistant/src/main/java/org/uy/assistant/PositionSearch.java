package org.uy.assistant;

import org.uy.assistant.entity.RGBInfo;

import java.awt.image.BufferedImage;

public class PositionSearch {

    private RGBTool rgbt = new RGBTool();

    //获取自己的中心坐标
    public int[] mePosition(BufferedImage image) throws Exception{
        int _x = 0,_y = 0;
        for(int y=900;y<=1150;y++){
            for(int x=200;x<=1000;x++){
                RGBInfo rgb = rgbt.getRGB(image,x,y);
                if(rgb.getR() > 55 && rgb.getR() < 60 && rgb.getG() > 55 && rgb.getG() < 60 && rgb.getB() > 100 && rgb.getB() < 110){
                    if(y > _y){
                        _x = x;
                        _y = y;
                    }
                }
            }
        }
        return new int[]{_x,_y-15};
    }

    public int[] targetPosition(BufferedImage image) throws Exception{
        int[] result = new int[2];

        int top_x = 0,top_y = 0;
        boolean isTop = false;
        for(int y=300;y<=1000;y++){
            RGBInfo bgRGB = rgbt.getRGB(image,1,y);
            for(int x=80;x<1080;x++){
                RGBInfo rgb = rgbt.getRGB(image,x,y);
                //获取目标跳板顶点坐标
                if(rgb.getR()==245&&rgb.getG()==245&&rgb.getB()==245){
                    result[0] = x+4;
                    result[1] = y+11;
                    isTop = true;
                    break;
                }
                /*if(!isTop){
                    if(rgb.getR()!=bgRGB.getR()&&rgb.getG()!=bgRGB.getG()&&rgb.getB()!=bgRGB.getB()){
                        top_x = x;
                        top_y = y;
                        System.out.print("顶点: "+top_x+","+top_y+"\t");
                        isTop = true;
                        break;
                    }
                }*/
            }
            if(isTop){
                break;
            }
        }

        /*for(int y=top_y;y<top_y+600;y++){
            RGBInfo rgb = rgbt.getRGB(image,top_x,y);
            if(rgb.getR()==245&&rgb.getG()==245&&rgb.getB()==245){
                result[0] = top_x;
                result[1] = y+11;
                break;
            }
        }*/
        System.out.println("目标: "+result[0]+","+result[1]);
        return result;
    }
}
