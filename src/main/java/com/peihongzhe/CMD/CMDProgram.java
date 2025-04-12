/*
Copyright(c) 2025 Pei HongZhe. All Rights Reserved.
版权所有(c) 2025 裴鸿喆，保留所有权利。

B站：https://space.bilibili.com/381317561
个人网站：https://www.peihongzhe.com
*/

package com.peihongzhe.CMD;

import com.peihongzhe.FileFinder;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CMDProgram {
    private static ResourceBundle bundle = null;

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public void Start(String[] args){
        selectLanguage();
        //System.out.println("语言测试"+bundle.getString("welcome"));
        // 设置要扫描的根目录（默认为当前工程目录）
        String rootPath = args.length > 0 ? args[0] : System.getProperty("user.dir");
        File rootDir = new File(rootPath);
        boolean confirm = false;
        int Mode = -1;
        do {
            System.out.println(bundle.getString("SCAN_DIRECTORY_PROMPT") +":"+ rootDir.getAbsolutePath());
            Mode = GetMode();
            confirm = confirm(bundle.getString("SCAN_DIRECTORY_LABEL")+":" + rootDir.getAbsolutePath()+"; "+bundle.getString("MODE_LABEL") +":"+ (Mode == 0 ? bundle.getString("LENIENT_MODE") : bundle.getString("STRICT_MODE")));
        }while(!confirm);
        System.out.println(bundle.getString("START_SCANNING") +":"+ rootDir.getAbsolutePath()+"; "+bundle.getString("MODE_LABEL")+":" + (Mode == 0 ? bundle.getString("LENIENT_MODE") : bundle.getString("STRICT_MODE")));
        FileFinder.traverseDirectory(rootDir,Mode==1);
    };

    private void selectLanguage(){
        String language = "";
        do{
            System.out.println("Please select a language, EN-English, ZH-Chinese.");
            Scanner scanner = new Scanner(System.in);
            language = scanner.nextLine();
        }while (!(language.equalsIgnoreCase("EN")||language.equalsIgnoreCase("ZH")));

        if(language.equalsIgnoreCase("EN")){
            bundle = ResourceBundle.getBundle("LanguageCMD",Locale.ENGLISH);
        }else{
            bundle = ResourceBundle.getBundle("LanguageCMD",Locale.CHINA);
        }
    }

    private boolean confirm(String Message){
        boolean Confirm = false;
        boolean Loop = false;
        Scanner scanner = new Scanner(System.in);
        do{
            Loop = false;
            System.out.println(Message+"\n"+bundle.getString("CONFIRMATION_PROMPT") );
            try{
                Confirm = scanner.nextBoolean();
            }catch(Exception e){
                //System.out.println("错误");
                Confirm = false;
                scanner.next();
                Loop = true;
            }
        }while(Loop);
        //scanner.close();
        return Confirm;
    }

    private int GetMode(){
        int Mode = -1;
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println(bundle.getString("MODE_SELECTION_PROMPT"));
            try{
                Mode = scanner.nextInt();
            }catch(java.util.InputMismatchException e){
                //System.out.println("错误，非数字");
                Mode = -1;
                scanner.next();
            }
        }while(!(Mode ==0||Mode==1));
        return Mode;
    }

}
