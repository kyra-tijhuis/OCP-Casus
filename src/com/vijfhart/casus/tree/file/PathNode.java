package com.vijfhart.casus.tree.file;

import com.vijfhart.casus.tree.NameNode;
import com.vijfhart.casus.tree.Node;
import com.vijfhart.casus.tree.NodeString;
import com.vijfhart.casus.tree.WrapperNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Maak vervolgens een class PathNode als subclass van WrapperNode. Hierin
 * is PathNode de wrapper class, en deze bevat een reference naar een Path
 * object, die met de method getObject() uit WrapperNode kan worden opgehaald.

 */
public class PathNode extends WrapperNode<PathNode, Path> {

    private long size;

    protected PathNode(Path object) {
        super(object);
        setSize(object);
    }

    public long getSize() {
        return size;
    }

    private void setSize(Path object) {
        File file = object.toFile();
        try {
            if (file.isFile()) {
                size = Files.size(object);
            } else {
                size = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
