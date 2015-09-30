import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;

public class MasterMind extends JFrame{
	private char colors[][] = new char[3125][5], avail[] = {'R', 'B', 'G', 'Y', 'P'};
	private char inH[][] = new char[10][5];
	private int iH = 0, row = 0, rand = (int)(Math.random() * 3125), countB = 0, countW = 0;
	private String ansH[] = new String[10];
	
	private int next[] = new int[3125], index = 0, head = 0, lastHead = 0;
	
	private String textDefault = new String("Now playing..."), 
			       textWinH = new String("Congratulations!! You WIN!!"), 
			       textLoseH = new String("Sorry, but...you lose. Maybe try again?"), 
			       textWinC = new String("Haha! Computer WINs!!"), 
			       textLoseC = new String("What!? Computer loses!? There's something wrong!!");
	
	private int c;
	private boolean gamePlaying = true;
	private Color bg = new Color(238, 238, 238);
	
	private ImageIcon rIcon = new ImageIcon("R.png");
	private ImageIcon bIcon = new ImageIcon("B.png");
	private ImageIcon gIcon = new ImageIcon("G.png");
	private ImageIcon yIcon = new ImageIcon("Y.png");
	private ImageIcon pIcon = new ImageIcon("P.png");	
	private ImageIcon BlIcon = new ImageIcon("Black.png");
	private ImageIcon WhIcon = new ImageIcon("White.png");
	
	private JButton rB = new JButton(rIcon);
	private JButton bB = new JButton(bIcon);
	private JButton gB = new JButton(gIcon);
	private JButton yB = new JButton(yIcon);
	private JButton pB = new JButton(pIcon);
	private JButton BlB = new JButton(BlIcon);
	private JButton WhB = new JButton(WhIcon);
	private JButton Re = new JButton("RESET");
	private JButton Do = new JButton("DONE");
	
	private MasterMindPanel p = new MasterMindPanel();
	private JTextField jtf = new JTextField(");
	
	public MasterMind(int c){
		this.c = c;
		
		for(int i = 0; i < 10; i++)
			ansH[i] = new String("0B0W");
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				for(int k = 0; k < 5; k++)
					for(int m = 0; m < 5; m++)
						for(int n = 0; n < 5; n++){
							colors[index][0] = avail[i]; 
							colors[index][1] = avail[j];
							colors[index][2] = avail[k];
							colors[index][3] = avail[m];
							colors[index][4] = avail[n];
							next[index] = ++index;
						}
		next[3124] = 0;	
		index = 0;
		
		if(c == 1)
			Computer(0);
				
		
		//this.setBackground(bg);
		//p.setBackground(bg);
		
		JPanel p1 = new JPanel();
		
		jtf.setEditable(false);
		//p1.setBackground(bg);
		p1.setLayout(new GridLayout(1, 1));
		p1.add(jtf);
		add(p1, BorderLayout.NORTH);
		
		JPanel p2 = new JPanel();
		//p2.setBackground(bg);
		if(c == 1){
			p2.setLayout(new GridLayout(1, 2));
			p2.add(BlB);
			p2.add(WhB);
		}
		else{
			p2.setLayout(new GridLayout(1, 5));
			p2.add(rB);
			p2.add(bB);
			p2.add(gB);
			p2.add(yB);
			p2.add(pB);
		}
		
		
		JPanel p3 = new JPanel();
		//p3.setBackground(bg);
		p3.setLayout(new GridLayout(1, 2));
		p3.add(Re);
		p3.add(Do);
		
		
		JPanel p4 = new JPanel();
		//p4.setBackground(bg);
		p4.add(p2, BorderLayout.CENTER);
		p4.add(p3, BorderLayout.SOUTH);
		
		
		add(p, BorderLayout.CENTER);
		add(p4, BorderLayout.SOUTH);
		
		rB.addActionListener(new ButtonListener());
		bB.addActionListener(new ButtonListener());
		gB.addActionListener(new ButtonListener());
		yB.addActionListener(new ButtonListener());
		pB.addActionListener(new ButtonListener());
		BlB.addActionListener(new ButtonListener());
		WhB.addActionListener(new ButtonListener());
		Re.addActionListener(new ButtonListener());
		Do.addActionListener(new ButtonListener());
	}
	
	class MasterMindPanel extends JPanel{	
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
						
			for(int i = 0; i < 10; i++)
				for(int j = 0; j < 5; j++){
					if(inH[i][j] == 'R')
						g.drawImage(rIcon.getImage(), 10 + j * 35, getHeight() - 80 - i * getHeight() / 12, 30, 30, this);
					else if(inH[i][j] == 'B')
						g.drawImage(bIcon.getImage(), 10 + j * 35, getHeight() - 80 - i * getHeight() / 12, 30, 30, this);
					else if(inH[i][j] == 'G')
						g.drawImage(gIcon.getImage(), 10 + j * 35, getHeight() - 80 - i * getHeight() / 12, 30, 30, this);
					else if(inH[i][j] == 'Y')
						g.drawImage(yIcon.getImage(), 10 + j * 35, getHeight() - 80 - i * getHeight() / 12, 30, 30, this);
					else if(inH[i][j] == 'P')
						g.drawImage(pIcon.getImage(), 10 + j * 35, getHeight() - 80 - i * getHeight() / 12, 30, 30, this);
					else{
						g.setColor(bg);
						g.fillRect(10 + j * 35, getHeight() - 80 - i * getHeight() / 12, 30, 30);
						g.setColor(Color.BLACK);
					}
				}
			
			for(int i = 0; i < 10; i++){
					int j;
					
					for(j = 0; j < ansH[i].charAt(0) - '0'; j++)
						g.drawImage(BlIcon.getImage(), getWidth() / 2 + 10 + j * 35, getHeight() - 80 - i * getHeight() / 12, 30, 30, this);
					for(; j < ansH[i].charAt(0) - '0' + ansH[i].charAt(2) - '0'; j++)
						g.drawImage(WhIcon.getImage(), getWidth() / 2 + 10 + j * 35, getHeight() - 80 - i * getHeight() / 12, 30, 30, this);				
			}
		}
	}
	
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(gamePlaying){
				if(e.getSource() == rB){
					if(iH < 5)
						inH[row][iH++] = 'R';
					p.repaint();
				}
				else if(e.getSource() == bB){
					if(iH < 5)
						inH[row][iH++] = 'B';
					p.repaint();
				}
				else if(e.getSource() == gB){
					if(iH < 5)
						inH[row][iH++] = 'G';			
					p.repaint();
				}
				else if(e.getSource() == yB){
					if(iH < 5)
						inH[row][iH++] = 'Y';
					p.repaint();
				}
				else if(e.getSource() == pB){
					if(iH < 5)
						inH[row][iH++] = 'P';
					p.repaint();
				}
				else if(e.getSource() == BlB){
					if(countB + countW < 5){
						countB++;
						ansH[row] = String.format(countB + "B" + countW + "W");
					}
					
					p.repaint();
				}
				else if(e.getSource() == WhB){
					if(countB + countW < 5){
						countW++;
						ansH[row] = String.format(countB + "B" + countW + "W");	
					}
					
					p.repaint();
				}
				else if(e.getSource() == Re){
					if(c == 1){
						countB = countW = 0;
						ansH[row] = String.format(countB + "B" + countW + "W");
					}
					else{
						for(int i = 0; i < 5; i++)
							inH[row][i] = 0;
						iH = 0;
					}
					p.repaint();
				}
				else if(e.getSource() == Do){
					if(c == 1){
						if(countB == 5){
							jtf.setText(textWinC);
							gamePlaying = false;
						}
						else{
							if(row == 9){
								jtf.setText(textLoseC);
								gamePlaying = false;
							}
							else{
								Computer(1);
								row++;
								countB = countW = 0;
								iH = 0;
								Computer(0);
							}
						}
					}
					else{
						if(iH == 5){			
							if(Human()){
								jtf.setText(textWinH);
								gamePlaying = false;
							}
							else{
								if(row == 9){
									jtf.setText(textLoseH);
									gamePlaying = false;
								}
								else{
									row++;
									iH = 0;
								}
							}
						}
					}
					p.repaint();
				}
			}
		}
	}
	
	private String Compare(char ans[], char in[]){
		int B = 0, W = 0;
		
		for(int i = 0; i < 5; i++)
			if(ans[i] == in[i]){
				B++;
				ans[i] = in[i] = 'N';
			}
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				if(ans[j] == in[i] && ans[j] != 'N'){
					W++;
					ans[j] = in[i] = 'N';
				}
		
		//System.out.println(String.format("***" + B + "B" + W + "W"));
		return String.format(B + "B" + W + "W");
	}
	
	private boolean Human(){
		char in[] = inH[row];
		
		System.out.printf("Hints: %c%c%c%c%c\n", colors[rand][0], colors[rand][1], colors[rand][2], colors[rand][3], colors[rand][4]);

		ansH[row] = Compare(colors[rand].clone(), in.clone());
		if(ansH[row].equals("5B0W"))
			return true;
		else
			return false;
	}
	
	private void Computer(int step){
		if(step == 0){
			for(int i = 0; i < 5; i++)
				inH[row][iH++] = colors[lastHead][i];
		}
		else{
			for(int i = next[lastHead]; i != lastHead; i = next[i]){
				if(ansH[row].equals(Compare(colors[lastHead].clone(), colors[i].clone()))){
					if(index == lastHead)
						index = head = i;
					else{
						next[index] = i;
						index = i;
					}
				}
			}
			next[index] = head;
			lastHead = head;
			index = lastHead;			
		}
	}
}
