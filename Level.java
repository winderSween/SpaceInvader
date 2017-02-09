/**
 * @author huangshengwei
 * @studentID:1475765
 */
package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import conf.Constant;
import model.Hero;
import model.Point;
/**
 * 
 * 
 * This class represents the level
 */
public class Level {
	private int grids[][] = new int[Constant.HEIGHT][Constant.WIDTH];
	private boolean aliensDirectionLeft = true;
	private Hero hero = new Hero();
	private static Level level = new Level();

	/**
	 * This method take one life from the hero off
	 * @return if the hero is dead
	 */
	public boolean subHeroLives() {
		hero.setLives(hero.getLives() - 1);
		return hero.getLives() <= 0;
	}

	private Level() {
		hero.setLives(Constant.HERO_LIVES);
		initGridsZero();
		int width = Constant.WIDTH;
		int aliensInOneLine = Constant.ALIENS_ONE_LINE;
		int begin = (width - aliensInOneLine * 2) / 2;
		for (int i = 1; i <= Constant.ALIENS_LINE_NUM; i++) {
			for (int j = begin; j < begin + 2 * aliensInOneLine; j++) {
				if (j % 2 == 0) {
					grids[i][j] = 1;// 1 means there is an alien
				}
			}
		}
		for (int i = Constant.HEIGHT - 5; i < Constant.HEIGHT - 3; i++) {
			int j = (Constant.WIDTH - 49) / 2;
			int m = 0;
			while (j + 10 < Constant.WIDTH && m < 4) {
				for (int k = 0; k < 4; k++) {
					grids[i][j + k] = 2;// 2 means there is a barrier
				}
				j += 15;
				m++;
			}
		}
		int heroPos = Constant.WIDTH / 2;
		hero.setHeroCol(heroPos);
		grids[Constant.HEIGHT - 1][heroPos] = 3;// 3 means there is a hero
	}

	/**
	 * This method redraw the level
	 */
	public void draw() {
		for (int j = 0; j < Constant.HEIGHT + 2; j++) {
			if (j == 0 || j == Constant.HEIGHT + 1) {
				for (int i = 0; i < Constant.WIDTH + 2; i++) {
					System.out.print("-");
				}
				System.out.println();
			} else {
				System.out.print("|");
				for (int i = 1; i < Constant.WIDTH + 1; i++) {
					if (grids[j - 1][i - 1] == 1) {
						System.out.print("X");
					} else if (grids[j - 1][i - 1] == 2) {
						System.out.print("=");
					} else if (grids[j - 1][i - 1] == 3) {
						System.out.print("P");
					} else if (grids[j - 1][i - 1] == 4) {
						System.out.print("^");
					} else if (grids[j - 1][i - 1] == 5) {
						System.out.print("v");
					} else {
						System.out.print(" ");
					}
				}
				System.out.println("|");
			}
		}
	}

	// init all grid to 0
	private void initGridsZero() {
		for (int i = 0; i < Constant.HEIGHT; i++) {
			for (int j = 0; j < Constant.WIDTH; j++) {
				grids[i][j] = 0;
			}
		}
	}

	/**
	 * 
	 * @return the only one instance of level, it is a singleton
	 */
	public static Level getInstance() {
		return level;
	}

	/**
	 * This method move the hero to left one step
	 */
	public void left() {
		if (hero.getHeroCol() > 0) {
			grids[Constant.HEIGHT - 1][hero.getHeroCol()] = 0;
			hero.setHeroCol(hero.getHeroCol() - 1);
			grids[Constant.HEIGHT - 1][hero.getHeroCol()] = 3;
		}
	}

	/**
	 * This method move the hero to right one step
	 */
	public void right() {
		if (hero.getHeroCol() < Constant.WIDTH - 1) {
			grids[Constant.HEIGHT - 1][hero.getHeroCol()] = 0;
			hero.setHeroCol(hero.getHeroCol() + 1);
			grids[Constant.HEIGHT - 1][hero.getHeroCol()] = 3;
		}
	}

	/**
	 * This method let the hero fire once
	 */
	public void fire() {
		grids[Constant.HEIGHT - 2][hero.getHeroCol()] = 4;
	}

	/**
	 * This method let the aliens fire randomly
	 */
	public void alienFire() {
		for (int i = 0; i < Constant.WIDTH; i++) {
			int maxRow = -1;// the max row of alien in column i
			for (int j = 0; j < Constant.HEIGHT; j++) {
				if (grids[j][i] == 1) {
					maxRow = j;
				}
			}
			if (maxRow != -1) {
				Random random = new Random();
				int rand = random.nextInt(100);
				if (rand < 10) {
					grids[maxRow + 1][i] = 5;
				}
			}
		}
	}
	/**
	 * This method  checks whether the hero has moved to the leftmost side
	 * @return if aliens have moved to the leftmost side
	 */
	public boolean aliensLeft(){
		boolean left = false;
		for (int j = 0; j < Constant.HEIGHT; j++) {
			if (grids[j][0] == 1) {
				if (aliensDirectionLeft) {
					left = true;
				}
			}
		}
		return left;
	}
	/**
	 * This method  checks whether the hero has moved to the rightmost side
	 * @return if aliens have moved to the rightmost side
	 */
	public boolean aliensRight(){
		boolean right = false;
		for (int j = 0; j < Constant.HEIGHT; j++) {
			if (grids[j][Constant.WIDTH - 1] == 1) {
				if (!aliensDirectionLeft) {
					right = true;
				}
			}
		}
		return right;
	}
	/**
	 * This method moves the aliens one step
	 * @return if the aliens have moved to the bottom
	 */
	public boolean moveAliens() {
		boolean left = false;
		boolean right = false;
		for (int j = 0; j < Constant.HEIGHT; j++) {
			if (grids[j][0] == 1) {
				if (aliensDirectionLeft) {
					left = true;
					aliensDirectionLeft = false;
				}
			}
			if (grids[j][Constant.WIDTH - 1] == 1) {
				if (!aliensDirectionLeft) {
					right = true;
					aliensDirectionLeft = true;
				}
			}
		}
		if (left || right) {
			for (int i = 0; i < Constant.WIDTH; i++) {
				for (int k = Constant.HEIGHT - 1; k > 0; k--) {
					if (grids[k - 1][i] == 1) {
						grids[k - 1][i] = 0;
						grids[k][i] = 1;
					} else {
						if (grids[k][i] == 1 && grids[k - 1][i] == 0) {
							grids[k][i] = 0;
						}
					}
				}
			}
			for (int i = 0; i < Constant.WIDTH; i++) {
				if (grids[Constant.HEIGHT - 1][i] == 1) {
					return true;
				}
			}
		} else {
			List<Point> list = new ArrayList<Point>();
			if (aliensDirectionLeft) {
				for (int i = 0; i < Constant.WIDTH - 1; i++) {
					for (int k = 0; k < Constant.HEIGHT; k++) {
						if (grids[k][i + 1] == 1) {
							if(grids[k][i] == 4){
								list.add(new Point(i,k));
							}
							grids[k][i + 1] = 0;
							grids[k][i] = 1;
						} else {
							if (grids[k][i] == 1 && grids[k][i + 1] == 0) {
								grids[k][i] = 0;
							}
						}
					}
				}
			} else {
				for (int i = Constant.WIDTH - 1; i > 0; i--) {
					for (int k = 0; k < Constant.HEIGHT; k++) {
						if (grids[k][i - 1] == 1) {
							if(grids[k][i] == 4){
								list.add(new Point(i,k));
							}
							grids[k][i - 1] = 0;
							grids[k][i] = 1;
						} else {
							if (grids[k][i] == 1 && grids[k][i - 1] == 0) {
								grids[k][i] = 0;
							}
						}
					}
				}
			}
			for(Point p : list){
				grids[p.getY()][p.getX()] = 0;
			}
		}
		return false;
	}

	/**
	 * This method moves the bullets
	 * @return if the hero is killed once
	 */
	public boolean moveBullets() {
		boolean heroKilled = false;
		//UP BULLET
		for (int i = 0; i < Constant.WIDTH; i++) {
			for (int j = 0; j < Constant.HEIGHT; j++) {
				if (grids[j][i] == 4) {
					int end = j - Constant.BULLET_STEPS_ONE_TURN;
					if (end < 0) {
						end = 0;
					}

					boolean alienKilled = false;
					for (int k = j; k >= end; k--) {
						if (grids[k][i] == 1 || grids[k][i] == 2 || grids[k][i] == 5) {
							grids[k][i] = 0;
							alienKilled = true;
							break;
						}
					}
					if(!alienKilled){
						for(int ii = 1;ii<=Constant.BULLET_STEPS_ONE_TURN;ii++){
							if(end-ii>=0&&grids[end-ii][i] == 5){
								grids[end-ii][i] = 0;
								alienKilled = true;
								break;
							}
						}
					}
					if (j - Constant.BULLET_STEPS_ONE_TURN >= 0) {
						if (!alienKilled) {
							grids[end][i] = 4;
						}
					}
					grids[j][i] = 0;
				}
			}
		}
		//DOWN BULLET
		for (int i = 0; i < Constant.WIDTH; i++) {
			for (int j = Constant.HEIGHT - 1; j >= 0; j--) {
				if (grids[j][i] == 5) {
					int end = j + Constant.BULLET_STEPS_ONE_TURN;
					if (end >= Constant.HEIGHT) {
						end = Constant.HEIGHT - 1;
					}

					boolean killed = false;
					for (int k = j; k <= end; k++) {
						if (grids[k][i] == 3 || grids[k][i] == 2 || grids[k][i] == 4) {
							if (grids[k][i] == 3) {
								heroKilled = true;
							}
							if(!heroKilled){
								grids[k][i] = 0;
							}
							killed = true;
							break;
						}
					}
					if (j + Constant.BULLET_STEPS_ONE_TURN < Constant.HEIGHT) {
						if (!killed) {
							grids[end][i] = 5;
						}
					}
					for(int m = 0;m<=end;m++){
						if(grids[m][i] == 4){
							grids[m][i] = 0;
							grids[end][i] = 0;
							heroKilled = false;
							break;
						}
					}
					
					grids[j][i] = 0;
				}
			}
		}
		return heroKilled;
	}

	/**
	 * This method checks whether the hero wins
	 * @return if the hero wins
	 */
	public boolean checkWin() {
		boolean win = true;
		for (int i = 0; i < Constant.WIDTH; i++) {
			for (int j = 0; j < Constant.HEIGHT; j++) {
				if (grids[j][i] == 1) {
					win = false;
				}
			}
		}
		return win;
	}

}
