import java.util.Scanner;
import javax.swing.JFrame;


public class MasterMindTest {
	public static void main(String args[]){
		int c;
	
		if(args.length != 1)
			System.out.printf("ERROR!\nFormat : <filename> <Human/Computer>\n");
		if(args[0].equals("Human"))
			c = 0;
		else
			c = 1;
		
		
		MasterMind game = new MasterMind(c);
		if(c == 1)
			game.setTitle("MasterMind_Computer");
		else
			game.setTitle("MasterMind_Human");
		game.setSize(500, 600);
		game.setLocationRelativeTo(null);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		
	}
}
