package org.uy.assistant;

import java.io.*;

public class ScreenShell {

    private boolean isWifiAdb = true;

    private String wifiAdbTarget = "192.168.0.2";

    private String PNG_URL = System.getProperty("user.dir")+"\\jumper-assistant\\png\\";

    private Runtime run = Runtime.getRuntime();

    public ScreenShell(){
        try{
            File png_url = new File(PNG_URL);
            if(!png_url.exists()){
                png_url.mkdirs();
            }
            if(isWifiAdb){
                Process process = run.exec("adb connect "+wifiAdbTarget+":5555");
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while((line = br.readLine()) != null){
                    if(("connected to "+wifiAdbTarget).equals(line)||("already connected to "+wifiAdbTarget).equals(line)){
                        System.out.println("wifi abd已连接到"+wifiAdbTarget);
                        break;
                    }
                }
                process.waitFor();
                process.destroy();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("wifi adb 目标："+wifiAdbTarget+"连接失败");
        }
    }

    public boolean screen(){
        try{
            run.exec("adb shell screencap /sdcard/screen.png").waitFor();
            run.exec("adb pull /sdcard/screen.png "+PNG_URL+"screen.png").waitFor();
            run.exec("adb shell rm /sdcard/screen.png").waitFor();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Runtime getRunTime() {
        return run;
    }
}
