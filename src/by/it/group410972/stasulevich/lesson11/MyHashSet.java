package by.it.group410972.stasulevich.lesson11;

import java.util.Set;

public class MyHashSet<E> implements Set<E> {

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    @SuppressWarnings("unchecked")
    private Node<E>[] buckets;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        buckets = new Node[DEFAULT_CAPACITY];
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
        Node<E>[] oldBuckets = buckets;
        buckets = new Node[buckets.length * 2];
        size = 0;

        for (Node<E> bucket : oldBuckets) {
            Node<E> current = bucket;
            while (current != null) {
                add(current.data);
                current = current.next;
            }
        }
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
        Node<E> current = buckets[index];

        if (current == null) {
            buckets[index] = new Node<>(element);
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

        current.next = new Node<>(element);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getBucketIndex(o);
        Node<E> current = buckets[index];

        if (current == null) {
            return false;
        }

        if ((o == null && current.data == null) || 
            (o != null && o.equals(current.data))) {
            buckets[index] = current.next;
            size--;
            return true;
        }

        while (current.next != null) {
            if ((o == null && current.next.data == null) || 
                (o != null && o.equals(current.next.data))) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean contains(Object o) {
        int index = getBucketIndex(o);
        Node<E> current = buckets[index];

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
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Node<E> bucket : buckets) {
            Node<E> current = bucket;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.data);
                current = current.next;
                first = false;
            }
        }
        sb.append("]");
        return sb.toString();
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

    @Override
    public boolean containsAll(java.util.Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(java.util.Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(java.util.Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(java.util.Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}

