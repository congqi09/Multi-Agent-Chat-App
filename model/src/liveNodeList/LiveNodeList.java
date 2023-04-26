package liveNodeList;

import node.Node;

import java.util.Iterator;

/**
 * Store current live nodes metadata with the ascending order of the user id.
 * Client side list will store io channel, server side list will not.
 */
public interface LiveNodeList <E extends Node> {

    /**
     * Add a new node to the list.
     *
     * @param node the node to be added
     * @return true if the node is successfully added, otherwise false due to duplicated nodes
     */
    boolean add(E node);

    /**
     * Remove the node by its user id. If the node associates with a channel, this will close the channel as well.
     *
     * @param id the user id
     * @return true if the channel has been removed, false if the channel doesn't exist
     */
    boolean remove(Long id);

    /**
     * Query the node channel by provided channel.
     *
     * @param id the user id
     * @return true if the user with the id is present, otherwise false
     */
    boolean isContain(Long id);

    /**
     * Get the node by the id of the user of the node
     *
     * @param id the user id
     * @return the node with the given id, or null if none node belongs to the id
     */
    E get(Long id);

    /**
     * Get the leader node that in the list
     *
     * @return the leader node if the leader node is present, otherwise null
     */
    E getLeaderNode();

    /**
     * Get all nodes in the order of their id.
     *
     * @return an iterator of the ordered nodes
     */
    Iterator<E> getAllNodes();

    /**
     * Get the size of the list.
     *
     * @return the size of the list
     */
    int size();

    /**
     * Get the first none leader node in the list.
     *
     * @return a node. If the list only contains the leader node, this method will return null.
     */
    E getNext();
}