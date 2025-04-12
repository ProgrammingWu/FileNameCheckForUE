/*
Copyright(c) 2025 Pei HongZhe. All Rights Reserved.
版权所有(c) 2025 裴鸿喆，保留所有权利。

B站：https://space.bilibili.com/381317561
个人网站：https://www.peihongzhe.com
*/

package com.peihongzhe;

import com.peihongzhe.CMD.CMDProgram;

import java.io.File;
import java.util.regex.Pattern;

public class FileFinder {
    private static final String VALID_PATTERN_STRICT = "^[a-zA-Z0-9_.]*$";
    private static final String VALID_PATTERN_LENIENT = "^[\\x00-\\x7F]*$";
    private static final Pattern patternStrict = Pattern.compile(VALID_PATTERN_STRICT);
    private static final Pattern patternLenient = Pattern.compile(VALID_PATTERN_LENIENT);

    private static final String[] DefaultIgnoreFile={".git",".idea",".vs","target"};

    private static int traverseDirectory(File RootDir,File dir,boolean isStrict) {
        File[] files = dir.listFiles();
        if (files == null) {return 0;} // 处理无权限访问的目录

        int invalidFilesCount = 0;
        for (File file : files) {
            if (isIgnoreFile(RootDir,file)) {
                continue;
            }
            // 检查文件名是否符合规则
            if (!isValidName(file.getName(),isStrict)) {
                System.out.println(CMDProgram.getBundle().getString("INVALID_NAMING") + file.getAbsolutePath());
                invalidFilesCount++;
            }
            // 递归处理子目录
            if (file.isDirectory()) {
                invalidFilesCount += traverseDirectory(RootDir,file,isStrict);
            }
        }
        return invalidFilesCount;
    }

    public static void traverseDirectory(File RootDir,boolean isStrict){
        int invalidFilesCount = traverseDirectory(RootDir,RootDir,isStrict);
        System.out.println(CMDProgram.getBundle().getString("PROCESSING_COMPLETED_INVALID_FILES_COUNT") + invalidFilesCount);
    }

    // 验证文件名是否仅包含字母、数字、下划线
    private static boolean isValidName(String fileName,boolean isStrict) {
        //System.out.println("=========: " + fileName);
        if (isStrict) {
            return patternStrict.matcher(fileName).matches();
        }else{
            return patternLenient.matcher(fileName).matches();
        }
    }
    private static boolean isIgnoreFile(File dir,File file, String[] IgnoreFiles) {
        if (IgnoreFiles == null || IgnoreFiles.length == 0) return true;
        for (String ignoreFile : IgnoreFiles) {
            //if (file.isDirectory() && file.getName().equals(ignoreFile)){
            if (file.equals(new File(dir,ignoreFile))){
                //System.out.println("忽略文件："+dir+"|||"+new File(dir,ignoreFile));
               return true;
            }
        }
        return false;
    }
    private static boolean isIgnoreFile(File dir,File file){
        return isIgnoreFile(dir,file, DefaultIgnoreFile);
    }
}
