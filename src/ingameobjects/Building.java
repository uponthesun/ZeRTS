package ingameobjects;
import java.awt.Image;


public class Building extends Thing{

	private int buildTime;
	private int buildCost;
	private Image[] constructionImages;
	private Unit[] beingsProduced;
	
	public int getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(int buildTime) {
		this.buildTime = buildTime;
	}
	public int getBuildCost() {
		return buildCost;
	}
	public void setBuildCost(int buildCost) {
		this.buildCost = buildCost;
	}
	public Image[] getConstructionImages() {
		return constructionImages;
	}
	public void setConstructionImages(Image[] constructionImages) {
		this.constructionImages = constructionImages;
	}
	public Unit[] getBeingsProduced() {
		return beingsProduced;
	}
	public void setBeingsProduced(Unit[] beingsProduced) {
		this.beingsProduced = beingsProduced;
	}
	
	
}
