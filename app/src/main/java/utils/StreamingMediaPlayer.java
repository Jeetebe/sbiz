package utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;

import com.jtb4.doantenshowbiz.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class StreamingMediaPlayer implements OnCompletionListener {

    private static final int INTIAL_KB_BUFFER =  96*10/8;//assume 96kbps*10secs/8bits per byte

	private long mediaLengthInKb, mediaLengthInSeconds;
	private int totalKbRead = 0;
	
	// Create Handler to call View updates on the main UI thread.
	private final Handler handler = new Handler();

	private MediaPlayer mediaPlayer;
	
	private File downloadingMediaFile; 
	
	private boolean isInterrupted;
	
	private Context context;
	
	private int counter = 0;
	
	private ImageButton playButton;	
	//private ProgressBar	progressBar;
	//private CircleProgress circleProgress;
	//DonutProgress circleProgressRunning;
	//private TextView songTotalDurationLabel;
	//private TextView songCurrentDurationLabel;
	private UtilitiesPlayer utils;
	private Boolean return_main=false;
	//ICallback ic;
	
	//public StreamingMediaPlayer(Context  context,ImageButton	playButton, ProgressBar	progressBar) 
	//public StreamingMediaPlayer(Context  context,ImageButton	playButton, CircleProgress circleProgress,DonutProgress circleProgressRunning)
	//public StreamingMediaPlayer(Context  context,ImageButton	playButton, DonutProgress circleProgressRunning)
	public StreamingMediaPlayer(Context  context,ImageButton	playButton)
 	{
		this.context = context;	
		mediaPlayer = new MediaPlayer();
		this.playButton = playButton;
		//this.progressBar = progressBar;
		//this.circleProgress=circleProgress;
		//this.circleProgressRunning=circleProgressRunning;
		//this.songCurrentDurationLabel=songCurrentDurationLabel;
		//this.songTotalDurationLabel=songTotalDurationLabel;
		utils=new UtilitiesPlayer();		
	}
	
    /**  
     * Progressivly download the media to a temporary location and update the MediaPlayer as new content becomes available.
     */  
    public void startStreaming(final String mediaUrl, long	mediaLengthInKb, long	mediaLengthInSeconds) throws IOException {
    	
    	this.mediaLengthInKb = mediaLengthInKb;
    	this.mediaLengthInSeconds = mediaLengthInSeconds;
    	
		Runnable r = new Runnable() {   
	        public void run() {   
	            try {   
	        		downloadAudioIncrement(mediaUrl);
	            } catch (IOException e) {
	            	Log.e(getClass().getName(), "Unable to initialize the MediaPlayer for fileUrl=" + mediaUrl, e);
	            	return;
	            }   
	        }   
	    };   
	    new Thread(r).start();
    }
    
    /**  
     * Download the url stream to a temporary location and then call the setDataSource  
     * for that local file
     */  
    public void downloadAudioIncrement(String mediaUrl) throws IOException {
    	
    	URLConnection cn = new URL(mediaUrl).openConnection();   
        cn.connect();   
        InputStream stream = cn.getInputStream();
        if (stream == null) {
        	Log.e(getClass().getName(), "Unable to create InputStream for mediaUrl:" + mediaUrl);
        }
        
		downloadingMediaFile = new File(context.getCacheDir(),"downloadingMedia_" + (counter++) + ".dat");
        FileOutputStream out = new FileOutputStream(downloadingMediaFile);   
        byte buf[] = new byte[16384];
        int totalBytesRead = 0, incrementalBytesRead = 0;
        do {
        	int numread = stream.read(buf);   
            if (numread <= 0)   
                break;   
            out.write(buf, 0, numread);
            totalBytesRead += numread;
            incrementalBytesRead += numread;
            totalKbRead = totalBytesRead/1000;
            
            testMediaBuffer();
           	fireDataLoadUpdate();
        } while (validateNotInterrupted());   

       	stream.close();
        if (validateNotInterrupted()) {
	       	fireDataFullyLoaded();
        }
    }  

    private boolean validateNotInterrupted() {
		if (isInterrupted) {
			if (mediaPlayer != null) {
				mediaPlayer.pause();
				//mediaPlayer.release();
			}
			return false;
		} else {
			return true;
		}
    }

    
    /**
     * Test whether we need to transfer buffered data to the MediaPlayer.
     * Interacting with MediaPlayer on non-main UI thread can causes crashes to so perform this using a Handler.
     */  
    private void  testMediaBuffer() {
	    Runnable updater = new Runnable() {
	        public void run() {
	            if (mediaPlayer == null) {
	            	//  Only create the MediaPlayer once we have the minimum buffered data
	            	if ( totalKbRead >= INTIAL_KB_BUFFER) {
	            		try {
		            		//startMediaPlayer();
	            		} catch (Exception e) {
	            			Log.e(getClass().getName(), "Error copying buffered conent.", e);    			
	            		}
	            	}
	            }// else if ( mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() <= 1000 ){ 
	            	//  NOTE:  The media player has stopped at the end so transfer any existing buffered data
	            	//  We test for < 1second of data because the media player can stop when there is still
	            	//  a few milliseconds of data left to play
	            	//transferBufferToMediaPlayer();
	            //}
	        }
	    };
	    handler.post(updater);
    }
    
    private void startMediaPlayer() {
        try {   
        	File bufferedFile = new File(context.getCacheDir(),"playingMedia" + (counter++) + ".dat");
        	moveFile(downloadingMediaFile,bufferedFile);
    		
        	Log.e("Player",bufferedFile.length()+"");
        	Log.e("Player",bufferedFile.getAbsolutePath());
        	
    		mediaPlayer = new MediaPlayer();
        	mediaPlayer.setDataSource(bufferedFile.getAbsolutePath());
        	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    		mediaPlayer.prepare();
        	fireDataPreloadComplete();
        	
        } catch (IOException e) {
        	Log.e(getClass().getName(), "Error initializing the MediaPlaer.", e);
        	return;
        }   
    }
    
    /**
     * Transfer buffered data to the MediaPlayer.
     * Interacting with MediaPlayer on non-main UI thread can causes crashes to so perform this using a Handler.
     */  
    private void transferBufferToMediaPlayer() {
	    try {
	    	// First determine if we need to restart the player after transferring data...e.g. perhaps the user pressed pause
	    	boolean wasPlaying = mediaPlayer.isPlaying();
	    	int curPosition = mediaPlayer.getCurrentPosition();
	    	mediaPlayer.pause();

        	File bufferedFile = new File(context.getCacheDir(),"playingMedia" + (counter++) + ".dat");
	    	//FileUtils.copyFile(downloadingMediaFile,bufferedFile);

			mediaPlayer = new MediaPlayer();
    		mediaPlayer.setDataSource(bufferedFile.getAbsolutePath());
    		//mediaPlayer.setAudioStreamType(AudioSystem.STREAM_MUSIC);
    		mediaPlayer.prepare();
    		mediaPlayer.seekTo(curPosition);
    		
    		//  Restart if at end of prior beuffered content or mediaPlayer was previously playing.  
    		//	NOTE:  We test for < 1second of data because the media player can stop when there is still
        	//  a few milliseconds of data left to play
    		//boolean atEndOfFile = mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition() <= 1000;
        	//if (wasPlaying || atEndOfFile){
    		mediaPlayer.start();
    		if (wasPlaying ){
        		mediaPlayer.start();
        	}
		}catch (Exception e) {
	    	Log.e(getClass().getName(), "Error updating to newly loaded content.", e);            		
		}
    }
    
    private void fireDataLoadUpdate() {
		Runnable updater = new Runnable() {
	        public void run() {
	        	//textStreamed.setText((CharSequence) (totalKbRead + " Kb read"));
	    		float loadProgress = ((float)totalKbRead/(float)mediaLengthInKb);
	    		//progressBar.setSecondaryProgress((int)(loadProgress*100));
	    		
	    		//circleProgress.setProgress((int)(loadProgress*100));
	    		//circleProgressRunning.setProgress((int)(loadProgress*100));
	    		//circleProgressRunning.setVisibility(View.GONE);
	    		//playButton.setVisibility(View.GONE);
	        }
	    };
	    handler.post(updater);
    }
    
    /**
     * We have preloaded enough content and started the MediaPlayer so update the buttons & progress meters.
     */
    private void fireDataPreloadComplete() {
    	Runnable updater = new Runnable() {
	        public void run() {
	    		mediaPlayer.start();
	    		startPlayProgressUpdater();
	        	playButton.setEnabled(true);
	        	//streamButton.setEnabled(false);
	        
	        }
	    };
	    handler.post(updater);
    }

    private void fireDataFullyLoaded() {
		Runnable updater = new Runnable() { 
			public void run() {
   	        	//transferBufferToMediaPlayer();
	        	//textStreamed.setText((CharSequence) ("Audio full loaded: " + totalKbRead + " Kb read"));
	        	startMediaPlayer();
	        	//progressBar.setSecondaryProgress(100);
	        	//circleProgress.setProgress(100);
	        	//circleProgress.setVisibility(View.GONE);
	        	//playButton.setVisibility(View.VISIBLE);	        
	    		//circleProgressRunning.setVisibility(View.VISIBLE);
	    	
	        	//mediaLengthInSeconds =mediaPlayer.getDuration();
	        }
	    };
	    handler.post(updater);
    }
    
    public MediaPlayer getMediaPlayer() {
    	return mediaPlayer;
	}
	
    public void startPlayProgressUpdater() {
    	
    	float progress = (((float)mediaPlayer.getCurrentPosition()/1000)/(float)mediaLengthInSeconds);
    	//float progress = (((float)mediaPlayer.getCurrentPosition()/1000)/(float)mediaPlayer.getDuration());
    	//progressBar.setProgress((int)(progress*100));
    	int i=(int)(progress*100);
    	if (i!=100)
    	{
    		//circleProgressRunning.setProgress((int)(progress*100));
    	}
    	else
    	{
    		//circleProgressRunning.setProgress((int)(100));
    		mediaPlayer.pause();
    	}
    	long totalDuration = mediaPlayer.getDuration();
        long currentDuration = mediaPlayer.getCurrentPosition();
        
     
        // Displaying Total Duration time
       // songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
        // Displaying time completed playing
       // songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));
       // Log.i("MtActivity","currentDuration="+Long.toString(currentDuration));
        /*
        if (currentDuration>45000)
		{
        	mediaPlayer.stop();
        	playButton.setImageResource(R.drawable.btn_play);
		}
		*/
		
	 
			if (mediaPlayer.isPlaying()) {
				Runnable notification = new Runnable() {
			        public void run() {
			        	try
			        	{
			        		startPlayProgressUpdater();
			        	}
			        	catch (Exception e){}
					}
			    };
			    handler.postDelayed(notification,1000);
	    	}
	  
    }    
    public void set_return_main(Boolean return_main1){
    	return_main=return_main1;
    }
 
    public void interrupt() {
    	playButton.setEnabled(false);
    	isInterrupted = true;
    	validateNotInterrupted();
    }
    
	public void moveFile(File	oldLocation, File	newLocation)
	throws IOException {

		if ( oldLocation.exists( )) {
			BufferedInputStream  reader = new BufferedInputStream( new FileInputStream(oldLocation) );
			BufferedOutputStream  writer = new BufferedOutputStream( new FileOutputStream(newLocation, false));
            try {
		        byte[]  buff = new byte[8192];
		        int numChars;
		        while ( (numChars = reader.read(  buff, 0, buff.length ) ) != -1) {
		        	writer.write( buff, 0, numChars );
      		    }
            } catch( IOException ex ) {
				throw new IOException("IOException when transferring " + oldLocation.getPath() + " to " + newLocation.getPath());
            } finally {
                try {
                    if ( reader != null ){
                    	writer.close();
                        reader.close();
                    }
                } catch( IOException ex ){
				    Log.e(getClass().getName(),"Error closing files when transferring " + oldLocation.getPath() + " to " + newLocation.getPath() ); 
				}
            }
        } else {
			throw new IOException("Old location does not exist when transferring " + oldLocation.getPath() + " to " + newLocation.getPath() );
        }
	}
	public boolean isPlaying()
	{
		return 	mediaPlayer.isPlaying();	
	}
	public void pause()
	{
		mediaPlayer.pause();	
	}
	public void start()
	{
		mediaPlayer.start();	
	}
	public void stop()
	{
		mediaPlayer.stop();	
	}
	public int getCurrentPosition()
	{
		return mediaPlayer.getCurrentPosition();
	}
	public int getDuration()
	{
		return mediaPlayer.getDuration();
	}
	public void seekTo(int currentPosition)
	{
		mediaPlayer.seekTo(currentPosition)	;
	}
	public void reset(){
		mediaPlayer.reset();
	}
	public void release(){
		mediaPlayer.release();
	}
	
	 public void setSeek(double percent) {
		 mediaPlayer.seekTo((int) (mediaLengthInSeconds * percent));
	    }

	
	public void updateProgressBar(){
		startPlayProgressUpdater();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.i("MyActivity","end playing");
		playButton.setImageResource(R.drawable.ico_play);
	}
}
