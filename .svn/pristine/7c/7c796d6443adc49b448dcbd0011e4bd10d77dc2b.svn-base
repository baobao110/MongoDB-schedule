package com.hysm.olympic.tool;

import android.content.Context;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;

/**
 * 语音业务处理逻辑
 * @author songkai
 *
 */
public class Speek {

	// 语音识别对象
    private SpeechRecognizer recognizer; 
    //语音合成对象
    private SpeechSynthesizer speaker; 
    
    public Speek(Context context){
    	//初始化语音对象
        recognizer = SpeechRecognizer.createRecognizer(context,mInitListener);
        speaker = SpeechSynthesizer.createSynthesizer(context,mInitListener);
		
		//设置听写参数
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        //设置为中文
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置为普通话
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 客户端引擎选择模式 --云端msc
        recognizer.setParameter(SpeechConstant.ENGINE_MODE, "msc");
		// 客户端引擎类型 --云端msc
        recognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		// 数据解码方式 --utf-8
        recognizer.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
         
         
      //设置发音人
        speaker.setParameter(SpeechConstant.VOICE_NAME, "nannan");
        //设置语速
        speaker.setParameter(SpeechConstant.SPEED, "50");
        //设置语调
       // speaker.setParameter(SpeechConstant.PITCH, "50");
        //设置音量，范围0~100
        speaker.setParameter(SpeechConstant.VOLUME, "100");
        //设置云端
        speaker.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置播放器音频流类型
        speaker.setParameter(SpeechConstant.STREAM_TYPE, "3");
        //设置播放合成音频打断音乐播放，默认为true
        speaker.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
    }
    
    
    
    
    public SpeechRecognizer getRecognizer() {
		return recognizer;
	}
 
	public SpeechSynthesizer getSpeaker() {
		return speaker;
	}
 
	//初始化监听器。
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) { 
            if(code != ErrorCode.SUCCESS){ 
            	
            }
        }
    };
}
