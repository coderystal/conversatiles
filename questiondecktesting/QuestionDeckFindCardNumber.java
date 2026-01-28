package questiondecktesting;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class QuestionDeckFindCardNumber {
	
	public static void main(String[] args) throws InterruptedException {
		ConversatilesStepDefinitions conversatileSteps = new ConversatilesStepDefinitions();
		
		//store complete deck *********************************************************************************DECK COMPLETE
		conversatileSteps.clickNumCardsAndStoreDeck();
		
		//find card # *****************************************************************************************FIND CARD #
        conversatileSteps.testFind();

		conversatileSteps.close();
	}
}
