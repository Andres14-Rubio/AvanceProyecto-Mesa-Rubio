package tower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shapes.Rectangle;

public class Tower {
    private final int width;
    private final int maxHeight;
    private boolean visible;
    private boolean lastOk;

    private final ArrayList<StackingItem> stack;
    private final Set<Integer> cupNums;
    private final Set<Integer> lidNums;
    private final HashMap<Integer, String> cupColor;
    private final ArrayList<Rectangle> cmMarks;

    private final int xBase = 120;
    private final int yFloor = 350;

    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.visible = false;
        this.lastOk = true;
        this.stack = new ArrayList<StackingItem>();
        this.cupNums = new HashSet<Integer>();
        this.lidNums = new HashSet<Integer>();
        this.cupColor = new HashMap<Integer, String>();
        this.cmMarks = new ArrayList<Rectangle>();
    }

    public Tower(int cups) {
        this(120, Math.max(20, cups * 10 + 20));
        create(cups);
    }

    public boolean ok() { return lastOk; }
    public int getWidth() { return width; }
    public int getMaxHeight() { return maxHeight; }

    public void makeVisible() { visible = true; redraw(); }

    public void makeInvisible() {
        visible = false;
        for (StackingItem it : stack) it.makeInvisible();
        for (Rectangle r : cmMarks) r.makeInvisible();
        cmMarks.clear();
    }

    public int height() {
        int h = 0;
        for (StackingItem it : stack) h += it.getHeightCm();
        return h;
    }

    public void create(int n) {
        clear();
        lastOk = false;
        for (int i = 1; i <= n; i++) {
            if (!pushCupInternal("normal", i, false)) {
                clear();
                return;
            }
        }
        lastOk = true;
        redraw();
    }

    public void pushCup(int i) { pushCup("normal", i); }

    public void pushCup(String type, int i) {
        lastOk = pushCupInternal(type, i, true);
        if (lastOk) redraw();
    }

    private boolean pushCupInternal(String type, int i, boolean redrawAtEnd) {
        if (cupNums.contains(i)) return false;
        Cup cup = createCup(type, i);
        if (height() + cup.getHeightCm() > maxHeight) return false;
        cup.onEnter(this);
        cupNums.add(i);
        cupColor.put(i, pickColor(i));
        if (redrawAtEnd) redraw();
        return true;
    }

    public void pushLid(int i) { pushLid("normal", i); }

    public void pushLid(String type, int i) {
        lastOk = false;
        if (lidNums.contains(i)) return;
        Lid lid = createLid(type, i);
        if (!lid.canEnter(this)) return;
        if (height() + lid.getHeightCm() > maxHeight) return;
        lid.onEnter(this);
        lidNums.add(i);
        lastOk = true;
        redraw();
    }

    public void popCup() {
        lastOk = false;
        for (int i = stack.size() - 1; i >= 0; i--) {
            StackingItem item = stack.get(i);
            if (item.getType().equals("cup") && item.canBeRemoved(this)) {
                removeCup(item.getNumber());
                return;
            }
        }
    }

    public void popLid() {
        lastOk = false;
        for (int i = stack.size() - 1; i >= 0; i--) {
            StackingItem item = stack.get(i);
            if (item.getType().equals("lid") && item.canBeRemoved(this)) {
                removeLid(item.getNumber());
                return;
            }
        }
    }

    public void removeCup(int i) {
        lastOk = false;
        int idx = indexOfCup(i);
        if (idx == -1) return;
        StackingItem cup = stack.get(idx);
        if (!cup.canBeRemoved(this)) return;
        int coverIdx = indexOfCoveringLid(i);
        if (coverIdx != -1 && stack.get(coverIdx) instanceof FearfulLid) return;
        if (coverIdx != -1) removeAt(coverIdx);
        removeAt(idx);
        cupNums.remove(i);
        cupColor.remove(i);
        lastOk = true;
        redraw();
    }

    public void removeLid(int i) {
        lastOk = false;
        int idx = indexOfLid(i);
        if (idx == -1) return;
        StackingItem lid = stack.get(idx);
        if (!lid.canBeRemoved(this)) return;
        removeAt(idx);
        lidNums.remove(i);
        lastOk = true;
        redraw();
    }

    public void orderTower() {
        Collections.sort(stack, new Comparator<StackingItem>() {
            public int compare(StackingItem a, StackingItem b) {
                if (a.getNumber() != b.getNumber()) return Integer.compare(a.getNumber(), b.getNumber());
                if (a.getType().equals(b.getType())) return 0;
                return a.getType().equals("cup") ? -1 : 1;
            }
        });
        lastOk = true;
        redraw();
    }

    public void reverseTower() {
        Collections.reverse(stack);
        lastOk = true;
        redraw();
    }

    public void swap(String first, String second) {
        lastOk = false;
        int i = indexOfDescriptor(first);
        int j = indexOfDescriptor(second);
        if (i == -1 || j == -1) return;
        Collections.swap(stack, i, j);
        lastOk = true;
        redraw();
    }

    public void cover() {
        lastOk = false;
        ArrayList<StackingItem> lids = new ArrayList<StackingItem>();
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).getType().equals("lid")) lids.add(stack.remove(i));
        }
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getType().equals("cup")) {
                int cupNumber = stack.get(i).getNumber();
                for (int j = 0; j < lids.size(); j++) {
                    if (lids.get(j).getNumber() == cupNumber) {
                        stack.add(i + 1, lids.remove(j));
                        i++;
                        break;
                    }
                }
            }
        }
        stack.addAll(lids);
        lastOk = true;
        redraw();
    }

    public int[] swapToReduce() {
        int current = height();
        for (int i = 0; i < stack.size(); i++) {
            for (int j = i + 1; j < stack.size(); j++) {
                Collections.swap(stack, i, j);
                int newer = height();
                Collections.swap(stack, i, j);
                if (newer < current) return new int[] { i, j };
            }
        }
        return new int[] { -1, -1 };
    }

    public String[] lidedCups() {
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < stack.size() - 1; i++) {
            if (stack.get(i).getType().equals("cup") && stack.get(i + 1).getType().equals("lid")
                    && stack.get(i).getNumber() == stack.get(i + 1).getNumber()) {
                out.add(String.valueOf(stack.get(i).getNumber()));
            }
        }
        return out.toArray(new String[0]);
    }

    public String[] stackingItems() {
        String[] out = new String[stack.size()];
        for (int i = 0; i < stack.size(); i++) {
            StackingItem it = stack.get(i);
            out[i] = descriptorOf(it);
        }
        return out;
    }

    public boolean containsCup(int number) { return indexOfCup(number) != -1; }

    boolean isCoveringOwnCup(FearfulLid lid) {
        int idx = indexOfLid(lid.getNumber());
        return idx > 0 && stack.get(idx - 1).getType().equals("cup") && stack.get(idx - 1).getNumber() == lid.getNumber();
    }

    boolean isAtBottom(Cup cup) { return !stack.isEmpty() && stack.get(0) == cup; }

    void removeBlockingLids() {
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).getType().equals("lid")) {
                lidNums.remove(stack.get(i).getNumber());
                removeAt(i);
            }
        }
    }

    void insertHierarchicalCup(HierarchicalCup cup) {
        int pos = 0;
        while (pos < stack.size() && stack.get(pos).getNumber() < cup.getNumber()) pos++;
        stack.add(pos, cup);
    }

    void addItem(StackingItem item) { stack.add(item); }
    void addItemAtBottom(StackingItem item) { stack.add(0, item); }

    void addLidDefault(Lid lid) {
        int cupIndex = indexOfCup(lid.getNumber());
        if (cupIndex != -1) stack.add(cupIndex + 1, lid);
        else stack.add(lid);
    }

    public void exit() { System.exit(0); }

    private Cup createCup(String type, int number) {
        String color = pickColor(number);
        int cupHeight = 8;
        if ("opener".equalsIgnoreCase(type)) return new OpenerCup(number, cupHeight, color);
        if ("hierarchical".equalsIgnoreCase(type)) return new HierarchicalCup(number, cupHeight, color);
        return new NormalCup(number, cupHeight, color);
    }

    private Lid createLid(String type, int number) {
        String color = cupColor.containsKey(number) ? cupColor.get(number) : "black";
        if ("fearful".equalsIgnoreCase(type)) return new FearfulLid(number, color);
        if ("crazy".equalsIgnoreCase(type)) return new CrazyLid(number, color);
        if ("heavy".equalsIgnoreCase(type)) return new HeavyLid(number, color);
        return new NormalLid(number, color);
    }

    private void clear() {
        makeInvisible();
        stack.clear();
        cupNums.clear();
        lidNums.clear();
        cupColor.clear();
    }

    private void removeAt(int idx) {
        stack.get(idx).makeInvisible();
        stack.remove(idx);
    }

    private int indexOfCup(int number) {
        for (int i = 0; i < stack.size(); i++) if (stack.get(i).getType().equals("cup") && stack.get(i).getNumber() == number) return i;
        return -1;
    }

    private int indexOfCoveringLid(int number) {
        int idx = indexOfCup(number);
        if (idx != -1 && idx + 1 < stack.size()) {
            StackingItem next = stack.get(idx + 1);
            if (next.getType().equals("lid") && next.getNumber() == number) return idx + 1;
        }
        return -1;
    }

    private int indexOfLid(int number) {
        for (int i = 0; i < stack.size(); i++) if (stack.get(i).getType().equals("lid") && stack.get(i).getNumber() == number) return i;
        return -1;
    }

    private int indexOfDescriptor(String descriptor) {
        for (int i = 0; i < stack.size(); i++) {
            if (descriptorOf(stack.get(i)).equals(descriptor)) return i;
        }
        return -1;
    }

    private String descriptorOf(StackingItem item) {
        return item.getType() + ":" + item.getSubtype() + ":" + item.getNumber();
    }

    private void redraw() {
        if (visible) for (StackingItem it : stack) it.makeInvisible();
        int baseY = yFloor;
        for (StackingItem it : stack) {
            it.moveTo(xBase, baseY);
            if (visible) it.makeVisible();
            baseY -= it.getHeightCm() * Cup.PX_PER_CM;
        }
        drawCmMarks();
    }

    private void drawCmMarks() {
        for (Rectangle r : cmMarks) r.makeInvisible();
        cmMarks.clear();
        if (!visible) return;
        int markX = xBase + 60;
        int baseY = yFloor;
        for (int cm = 0; cm <= maxHeight; cm++) {
            Rectangle mark = new Rectangle();
            mark.changeColor("black");
            int length = (cm % 5 == 0) ? 25 : 15;
            mark.changeSize(2, length);
            mark.moveHorizontal(-70);
            mark.moveVertical(-15);
            int y = baseY - (cm * Cup.PX_PER_CM);
            mark.moveHorizontal(markX);
            mark.moveVertical(y);
            mark.makeVisible();
            cmMarks.add(mark);
        }
    }

    private String pickColor(int i) {
        String[] colors = { "red", "blue", "green", "yellow", "magenta", "black" };
        return colors[Math.abs(i) % colors.length];
    }
}
