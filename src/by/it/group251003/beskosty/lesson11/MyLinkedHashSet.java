package by.it.group251003.beskosty.lesson11;

import java.util.*;

public class MyLinkedHashSet<E> implements Set<E> {
    protected static class MyNode<E> {
        public E data;
        public MyNode<E> next;
        public int pose;

        public MyNode(E data, int actPose) {
            this.data = data;
            this.pose = actPose;
            next = null;
        }
    }

    private final int DefaultSize = 100;
    private int actSize = 0, lastPose = 0;


    protected static class MyList<E> {
        private MyNode<E> head, tail;


        public boolean contains(E o) {
            MyNode<E> curr = head;
            while (curr != null && !curr.data.equals(o)) {
                curr = curr.next;
            }
            return curr != null;
        }

        public boolean add(E o, int actPose, boolean toCheck) {
            if (toCheck && contains(o)) {
                return false;
            }
            MyNode<E> curr = new MyNode<>(o, actPose);
            if (tail == null) {
                head = tail = curr;
            }
            else {
                tail.next = curr;
                tail = curr;
            }
            return true;
        }

        public boolean remove(E o) {
            if (head == null) {
                return false;
            }
            if (head.data.equals(o)) {
                head = head.next;
                return true;
            }
            MyNode<E> prev = head;
            while (prev.next != null && !prev.next.data.equals(o)) {
                prev = prev.next;
            }
            if (prev.next == null) {
                return false;
            }
            if (prev.next == tail) {
                prev.next = null;
                tail = prev;
            } else {
                prev.next = prev.next.next;
            }
            return true;
        }
    }

    private final MyList[] map = new MyList[DefaultSize];
    {
        for (int i = 0; i < DefaultSize; i++)
            map[i] = new MyList<E>();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder res = new StringBuilder("[");
        MyNode<E>[] nods = new MyNode[DefaultSize];
        for (int i = 0; i < DefaultSize; i++) {
            nods[i] = (MyNode<E>) map[i].head;
        }
        while (true) {
            MyNode<E> minInf = new MyNode<>((E) new Object(), Integer.MAX_VALUE);
            int minI = -1;
            for (int i = 0; i < DefaultSize; i++)
                if (nods[i] != null && nods[i].pose < minInf.pose) {
                    minInf = nods[i];
                    minI = i;
                }
            if (minI == -1) {
                break;
            }
            nods[minI] = nods[minI].next;
            res.append(minInf.data.toString()).append(", ");
        }
        return res.substring(0, res.length() - 2) + "]";
    }

    @Override
    public int size() {
        return actSize;
    }

    @Override
    public void clear() {
        actSize = 0;
        for(int i = 0; i < DefaultSize; i++) {
            map[i] = new MyList<E>();
        }
    }
    @Override
    public boolean isEmpty() {
        return actSize == 0;
    }

    @Override
    public boolean add(E e) {
        if (map[e.hashCode() % DefaultSize].add(e, lastPose++, true))
            actSize++;
        else {
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (map[o.hashCode()%DefaultSize].remove(o))
            actSize--;
        else {
            return false;
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return map[o.hashCode()%DefaultSize].contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean retBull = false;
        for (E o : c) {
            if (add(o)) {
                retBull = true;
            }
        }
        return retBull;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int deleted = 0;
        for (int i = 0; i < DefaultSize; i++) {
            MyList<E> newMyList = new MyList<>();
            MyNode<E> curr = map[i].head;
            while (curr != null) {
                if (!c.contains(curr.data)) {
                    newMyList.add(curr.data, curr.pose, false);
                }
                else {
                    deleted++;
                }
                curr = curr.next;
            }
            map[i] = newMyList;
        }
        actSize -= deleted;
        return deleted > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int deleted = 0;
        for (int i = 0; i < DefaultSize; i++) {
            MyList<E> newMyList = new MyList<>();
            MyNode<E> curr = map[i].head;
            while (curr != null) {
                if (c.contains(curr.data)) {
                    newMyList.add(curr.data, curr.pose, false);
                }
                else {
                    deleted++;
                }
                curr = curr.next;
            }
            map[i] = newMyList;
        }
        actSize -= deleted;
        return deleted > 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

}