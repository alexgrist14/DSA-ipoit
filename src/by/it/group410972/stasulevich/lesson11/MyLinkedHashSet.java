package by.it.group410972.stasulevich.lesson11;

import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    private static class HashNode<E> {
        E data;
        HashNode<E> next;

        HashNode(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private static class LinkedNode<E> {
        E data;
        LinkedNode<E> prev;
        LinkedNode<E> next;

        LinkedNode(E data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    @SuppressWarnings("unchecked")
    private HashNode<E>[] buckets;
    private LinkedNode<E> head;
    private LinkedNode<E> tail;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        buckets = new HashNode[DEFAULT_CAPACITY];
        head = null;
        tail = null;
        size = 0;
    }

    private int hash(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    private int getBucketIndex(Object o) {
        int hash = hash(o);
        return (hash & 0x7FFFFFFF) % buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedNode<E> oldHead = head;
        buckets = new HashNode[buckets.length * 2];
        head = null;
        tail = null;
        size = 0;

        LinkedNode<E> current = oldHead;
        while (current != null) {
            add(current.data);
            current = current.next;
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        LinkedNode<E> current = head;
        boolean first = true;
        while (current != null) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(current.data);
            current = current.next;
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(Object e) {
        @SuppressWarnings("unchecked")
        E element = (E) e;
        if ((double) size / buckets.length >= LOAD_FACTOR) {
            resize();
        }

        int index = getBucketIndex(element);
        HashNode<E> current = buckets[index];

        if (current == null) {
            buckets[index] = new HashNode<>(element);
            LinkedNode<E> newNode = new LinkedNode<>(element);
            if (head == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            size++;
            return true;
        }

        while (current.next != null) {
            if ((element == null && current.data == null) || 
                (element != null && element.equals(current.data))) {
                return false;
            }
            current = current.next;
        }

        if ((element == null && current.data == null) || 
            (element != null && element.equals(current.data))) {
            return false;
        }

        current.next = new HashNode<>(element);
        LinkedNode<E> newNode = new LinkedNode<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getBucketIndex(o);
        HashNode<E> current = buckets[index];

        if (current == null) {
            return false;
        }

        if ((o == null && current.data == null) || 
            (o != null && o.equals(current.data))) {
            buckets[index] = current.next;
            removeFromLinked((E) o);
            size--;
            return true;
        }

        while (current.next != null) {
            if ((o == null && current.next.data == null) || 
                (o != null && o.equals(current.next.data))) {
                current.next = current.next.next;
                removeFromLinked((E) o);
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    private void removeFromLinked(E element) {
        LinkedNode<E> current = head;
        while (current != null) {
            if ((element == null && current.data == null) || 
                (element != null && element.equals(current.data))) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                return;
            }
            current = current.next;
        }
    }

    @Override
    public boolean contains(Object o) {
        int index = getBucketIndex(o);
        HashNode<E> current = buckets[index];

        while (current != null) {
            if ((o == null && current.data == null) || 
                (o != null && o.equals(current.data))) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean containsAll(java.util.Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(java.util.Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(java.util.Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(java.util.Collection<?> c) {
        boolean modified = false;
        LinkedNode<E> current = head;
        while (current != null) {
            LinkedNode<E> next = current.next;
            if (!c.contains(current.data)) {
                remove(current.data);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    // Unsupported operations
    @Override
    public java.util.Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
}

