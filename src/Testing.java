public class Testing {
    private final GameLogic testGL = new GameLogic();


    public boolean testLogic(GameLogic.Selection player, GameLogic.Selection computer, GameLogic.Result expected) {
        GameLogic.Result actual = testGL.determineOutcome(player, computer);

        return actual == expected;
    }

    public void testEveryPossibleValid(){
        GameLogic.Selection[] possibleSelections = GameLogic.Selection.values();

       for(GameLogic.Selection player : possibleSelections){
           for(GameLogic.Selection computer : possibleSelections){

               GameLogic.Result actual = testGL.determineOutcome(player, computer);

               for(GameLogic.Result expected : GameLogic.Result.values()){

                   boolean passed = testLogic(player, computer, expected);
                   System.out.println(player + " : " + computer + " | Expected: " + expected + " | Actual: " + actual + " | Match: " + passed);

               }

           }
       }
    }




}