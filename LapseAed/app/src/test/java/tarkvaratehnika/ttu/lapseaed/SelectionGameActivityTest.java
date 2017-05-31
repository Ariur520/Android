package tarkvaratehnika.ttu.lapseaed;


import org.junit.Assert;
import org.junit.Test;

import tarkvaratehnika.ttu.lapseaed.selectionGame.SelectionGameActivity;

public class SelectionGameActivityTest {

    @Test
    public void testIfAsnswerIsCorrect() {
        int score = SelectionGameActivity.scoreTotal;
        if(SelectionGameActivity.isRight){
            Assert.assertTrue(score > 0);
        } else {
            Assert.assertTrue(score == 0);
        }
    }
}
