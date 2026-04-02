package tower;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TowerC4Test {
    private Tower tower;

    @Before
    public void setUp() {
        tower = new Tower(200, 120);
    }

    @Test
    public void shouldAddOpenerCup() {
        tower.pushLid(1);
        tower.pushCup("opener", 3);
        assertTrue(tower.ok());
        assertEquals("cup:opener:3", tower.stackingItems()[tower.stackingItems().length - 1]);
    }

    @Test
    public void fearfulLidShouldNotEnterWithoutCup() {
        tower.pushLid("fearful", 5);
        assertFalse(tower.ok());
    }

    @Test
    public void crazyLidShouldGoToBottom() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushLid("crazy", 2);
        assertEquals("lid:crazy:2", tower.stackingItems()[0]);
    }

    @Test
    public void hierarchicalCupAtBottomCannotBeRemoved() {
        tower.pushCup("hierarchical", 7);
        tower.removeCup(7);
        assertFalse(tower.ok());
    }

    @Test
    public void heavyLidShouldBeCreated() {
        tower.pushCup(4);
        tower.pushLid("heavy", 4);
        assertTrue(tower.ok());
        assertEquals("lid:heavy:4", tower.stackingItems()[1]);
    }
}
