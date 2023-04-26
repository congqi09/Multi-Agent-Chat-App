package liveNodeList;

import node.Node;

import java.util.Iterator;

/**
 * Store current live nodes with the ascending order of the user id.
 * Client side list will store io channel, server side list will not.
 */
public interface LiveNodeList <E extends Node> {

    // Add a new node to the list.
    boolean add(E node);

    // Remove the node by its user id. If the node associates with a channel, this will close the channel as well.
    boolean remove(Long userId);

    // Query the node channel using userid
    // return true if the user with the id is present, otherwise false
    boolean isContain(Long userId);

    // Get the node with the given user id
    E get(Long userId);

    // Get the leader node that in the list
    E getLeaderNode();

    // Get all nodes in the order of their id.
    Iterator<E> getAllNodes();

    // Get the size of the list.
    int size();

    // Get the first none leader node in the list.
    E getNext();
}