package by.it.group410972.stasulevich.lesson11;

import java.util.Set;

@SuppressWarnings("unchecked")
public class MyTreeSet<E> implements Set<E> {

    private E[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyTreeSet() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size >= elements.length) {
            int newCapacity = elements.length * 2;
            E[] newElements = (E[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    private int compare(E a, E b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return 1;
        }
        if (b == null) {
            return -1;
        }
        if (a instanceof Comparable && b instanceof Comparable) {
            return ((Comparable<E>) a).compareTo(b);
        }
        return 0;
    }

    private int binarySearch(E element) {
        int left = 0;
        int right = size - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = compare(element, elements[mid]);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(elements[i]);
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
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(Object e) {
        E element = (E) e;
        int index = binarySearch(element);
        if (index < size && compare(element, elements[index]) == 0) {
            return false;
        }
        ensureCapacity();
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        E element = (E) o;
        int index = binarySearch(element);
        if (index >= size || compare(element, elements[index]) != 0) {
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        E element = (E) o;
        int index = binarySearch(element);
        return index < size && compare(element, elements[index]) == 0;
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
        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                modified = true;
            }
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

