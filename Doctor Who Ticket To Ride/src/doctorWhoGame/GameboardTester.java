package doctorWhoGame;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageIO.class)
public class GameboardTester {
	
	@Test
	public void TestGameboardExists(){
		assertNotNull(new Gameboard());
	}
	
	@Test
	public void TestGameboardHasHandAreaImageFile() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Gameboard gameScreen = new Gameboard();
		Field privateHandAreaImageFile = Gameboard.class.
	            getDeclaredField("handAreaFile");
		
		privateHandAreaImageFile.setAccessible(true);
		
		File handAreaFile = (File) privateHandAreaImageFile.get(gameScreen);
		assertNotNull(handAreaFile);

	}
	
	@Test
	public void TestGameboardHasHandAreaImage() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		Gameboard gameScreen = new Gameboard();
		Field privateHandAreaImage = Gameboard.class.
	            getDeclaredField("handAreaImage");
		
		privateHandAreaImage.setAccessible(true);
		
		BufferedImage handAreaImage = (BufferedImage) privateHandAreaImage.get(gameScreen);
		assertNotNull(handAreaImage);
	}
	
	
	
	

}
