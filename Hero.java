/**
 * @author huangshengwei
 * @studentID:1475765
 */
package model;

/**
 * 
 * 
 * This class represents the hero
 */
public class Hero {
	private int heroCol;
	private int lives;

	/**
	 * 
	 * @return which column the hero is in
	 */
	public int getHeroCol() {
		return heroCol;
	}

	/**
	 * 
	 * @param heroCol which column to put the hero to
	 */
	public void setHeroCol(int heroCol) {
		this.heroCol = heroCol;
	}

	/**
	 * 
	 * @return how many lives the hero has
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * 
	 * @param lives the num of lives of the hero to set
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	
}
