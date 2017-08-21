package com.yy.hiidostatis.defs.obj;

import com.yy.hiidostatis.inner.util.Util;
import com.yy.hiidostatis.inner.util.log.C1923L;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Info<T extends Elem> implements Serializable {
    private static final String DIVIDE_ELEM = "|";
    private static final long serialVersionUID = 1;
    private List<T> elems = new CopyOnWriteArrayList();

    private void readObject(ObjectInputStream objectInputStream) {
        try {
            this.elems = (List) objectInputStream.readObject();
        } catch (Exception e) {
            C1923L.error(this, "Failed to read object from stream for %s", e);
            this.elems = new CopyOnWriteArrayList();
        }
        if (this.elems == null) {
            C1923L.brief("read elements is null, create an empty array list.", new Object[0]);
            this.elems = new ArrayList();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.elems);
    }

    public void add(Info<T> info) {
        Iterator it = info.iterator();
        while (it.hasNext()) {
            addElem((T) it.next());
        }
    }

    public void addElem(T t) {
        if (t != null) {
            this.elems.add(t);
        }
    }

    public void clear() {
        this.elems.clear();
    }

    public T getElem(int i) {
        return (T) this.elems.get(i);
    }

    public int getElemsCount() {
        return this.elems.size();
    }

    public String getResult() {
        if (Util.empty(this.elems)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Elem stringRep : this.elems) {
            stringBuilder.append(Util.replaceEncode(stringRep.getStringRep(), DIVIDE_ELEM));
            stringBuilder.append(DIVIDE_ELEM);
        }
        String stringBuilder2 = stringBuilder.toString();
        return stringBuilder2.length() > 1 ? stringBuilder2.substring(0, stringBuilder2.length() - 1) : stringBuilder2;
    }

    public Iterator<T> iterator() {
        return this.elems.iterator();
    }

    public void removeElem(T t) {
        if (t != null) {
            this.elems.remove(t);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Elem obj : this.elems) {
            stringBuilder.append(obj.toString());
            stringBuilder.append(" ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
