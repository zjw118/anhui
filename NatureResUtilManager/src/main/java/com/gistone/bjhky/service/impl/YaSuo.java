package com.gistone.bjhky.service.impl;

import java.io.File;

public class YaSuo {
    public static void main(String[] args) {
        File source = new File("D:/biudata/video/1.mp4");
        File target = new File("D:/biudata/yasuo/xxx1.mp4");

        /*try{
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(new Integer(56000));
            audio.setChannels(new Integer(1));
            audio.setSamplingRate(new Integer(22050));

            VideoAttributes video = new VideoAttributes();
            video.setCodec("mpeg4");
            video.setBitRate(new Integer(800000));
            video.setFrameRate(new Integer(15));

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp4");
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);

            Encoder encoder = new Encoder();
            encoder.encode(source, target, attrs);
            System.out.println("压缩完成...");
        }catch (EncoderException e){
            e.printStackTrace();
        }*/

    }
}
