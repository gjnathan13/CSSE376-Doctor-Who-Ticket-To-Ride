package doctorWhoGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;

public class GameStarterTester {
	
	private GameStarter testStarter;
	
	@Before
	public void setup(){
		testStarter = new GameStarter();
	}
	
	@Test
	public void testStartButtonCreation() throws Exception{
		Method startButtonMaker = testStarter.getClass().getDeclaredMethod("createStartButton", BufferedImage.class, Image.class);
		startButtonMaker.setAccessible(true);

		BufferedImage startButtonImage = ImageIO.read(new File("GameImages\\StartButtonImage.png"));
		Image startButtonScaled = startButtonImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		
		JButton startButton = (JButton) startButtonMaker.invoke(testStarter, startButtonImage,startButtonScaled);
		
		assertEquals(startButton.getBorder(),BorderFactory.createEmptyBorder());
		assertEquals(startButton.getX(), (int)(125*GameStarter.getWidthModifier()));
		assertEquals(startButton.getY(), (int)(250*GameStarter.getHeightModifier()));
		assertEquals(startButton.getWidth(), (int) (startButtonImage.getWidth() * GameStarter.getWidthModifier()));
		assertEquals(startButton.getHeight(), (int) (startButtonImage.getHeight() * GameStarter.getHeightModifier()));
	}
	
	@Test
	public void testQuestionButtonCreation() throws Exception{
		Method questionButtonMaker = testStarter.getClass().getDeclaredMethod("createQuestionButton");
		questionButtonMaker.setAccessible(true);
		
		JButton questionButton = (JButton) questionButtonMaker.invoke(testStarter);
		
		assertEquals(questionButton.getText(), "?");
		assertTrue(questionButton.getActionListeners().length > 0);
		assertEquals(questionButton.getForeground(), Color.CYAN);
		assertEquals(questionButton.getBackground(), Color.BLACK);
	}
	
	@Test
	public void testTitleLabelCreation() throws Exception{
		Method labelMaker = testStarter.getClass().getDeclaredMethod("createStartLabel",int.class, int.class, Image.class);
		labelMaker.setAccessible(true);
		

		BufferedImage startBack = ImageIO.read(new File("GameImages\\TitleImage.png"));
		Image startButtonScaled = startBack.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		
		JLabel titleLabel = (JLabel) labelMaker.invoke(testStarter, 20, 20, startButtonScaled);
		
		assertEquals(titleLabel.getX(), 0);
		assertEquals(titleLabel.getY(), 0);
	}
}
