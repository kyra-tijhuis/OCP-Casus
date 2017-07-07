package com.vijfhart.casus.tree;

import java.util.function.Function;

/**
 * Maak daarvoor de interface TreeNode > aan, met de volgende methods:
 *  - T node();
 *  - String path(String separator);
 *  - String path(String separator, Function<T, String> f);
 *  - boolean isLeaf();
 *  - int level();
 */
public interface TreeNode<T> {
    T node();
    String path(String separator);
    String path(String separator, Function<T, String> f);
    boolean isLeaf();
    int level();
}
