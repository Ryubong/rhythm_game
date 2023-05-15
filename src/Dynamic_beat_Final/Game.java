package Dynamic_beat_Final;
//노트 및 싱크 설정
//노트가 늦게 내려올땐 리치 타임 * 값을 더 높힐것
//노트가 빨리 내려올땐 리치 타임 * 값을 낮출것

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import dynamic_beat_19.Beat;
import dynamic_beat_19.Main;

public class Game extends Thread {

	private Image noteRouteLineImage = new ImageIcon(Main.class.getResource("../images/noteRouteLine.png")).getImage();
	private Image judgementLineImage = new ImageIcon(Main.class.getResource("../images/judgementLine.png")).getImage();
	private Image gameInfoImage = new ImageIcon(Main.class.getResource("../images/gameInfo.png")).getImage();
	private Image noteRouteSImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteDImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteFImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteSpace1Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteSpace2Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteJImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteKImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteLImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image blueFlareImage;
	private Image judgeImage;

	
	private String titleName;
	private String difficulty;
	private String musicTitle;
	private Music gameMusic;
	
	static boolean noteMaker = false;
    FileWriter fw;
	
	int score = 0;
	int highScore = 0;
	
	int combo = 0;
	int highCombo = 0;
	private Image comboImage = new ImageIcon(Main.class.getResource("../images/combo.png")).getImage();

	ArrayList<Note> noteList = new ArrayList<Note>();

	public Game(String titleName, String difficulty, String musicTitle) {
		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		gameMusic = new Music(this.musicTitle, false);
		
		if (noteMaker) {
            try {
                // 작성자가 입력한 시간과 입력한 키를 노래명_난이도.txt 로 저장
                fw = new FileWriter("../images" + titleName + ".txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(noteRouteSImage, 228, 30, null);
		g.drawImage(noteRouteDImage, 332, 30, null);
		g.drawImage(noteRouteFImage, 436, 30, null);
		g.drawImage(noteRouteSpace1Image, 540, 30, null);
		g.drawImage(noteRouteSpace2Image, 640, 30, null);
		g.drawImage(noteRouteJImage, 744, 30, null);
		g.drawImage(noteRouteKImage, 848, 30, null);
		g.drawImage(noteRouteLImage, 952, 30, null);
		g.drawImage(noteRouteLineImage, 224, 30, null);
		g.drawImage(noteRouteLineImage, 328, 30, null);
		g.drawImage(noteRouteLineImage, 432, 30, null);
		g.drawImage(noteRouteLineImage, 536, 30, null);
		g.drawImage(noteRouteLineImage, 740, 30, null);
		g.drawImage(noteRouteLineImage, 844, 30, null);
		g.drawImage(noteRouteLineImage, 948, 30, null);
		g.drawImage(noteRouteLineImage, 1052, 30, null);
		g.drawImage(gameInfoImage, 0, 660, null);
		g.drawImage(judgementLineImage, 0, 580, null);
		g.drawImage(blueFlareImage, 260, 200, null);
		g.drawImage(judgeImage, 562, 440, null);

		
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (note.getY() > 620) {
				judgeImage = new ImageIcon(Main.class.getResource("../images/judgeMiss.png")).getImage();
				score -= 10;
				combo = 0;
			}
			if(!note.isProceeded()) {
				noteList.remove(i);
				i--;
			}
			else {
				note.screenDraw(g);
			}
		}
		// 점수 출력
		g.drawImage(comboImage, 1050, 130, null);
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString(String.valueOf(highCombo), 1150, 270);
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 35));
		highScore = score;

        if(highCombo < combo){
            highCombo = combo;
        }
		
		if(score<0) {
			g.drawString(String.valueOf(0), 620, 702);
		}else {
			g.drawString(String.valueOf(score), 620, 702);
		}
		
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(titleName, 20, 702);
		g.drawString(difficulty, 1190, 702);
		g.setFont(new Font("Arial", Font.PLAIN, 26));
		g.setColor(Color.DARK_GRAY);
		g.drawString("S", 270, 609);
		g.drawString("D", 374, 609);
		g.drawString("F", 478, 609);
		g.drawString("Space Bar", 580, 609);
		g.drawString("J", 784, 609);
		g.drawString("K", 889, 609);
		g.drawString("L", 993, 609);
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
	}

	public void pressS() {
		judge("S");
		if (noteMaker == true) {
            System.out.println(gameMusic.getTime() + " S");
        }
		noteRouteSImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
	}

	public void releaseS() {
		noteRouteSImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressD() {
		judge("D");
		if (noteMaker == true) {
            System.out.println(gameMusic.getTime() + " D");
        }
		noteRouteDImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
	}

	public void releaseD() {
		noteRouteDImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressF() {
		judge("F");
		if (noteMaker == true) {
            System.out.println(gameMusic.getTime() + " F");
        }
		noteRouteFImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
	}

	public void releaseF() {
		noteRouteFImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressSpace() {
		judge("Space");
		if (noteMaker == true) {
            System.out.println(gameMusic.getTime() + "Space");
        }
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
	}

	public void releaseSpace() {
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressJ() {
		judge("J");
		if (noteMaker == true) {
            System.out.println(gameMusic.getTime() + " J");
        }
		noteRouteJImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
	}

	public void releaseJ() {
		noteRouteJImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressK() {
		judge("K");
		if (noteMaker == true) {
            System.out.println(gameMusic.getTime() + " K");
        }
		noteRouteKImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
	}

	public void releaseK() {
		noteRouteKImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressL() {
		judge("L");
		if (noteMaker == true) {
            System.out.println(gameMusic.getTime() + " L");
        }
		noteRouteLImage = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
	}

	public void releaseL() {
		noteRouteLImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	@Override
	public void run() {
		dropNotes(this.titleName);
	}

	public void close() {
		gameMusic.close();
		this.interrupt();
	}

	public void dropNotes(String titleName) {
		Beat[] beats = null;
		if(titleName.equals("King") && difficulty.equals("Easy")) {
			int startTime = 4000 - Main.REACH_TIME * 3050;
			beats = new Beat[] { 
					new Beat(3310 + startTime ,"Space"),
					new Beat(3670 + startTime ,"J"),
					new Beat(3810 + startTime ,"F"),
					new Beat(4010 + startTime ,"K"),
					new Beat(4190 + startTime ,"D"),
					new Beat(4380 + startTime ,"L"),
					new Beat(4580 + startTime ,"K"),
					new Beat(4760 + startTime ,"K"),
					new Beat(4930 + startTime ,"Space"),
					new Beat(5130 + startTime ,"F"),
					new Beat(5310 + startTime ,"F"),
					new Beat(5560 + startTime ,"Space"),
					new Beat(5880 + startTime ,"Space"),
					new Beat(6200 + startTime ,"D"),
					new Beat(6210 + startTime ,"K"),
					new Beat(6210 + startTime ,"Space"),
					new Beat(6580 + startTime ,"L"),
					new Beat(6750 + startTime ,"D"),
					new Beat(7030 + startTime ,"K"),
					new Beat(7220 + startTime ,"J"),
					new Beat(7340 + startTime ,"Space"),
					new Beat(7530 + startTime ,"F"),
					new Beat(7730 + startTime ,"Space"),
					new Beat(7920 + startTime ,"K"),
					new Beat(8070 + startTime ,"K"),
					new Beat(8350 + startTime ,"D"),
					new Beat(9040 + startTime ,"L"),
					new Beat(9050 + startTime ,"S"),
					new Beat(9440 + startTime ,"K"),
					new Beat(9650 + startTime ,"D"),
					new Beat(9830 + startTime ,"J"),
					new Beat(9960 + startTime ,"F"),
					new Beat(10180 + startTime ,"Space"),
					new Beat(10390 + startTime ,"K"),
					new Beat(10550 + startTime ,"L"),
					new Beat(10730 + startTime ,"K"),
					new Beat(10910 + startTime ,"J"),
					new Beat(11050 + startTime ,"Space"),
					new Beat(11260 + startTime ,"D"),
					new Beat(11620 + startTime ,"K"),
					new Beat(11620 + startTime ,"D"),
					new Beat(11640 + startTime ,"Space"),
					new Beat(12000 + startTime ,"J"),
					new Beat(12360 + startTime ,"F"),
					new Beat(12560 + startTime ,"Space"),
					new Beat(12770 + startTime ,"J"),
					new Beat(12920 + startTime ,"F"),
					new Beat(13080 + startTime ,"J"),
					new Beat(13210 + startTime ,"Space"),
					new Beat(13410 + startTime ,"D"),
					new Beat(13520 + startTime ,"K"),
					new Beat(13690 + startTime ,"K"),
					new Beat(13870 + startTime ,"Space"),
					new Beat(14040 + startTime ,"F"),
					new Beat(14470 + startTime ,"K"),
					new Beat(14470 + startTime ,"Space"),
					new Beat(14470 + startTime ,"D"),
					new Beat(14780 + startTime ,"S"),
					new Beat(14830 + startTime ,"J"),
					new Beat(15570 + startTime ,"K"),
					new Beat(15950 + startTime ,"D"),
					new Beat(16129 + startTime ,"L"),
					new Beat(16460 + startTime ,"S"),
					new Beat(16640 + startTime ,"K"),
					new Beat(16840 + startTime ,"D"),
					new Beat(17040 + startTime ,"Space"),
					new Beat(17400 + startTime ,"J"),
					new Beat(17410 + startTime ,"F"),
					new Beat(17750 + startTime ,"Space"),
					new Beat(17800 + startTime ,"K"),
					new Beat(18160 + startTime ,"K"),
					new Beat(18340 + startTime ,"D"),
					new Beat(18530 + startTime ,"L"),
					new Beat(18850 + startTime ,"S"),
					new Beat(19040 + startTime ,"K"),
					new Beat(19380 + startTime ,"Space"),
					new Beat(19580 + startTime ,"J"),
					new Beat(19940 + startTime ,"F"),
					new Beat(20290 + startTime ,"K"),
					new Beat(20630 + startTime ,"S"),
					new Beat(20630 + startTime ,"D"),
					new Beat(21030 + startTime ,"K"),
					new Beat(21030 + startTime ,"L"),
					new Beat(21400 + startTime ,"F"),
					new Beat(21400 + startTime ,"D"),
					new Beat(21720 + startTime ,"Space"),
					new Beat(21890 + startTime ,"Space"),
					new Beat(22250 + startTime ,"J"),
					new Beat(22410 + startTime ,"F"),
					new Beat(22750 + startTime ,"J"),
					new Beat(22750 + startTime ,"Space"),
					new Beat(23150 + startTime ,"F"),
					new Beat(23150 + startTime ,"Space"),
					new Beat(23530 + startTime ,"F"),
					new Beat(23530 + startTime ,"J"),
					new Beat(23850 + startTime ,"F"),
					new Beat(23880 + startTime ,"J"),
					new Beat(24040 + startTime ,"F"),
					new Beat(24060 + startTime ,"J"),
					new Beat(24260 + startTime ,"D"),
					new Beat(24260 + startTime ,"K"),
					new Beat(24570 + startTime ,"S"),
					new Beat(24600 + startTime ,"L"),
					new Beat(24760 + startTime ,"K"),
					new Beat(24780 + startTime ,"D"),
					new Beat(25120 + startTime ,"F"),
					new Beat(25130 + startTime ,"J"),
					new Beat(25320 + startTime ,"Space"),
					new Beat(25670 + startTime ,"F"),
					new Beat(25670 + startTime ,"J"),
					new Beat(26630 + startTime ,"K"),
					new Beat(26630 + startTime ,"L"),
					new Beat(26820 + startTime ,"K"),
					new Beat(27000 + startTime ,"K"),
					new Beat(27240 + startTime ,"Space"),
					new Beat(27410 + startTime ,"F"),
					new Beat(27620 + startTime ,"Space"),
					new Beat(27790 + startTime ,"D"),
					new Beat(27910 + startTime ,"J"),
					new Beat(28070 + startTime ,"F"),
					new Beat(28250 + startTime ,"Space"),
					new Beat(28400 + startTime ,"F"),
					new Beat(28590 + startTime ,"F"),
					new Beat(28900 + startTime ,"J"),
					new Beat(29110 + startTime ,"D"),
					new Beat(29500 + startTime ,"K"),
					new Beat(29680 + startTime ,"D"),
					new Beat(29870 + startTime ,"Space"),
					new Beat(30030 + startTime ,"J"),
					new Beat(30180 + startTime ,"F"),
					new Beat(30410 + startTime ,"Space"),
					new Beat(30570 + startTime ,"K"),
					new Beat(30730 + startTime ,"D"),
					new Beat(30880 + startTime ,"L"),
					new Beat(31060 + startTime ,"S"),
					new Beat(31220 + startTime ,"D"),
					new Beat(31410 + startTime ,"K"),
					new Beat(31410 + startTime ,"D"),
					new Beat(31430 + startTime ,"L"),
					new Beat(31710 + startTime ,"D"),
					new Beat(31730 + startTime ,"K"),
					new Beat(31970 + startTime ,"J"),
					new Beat(31990 + startTime ,"D"),
					new Beat(31990 + startTime ,"S"),
					new Beat(32420 + startTime ,"Space"),
					new Beat(32430 + startTime ,"K"),
					new Beat(32570 + startTime ,"F"),
					new Beat(32640 + startTime ,"J"),
					new Beat(32750 + startTime ,"Space"),
					new Beat(32939 + startTime ,"D"),
					new Beat(33130 + startTime ,"F"),
					new Beat(33259 + startTime ,"K"),
					new Beat(33450 + startTime ,"Space"),
					new Beat(33660 + startTime ,"J"),
					new Beat(33820 + startTime ,"F"),
					new Beat(34020 + startTime ,"J"),
					new Beat(34150 + startTime ,"Space"),
					new Beat(34350 + startTime ,"D"),
					new Beat(34350 + startTime ,"K"),
					new Beat(34370 + startTime ,"Space"),
					new Beat(34650 + startTime ,"F"),
					new Beat(34670 + startTime ,"J"),
					new Beat(34670 + startTime ,"Space"),
					new Beat(34910 + startTime ,"J"),
					new Beat(34920 + startTime ,"Space"),
					new Beat(34920 + startTime ,"F"),
					new Beat(35320 + startTime ,"D"),
					new Beat(35490 + startTime ,"S"),
					new Beat(35690 + startTime ,"D"),
					new Beat(35840 + startTime ,"K"),
					new Beat(36040 + startTime ,"Space"),
					new Beat(36180 + startTime ,"L"),
					new Beat(36380 + startTime ,"F"),
					new Beat(36570 + startTime ,"Space"),
					new Beat(36590 + startTime ,"K"),
					new Beat(36920 + startTime ,"F"),
					new Beat(36930 + startTime ,"J"),
					new Beat(37270 + startTime ,"D"),
					new Beat(37280 + startTime ,"Space"),
					new Beat(37600 + startTime ,"S"),
					new Beat(37620 + startTime ,"Space"),
					new Beat(37920 + startTime ,"D"),
					new Beat(37920 + startTime ,"J"),
					new Beat(37940 + startTime ,"Space"),
					new Beat(39440 + startTime ,"D"),
					new Beat(39440 + startTime ,"K"),
					new Beat(39440 + startTime ,"Space"),
					new Beat(39600 + startTime ,"D"),
					new Beat(39620 + startTime ,"Space"),
					new Beat(39630 + startTime ,"K"),
					new Beat(39780 + startTime ,"D"),
					new Beat(39780 + startTime ,"Space"),
					new Beat(39800 + startTime ,"K"),
					new Beat(39950 + startTime ,"D"),
					new Beat(39970 + startTime ,"Space"),
					new Beat(39980 + startTime ,"K"),
					new Beat(40150 + startTime ,"D"),
					new Beat(40170 + startTime ,"Space"),
					new Beat(40180 + startTime ,"K"),
					new Beat(40880 + startTime ,"S"),
					new Beat(40880 + startTime ,"L"),
					new Beat(41240 + startTime ,"D"),
					new Beat(41240 + startTime ,"K"),
					new Beat(41540 + startTime ,"F"),
					new Beat(41580 + startTime ,"J"),
					new Beat(41840 + startTime ,"Space"),
					new Beat(42110 + startTime ,"J"),
					new Beat(42290 + startTime ,"F"),
					new Beat(42470 + startTime ,"K"),
					new Beat(42680 + startTime ,"D"),
					new Beat(42850 + startTime ,"L"),
					new Beat(43050 + startTime ,"S"),
					new Beat(43330 + startTime ,"K"),
					new Beat(43570 + startTime ,"Space"),
					new Beat(43760 + startTime ,"L"),
					new Beat(43760 + startTime ,"K"),
					new Beat(44150 + startTime ,"D"),
					new Beat(44320 + startTime ,"S"),
					new Beat(44520 + startTime ,"Space"),
					new Beat(44690 + startTime ,"F"),
					new Beat(44900 + startTime ,"Space"),
					new Beat(45060 + startTime ,"D"),
					new Beat(45240 + startTime ,"Space"),
					new Beat(45430 + startTime ,"J"),
					new Beat(45570 + startTime ,"F"),
					new Beat(45760 + startTime ,"Space"),
					new Beat(45930 + startTime ,"K"),
					new Beat(46080 + startTime ,"D"),
					new Beat(46270 + startTime ,"J"),
					new Beat(46430 + startTime ,"F"),
					new Beat(46630 + startTime ,"Space"),
					new Beat(47030 + startTime ,"K"),
					new Beat(47220 + startTime ,"D"),
					new Beat(47410 + startTime ,"L"),
					new Beat(47580 + startTime ,"S"),
					new Beat(47750 + startTime ,"K"),
					new Beat(47940 + startTime ,"D"),
					new Beat(48130 + startTime ,"Space"),
					new Beat(48320 + startTime ,"J"),
					new Beat(48490 + startTime ,"F"),
					new Beat(48680 + startTime ,"Space"),
					new Beat(48870 + startTime ,"Space"),
					new Beat(48880 + startTime ,"J"),
					new Beat(49150 + startTime ,"Space"),
					new Beat(49170 + startTime ,"F"),
					new Beat(49390 + startTime ,"Space"),
					new Beat(49390 + startTime ,"F"),
					new Beat(49890 + startTime ,"D"),
					new Beat(49890 + startTime ,"K"),
					new Beat(50090 + startTime ,"J"),
					new Beat(50210 + startTime ,"Space"),
					new Beat(50410 + startTime ,"D"),
					new Beat(50610 + startTime ,"J"),
					new Beat(50760 + startTime ,"F"),
					new Beat(50970 + startTime ,"Space"),
					new Beat(50980 + startTime ,"K"),
					new Beat(51340 + startTime ,"F"),
					new Beat(51360 + startTime ,"J"),
					new Beat(51730 + startTime ,"D"),
					new Beat(51750 + startTime ,"K"),
					new Beat(51750 + startTime ,"Space"),
					new Beat(52070 + startTime ,"F"),
					new Beat(52080 + startTime ,"J"),
					new Beat(52430 + startTime ,"L"),
					new Beat(52470 + startTime ,"K"),
					new Beat(52550 + startTime ,"J"),
					new Beat(52630 + startTime ,"Space"),
					new Beat(52840 + startTime ,"Space"),
					new Beat(53160 + startTime ,"S"),
					new Beat(53200 + startTime ,"D"),
					new Beat(53260 + startTime ,"F"),
					new Beat(53580 + startTime ,"Space"),
					new Beat(54070 + startTime ,"Space"),
					new Beat(54100 + startTime ,"K"),
					new Beat(54420 + startTime ,"F"),
					new Beat(54440 + startTime ,"J"),
					new Beat(54610 + startTime ,"D"),
					new Beat(54630 + startTime ,"Space"),
					new Beat(54790 + startTime ,"F"),
					new Beat(54970 + startTime ,"Space"),
					new Beat(55120 + startTime ,"F"),
					new Beat(55310 + startTime ,"J"),
					new Beat(55340 + startTime ,"Space"),
					new Beat(55870 + startTime ,"Space"),
					new Beat(55870 + startTime ,"D"),
					new Beat(55890 + startTime ,"J"),
					new Beat(56210 + startTime ,"F"),
					new Beat(56230 + startTime ,"Space"),
					new Beat(56230 + startTime ,"K"),
					new Beat(56580 + startTime ,"L"),
					new Beat(56580 + startTime ,"Space"),
					new Beat(56580 + startTime ,"D"),
					new Beat(56580 + startTime ,"K"),
					new Beat(57000 + startTime ,"D"),
					new Beat(57010 + startTime ,"K"),
					new Beat(57200 + startTime ,"J"),
					new Beat(57260 + startTime ,"Space"),
					new Beat(57470 + startTime ,"F"),
					new Beat(57620 + startTime ,"Space"),
					new Beat(57790 + startTime ,"D"),
					new Beat(57930 + startTime ,"Space"),
					new Beat(58220 + startTime ,"L"),
					new Beat(58270 + startTime ,"K"),
					new Beat(58330 + startTime ,"J"),
					new Beat(58460 + startTime ,"Space"),
					new Beat(58680 + startTime ,"Space"),
					new Beat(58960 + startTime ,"S"),
					new Beat(59000 + startTime ,"D"),
					new Beat(59040 + startTime ,"F"),
					new Beat(59150 + startTime ,"Space"),
					new Beat(59350 + startTime ,"Space"),
					new Beat(59850 + startTime ,"K"),
					new Beat(59870 + startTime ,"D"),
					new Beat(60200 + startTime ,"J"),
					new Beat(60340 + startTime ,"S"),
					new Beat(60530 + startTime ,"D"),
					new Beat(60710 + startTime ,"F"),
					new Beat(60960 + startTime ,"Space"),
					new Beat(61130 + startTime ,"J"),
					new Beat(61650 + startTime ,"K"),
					new Beat(61670 + startTime ,"D"),
					new Beat(62010 + startTime ,"L"),
					new Beat(62030 + startTime ,"Space"),
					new Beat(62340 + startTime ,"K"),
					new Beat(62350 + startTime ,"F"),
					new Beat(62730 + startTime ,"K"),
					new Beat(62730 + startTime ,"L"),
					new Beat(62760 + startTime ,"Space"),
					new Beat(62890 + startTime ,"L"),
					new Beat(62910 + startTime ,"K"),
					new Beat(62910 + startTime ,"Space"),
					new Beat(63260 + startTime ,"D"),
					new Beat(63650 + startTime ,"F"),
					new Beat(64019 + startTime ,"Space"),
					new Beat(65450 + startTime ,"D"),
					new Beat(65459 + startTime ,"Space"),
					new Beat(65459 + startTime ,"K"),
					new Beat(65620 + startTime ,"D"),
					new Beat(65620 + startTime ,"Space"),
					new Beat(65650 + startTime ,"K"),
					new Beat(65780 + startTime ,"D"),
					new Beat(65800 + startTime ,"Space"),
					new Beat(65819 + startTime ,"K"),
					new Beat(65970 + startTime ,"D"),
					new Beat(65980 + startTime ,"Space"),
					new Beat(66000 + startTime ,"K"),
					new Beat(66170 + startTime ,"S"),
					new Beat(66180 + startTime ,"L"),
					new Beat(66510 + startTime ,"K"),
					new Beat(66530 + startTime ,"D"),
					new Beat(66900 + startTime ,"J"),
					new Beat(67090 + startTime ,"Space"),
					new Beat(67280 + startTime ,"K"),
					new Beat(67450 + startTime ,"F"),
					new Beat(67670 + startTime ,"J"),
					new Beat(67870 + startTime ,"Space"),
					new Beat(68050 + startTime ,"F"),
					new Beat(68250 + startTime ,"D"),
					new Beat(68420 + startTime ,"Space"),
					new Beat(68580 + startTime ,"K"),
					new Beat(68700 + startTime ,"J"),
					new Beat(68700 + startTime ,"L"),
					new Beat(68820 + startTime ,"F"),
					new Beat(68980 + startTime ,"Space"),
					new Beat(69010 + startTime ,"K"),
					new Beat(69210 + startTime ,"J"),
					new Beat(69380 + startTime ,"D"),
					new Beat(69560 + startTime ,"Space"),
					new Beat(69750 + startTime ,"D"),
					new Beat(69790 + startTime ,"K"),
					new Beat(69970 + startTime ,"F"),
					new Beat(70000 + startTime ,"J"),
					new Beat(70190 + startTime ,"Space"),
					new Beat(70200 + startTime ,"K"),
					new Beat(70390 + startTime ,"F"),
					new Beat(70410 + startTime ,"J"),
					new Beat(70610 + startTime ,"D"),
					new Beat(70770 + startTime ,"Space"),
					new Beat(70920 + startTime ,"F"),
					new Beat(71110 + startTime ,"Space"),
					new Beat(71290 + startTime ,"J"),
					new Beat(71450 + startTime ,"F"),
					new Beat(71620 + startTime ,"K"),
					new Beat(71790 + startTime ,"Space"),
					new Beat(71950 + startTime ,"J"),
					new Beat(72300 + startTime ,"F"),
					new Beat(72720 + startTime ,"Space"),
					new Beat(72730 + startTime ,"K"),
					new Beat(73090 + startTime ,"J"),
					new Beat(73300 + startTime ,"F"),
					new Beat(73490 + startTime ,"Space"),
					new Beat(73660 + startTime ,"D"),
					new Beat(73850 + startTime ,"F"),
					new Beat(74030 + startTime ,"Space"),
					new Beat(74200 + startTime ,"Space"),
					new Beat(74360 + startTime ,"J"),
					new Beat(74480 + startTime ,"K"),
					new Beat(74500 + startTime ,"F"),
					new Beat(74740 + startTime ,"Space"),
					new Beat(74900 + startTime ,"F"),
					new Beat(75240 + startTime ,"D"),
					new Beat(75580 + startTime ,"S"),
					new Beat(75930 + startTime ,"L"),
					new Beat(76130 + startTime ,"D"),
					new Beat(76270 + startTime ,"K"),
					new Beat(76480 + startTime ,"F"),
					new Beat(76640 + startTime ,"J"),
					new Beat(76830 + startTime ,"Space"),
					new Beat(76950 + startTime ,"F"),
					new Beat(77120 + startTime ,"Space"),
					new Beat(77130 + startTime ,"D"),
					new Beat(77320 + startTime ,"F"),
					new Beat(77540 + startTime ,"Space"),
					new Beat(77720 + startTime ,"J"),
					new Beat(77900 + startTime ,"F"),
					new Beat(78100 + startTime ,"K"),
					new Beat(78270 + startTime ,"D"),
					new Beat(78450 + startTime ,"L"),
					new Beat(78610 + startTime ,"K"),
					new Beat(78830 + startTime ,"J"),
					new Beat(79020 + startTime ,"S"),
					new Beat(79180 + startTime ,"D"),
					new Beat(79340 + startTime ,"D"),
					new Beat(79560 + startTime ,"F"),
					new Beat(79910 + startTime ,"Space"),
					new Beat(80000 + startTime ,"J"),
					new Beat(80190 + startTime ,"F"),
					new Beat(80270 + startTime ,"J"),
					new Beat(80470 + startTime ,"Space"),
					new Beat(80630 + startTime ,"F"),
					new Beat(80790 + startTime ,"J"),
					new Beat(80980 + startTime ,"D"),
					new Beat(81140 + startTime ,"K"),
					new Beat(81310 + startTime ,"S"),
					new Beat(81480 + startTime ,"L"),
					new Beat(81690 + startTime ,"D"),
					new Beat(81820 + startTime ,"K"),
					new Beat(82040 + startTime ,"F"),
					new Beat(82220 + startTime ,"F"),
					new Beat(82450 + startTime ,"Space"),
					new Beat(82820 + startTime ,"J"),
					new Beat(82890 + startTime ,"Space"),
					new Beat(83110 + startTime ,"J"),
					new Beat(83170 + startTime ,"F"),
					new Beat(83380 + startTime ,"Space"),
					new Beat(83570 + startTime ,"F"),
					new Beat(83770 + startTime ,"D"),
					new Beat(83870 + startTime ,"K"),
					new Beat(84030 + startTime ,"S"),
					new Beat(84220 + startTime ,"L"),
					new Beat(84380 + startTime ,"D"),
					new Beat(84560 + startTime ,"K"),
					new Beat(84730 + startTime ,"K"),
					new Beat(84900 + startTime ,"Space"),
					new Beat(85160 + startTime ,"Space"),
					new Beat(85190 + startTime ,"Space"),
					new Beat(85220 + startTime ,"Space"),
					new Beat(85270 + startTime ,"Space"),
					new Beat(85680 + startTime ,"J"),
					new Beat(85740 + startTime ,"F"),
					new Beat(85950 + startTime ,"J"),
					new Beat(86040 + startTime ,"Space"),
					new Beat(86260 + startTime ,"J"),
					new Beat(86430 + startTime ,"F"),
					new Beat(86570 + startTime ,"K"),
					new Beat(86750 + startTime ,"D"),
					new Beat(86910 + startTime ,"L"),
					new Beat(87110 + startTime ,"S"),
					new Beat(87140 + startTime ,"K"),
					new Beat(87320 + startTime ,"K"),
					new Beat(87370 + startTime ,"D"),
					new Beat(87490 + startTime ,"K"),
					new Beat(87550 + startTime ,"D"),
					new Beat(87660 + startTime ,"K"),
					new Beat(87720 + startTime ,"D"),
					new Beat(87860 + startTime ,"J"),
					new Beat(87910 + startTime ,"F"),
					new Beat(88040 + startTime ,"J"),
					new Beat(88050 + startTime ,"F"),
					new Beat(88260 + startTime ,"Space"),
					new Beat(88620 + startTime ,"J"),
					new Beat(88770 + startTime ,"F"),
					new Beat(88910 + startTime ,"J"),
					new Beat(89070 + startTime ,"D"),
					new Beat(89180 + startTime ,"K"),
					new Beat(89360 + startTime ,"S"),
					new Beat(89480 + startTime ,"L"),
					new Beat(89680 + startTime ,"D"),
					new Beat(89800 + startTime ,"K"),
					new Beat(90020 + startTime ,"Space"),
					new Beat(90380 + startTime ,"J"),
					new Beat(90570 + startTime ,"F"),
					new Beat(90750 + startTime ,"K"),
					new Beat(91030 + startTime ,"K"),
					new Beat(91280 + startTime ,"K"),
					new Beat(91440 + startTime ,"D"),
					new Beat(91630 + startTime ,"L"),
					new Beat(91810 + startTime ,"S"),
					new Beat(91940 + startTime ,"K"),
					new Beat(92140 + startTime ,"D"),
					new Beat(92200 + startTime ,"K"),
					new Beat(92410 + startTime ,"K"),
					new Beat(92700 + startTime ,"F"),
					new Beat(92780 + startTime ,"K"),
					new Beat(92940 + startTime ,"Space"),
					new Beat(93160 + startTime ,"K"),
					new Beat(93320 + startTime ,"F"),
					new Beat(93450 + startTime ,"K"),
					new Beat(93610 + startTime ,"Space"),
					new Beat(93690 + startTime ,"F"),
					new Beat(93820 + startTime ,"K"),
					new Beat(94080 + startTime ,"K"),
					new Beat(94280 + startTime ,"D"),
					new Beat(94410 + startTime ,"K"),
					new Beat(94610 + startTime ,"S"),
					new Beat(94810 + startTime ,"L"),
					new Beat(94980 + startTime ,"D"),
					new Beat(95130 + startTime ,"K"),
					new Beat(95320 + startTime ,"F"),
					new Beat(95450 + startTime ,"J"),
					new Beat(95630 + startTime ,"Space"),
					new Beat(95830 + startTime ,"F"),
					new Beat(95860 + startTime ,"J"),
					new Beat(96180 + startTime ,"Space"),
					new Beat(96190 + startTime ,"K"),
					new Beat(96530 + startTime ,"K"),
					new Beat(96820 + startTime ,"K"),
					new Beat(97050 + startTime ,"K"),
					new Beat(97240 + startTime ,"Space"),
					new Beat(97390 + startTime ,"D"),
					new Beat(97570 + startTime ,"F"),
					new Beat(97760 + startTime ,"Space"),
					new Beat(97990 + startTime ,"D"),
					new Beat(98230 + startTime ,"D"),
					new Beat(98500 + startTime ,"D"),
					new Beat(98710 + startTime ,"Space"),
					new Beat(98870 + startTime ,"D"),
					new Beat(99040 + startTime ,"J"),
					new Beat(99200 + startTime ,"D"),
					new Beat(99370 + startTime ,"J"),
					new Beat(99540 + startTime ,"D"),
					new Beat(99690 + startTime ,"Space"),
					new Beat(99880 + startTime ,"D"),
					new Beat(100040 + startTime, "J"),
					new Beat(100160 + startTime, "F"),
					new Beat(100500 + startTime, "Space"),
					new Beat(100860 + startTime, "F"),
					new Beat(100880 + startTime, "J"),
					new Beat(101230 + startTime, "D"),
					new Beat(101230 + startTime, "K"),
					new Beat(101230 + startTime, "Space"),
					new Beat(101560 + startTime, "L"),
					new Beat(101590 + startTime, "K"),
					new Beat(101660 + startTime, "J"),
					new Beat(101700 + startTime, "Space"),
					new Beat(101990 + startTime, "Space"),
					new Beat(102300 + startTime, "S"),
					new Beat(102330 + startTime, "D"),
					new Beat(102360 + startTime, "F"),
					new Beat(102450 + startTime, "Space"),
					new Beat(102710 + startTime, "Space"),
					new Beat(103200 + startTime, "D"),
					new Beat(103210 + startTime, "Space"),
					new Beat(103220 + startTime, "K"),
					new Beat(103580 + startTime, "L"),
					new Beat(103740 + startTime, "Space"),
					new Beat(103910 + startTime, "K"),
					new Beat(104090 + startTime, "J"),
					new Beat(104270 + startTime, "F"),
					new Beat(104480 + startTime, "Space"),
					new Beat(104490 + startTime, "D"),
					new Beat(105010 + startTime, "S"),
					new Beat(105020 + startTime, "Space"),
					new Beat(105070 + startTime, "D"),
					new Beat(105360 + startTime, "D"),
					new Beat(105360 + startTime, "Space"),
					new Beat(105710 + startTime, "Space"),
					new Beat(105710 + startTime, "F"),
					new Beat(106100 + startTime, "J"),
					new Beat(106270 + startTime, "Space"),
					new Beat(106460 + startTime, "K"),
					new Beat(106590 + startTime, "D"),
					new Beat(106750 + startTime, "L"),
					new Beat(106940 + startTime, "K"),
					new Beat(107060 + startTime, "Space"),
					new Beat(107360 + startTime, "L"),
					new Beat(107400 + startTime, "K"),
					new Beat(107480 + startTime, "J"),
					new Beat(107580 + startTime, "Space"),
					new Beat(107790 + startTime, "Space"),
					new Beat(108050 + startTime, "S"),
					new Beat(108090 + startTime, "D"),
					new Beat(108130 + startTime, "F"),
					new Beat(108210 + startTime, "Space"),
					new Beat(108440 + startTime, "Space"),
					new Beat(108960 + startTime, "Space"),
					new Beat(108960 + startTime, "D"),
					new Beat(108960 + startTime, "K"),
					new Beat(109340 + startTime, "F"),
					new Beat(109350 + startTime, "J"),
					new Beat(109520 + startTime, "Space"),
					new Beat(109690 + startTime, "F"),
					new Beat(109870 + startTime, "Space"),
					new Beat(110060 + startTime, "D"),
					new Beat(110250 + startTime, "Space"),
					new Beat(110250 + startTime, "F"),
					new Beat(110790 + startTime, "K"),
					new Beat(110800 + startTime, "D"),
					new Beat(110800 + startTime, "Space"),
					new Beat(111150 + startTime, "F"),
					new Beat(111160 + startTime, "J"),
					new Beat(111510 + startTime, "Space"),
					new Beat(111510 + startTime, "K"),
					new Beat(111870 + startTime, "L"),
					new Beat(111870 + startTime, "K"),
					new Beat(112060 + startTime, "K"),
					new Beat(112060 + startTime, "L"),
					new Beat(112420 + startTime, "K"),
					new Beat(112440 + startTime, "D"),
					new Beat(112440 + startTime, "Space"),
					new Beat(112760 + startTime, "F"),
					new Beat(112790 + startTime, "J"),
					new Beat(113140 + startTime, "J"),
					new Beat(113160 + startTime, "D"),
					new Beat(113160 + startTime, "Space"),
					new Beat(113160 + startTime, "S"),
					new Beat(114580 + startTime, "D"),
					new Beat(114580 + startTime, "K"),
					new Beat(114580 + startTime, "Space"),
					new Beat(114770 + startTime, "F"),
					new Beat(114770 + startTime, "J"),
					new Beat(114950 + startTime, "Space"),
					new Beat(114980 + startTime, "K"),
					new Beat(115170 + startTime, "F"),
					new Beat(115200 + startTime, "J"),
					new Beat(115370 + startTime, "Space"),
					new Beat(115390 + startTime, "D"),
					new Beat(115390 + startTime, "K"),
					new Beat(115710 + startTime, "F"),
					new Beat(115730 + startTime, "J"),
					new Beat(116090 + startTime, "K"),
					new Beat(116110 + startTime, "D"),
					new Beat(116480 + startTime, "L"),
					new Beat(116490 + startTime, "Space"),
					new Beat(116660 + startTime, "K"),
					new Beat(116830 + startTime, "J"),
					new Beat(116920 + startTime, "F"),
					new Beat(117140 + startTime, "Space"),
					new Beat(117330 + startTime, "D"),
					new Beat(117500 + startTime, "F"),
					new Beat(117680 + startTime, "Space"),
					new Beat(117850 + startTime, "D"),
					new Beat(118080 + startTime, "S"),
					new Beat(118250 + startTime, "Space"),
					new Beat(118260 + startTime, "D"),
					new Beat(118610 + startTime, "F"),
					new Beat(118610 + startTime, "Space"),
					new Beat(118970 + startTime, "Space"),
					new Beat(118970 + startTime, "J"),
					new Beat(119290 + startTime, "D"),
					new Beat(119290 + startTime, "K"),
					new Beat(119460 + startTime, "L"),
					new Beat(119460 + startTime, "S"),
					new Beat(119660 + startTime, "Space"),
					new Beat(119680 + startTime, "J"),
					new Beat(119840 + startTime, "F"),
					new Beat(119880 + startTime, "K"),
					new Beat(120040 + startTime, "J"),
					new Beat(120160 + startTime, "Space"),
					new Beat(120350 + startTime, "K"),
					new Beat(120360 + startTime, "D"),
					new Beat(120550 + startTime, "J"),
					new Beat(120570 + startTime, "F"),
					new Beat(120760 + startTime, "Space"),
					new Beat(120920 + startTime, "F"),
					new Beat(121040 + startTime, "L"),
					new Beat(121040 + startTime, "K"),
					new Beat(121130 + startTime, "Space"),
					new Beat(121480 + startTime, "J"),
					new Beat(121850 + startTime, "F"),
					new Beat(122200 + startTime, "Space"),
					new Beat(122380 + startTime, "D"),
					new Beat(122560 + startTime, "F"),
					new Beat(122740 + startTime, "Space"),
					new Beat(122940 + startTime, "J"),
					new Beat(123150 + startTime, "F"),
					new Beat(123360 + startTime, "Space"),
					new Beat(123360 + startTime, "F"),
					new Beat(123580 + startTime, "D"),
					new Beat(123720 + startTime, "K"),
					new Beat(123880 + startTime, "F"),
					new Beat(124050 + startTime, "J"),
					new Beat(124420 + startTime, "J"),
					new Beat(124420 + startTime, "Space"),
					new Beat(124790 + startTime, "K"),
					new Beat(124800 + startTime, "D"),
					new Beat(124800 + startTime, "Space"),
					new Beat(125150 + startTime, "F"),
					new Beat(125170 + startTime, "L"),
					new Beat(125360 + startTime, "K"),
					new Beat(125550 + startTime, "J"),
					new Beat(125710 + startTime, "Space"),
					new Beat(125880 + startTime, "F"),
					new Beat(126060 + startTime, "D"),
					new Beat(126220 + startTime, "F"),
					new Beat(126410 + startTime, "Space"),
					new Beat(126530 + startTime, "S"),
					new Beat(126680 + startTime, "D"),
					new Beat(126880 + startTime, "Space"),
					new Beat(127060 + startTime, "F"),
					new Beat(127280 + startTime, "Space"),
					new Beat(127480 + startTime, "J"),
					new Beat(127660 + startTime, "K"),
					new Beat(127830 + startTime, "K"),
					new Beat(128180 + startTime, "K"),
					new Beat(128580 + startTime, "K"),
					new Beat(128910 + startTime, "K"),
					new Beat(129090 + startTime, "L"),
					new Beat(129229 + startTime, "K"),
					new Beat(129620 + startTime, "K"),
					new Beat(129940 + startTime, "K"),
					new Beat(130289 + startTime, "K"),
					new Beat(130490 + startTime, "J"),
					new Beat(130699 + startTime, "K"),
					new Beat(131070 + startTime, "K"),
					new Beat(131389 + startTime, "K"),
					new Beat(131759 + startTime, "K"),
					new Beat(131960 + startTime, "J"),
					new Beat(132139 + startTime, "K"),
					new Beat(132500 + startTime, "K"),
					new Beat(132840 + startTime, "K"),
					new Beat(133180 + startTime, "K"),


			};
		}
		else if(titleName.equals("King") && difficulty.equals("Hard")) {
			int startTime = 4460 - Main.REACH_TIME * 1000;
			beats = new Beat[] {

			};
		}
		else if(titleName.equals("Ignite")&& difficulty.equals("Easy"))
		{
			int startTime = 4460 - Main.REACH_TIME * 1000;
			beats = new Beat[] {
					new Beat(startTime, "Space"),
					new Beat(startTime +   2, "D"),
					new Beat(startTime +   4, "D")
			};
		}
		else if(titleName.equals("Ignite")&& difficulty.equals("Hard"))
		{
			int startTime = 4460 - Main.REACH_TIME * 1000;
			beats = new Beat[] {
					new Beat(startTime, "Space"),
					new Beat(startTime +  2, "D"),
					new Beat(startTime +  2, "D")
			};
		}
		else if(titleName.equals("Toki toshite Violence")&& difficulty.equals("Easy"))
		{
			int startTime = 4460 - Main.REACH_TIME * 1000;
			beats = new Beat[] {
					new Beat(startTime, "Space"),
					new Beat(startTime +  2, "J"),
					new Beat(startTime +  4, "D")
		};
		}
		else if(titleName.equals("Jjin"))
		{
			int startTime = 4000 - Main.REACH_TIME * 3030;
			beats = new Beat[] {
					new Beat (startTime + 14710,  "F"),
					new Beat (startTime + 15610,  "F"),
					new Beat (startTime + 16360,  "J"),
					new Beat (startTime + 17140,  "J"),
					new Beat (startTime + 20980,  "F"),
					new Beat (startTime + 21800,  "J"),
					new Beat (startTime + 22600,  "F"),
					new Beat (startTime + 23390,  "J"),
					new Beat (startTime + 27240,  "D"),
					new Beat (startTime + 28020,  "D"),
					new Beat (startTime + 28840,  "K"),
					new Beat (startTime + 29590,  "K"),
					new Beat (startTime + 30380,  "D"),
					new Beat (startTime + 31140,  "D"),
					new Beat (startTime + 31930,  "K"),
					new Beat (startTime + 32710,  "K"),
					new Beat (startTime + 33490,  "F"),
					new Beat (startTime + 34270,  "J"),
					new Beat (startTime + 39700,  "D"),
					new Beat (startTime + 42760,  "K"),
					new Beat (startTime + 45890,  "F"),
					new Beat (startTime + 49040,  "J"),
					new Beat (startTime + 52180,  "D"),
					new Beat (startTime + 53700,  "K"),
					new Beat (startTime + 55280,  "F"),
					new Beat (startTime + 56900,  "J"),
					new Beat (startTime + 58430,  "D"),
					new Beat (startTime + 59950,  "K"),
					new Beat (startTime + 61870,  "F"),
					new Beat (startTime + 64660,  "J"),
					new Beat (startTime + 66190,  "J"),
					new Beat (startTime + 67780,  "F"),
					new Beat (startTime + 69300,  "F"),
					new Beat (startTime + 70880,  "K"),
					new Beat (startTime + 72400,  "K"),
					new Beat (startTime + 73970,  "D"),
					new Beat (startTime + 75520,  "D"),
					new Beat (startTime + 77090,  "J"),
					new Beat (startTime + 78620,  "F"),
					new Beat (startTime + 80160,  "K"),
					new Beat (startTime + 81750,  "D"),
					new Beat (startTime + 83340,  "J"),
					new Beat (startTime + 84880,  "F"),
					new Beat (startTime + 86460,  "K"),
					new Beat (startTime + 89540,  "D"),
					new Beat (startTime + 91110,  "D"),
					new Beat (startTime + 92660,  "K"),
					new Beat (startTime + 94240,  "K"),
					new Beat (startTime + 95780,  "F"),
					new Beat (startTime + 97320,  "J"),
			};
		}
		else if(titleName.equals("Candy")&& difficulty.equals("Easy"))
		{
			int startTime = 4000 - Main.REACH_TIME * 2990;
			beats =			new Beat[] {new Beat (startTime + 3600,  "F"),
					new Beat (startTime + 4580,  "J"),
					new Beat (startTime + 5590,  "J"),
					new Beat (startTime + 6550,  "F"),
					new Beat (startTime + 7560,  "K"),
					new Beat (startTime + 8560,  "D"),
					new Beat (startTime + 9080,  "Space"),
					new Beat (startTime + 11590,  "J"),
					new Beat (startTime + 12570,  "F"),
					new Beat (startTime + 13590,  "K"),
					new Beat (startTime + 14560,  "D"),
					new Beat (startTime + 15560,  "K"),
					new Beat (startTime + 16580,  "K"),
					new Beat (startTime + 17570,  "D"),
					new Beat (startTime + 18530,  "D"),
					new Beat (startTime + 19570,  "J"),
					new Beat (startTime + 20530,  "F"),
					new Beat (startTime + 21590,  "K"),
					new Beat (startTime + 22610,  "D"),
					new Beat (startTime + 23580,  "J"),
					new Beat (startTime + 24600,  "F"),
					new Beat (startTime + 25050,  "Space"),
					new Beat (startTime + 27070,  "Space"),
					new Beat (startTime + 28090,  "K"),
					new Beat (startTime + 29090,  "D"),
					new Beat (startTime + 30040,  "J"),
					new Beat (startTime + 31060,  "F"),
					new Beat (startTime + 32080,  "K"),
					new Beat (startTime + 33090,  "D"),
					new Beat (startTime + 34060,  "J"),
					new Beat (startTime + 35060,  "F"),
					new Beat (startTime + 36070,  "K"),
					new Beat (startTime + 37120,  "Space"),
					new Beat (startTime + 38070,  "K"),
					new Beat (startTime + 39090,  "D"),
					new Beat (startTime + 40090,  "J"),
					new Beat (startTime + 41070,  "F"),
					new Beat (startTime + 42050,  "K"),
					new Beat (startTime + 43020,  "D"),
					new Beat (startTime + 44080,  "J"),
					new Beat (startTime + 45060,  "F"),
					new Beat (startTime + 46070,  "K"),
					new Beat (startTime + 47030,  "D"),
					new Beat (startTime + 48090,  "J"),
					new Beat (startTime + 49070,  "F"),
					new Beat (startTime + 50090,  "Space"),
					new Beat (startTime + 51110,  "Space"),
					new Beat (startTime + 52060,  "K"),
					new Beat (startTime + 53070,  "D"),
					new Beat (startTime + 54050,  "J"),
					new Beat (startTime + 55060,  "F"),
					new Beat (startTime + 56080,  "K"),
					new Beat (startTime + 57070,  "D"),
					new Beat (startTime + 58090,  "J"),
					new Beat (startTime + 59110,  "L"),
					new Beat (startTime + 60020,  "S"),
					new Beat (startTime + 61080,  "K"),
					new Beat (startTime + 62080,  "D"),
					new Beat (startTime + 63100,  "L"),
					new Beat (startTime + 64050,  "S"),
					new Beat (startTime + 65090,  "L"),
					new Beat (startTime + 66090,  "S"),
					new Beat (startTime + 67050,  "Space"),
					new Beat (startTime + 68060,  "Space"),
					new Beat (startTime + 71110,  "K"),
					new Beat (startTime + 72060,  "D"),
					new Beat (startTime + 73100,  "J"),
					new Beat (startTime + 73640,  "F"),
					new Beat (startTime + 74130,  "Space"),
					new Beat (startTime + 75100,  "L"),
					new Beat (startTime + 75650,  "S"),
					new Beat (startTime + 76120,  "K"),
					new Beat (startTime + 76660,  "D"),
					new Beat (startTime + 77120,  "J"),
					new Beat (startTime + 77600,  "F"),
					new Beat (startTime + 78090,  "Space"),
					new Beat (startTime + 79100,  "L"),
					new Beat (startTime + 80090,  "S"),
					new Beat (startTime + 81080,  "Space"),
					new Beat (startTime + 81570,  "Space"),
					new Beat (startTime + 82070,  "Space"),
					new Beat (startTime + 82580,  "Space"),
					new Beat (startTime + 83080,  "K"),
					new Beat (startTime + 84120,  "D"),
					new Beat (startTime + 85080,  "J"),
					new Beat (startTime + 85630,  "F"),
					new Beat (startTime + 86120,  "Space"),
					new Beat (startTime + 87110,  "K"),
					new Beat (startTime + 87600,  "D"),
					new Beat (startTime + 88110,  "Space"),
					new Beat (startTime + 89090,  "L"),
					new Beat (startTime + 89590,  "S"),
					new Beat (startTime + 90070,  "Space"),
					new Beat (startTime + 91090,  "Space"),
					new Beat (startTime + 92070,  "Space"),
					new Beat (startTime + 93030,  "Space"),
					new Beat (startTime + 93040,  "D"),
					new Beat (startTime + 93040,  "K"),
					new Beat (startTime + 94070,  "J"),
					new Beat (startTime + 95060,  "F"),
					new Beat (startTime + 96090,  "K"),
					new Beat (startTime + 97060,  "D"),
					new Beat (startTime + 98080,  "L"),
					new Beat (startTime + 99090,  "S"),
					new Beat (startTime + 100050, "K"),
					new Beat (startTime + 101070, "D"),
					new Beat (startTime + 102090, "Space"),
					new Beat (startTime + 103100, "J"),
					new Beat (startTime + 104050, "F"),
					new Beat (startTime + 105070, "K"),
					new Beat (startTime + 106070, "D"),
					new Beat (startTime + 107070, "L"),
					new Beat (startTime + 107580, "K"),
					new Beat (startTime + 108100, "J"),
					new Beat (startTime + 108590, "Space"),
					new Beat (startTime + 109080, "F"),
					new Beat (startTime + 109600, "D"),
					new Beat (startTime + 110030, "S"),
					new Beat (startTime + 110590, "Space"),

			};
		}
		else if(titleName.equals("Candy")&& difficulty.equals("Hard"))
		{
			int startTime = 4000 - Main.REACH_TIME * 3030;
			beats =			new Beat[] {
					new Beat(startTime + 3590 ,"K"),
					new Beat(startTime + 4580 ,"D"),
					new Beat(startTime + 5570 ,"L"),
					new Beat(startTime + 6570 ,"S"),
					new Beat(startTime + 7560 ,"K"),
					new Beat(startTime + 8530 ,"D"),
					new Beat(startTime + 9120 ,"Space"),
					new Beat(startTime + 11040 ,"F"),
					new Beat(startTime + 11040 ,"J"),
					new Beat(startTime + 11600 ,"K"),
					new Beat(startTime + 12060 ,"D"),
					new Beat(startTime + 12620 ,"J"),
					new Beat(startTime + 13380 ,"Space"),	
					new Beat(startTime + 13610 ,"Space"),
					new Beat(startTime + 13850 ,"Space"),
					new Beat(startTime + 14090 ,"Space"),
					new Beat(startTime + 14330 ,"Space"),
					new Beat(startTime + 14570 ,"Space"),
					new Beat(startTime + 14810 ,"Space"),
					new Beat(startTime + 15590 ,"J"),
					new Beat(startTime + 16100 ,"F"),
					new Beat(startTime + 16630 ,"K"),
					new Beat(startTime + 17040 ,"D"),
					new Beat(startTime + 17340 ,"L"),
					new Beat(startTime + 17580 ,"S"),
					new Beat(startTime + 17850 ,"K"),
					new Beat(startTime + 18060 ,"D"),
					new Beat(startTime + 18320 ,"J"),
					new Beat(startTime + 18590 ,"F"),
					new Beat(startTime + 18860 ,"J"),
					new Beat(startTime + 19590 ,"Space"),
					new Beat(startTime + 20550 ,"K"),
					new Beat(startTime + 21580 ,"D"),
					new Beat(startTime + 22550 ,"L"),
					new Beat(startTime + 23560 ,"S"),
					new Beat(startTime + 24520 ,"D"),
					new Beat(startTime + 25080 ,"Space"),
					new Beat(startTime + 25360 ,"Space"),
					new Beat(startTime + 25620 ,"Space"),
					new Beat(startTime + 25870 ,"Space"),
					new Beat(startTime + 26120 ,"Space"),
					new Beat(startTime + 26360 ,"Space"),
					new Beat(startTime + 26620 ,"Space"),
					new Beat(startTime + 26880 ,"Space"),
					new Beat(startTime + 27150 ,"Space"),
					new Beat(startTime + 28100 ,"J"),
					new Beat(startTime + 29030 ,"D"),
					new Beat(startTime + 30030 ,"K"),
					new Beat(startTime + 31010 ,"L"),
					new Beat(startTime + 32020 ,"S"),
					new Beat(startTime + 33080 ,"J"),
					new Beat(startTime + 33590 ,"D"),
					new Beat(startTime + 34110 ,"J"),
					new Beat(startTime + 34580 ,"F"),
					new Beat(startTime + 35080 ,"K"),
					new Beat(startTime + 35600 ,"D"),
					new Beat(startTime + 36090 ,"L"),
					new Beat(startTime + 36590 ,"S"),
					new Beat(startTime + 37100 ,"K"),
					new Beat(startTime + 37600 ,"D"),
					new Beat(startTime + 38100 ,"J"),
					new Beat(startTime + 38600 ,"F"),
					new Beat(startTime + 39090 ,"Space"),
					new Beat(startTime + 40070 ,"Space"),
					new Beat(startTime + 41020 ,"S"),
					new Beat(startTime + 41050 ,"L"),
					new Beat(startTime + 41540 ,"D"),
					new Beat(startTime + 41550 ,"K"),
					new Beat(startTime + 42040 ,"F"),
					new Beat(startTime + 42050 ,"J"),
					new Beat(startTime + 42550 ,"Space"),
					new Beat(startTime + 43090 ,"K"),
					new Beat(startTime + 43570 ,"D"),
					new Beat(startTime + 44100 ,"J"),
					new Beat(startTime + 44590 ,"F"),
					new Beat(startTime + 45100 ,"L"),
					new Beat(startTime + 45580 ,"S"),
					new Beat(startTime + 46080 ,"K"),
					new Beat(startTime + 46550 ,"D"),
					new Beat(startTime + 47040 ,"S"),
					new Beat(startTime + 47580 ,"D"),
					new Beat(startTime + 48080 ,"F"),
					new Beat(startTime + 48600 ,"Space"),
					new Beat(startTime + 49140 ,"J"),
					new Beat(startTime + 49590 ,"K"),
					new Beat(startTime + 50080 ,"L"),
					new Beat(startTime + 51010 ,"S"),
					new Beat(startTime + 51510 ,"D"),
					new Beat(startTime + 52020 ,"F"),
					new Beat(startTime + 52560 ,"Space"),
					new Beat(startTime + 53050 ,"F"),
					new Beat(startTime + 53570 ,"D"),
					new Beat(startTime + 54030 ,"S"),
					new Beat(startTime + 55080 ,"L"),
					new Beat(startTime + 55560 ,"K"),
					new Beat(startTime + 56040 ,"J"),
					new Beat(startTime + 56580 ,"Space"),
					new Beat(startTime + 57090 ,"J"),
					new Beat(startTime + 57570 ,"K"),
					new Beat(startTime + 58020 ,"L"),
					new Beat(startTime + 59090 ,"L"),
					new Beat(startTime + 59550 ,"S"),
					new Beat(startTime + 60100 ,"K"),
					new Beat(startTime + 60580 ,"D"),
					new Beat(startTime + 61100 ,"J"),
					new Beat(startTime + 61570 ,"F"),
					new Beat(startTime + 62050 ,"Space"),
					new Beat(startTime + 62600 ,"Space"),
					new Beat(startTime + 63130 ,"J"),
					new Beat(startTime + 63600 ,"F"),
					new Beat(startTime + 64110 ,"K"),
					new Beat(startTime + 64590 ,"D"),
					new Beat(startTime + 65110 ,"L"),
					new Beat(startTime + 65530 ,"S"),
					new Beat(startTime + 66050 ,"K"),
					new Beat(startTime + 66550 ,"D"),
					new Beat(startTime + 67070 ,"Space"),
					new Beat(startTime + 68110 ,"Space"),
					new Beat(startTime + 69310 ,"L"),
					new Beat(startTime + 70080 ,"S"),
					new Beat(startTime + 71040 ,"K"),
					new Beat(startTime + 71530 ,"D"),
					new Beat(startTime + 72090 ,"J"),
					new Beat(startTime + 72540 ,"F"),
					new Beat(startTime + 73090 ,"Space"),
					new Beat(startTime + 73600 ,"Space"),
					new Beat(startTime + 74100 ,"Space"),
					new Beat(startTime + 75080 ,"J"),
					new Beat(startTime + 75620 ,"F"),
					new Beat(startTime + 76110 ,"K"),
					new Beat(startTime + 76560 ,"D"),
					new Beat(startTime + 77100 ,"L"),
					new Beat(startTime + 77610 ,"S"),
					new Beat(startTime + 78040 ,"S"),
					new Beat(startTime + 78050 ,"L"),
					new Beat(startTime + 79050 ,"D"),
					new Beat(startTime + 79050 ,"K"),
					new Beat(startTime + 80040 ,"F"),
					new Beat(startTime + 80040 ,"J"),
					new Beat(startTime + 81090 ,"L"),
					new Beat(startTime + 81090 ,"K"),
					new Beat(startTime + 81590 ,"J"),
					new Beat(startTime + 81590 ,"K"),
					new Beat(startTime + 82060 ,"Space"),
					new Beat(startTime + 82060 ,"J"),
					new Beat(startTime + 82570 ,"F"),
					new Beat(startTime + 82570 ,"Space"),
					new Beat(startTime + 83060 ,"F"),
					new Beat(startTime + 83060 ,"Space"),
					new Beat(startTime + 83560 ,"F"),
					new Beat(startTime + 83580 ,"D"),
					new Beat(startTime + 84070 ,"D"),
					new Beat(startTime + 84070 ,"S"),
					new Beat(startTime + 85090 ,"J"),
					new Beat(startTime + 85580 ,"F"),
					new Beat(startTime + 86100 ,"K"),
					new Beat(startTime + 86570 ,"D"),
					new Beat(startTime + 87100 ,"L"),
					new Beat(startTime + 87590 ,"S"),
					new Beat(startTime + 88120 ,"K"),
					new Beat(startTime + 88530 ,"D"),
					new Beat(startTime + 89030 ,"D"),
					new Beat(startTime + 89030 ,"Space"),
					new Beat(startTime + 89030 ,"K"),
					new Beat(startTime + 90030 ,"F"),
					new Beat(startTime + 90030 ,"Space"),
					new Beat(startTime + 90040 ,"J"),
					new Beat(startTime + 91120 ,"Space"),
					new Beat(startTime + 91360 ,"Space"),
					new Beat(startTime + 91620 ,"Space"),
					new Beat(startTime + 91850 ,"Space"),
					new Beat(startTime + 92090 ,"Space"),
					new Beat(startTime + 92350 ,"Space"),
					new Beat(startTime + 92590 ,"Space"),
					new Beat(startTime + 92850 ,"Space"),
					new Beat(startTime + 93100 ,"Space"),
					new Beat(startTime + 94050 ,"D"),
					new Beat(startTime + 94590 ,"K"),
					new Beat(startTime + 95070 ,"S"),
					new Beat(startTime + 95550 ,"L"),
					new Beat(startTime + 96040 ,"D"),
					new Beat(startTime + 96520 ,"K"),
					new Beat(startTime + 97020 ,"F"),
					new Beat(startTime + 97540 ,"J"),
					new Beat(startTime + 98050 ,"J"),
					new Beat(startTime + 98570 ,"J"),
					new Beat(startTime + 99120 ,"S"),
					new Beat(startTime + 99340 ,"L"),
					new Beat(startTime + 99620 ,"D"),
					new Beat(startTime + 99860 ,"K"),
					new Beat(startTime + 100090, "F"),
					new Beat(startTime + 100310, "J"),
					new Beat(startTime + 100590, "Space"),
					new Beat(startTime + 101060, "D"),
					new Beat(startTime + 101070, "K"),
					new Beat(startTime + 102050, "F"),
					new Beat(startTime + 102070, "J"),
					new Beat(startTime + 103050, "L"),
					new Beat(startTime + 104040, "S"),
					new Beat(startTime + 105050, "K"),
					new Beat(startTime + 106070, "D"),
					new Beat(startTime + 107110, "L"),
					new Beat(startTime + 107360, "S"),
					new Beat(startTime + 107640, "K"),
					new Beat(startTime + 107830, "D"),
					new Beat(startTime + 108080, "J"),
					new Beat(startTime + 108270, "F"),
					new Beat(startTime + 108570, "Space"),
					new Beat(startTime + 108790, "Space"),
					new Beat(startTime + 109040, "Space"),
					new Beat(startTime + 110550, "Space"),
					new Beat(startTime + 110550, "D"),
					new Beat(startTime + 110570, "K"),

			};
		}
		int i = 0;
		gameMusic.start();
		while (i < beats.length && !isInterrupted()) {
			boolean dropped = false;
			if (beats[i].getTime() <= gameMusic.getTime()) {
				Note note = new Note(beats[i].getNoteName());
				note.start();
				noteList.add(note);
				i++;
				dropped = true;
			}
			if(!dropped) {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		}
	}
	
	public void judge(String input) {
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if(input.equals(note.getNoteType())) {
				judgeEvent(note.judge());
				break;
			}
				
		}
	}
	
	public void judgeEvent(String judge) {
		if(!judge.equals("None")) {
			blueFlareImage = new ImageIcon(Main.class.getResource("../images/blueFlare.png")).getImage();			
		}
		if(judge.equals("Miss")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeMiss.png")).getImage();
			score -= 5;
            combo = 0;
		}
		else if(judge.equals("Late")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeLate.png")).getImage();
			score += 200;
			combo += 1;		}
		else if(judge.equals("Good")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeGood.png")).getImage();
			score += 400;
            combo += 1;
		}
		else if(judge.equals("Great")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeGreat.png")).getImage();
			score += 400;
			combo += 1;		}
		else if(judge.equals("Perfect")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgePerfect.png")).getImage();
			score += 700;
			combo += 1;		}
		else if(judge.equals("Early")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeEarly.png")).getImage();
			score += 200;
			combo += 1;
		}
	}
}

