package Dynamic_beat_Final;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class scoreResult extends Thread {
    private Image scoreResult = new ImageIcon(getClass().getResource("../images/scoreResult.png")).getImage();
	
    Graphics2D g;
	public scoreResult(Graphics2D g){
        this.g = g;
    }

    public void screenDraw(Graphics2D g){
        String grade=null;


        int totalScore = DynamicBeat.game.highScore;
        int totalCombo = DynamicBeat.game.highCombo;

        if(totalScore > (300*100*0.9)) {
            grade = "S";
        }else if(totalScore > (300*100*0.6)) {
            grade = "A";
        }else if(totalScore > (300*100*0.4)) {
            grade = "B";
        }else if(totalScore >= 0) {
            grade = "C";
        }
        g.drawImage(scoreResult, 0, 0, null);

        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.setColor(Color.white);
        g.drawString("Score : "+String.valueOf(totalScore), 350, 290);
        g.drawString("Combo : "+String.valueOf(totalCombo), 350,400);
        g.setColor(Color.pink);
        g.drawString(grade, 600, 600);
    }

    @Override
    public void run() {
        screenDraw(g);
		
    }

    public void close(){
        interrupt();
    }

}