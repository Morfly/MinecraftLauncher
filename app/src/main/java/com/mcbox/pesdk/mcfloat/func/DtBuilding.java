package com.mcbox.pesdk.mcfloat.func;

import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DtBuilding {
    public static final int BUILD_TYPE_BALL = 3;
    public static final int BUILD_TYPE_CUBOID = 2;
    public static final int BUILD_TYPE_LINE = 1;
    public static final int BUILD_TYPE_NONE = 0;
    public static final int HISTORY_SIZE = 5;
    public static final int ITEM_ID_MUSIC_BOX = 25;
    public static final int LINE_VERTICAL_DOWN = 2;
    public static final int LINE_VERTICAL_NONE = 0;
    public static final int LINE_VERTICAL_UP = 1;
    public static final int MODEL_TYPE_FRAME = 3;
    public static final int MODEL_TYPE_HOLLOW = 2;
    public static final int MODEL_TYPE_NONE = 0;
    public static final int MODEL_TYPE_SOLID = 1;
    public static final int MUSIC_BOX_SIZE = 20;
    public static boolean allowBuilding = false;
    public static boolean allowMusic = false;
    public static int ballRadius = 0;
    public static int ballType = 0;
    private static Queue<BuildPoint> boxPosList = new ConcurrentLinkedQueue();
    public static int buildType = 0;
    public static int cuboidHeight = 0;
    public static int cuboidLength = 0;
    public static int cuboidType = 0;
    public static int cuboidWidth = 0;
    private static Stack<List<BuildPoint>> history = new Stack();
    public static int lineDestroy = 0;
    public static int lineInterval = 0;
    public static int lineLength = 0;
    public static int lineType = 0;
    public static int lineVertical = 0;
    public static int musicData = 0;
    public static boolean requestRevokeBuildingOnce = false;

    public static class BuildPoint {
        public int f3589x;
        public int f3590y;
        public int f3591z;
    }

    public static void addHistory(List<BuildPoint> list) {
        while (history.size() >= 5) {
            history.remove(0);
        }
        history.add(list);
    }

    public static void addMusicBoxPos(int i, int i2, int i3) {
        BuildPoint buildPoint = new BuildPoint();
        buildPoint.f3589x = i;
        buildPoint.f3590y = i2;
        buildPoint.f3591z = i3;
        boxPosList.add(buildPoint);
    }

    public static void clearHistory() {
        history.clear();
    }

    public static void clearMusicBoxList() {
        boxPosList.clear();
    }

    public static int getHistorySize() {
        return history.size();
    }

    public static List<BuildPoint> getLastBuilding() {
        return history.size() == 0 ? null : (List) history.pop();
    }

    public static BuildPoint getMusicBoxPos() {
        return (BuildPoint) boxPosList.poll();
    }

    private void recordPoint(List<BuildPoint> list, int i, int i2, int i3) {
        BuildPoint buildPoint = new BuildPoint();
        buildPoint.f3589x = i;
        buildPoint.f3590y = i2;
        buildPoint.f3591z = i3;
        list.add(buildPoint);
    }

    public static void reset() {
        resetSwithes();
        resetLineParams();
        resetCuboidParams();
        resetBallParams();
    }

    private static void resetBallParams() {
        ballType = 0;
        ballRadius = 0;
    }

    private static void resetCuboidParams() {
        cuboidType = 0;
        cuboidLength = 0;
        cuboidWidth = 0;
        cuboidHeight = 0;
    }

    private static void resetLineParams() {
        lineType = 0;
        lineLength = 0;
        lineInterval = 0;
        lineVertical = 0;
        lineDestroy = 0;
    }

    public static void resetSwithes() {
        allowBuilding = false;
        buildType = 0;
    }
}
