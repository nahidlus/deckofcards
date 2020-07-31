package tests;

import com.decks.constants.MethodType;
import com.decks.constants.Services;
import com.decks.response.GoResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.decks.requet.GoRequest;
import com.decks.types.ExpectedResponseTypes;

public class SampleTest {

    @Test
    public void generateANewPack(){
        System.out.println("*************Hello generateANewPack new test*************");
        GoRequest request = new GoRequest.Builder("/api/deck/new/", MethodType.GET)
                .build();
        GoResponse response = request.execute(Services.DeckOfCards, ExpectedResponseTypes.OK);
        System.out.print("Generate a new pack Response: ");
        System.out.println(response.jsonPath().getBoolean("success"));
        System.out.println("Asserting if we got a new pack");
        Assert.assertEquals(true,response.jsonPath().getBoolean("success")); //Asserting if we got a new pack
        System.out.println("Asserting 200 response code");
        Assert.assertEquals(200,response.getHttpStatusCode()); //Asserting 200 response code
    }

    @Test
    public void DrawACard(){
        System.out.println("*************Hello DrawACard new test*************");
        GoRequest request = new GoRequest.Builder("/api/deck/new/", MethodType.GET)
                .build();
        GoResponse response = request.execute(Services.DeckOfCards, ExpectedResponseTypes.OK);
        System.out.println(response.jsonPath().getBoolean("success"));
        String deckId = response.jsonPath().getString("deck_id");
        request = new GoRequest.Builder("/api/deck/"+deckId+"/draw", MethodType.GET)
                .build();
        System.out.print("Generate a new Card Response : ");
        response = request.execute(Services.DeckOfCards, ExpectedResponseTypes.OK);
        System.out.println("Asserting 200 response code");
        Assert.assertEquals(200,response.getHttpStatusCode()); //Asserting 200 response code
        Assert.assertEquals(51,response.jsonPath().getInt("remaining")); //Asserting remaining cards from deck
        System.out.println("Asserting remaining cards from deck should be 51");
        System.out.println(response.jsonPath().getString("cards"));

    }

    @Test
    public void AddTwoJokersToDeckAndValidateDeckSizeShouldBe54(){
        System.out.println("*************Hello AddTwoJokersToDeckAndValidateDeckSizeShouldBe54 new test*************");
        GoRequest request = new GoRequest.Builder("/api/deck/new", MethodType.GET).queryParam("jokers_enabled","true")
                .build();
        GoResponse response = request.execute(Services.DeckOfCards, ExpectedResponseTypes.OK);
        System.out.println(response.jsonPath().getBoolean("success"));
        System.out.println("Asserting 200 response code");
        Assert.assertEquals(200,response.getHttpStatusCode()); //Asserting 200 response code
        System.out.println("Asserting remaining cards from new deck should be 54");
        Assert.assertEquals(54,response.jsonPath().getInt("remaining")); //Asserting remaining cards from deck

    }
}
