package com.vijfhart.casus.tree;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import static java.util.Spliterator.*;

/**
 * NodeTree is an implementation of {@link Tree}.
 * It uses an anonymous inner class to implement a TreeIterator.
 * The descendantsOf method is used to generate a list of descendants of a node.
 * This method is also used to calculate summary measures of a node.
 *
 * @author Marko Draisma
 * @version 1.0
 */

public class NodeTree<T extends Node<T>>  implements Tree<T> {

    private List<T> nodeList = new ArrayList<>();

    /**
     * empty constructor
     */
    public NodeTree(){}

    /**
     * This constructor can be used to generate a copy of a NodeTree.
     * @param list: a node list
     */

    public NodeTree(List<T> list){
        nodeList = new ArrayList<>();
        for(T t:list){
            nodeList.add(t);
        }
    }

    /**
     * Adds a node to the tree.
     * NodeTree uses a List for its elements. The add method is delegated to this list.
     * @param t: the node to add to the tree
     */

    public void add(T t){
        nodeList.add(t);
    }


    /*
     * Daartoe zal NodeTree moeten worden aangevuld met een method om twee NodeTree objecten te combineren,
     * vergelijkbaar met de method addAll() van ArrayList en add van StringBuilder
     * (zie API documentatie bij de method collect() van java.util.Stream).
     */

    public void addAll(NodeTree<T> nodeTree) {
        TreeIterator<T> iterator = nodeTree.iterator();
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }

    /**
     * Generates a list of nodes descending a given node, including the given node itself.
     * It uses the startWith method to generate a sublist of the node itself and its descendants.
     * @param node: a node
     * @return a list of nodes descending and including the given node.
     */

    public List<T> descendantsOf(T node){
        Tree<T> copy = new NodeTree<>(nodeList);
        TreeIterator<T> iterator = copy.iterator();
        iterator.startWith(node);
        List<T> list = new ArrayList<>();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }

    public int getTreeSize() {
        return nodeList.size();
    }

    /**
     * A count of the nodes descending a given node.
     * It uses descendantsOf to get the list of nodes.
     * @param t: a node
     * @return the count of nodes descending the given node, excluding the given node.
     */

    public int descendantCount(T t){
        List<T> list = descendantsOf(t);
        return descendantsOf(t).size()-1;
    }


    /**
     * Returns the sum of a given Node-characteristic, provided by the application
     * using a NodeInt implementation.
     * A Tree doesn't know what characteristics of Type T can be used to calculate a sum.
     * This can be provided by the application however: then the Node type to be used is known.
     * The interface {@link NodeInt} is used for this purpose.
     * @param  t: the node beneath which the sum is calculated.
     * @param nodeInt: the NodeInt implementation, as needed to specify what characteristic of type T
     * is to be used to calculate the sum.
     */
    public int descendantSum(T t, NodeInt<T> nodeInt){
        List<T> list = descendantsOf(t);
        int sum=0;
        for(T node: list){
            sum+=nodeInt.get(node);
        }
        return sum;
    }

    /**
     * Returns the sum of a given Node-characteristic, provided by the application
     * using a NodeInt implementation.
     * A Tree doesn't know what characteristics of Type T can be used to calculate a sum.
     * This can be provided by the application however: then the Node type to be used is known.
     * The interface {@link ToLongFunction} is used for this purpose.
     * @param  t: the node beneath which the sum is calculated.
     * @param function: the ToLongFunction implementation, as needed to specify what characteristic of type T
     * is to be used to calculate the sum.
     */
    public long descendantSum(T t, ToLongFunction<T> function){
        List<T> list = descendantsOf(t);
        int sum=0;
        for(T node: list){
            sum+=function.applyAsLong(node);
        }
        return sum;
    }

    /**
     * Returns the sum of a given Node-characteristic, provided by the application
     * using a NodeDouble implemenation.
     * @param t: the node to start the calculation of the sum
     * @param nodeDouble: the NodeDouble implementation
     * @return the sum of double values returned by the nodeDouble
     *         for each node descending and including the node specified.
     * @see int descendantSum(T t, NodeInt nodeInt);
     */
    public double descendantSum(T t, NodeDouble<T> nodeDouble){
        List<T> list = descendantsOf(t);
        double sum=0;
        for(T node: list){
            sum+=nodeDouble.get(node);
        }
        return sum;
    }

    /**
     * Returns the sum of a given Node-characteristic, provided by the application
     * using a NodeDouble implemenation.
     * @param t: the node to start the calculation of the sum
     * @param function: the ToDoubleFunction implementation
     * @return the sum of double values returned by the nodeDouble
     *         for each node descending and including the node specified.
     * @see long descendantSum(T t, ToLongFunction<T> function);
     *
     */
    public double descendantSum(T t, ToDoubleFunction<T> function){
        List<T> list = descendantsOf(t);
        double sum=0;
        for(T node: list){
            sum+=function.applyAsDouble(node);
        }
        return sum;
    }

    public TreeIterator<T> getTreeIterator() {
        return this.iterator();
    }

    /**
     * Deze method gebruikt een TreeIterator<T> object (maak deze aan met
     * de betreffende Tree method).
     * Uiteindelijk hebben we een Iterator<TreeNode<T>> nodig om de
     * gewenste stream aan te kunnen maken: elke next() van deze iterator
     * wordt dan een volgend element van de stream.
     *
     * Om van de TreeIterator<T> een Iterator<TreeNode<T>> te kunnen maken
     * hebben we twee lokale classes nodig:
     *  - een class die TreeNode<T> implementeert. Elk object van deze
     *    class verpakt een object van type T. De TreeNode <T> methods
     *    kunnen worden geïmplementeerd met behulp van dit object en het
     *    TreeIterator<T> object.
     *  - een class die Iterator<TreeNode<T>> implementeert, die remove()
     *    en hasNext() delegeert naar de TreeIterator, en die bij elke next()
     *    de verkregen node verpakt in een nieuw TreeNode object.
     *
     *  Met behulp van een Iterator<TreeNode>> object kan uiteindelijk een
     *  Stream<TreeNode>> worden gemaakt:
     *   - verpak de Iterator in een java.util.Spliterator met
     *     Spliterators.spliterator(…). Deze heeft behalve de Iterator <TreeNode>>
     *     ook een grootte nodig (de grootte van de tree), en specifieke
     *     eigenschappen van de Spliterator. Na een static import van de-
     *     constanten van Spliterator kunnen deze eigenschappen
     *     met een bitwise OR ( | ) worden meegegeven:
     *     ORDERED | SORTED | SIZED | DISTINCT | NONNULL.
     *   - maak van de Spliterator een Stream met StreamSupport.stream().
     *     Geef hierbij aan dat de stream niet parallel kan worden doorlopen.
     *
     *     Test uiteindelijk de stream() method in FileTreeApp.
     */
    public Stream<TreeNode<T>> stream() {
        TreeIterator<T> treeIterator = getTreeIterator();

        class TreeNodeImp<T> implements TreeNode<T> {
            public T node() {
                return (T) treeIterator.next();
            }

            public String path(String separator) {
                return path(separator, t -> node().toString());
            }

            public String path(String separator, Function<T, String> f) {
                return treeIterator.path(t -> f.apply((T) t), separator);
            }

            public boolean isLeaf() {
                return treeIterator.isLeaf();
            }

            public int level() {
                return treeIterator.level();
            }
        };

        class TreeNodeIterator<T> implements Iterator<TreeNode<T>> {
            @Override
            public boolean hasNext() {
                return treeIterator.hasNext();
            }

            @Override
            public TreeNode<T> next() {
                return new TreeNodeImp<T>();
            }

            @Override
            public void remove() {
                treeIterator.remove();
            }
        };

        Spliterator<TreeNode<T>> spliterator = Spliterators.spliterator(new TreeNodeIterator<T>(), getTreeSize(), ORDERED | IMMUTABLE);

        return StreamSupport.stream(spliterator, false);
    }

    /**
     * Returns a TreeIterator to loop through the node elements in a Tree.
     * The TreeIterator uses the iterator of the nodeList of de NodeTree.
     * With every loop information is saved about the current node and the ancestors of the current node.
     */
    public TreeIterator<T> iterator(){
        return new TreeIterator<T>(){

            private Iterator<T> iterator;
            private T current;
            private CopyOnWriteArrayList<T> ancestors = new CopyOnWriteArrayList<>();
            private boolean nextCalled;
            private T startWith;
            private boolean started;
            private TreeComparator<T> treeComparator = new TreeComparator<>();

            /**
             * Initializer of this anonymous inner class.
             * The nodeList is sorted and the inner iterator is refreshed.
             */
            {
                Collections.sort(nodeList);
                iterator = nodeList.iterator();
            }

            /**
             * Specifies at what node the iterator will be looping through the NodeTree.
             *
             * @param startWith: the node where to start the iteration.
             * @throws IllegalStateException if this method is called after a call to next.
             */
            public void startWith(T startWith){
                if(started)
                    throw new IllegalStateException(
                            "startWith can only be called before the first call to next");
                List<T> list=new ArrayList<>();
                this.startWith=startWith;
                T node = null;
                while(iterator.hasNext()){
                    node = iterator.next();
                    if(childOfStartWith(node))
                        list.add(node);
                }
                this.iterator=list.iterator();
                started=true;
            }

            /**
             * Specifies how siblings are to be sorted.
             * This method uses a local {@link TreeComparator} to specify the global ordering,
             * in which the given Comparator is used to specify the ordering of siblings.
             * @param comparator: a Comparator to specify the ordering of siblings.
             */
            public void orderSiblingsBy(Comparator<T> comparator){
                this.treeComparator.orderSiblingsBy(comparator);
                Collections.sort(nodeList, treeComparator);
                this.iterator = nodeList.iterator();
            }

            private boolean childOfStartWith(T t){
                T node = t;
                if(node == startWith) return true;
                while(node.getParent() != null){
                    if(node.getParent()==startWith) return true;
                    node=node.getParent();
                }
                return false;
            }

            /**
             * Returns true if there are more nodes in the node list.
             * This method is delegated to the local iterator.
             */
            public boolean hasNext(){
                return iterator.hasNext();
            }

            /**
             * Retrieves the next node.
             * For convenience in other methods, the current node is saved,
             * along with the ancestors of the current node.
             * @return the next node in the iteration
             * @throws NoSuchElementException if the iterattion has no more elements.
             */
            public T next(){
                if(iterator.hasNext()){
                    ancestors.clear();
                    current=this.iterator.next();
                    fillAncestors(current);
                    nextCalled=true;
                    started=true;
                    return current;
                }
                else throw new NoSuchElementException("Last element has been reached");
            }

            /**
             * Returns the relative level of the current node.
             */
            public int level(){
                return ancestors.size();
            }

            private void fillAncestors(T t){
                if(t==startWith) return ;
                T node=t;
                while(node.getParent()!=null){
                    if(node.getParent()==startWith){
                        ancestors.add(node.getParent());
                        return;
                    }
                    ancestors.add(node.getParent());
                    node=node.getParent();
                }
            }

            /**
             * Returns true if the node is a leaf node.
             * This method uses the size of the descendants of the current node.
             * Another way may be to use a method isLeaf() in Node. This will perform better.
             */
            public boolean isLeaf(){
                return descendantsOf(current).size()==1;
            }

            /**
             * Removes the current node.
             * @throws RuntimeException if this node is a parent of another node.
             * @throws IllegalStateException if remove is called without a previous call to next
             */
            public void remove(){
                if(nextCalled){
                    if(isLeaf()){
                        iterator.remove();
                        nextCalled=false;
                        if(startWith!=null){
                            nodeList.remove(current);
                        }
                    }
                    else {
                        throw new RuntimeException(String.format( "%s is a parent, so can't be removed", current));
                    }
                }
                else {
                    throw new IllegalStateException("remove can only be called after a call to next");
                }
            }

            /**
             * Returns the path from the ancestors to the current node.
             * This method uses a NodeString implementation to specify what part of a node
             * is shown in the path.
             * @param nodeString: a NodeString to specify what String value
             *                    of the node will be shown as part of the path.
             * @param separator: the String to separate the parts of a path
             */
            public String path(NodeString<T> nodeString, String separator){
                StringBuilder sb = new StringBuilder();
                sb.append(new StringBuilder(separator).append(nodeString.get(current)));
                for(T t:ancestors){
                    sb.insert(0,new StringBuilder(separator).append(nodeString.get(t)));
                }
                return sb.toString();
            }

        };
    }
}

