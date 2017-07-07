package com.vijfhart.casus.tree.file;

import com.vijfhart.casus.tree.NodeTree;

import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Kyra on 14-6-2017.
 */
public class FileTreeApp {

    public void printCurrentDirectory() {
        new FileTree(".").printTree();
    }

    public void toStream() {
        NodeTree<PathNode> ft = new FileTree(".");
        ft.stream().forEach(pathNodeTreeNode -> System.out.println(pathNodeTreeNode.path("\n")));
    }

    public static void main(String[] args) {
        FileTreeApp obj = new FileTreeApp();
//        obj.printCurrentDirectory();
        obj.toStream();
    }
}
