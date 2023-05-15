package Dynamic_beat_Final;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {

	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private Image noteOddImage = new ImageIcon(Main.class.getResource("../images/noteOdd.png")).getImage();
	private Image noteSpaceImage = new ImageIcon(Main.class.getResource("../images/noteSpace.png")).getImage();

	
	private int x, y = 580 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;
	private String noteType;
	private boolean proceeded = true;
	
	public String getNoteType() {
		return noteType;
	}
	
	public boolean  isProceeded() {
		return proceeded;
	}
	
	public void close() {
		proceeded = false;
	}
	
	public Note(String noteType) {
		if(noteType.equals("S")) {
			x = 228;
		}
		else if(noteType.equals("D")) {
			x = 332;
		}
		else if(noteType.equals("F")) {
			x = 436;
		}
		else if(noteType.equals("Space")) {
			x = 540;
		}
		else if(noteType.equals("J")) {
			x = 744;
		}
		else if(noteType.equals("K")) {
			x = 848;
		}
		else if(noteType.equals("L")) {
			x = 952;
		}
		this.noteType = noteType;
	}

	public void screenDraw(Graphics2D g) {
		if (noteType.equals("Space")) 
		{
			g.drawImage(noteSpaceImage, x, y, null);
			g.drawImage(noteSpaceImage, x + 100, y, null);
		} 
		else if (noteType.equals("S"))
		{
			g.drawImage(noteOddImage, x, y, null);

		}
		else if (noteType.equals("F"))
		{
			g.drawImage(noteOddImage, x, y, null);

		}
		else if (noteType.equals("J"))
		{
			g.drawImage(noteOddImage, x, y, null);

		}
		else if (noteType.equals("L"))
		{
			g.drawImage(noteOddImage, x, y, null);

		}
		else 
		{
			g.drawImage(noteBasicImage, x, y, null);
		}
	}

	public void drop() {
		y += Main.NOTE_SPEED;
		if(y > 620) {
			System.out.println("Miss");
			close();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				drop();
				if(proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
				}
				else {
					interrupt();
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	public String judge() {
		if (y >= 619) {
			System.out.println("Late");
			close();
			return "Late";
		}
		else if (y >= 610) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if (y >= 590) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if (y >= 573) {
			System.out.println("Perfect");
			close();
			return "Perfect";
		}
		else if (y >= 556) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if (y >= 536) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if (y >= 545) {
			System.out.println("Early");
			close();
			return "Early";
		}
		return "None";
	}
	public int getY() {
		return y;
	}

}
