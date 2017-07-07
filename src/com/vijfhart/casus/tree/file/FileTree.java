package com.vijfhart.casus.tree.file;

import com.vijfhart.casus.tree.NodeTree;
import com.vijfhart.casus.tree.Tree;
import com.vijfhart.casus.tree.TreeIterator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

/*
 * De class FileTree is bedoeld om bestanden in te lezen in een NodeTree,
 * en om informatie daaruit op het scherm weer te geven. De class heeft
 * initieel een lege tree om PathNode objecten in op te slaan. Geef
 * FileTree ook een constructor waaraan een directory naam aan kan
 * worden meegegeven, om de start directory op te geven. In de volgende
 * stap zal de tree worden opgebouwd uit de opgegeven directory en de
 * onderliggende bestanden/directories.
 */

public class FileTree extends NodeTree<PathNode> {

    private NodeTree<PathNode> fileTree;

    public FileTree() {
        fileTree = new NodeTree<>();
    }

    public FileTree(String directory) {
        this();
        fillTree(directory);
    }

    @Override
    public int getTreeSize() {
        return fileTree.getTreeSize();
    }

    public void fillTree(String directory) {
        Function<Path, PathNode> f = path -> new PathNode(path);
        Path path = Paths.get(directory);
        try {
            Map<Path, PathNode> map = Files.walk(path)
                        .collect(
                                Collectors.toMap(Function.identity(), f));
            map.entrySet().stream().forEach(entry -> entry.getValue().setParent(map.get(entry.getKey().getParent())));
            fileTree = map.values().stream().collect(FileTree::new, FileTree::add, FileTree::addAll);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TreeIterator<PathNode> getTreeIterator() {
        return fileTree.iterator();
    }

    private long getDirectorySize(Path folder) throws IOException {
        return Files.walk(folder)
                .filter(p -> p.toFile().isFile())
                .mapToLong(p -> p.toFile().length())
                .sum();
    }

    private long countFiles(Path folder) throws IOException {
        return Files.walk(folder).filter(p -> p.toFile().isFile()).count();
    }

    /*
     * Maak in FileTree ook een methode printTree aan. Deze doorloopt de tree
     * met een TreeIterator, en toont van elke PathNode in de tree de volgende
     * informatie:
     *      - het aantal bestanden onder de directory
     *      - het totale aantal bytes binnen de directory
     *      - het pad van het bestand / de directory
     */
    public void printTree() {
        TreeIterator<PathNode> iterator = fileTree.iterator();
/*        while (iterator.hasNext()) {
            PathNode node = iterator.next();
            long nrFiles = fileTree.descendantCount(node);
            ToLongFunction<PathNode> getSize = value -> value.getSize();
            long bytes = fileTree.descendantSum(node, getSize);
            String pad = node.getObject().toString();
            System.out.format("%2d %6d %s\n", nrFiles, bytes, pad);
        }*/
        while (iterator.hasNext()) {
            Path path = iterator.next().getObject();
            File file = path.toFile();
            if (file.isDirectory()) {
                long nrFiles = 0;
                long bytes = 0;
                try {
                    nrFiles = countFiles(path);
                    bytes = getDirectorySize(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String pad = path.toString();
                System.out.format("%2d %6d %s\n", nrFiles, bytes, pad);
            } else if (file.isFile()) {
                BasicFileAttributes attributes = null;
                try {
                    attributes = Files.readAttributes(path, BasicFileAttributes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String nrFiles = "-";
                long bytes = attributes.size();
                String pad = path.toString();
                System.out.format("%2s %6d %s\n", nrFiles, bytes, pad);
            }
        }
    }
}
