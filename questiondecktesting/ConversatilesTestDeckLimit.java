package questiondecktesting;

public class ConversatilesTestDeckLimit {
	public static void main(String[] args) throws InterruptedException {
		ConversatilesStepDefinitions conversatileSteps = new ConversatilesStepDefinitions();
		conversatileSteps.launchConversatiles(conversatileSteps.getLocalSiteUrl());
		
		for (int i = 0; i < 20; i++) {
			System.out.println("random deck " + (i+1) + " - trying different sizes -----------------------------");
			conversatileSteps.compareDeckLimit();
		}

		conversatileSteps.close();
	}
}
