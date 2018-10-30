package com.vodafone.domain;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by ktmle on 27/10/2018.
 */
@Component
@PropertySource("classpath:application.properties")
public class KdTree {

    // helper data type representing a node of a kd-tree
    private static class KdNode {
        private KdNode left;
        private KdNode right;
        private final boolean vertical;
        private final double x;
        private final double y;

        public KdNode(final double x, final double y, final KdNode l,
                      final KdNode r, final boolean v) {
            this.x = x;
            this.y = y;
            left = l;
            right = r;
            vertical = v;
        }
    }

    private static RectHV container;

    private KdNode root;
    private int size;

    // construct an empty tree of points
    public KdTree() {
        size = 0;
        root = null;
    }

    // does the tree contain the point p?
    public boolean contains(final PointTwoD p) {
        return contains(root, p.x(), p.y());
    }

    // helper: does the subtree rooted at node contain (x, y)?
    private boolean contains(KdNode node, double x, double y) {
        if (node == null) return false;
        if (node.x == x && node.y == y) return true;

        if (node.vertical && x < node.x || !node.vertical && y < node.y)
            return contains(node.left, x, y);
        else
            return contains(node.right, x, y);
    }


    // helper: add point p to subtree rooted at node
    private KdNode insert(final KdNode node, final PointTwoD PointTwoD,
                          final boolean vertical) {
        // if new node, create it
        if (node == null) {
            size++;
            return new KdNode(PointTwoD.x(), PointTwoD.y(), null, null, vertical);
        }

        // if already in, return it
        if (node.x == PointTwoD.x() && node.y == PointTwoD.y()) return node;

        // else, insert it where corresponds (left - right recursive call)
        if (node.vertical && PointTwoD.x() < node.x || !node.vertical && PointTwoD.y() < node.y)
            node.left = insert(node.left, PointTwoD, !node.vertical);
        else
            node.right = insert(node.right, PointTwoD, !node.vertical);

        return node;
    }

    // add the point p to the tree (if it is not already in the tree)
    public void insert(final PointTwoD p) {
        root = insert(root, p, true);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // helper: nearest neighbor of (x,y) in subtree rooted at node
    private PointTwoD nearest(final KdNode node, final RectHV rect,
                              final double x, final double y, final PointTwoD candidate) {
        if (node == null) return candidate;

        double dqn = 0.0;
        double drq = 0.0;
        RectHV left = null;
        RectHV rigt = null;
        final PointTwoD query = new PointTwoD(x, y);
        PointTwoD nearest = candidate;

        if (nearest != null) {
            dqn = query.distanceSquaredTo(nearest);
            drq = rect.distanceSquaredTo(query);
        }

        if (nearest == null || dqn > drq) {
            final PointTwoD point = new PointTwoD(node.x, node.y);
            if (nearest == null || dqn > query.distanceSquaredTo(point))
                nearest = point;

            if (node.vertical) {
                left = new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
                rigt = new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());

                if (x < node.x) {
                    nearest = nearest(node.left, left, x, y, nearest);
                    nearest = nearest(node.right, rigt, x, y, nearest);
                } else {
                    nearest = nearest(node.right, rigt, x, y, nearest);
                    nearest = nearest(node.left, left, x, y, nearest);
                }
            } else {
                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
                rigt = new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());

                if (y < node.y) {
                    nearest = nearest(node.left, left, x, y, nearest);
                    nearest = nearest(node.right, rigt, x, y, nearest);
                } else {
                    nearest = nearest(node.right, rigt, x, y, nearest);
                    nearest = nearest(node.left, left, x, y, nearest);
                }
            }
        }

        return nearest;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public PointTwoD nearest(final PointTwoD PointTwoD) {
        return nearest(root, container, PointTwoD.x(), PointTwoD.y(), null);
    }

    public static void setContainer(RectHV container) {
        KdTree.container = container;
    }
    // number of points in the tree
    public int size() {
        return size;
    }


}