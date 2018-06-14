package com.hysm.olympic.tool;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.Message;

/**
 * 音频播放器
 * @author songkai 
 */
public class Player {

	 public MediaPlayer mediaPlayer;  
	 private Handler handler;
	 private Timer mTimer;
	 
	 public static final int Player_start = 1000; 
	 public static final int Player_err = 1001;
	 public static final int Player_perpare = 1002;
	 public static final int Player_update = 1003;
	 public static final int Player_Progress = 1004;
	 public static final int Player_end = 1005;
	 
	 public Player(Handler myhandler){
		 handler= myhandler;
		 
		 mediaPlayer = new MediaPlayer();  
         mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        
        //mTimer=new Timer();
        //mTimer.schedule(mTimerTask, 0, 1000);  
        
        init_listener();
         
	 }

	private void init_listener() {
		
		mediaPlayer.setOnErrorListener(new OnErrorListener() { 
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				
				Message msg = handler.obtainMessage();
				msg.what = Player_update;
				msg.obj = what; 
				handler.sendMessage(msg);
				
				return false;
			}
		});
		
		mediaPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() { 
			@Override
			public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
				Message msg = handler.obtainMessage();
				msg.what = Player_err; 
				handler.sendMessage(msg);
			}
		});
		
	   mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
		
		@Override
		public void onPrepared(MediaPlayer arg0) {
			Message msg = handler.obtainMessage();
			msg.what = Player_perpare; 
			handler.sendMessage(msg); 
		} 
	   });
	   
	   mediaPlayer.setOnCompletionListener(new OnCompletionListener(){ 
		@Override
		public void onCompletion(MediaPlayer arg0) {
			Message msg = handler.obtainMessage();
			msg.what = Player_end; 
			handler.sendMessage(msg);
		}
	   });
	}
	
	 public void playUrl(String videoUrl)  
	 {  
			if(mediaPlayer == null){
				 mediaPlayer = new MediaPlayer();  
		         mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			}
	        try {  
	            mediaPlayer.reset();  
	            mediaPlayer.setDataSource(videoUrl);  
	            mediaPlayer.prepare();//prepare之后自动播放  
	            mediaPlayer.start();
	            
	            Message msg = handler.obtainMessage();
				msg.what = Player_start; 
				handler.sendMessage(msg);
	             
	        } catch (IllegalArgumentException e) {   
	            e.printStackTrace();  
	        } catch (IllegalStateException e) {   
	            e.printStackTrace();  
	        } catch (IOException e){     
	            e.printStackTrace();  
	        }  
	  }  
	
	
	public void pause()  
   {  
		 if(mediaPlayer != null){  
			 if(mediaPlayer.isPlaying()){
				 mediaPlayer.pause();  
			}else{
				 mediaPlayer.start();  
			}
		 }
		
      
   }  
     
   public void stop()  
   {  
       if (mediaPlayer != null) {   
           mediaPlayer.stop();  
           mediaPlayer.release();   
           mediaPlayer = null;   
       }   
   }
   
   public int get_progress(){
   	if(mediaPlayer != null){  
   		int position = mediaPlayer.getCurrentPosition();  
           int duration = mediaPlayer.getDuration(); 
           
           if (duration > 0) { 
          	 return 100*position/duration;
           }else{
          	 return 0;
           }
   	}else{
   		return 100;
   	}
   	 
   }
   
   /******************************************************* 
    	通过定时器和Handler来更新进度条 
    ******************************************************/  
   TimerTask mTimerTask = new TimerTask() {  
       @Override  
       public void run() {  
           if(mediaPlayer==null){
           	 return; 
           }else{
           	if (mediaPlayer.isPlaying()) { 
               	//播放进度
               	Message msg = handler.obtainMessage();
       			msg.what = Player_Progress; 
       			handler.sendMessage(msg); 
               }
           }
               
             
       }  
   }; 
}
