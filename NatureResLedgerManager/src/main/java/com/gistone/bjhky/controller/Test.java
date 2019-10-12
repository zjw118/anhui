package com.gistone.bjhky.controller;

import com.gistone.bjhky.entity.St4ScsCk;

public class Test {
    public static void main(String[] args) {
        St4ScsCk ck = new St4ScsCk();
        ck.setCk001(2);
        ck.setCk002(3);
        St4ScsCk ck1 = new St4ScsCk();
        ck1.setCk001(1);
        ck1.setCk002(3);
        System.out.println(ck.equals(ck1));
    }
}
