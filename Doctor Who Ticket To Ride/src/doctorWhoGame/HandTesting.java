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
	
	//Sets up local variables
	@Before
	public void testSetup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
	this.newHand=new Hand();
	Field trainCardField=Hand.class.getDeclaredField("trainCards");
	trainCardField.setAccessible(true);
	this.trainCardList= (ArrayList<ArrayList<String>>) trainCardField.get(newHand);
	
	}
	
	//Tests the constructor
	@Test
	public void testThatHandExists(){
		Hand trainCardHand=new Hand();
		
	}
	
	//Tests adding a red card to the hand
	@Test
	public void testAddRedTrainCard(){
		String firstTrainCard="Red";
		newHand.addTrainCard(firstTrainCard);
		assertEquals(1, trainCardList.get(0).size());
		assertEquals(firstTrainCard,trainCardList.get(0).get(0));
		for(int i=1;i<9;i++){
			assertEquals(0, trainCardList.get(i).size());
		}
	}
	
	//Tests adding a pink card to the hand
	@Test
	public void testAddPinkTrainCard(){
		String firstTrainCard="Pink";
		newHand.addTrainCard(firstTrainCard);
		assertEquals(1, trainCardList.get(1).size());
		assertEquals(firstTrainCard,trainCardList.get(1).get(0));
		assertEquals(0, trainCardList.get(0).size());
		for(int i=2;i<9;i++){
			assertEquals(0, trainCardList.get(i).size());
		}
	}
	
	
	

}
