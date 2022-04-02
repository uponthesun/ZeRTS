package mechanicsobjects;
/*ZeRTS
 * Tileset Class
 * Created March 3, 2008 
 * Finished: (not yet)
 * Does:
 *	
 */
import java.awt.*;
import java.util.ArrayList;

public class Tileset {
	private ArrayList/*<Image>*/ set;
	private ArrayList/*<Character>*/ charSet;
	public Tileset(){
		set = new ArrayList/*<Image>*/();
		charSet = new ArrayList/*<Character>*/();
	}
	public Tileset(ArrayList/*<Image>*/ setSet){
		set = setSet;
	}
	public Image get(int n) {
		return (Image)set.get(n);
	}
	public Image get(char c){
		return (Image)set.get(charSet.indexOf(new Character(c)));
	}
	public void set (int n, Image i, char c){
		set.set(n, i);
		charSet.set(n, new Character(c));
	}
	public void add(Image i, char c){
		set.add(i);
		charSet.add(new Character(c));
	}
	
}
