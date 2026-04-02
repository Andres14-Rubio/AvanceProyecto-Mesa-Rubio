package tower;

import java.util.ArrayList;

/**
 * Contest usa solo elementos normales, tal como pide el enunciado.
 */
public class TowerContest {
    public String solve(int n, long h) {
        int[] order = solveOrder(n, h);
        if (order == null) return "impossible";
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < order.length; i++) {
            if (i > 0) answer.append(" ");
            answer.append(2 * order[i] - 1);
        }
        return answer.toString();
    }

    public void simulate(int n, long h) {
        int[] order = solveOrder(n, h);
        if (order == null) {
            System.out.println("impossible");
            return;
        }
        Tower tower = new Tower(120, n * 10 + 20);
        tower.makeVisible();
        for (int i = 0; i < order.length; i++) tower.pushCup(order[i]);
    }

    private int[] solveOrder(int n, long h) {
        if (n <= 0) return null;
        long minHeight = 2L * n - 1;
        long maxHeight = 1L * n * n;
        if (h < minHeight || h > maxHeight || h == maxHeight - 2) return null;
        if (n <= 5) return bruteSolve(n, h);
        if (h >= 4L * n - 4) {
            int[] sub = solveOrder(n - 1, h - (2L * n - 1));
            if (sub == null) return null;
            return append(sub, n);
        }
        long target = (h == 2L * n - 1) ? 2L * (n - 1) - 1 : h - 1;
        int[] sub = solveOrder(n - 1, target);
        if (sub == null) return null;
        return prepend(n, sub);
    }

    private int[] bruteSolve(int n, long h) {
        ArrayList<int[]> all = new ArrayList<int[]>();
        generatePermutations(n, 0, new boolean[n + 1], new int[n], all);
        for (int[] perm : all) if (towerHeight(perm) == h) return perm;
        return null;
    }

    private void generatePermutations(int n, int pos, boolean[] used, int[] current, ArrayList<int[]> all) {
        if (pos == n) {
            int[] copy = new int[n];
            for (int i = 0; i < n; i++) copy[i] = current[i];
            all.add(copy);
            return;
        }
        for (int x = 1; x <= n; x++) {
            if (!used[x]) {
                used[x] = true;
                current[pos] = x;
                generatePermutations(n, pos + 1, used, current, all);
                used[x] = false;
            }
        }
    }

    private long towerHeight(int[] perm) {
        if (perm.length == 0) return 0;
        long baseY = 0;
        long best = cupHeight(perm[0]);
        for (int i = 1; i < perm.length; i++) {
            int prev = perm[i - 1];
            int curr = perm[i];
            baseY += (curr < prev) ? 1 : cupHeight(prev);
            best = Math.max(best, baseY + cupHeight(curr));
        }
        return best;
    }

    private int cupHeight(int cupNumber) { return 2 * cupNumber - 1; }
    private int[] append(int[] arr, int value) {
        int[] out = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) out[i] = arr[i];
        out[arr.length] = value;
        return out;
    }
    private int[] prepend(int value, int[] arr) {
        int[] out = new int[arr.length + 1];
        out[0] = value;
        for (int i = 0; i < arr.length; i++) out[i + 1] = arr[i];
        return out;
    }
}
