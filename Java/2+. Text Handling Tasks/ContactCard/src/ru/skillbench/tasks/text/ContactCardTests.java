package ru.skillbench.tasks.text;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

/**
 * Open test class for {@link ContactCard}.
 * @author Alexey Evdokimov
 */
public class ContactCardTests {
	private static final String GOOD[] = {
			"FN:Forrest Gump\r\nORG:Bubba Gump Shrimp Co.\r\nGENDER:M\r\nTEL;TYPE=HOME:4951234567", 
			"FN:Chuck Norris\r\nORG:Hollywood\r\nBDAY:10-04-1940\r\nTEL;TYPE=WORK:1234567890", 
	};
	private static final String BAD[] = {
			"FN:Forrest Gump\r\nORG:Bubba Gump Shrimp Co.\r\nGENDER:M\r\nBDAY:06-06-1944\r\nTEL;TYPE=HOME:+1 234-567", //wrong phone format
			"FN:Chuck Norris\r\nORG:Hollywood\r\nBDAY:10-04-1940\r\nTEL;TYPE=WORK:12345678901", //digits > 10
	};
	
	private ContactCard impl = new ContactCardImpl();
	
	@Rule
	public TestRule globalTimeout = new DisableOnDebug(Timeout.seconds(1));	
	
	private ContactCard getCard(String text){
		return impl.getInstance("BEGIN:VCARD\r\n"+text+"\r\nEND:VCARD");
	}
	
	@Test
	public void getFullName1(){
		String text = GOOD[1];
		ContactCard card = getCard(text);
		assertEquals("getFullName() failed with this text:\n"+text+"\n", 
				"Chuck Norris", card.getFullName());
	}
	
	@Test
	public void getOrganization0(){
		String text = GOOD[0];
		ContactCard card = getCard(text);
		assertEquals("getOrganization() failed with this text:\n"+text+"\n", 
				"Bubba Gump Shrimp Co.", card.getOrganization());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getBirthdayException(){
		getCard(GOOD[0]).getBirthday();
	}

	@Test
	public void getAgeYears(){
		//This solution is not recommended for ContactCardImpl (it does not use modern API)
		Calendar today = Calendar.getInstance(); 
		Calendar bday = Calendar.getInstance();
		bday.set(1940, 4-1, 10);
		int age = today.get(Calendar.YEAR) - bday.get(Calendar.YEAR); 
		bday.add(Calendar.YEAR, age); 
		if (today.before(bday)) 
			age--;
		
		String text = GOOD[1];
		ContactCard card = getCard(text);
		assertEquals("getAgeYears() failed with this text:\n"+text+"\n", age, card.getAgeYears());
	}
	
	@Test
	public void getPhone0(){
		String text = GOOD[0];
		ContactCard card = getCard(text);
		assertEquals("getPhone(\"HOME\") failed with this text:\n"+text+"\n",
				"(495) 123-4567", card.getPhone("HOME"));
	}
	
	@Test
	public void getPhone1(){
		String text = GOOD[1];
		ContactCard card = getCard(text);
		assertEquals("getPhone(\"WORK\") failed with this text:\n"+text+"\n",
				"(123) 456-7890", card.getPhone("WORK"));
	}
	
	@Test(expected = InputMismatchException.class)
	public void phoneExceptionNotNumber(){
		getCard(BAD[0]);
	}
	
	@Test(expected = InputMismatchException.class)
	public void phoneExceptionNot10Digits(){
		getCard(BAD[1]);
	}
	
}