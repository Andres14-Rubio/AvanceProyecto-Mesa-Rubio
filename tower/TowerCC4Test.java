package tower;

import static org.junit.Assert.*;
import org.junit.Test;

public class TowerCC4Test {
    @Test
    public void shouldKeepContestUsingNormalItems() {
        TowerContest contest = new TowerContest();
        String answer = contest.solve(3, 9);
        assertNotNull(answer);
    }
}
