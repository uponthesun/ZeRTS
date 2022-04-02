package mechanicsobjects;
import java.awt.Image;
import libraries.ImageLibrary;

public class AnimationCycle {
	
	public Image[][] frames; // [framenumber][direction]
	public int[] frameTimes;
	public int cycleTime;
	private int currentFrameIndex = 0;
	public boolean end = false;
	public boolean repeat = false;
	public String type;
	
	public AnimationCycle(Image[][] frames, int[] frameTimes){

		this.frames = frames;
		
/*		frames = new Image[baseFrames.length][8];		
		for(int n = 0; n < baseFrames.length; n++)
			frames[n] = ImageLibrary.directionalize(baseFrames[n]);
*/			
		this.frameTimes = frameTimes;
	}

	public Image getCurrentFrame(int direction){
		return frames[currentFrameIndex][direction];
	}
	public Image getCurrentFrame(){
		return frames[currentFrameIndex][0];
	}
	
	public void timeStep(){
		cycleTime++;
		if(cycleTime > frameTimes[currentFrameIndex]){
			currentFrameIndex++;
			if(currentFrameIndex >= frameTimes.length){
				if(repeat){
					currentFrameIndex = 0;
					cycleTime = 0;
				}
				else
					end = true;
			}
		}
	}
	
}
