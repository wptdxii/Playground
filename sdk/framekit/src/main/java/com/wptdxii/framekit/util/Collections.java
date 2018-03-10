package com.wptdxii.framekit.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class Collections {

    private Collections() {
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    public static <E> List<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = new ArrayList<>();
        addAll(list, elements);
        return list;
    }

    public static <E> List<E> newArrayList(Iterable<E> elements) {
        return (elements instanceof Collection) ?
                new ArrayList<>(cast(elements)) : newArrayList(elements.iterator());
    }


    public static <E> void addAll(Collection<E> collection, Iterator<? extends E> iterator) {
        while (iterator.hasNext()) {
            collection.add(iterator.next());
        }
    }

    public static <E> Collection<E> cast(Iterable<E> iterable) {
        return (Collection<E>) iterable;
    }
}
