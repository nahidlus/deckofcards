### deckofcards

This is a backend automation framework setup for a set of deckofcards apis.

Setup:

```

git clone https://github.com/nahidlus/deckofcards.git

cd deckofcards

mvn test

```


As of now we have four below tests:
1. Generating a new Deck api should return a deck id with api response code as 200
2. Add two jockers to deck and validate deck size should be 54 
3. Using a deck id, user is able to call draw cards api.
4. After successful drawing 1 of the cards from a 52 card deck, remaining cards in response should be 51
