/**
 * @author huangshengwei
 * @studentID 1475765
 */
package main;

import java.util.Scanner;

import view.Level;
/**
 * 
 * 
 * @this is the main class, then entrance of this program
 */
public class Main {
	private static Scanner scanner = new Scanner(System.in);

	/**
	 * this is the main method
	 * @param args the parameters from the command line
	 */
	public static void main(String args[]) {
		Level level = Level.getInstance();
		while (true) {
			level.draw();
			String cmd = getUserInput();
			if (cmd.equalsIgnoreCase("Q") || cmd.equalsIgnoreCase("Quit")) {
				break;
			}else
			if (cmd.equalsIgnoreCase("A")) {
				level.left();
			}else
			if (cmd.equalsIgnoreCase("D")) {
				level.right();
			}else
			if (cmd.equalsIgnoreCase("F")) {
				level.fire();
			}else
			if (cmd.equalsIgnoreCase("AF")) {
				level.left();
				level.fire();
			}else
			if (cmd.equalsIgnoreCase("DF")) {
				level.right();
				level.fire();
			}else
			if (cmd.equalsIgnoreCase("FD")) {
				level.fire();
				level.right();
			}else
			if (cmd.equalsIgnoreCase("FA")) {
				level.fire();
				level.left();
			}else{
				System.out.println("Invalid Command!");
				continue;
			}
			boolean end = level.moveAliens();
			if (end) {
				System.out.println("Game Over! You have lost!");
				break;
			}
			end = level.moveBullets();
			if (end) {
				if (level.subHeroLives()) {
					System.out.println("Game Over! You have lost!");
					break;
				}else{
					System.out.println("You have lost one life!");
				}
			}
			if (level.checkWin()) {
				System.out.println("Game Over! You won!");
				break;
			}
			if((!level.aliensLeft())&&(!level.aliensRight())){
				level.alienFire();
			}
		}
		scanner.close();
	}

	private static String getUserInput() {
		System.out.println("Please input the command(A/D/F/AF/DF/FD/FA/Q(uit) ):");
		String input = scanner.nextLine();
		return input;
	}
}
