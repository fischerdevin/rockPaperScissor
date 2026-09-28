public class Testing {
    private final GameLogic testGL = new GameLogic();


    public boolean testLogic(char player, char computer, GameLogic.Result expected) {
        GameLogic.Result actual = testGL.checkGame(player, computer);

        return actual == expected;
    }

    public void testEveryPossibleValid(){
        char[] validSelection = {'R','S','P'};

       for(char player : validSelection){
           for(char computer : validSelection){
               GameLogic.Result actual = testGL.checkGame(player, computer);
               for(GameLogic.Result expected : GameLogic.Result.values()){
                   boolean passed = testLogic(player, computer, expected);

                   System.out.println(player + " : " + computer + " | Expected: " + expected + " | Actual: " + actual + " | Match: " + passed);
               }
           }
       }
    }




}