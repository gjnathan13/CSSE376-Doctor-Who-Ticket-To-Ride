package doctorWhoGame;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class HandTesting {
	private ArrayList<ArrayList<String>> trainCardList;
	private Hand newHand;
	
	@Before
	public void testSetup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
	this.newHand=new Hand();
	Field trainCardField=Hand.class.getDeclaredField("trainCards");
	trainCardField.setAccessible(true);
	this.trainCardList= (ArrayList<ArrayList<String>>) trainCardField.get(newHand);
	
	}
	
	@Test
	public void testThatHandExists(){
		Hand trainCardHand=new Hand();
		
	}
	
	
	@Test
	public void testAddRedCard(){
		String firstTrainCard="Red";
		newHand.addTrainCard(firstTrainCard);
		assertEquals(1, trainCardList.get(0).size());
		assertEquals(firstTrainCard,trainCardList.get(0).get(0));
	}
	
	

}
