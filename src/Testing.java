public class Testing {
    private final GameLogic testGL = new GameLogic();


    public boolean testLogic(GameLogic.Selection player, GameLogic.Selection computer, GameLogic.Result possibleOutcomes) {
        GameLogic.Result actual = testGL.determineOutcome(player, computer);

        return actual == possibleOutcomes;
    }

    public void testEveryPossibleValid(){
        GameLogic.Selection[] possibleSelections = GameLogic.Selection.values();

       for(GameLogic.Selection player : possibleSelections){
           for(GameLogic.Selection computer : possibleSelections){

               GameLogic.Result actual = testGL.determineOutcome(player, computer);

               for(GameLogic.Result possibleOutcomes : GameLogic.Result.values()){

                   boolean passed = testLogic(player, computer, possibleOutcomes);
                   System.out.println(player + " : " + computer + " | Possible Outcome: " + possibleOutcomes + " | Actual Outcome: " + actual + " | Match: " + passed);

               }

           }
       }
    }




}