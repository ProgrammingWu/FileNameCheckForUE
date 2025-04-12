/*
Copyright(c) 2025 Pei HongZhe. All Rights Reserved.
版权所有(c) 2025 裴鸿喆，保留所有权利。

B站：https://space.bilibili.com/381317561
个人网站：https://www.peihongzhe.com
*/

package com.peihongzhe;

import com.peihongzhe.CMD.CMDProgram;

public class Main {
    public static void main(String[] args) {
        //目前仅有CMD，还没做UI。所以这里就只有CMD了
        new CMDProgram().Start(args);
    }
}
