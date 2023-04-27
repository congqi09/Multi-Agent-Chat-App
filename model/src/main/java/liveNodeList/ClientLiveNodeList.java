package liveNodeList;

import io.netty.channel.Channel;
import node.Node;
import node.NodeChannel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * List of client live node
 */
public class ClientLiveNodeList<T extends NodeChannel> implements LiveNodeList<T>, Iterable<T> {

    private final TreeSet<T> nodes;

    public ClientLiveNodeList() {
        this.nodes = new TreeSet<>(T::compareTo);
    }

    @Override
    public synchronized boolean add(T node) {
        if (node == null) {
            return false;
        }
        return nodes.add(node);
    }

    @Override
    public synchronized boolean remove(Long userId) {
        if (userId == null) {
            return false;
        }
        T t = get(userId);
        if (t == null) {
            return false;
        }
        Channel channelToBeRemoved = t.getChannel();
        nodes.remove(t);
        if (channelToBeRemoved != null) {
            // close the channel with all handler resources associated with the channel
            channelToBeRemoved.close();
        }
        return true;
    }

    @Override
    public synchronized boolean isContain(Long userId) {
        if (userId == null) {
            return false;
        }
        T t = get(userId);
        return t != null;
    }


    @Override
    public synchronized T get(Long userId) {
        if (userId == null) {
            return null;
        }
        List<T> collect = nodes.stream().filter(node -> node.getUserId().equals(userId)).collect(Collectors.toList());
        return collect.isEmpty() ? null : collect.get(0);
    }

    @Override
    public synchronized T getLeaderNode() {
        if (nodes.isEmpty()) {
            return null;
        }
        List<T> collect = nodes.stream().filter(Node::isLeader).collect(Collectors.toList());
        return collect.isEmpty() ? null : collect.get(0);
    }

    @Override
    public synchronized Iterator<T> getAllNodes() {
        return iterator();
    }

    @Override
    public synchronized int size() {
        return nodes.size();
    }

    @Override
    public synchronized T getNext() {
        if (nodes.size() == 0) {
            return null;
        }
        List<T> collect = nodes.stream().filter(node -> !node.isLeader()).collect(Collectors.toList());
        return collect.isEmpty() ? null : collect.get(0);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return nodes.iterator();
    }
}