package doctorWhoGame;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

public class HandTesting {
	
	@Test
	public void testThatHandExists(){
		Hand trainCardHand=new Hand();
	}
	
	@Test
	public void testAddRedCard() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Hand newHand=new Hand();
		String firstTrainCard="Red";
		newHand.addTrainCard(firstTrainCard);
		Field trainCardField=Hand.class.getDeclaredField("trainCards");
		trainCardField.setAccessible(true);
		ArrayList<ArrayList<String>> trainCardList= (ArrayList<ArrayList<String>>) trainCardField.get(newHand);
		assertEquals(1, trainCardList.get(0).size());
		assertEquals(firstTrainCard,trainCardList.get(0).get(0));
	}

}
