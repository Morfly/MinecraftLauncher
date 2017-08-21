package com.yy.hiidostatis.inner.implementation;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class TaskDataSet implements Serializable {
    private static final Comparator<TaskData> DATA_COMPARATOR = new MyComparator();
    private static final long serialVersionUID = -8880276834197410994L;
    private Set<TaskData> dataSet = new TreeSet(DATA_COMPARATOR);
    private TaskData taskData2;

    static final class MyComparator implements Serializable, Comparator<TaskData> {
        private static final long serialVersionUID = 605434724079570979L;

        private MyComparator() {
        }

        public int compare(TaskData taskData, TaskData taskData2) {
            if (taskData.hashCode() == taskData2.hashCode()) {
                return 0;
            }
            if (taskData.getOrder() > taskData2.getOrder()) {
                return 1;
            }
            if (taskData.getOrder() < taskData2.getOrder()) {
                return -1;
            }
            int time = (int) (taskData.getTime() - taskData2.getTime());
            return time == 0 ? taskData.hashCode() - taskData2.hashCode() : time;
        }
    }

    public void clear() {
        this.dataSet.clear();
    }

    public TaskDataSet copy() {
        TaskDataSet taskDataSet = new TaskDataSet();
        taskDataSet.dataSet.addAll(this.dataSet);
        return taskDataSet;
    }

    public TaskData getFirst() {
        return this.dataSet.size() > 0 ? (TaskData) this.dataSet.iterator().next() : null;
    }

    public TaskData getLast() {
        TaskData taskData = null;
        if (this.dataSet.size() > 0) {
            for (TaskData taskData2 : this.dataSet) {
            }
        }
        return taskData;
    }

    public TaskData getRandom() {
        if (this.dataSet.size() <= 0) {
            return null;
        }
        int nextInt = new Random().nextInt(this.dataSet.size());
        int i = nextInt;
        TaskData taskData = null;
        int i2 = i;
        for (TaskData taskData2 : this.dataSet) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return taskData2;
            }
            i2 = i3;
        }
        return taskData2;
    }

    public boolean isEmpty() {
        return this.dataSet.isEmpty();
    }

    public Iterator<TaskData> iterator() {
        return this.dataSet.iterator();
    }

    public boolean remove(TaskData taskData) {
        return this.dataSet.remove(taskData);
    }

    public TaskData removeFirst() {
        if (this.dataSet.size() <= 0) {
            return null;
        }
        TaskData taskData = (TaskData) this.dataSet.iterator().next();
        this.dataSet.remove(taskData);
        return taskData;
    }

    public boolean saveOrUpdate(TaskData taskData) {
        this.dataSet.remove(taskData);
        return this.dataSet.add(taskData);
    }

    public int size() {
        return this.dataSet.size();
    }
}
