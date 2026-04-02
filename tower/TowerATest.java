package tower;

import javax.swing.JOptionPane;
import org.junit.Test;

public class TowerATest {
    @Test
    public void acceptanceScenarioOne() {
        Tower tower = new Tower(200, 120);
        tower.makeVisible();
        tower.pushCup(1);
        tower.pushCup("opener", 2);
        tower.pushLid("crazy", 1);
        JOptionPane.showConfirmDialog(null, "¿Acepta el escenario 1 del ciclo 4?");
    }

    @Test
    public void acceptanceScenarioTwo() {
        Tower tower = new Tower(200, 120);
        tower.makeVisible();
        tower.pushCup(3);
        tower.pushLid("fearful", 3);
        tower.pushCup("hierarchical", 8);
        JOptionPane.showConfirmDialog(null, "¿Acepta el escenario 2 del ciclo 4?");
    }
}
