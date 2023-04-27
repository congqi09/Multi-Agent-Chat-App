# Multi-Agent Chat Application

This is a real-time multi-agent chat application built with Java. The system can support multiple agents to use the application concurrently, contend for shared resources, and perform real-time updates to some form of shared state.

## Team Members

Xueyang Li
Cong Qi
Wenjie Qi

## Functions

Users can sign up, log in, log out to join the chat. A server manages these functions for the user and stores all the registration and chat room information. Once a user starts the chat room, a server inside the chat room is started, and users can connect to it. After connecting to the server, users can communicate with other users in the network. This multi-agent chat system relies on a peer-to-peer network setup.

To make our application more user-friendly and efficient, we are planning to develop a user interface (UI). A well-designed UI would enable users to interact with our chat system seamlessly. Also, we will thoroughly test all the client and server implementations in the distributed system.

## Algorithms

We implemented the following concepts taught in class for this project:  

Distributed mutual exclusion  
Distributed transactions  
Fault tolerance  
Group communications  
Peer-to-peer networks  

### Distributed Mutual Exclusion
The central server grants a token to the lead node, which can use it to establish a long polling connection and perform its required tasks without interference from other nodes. Once the lead node completes its tasks, it releases the token, which becomes available to be granted to another node.

### Distributed Transactions
We have used 2 phase commits for maintaining node information in our application. Whenever a new node joins or leaves the P-to-P network, two-phase commits guarantee that all nodes and the central server have the same live node lists. This use of distributed transactions ensures the consistency of the nodes.

### Fault Tolerance
Our chat system is designed to be fault-tolerant through the implementation of the leader election and transaction APIs. These APIs enable other nodes to update the status and synchronize data in the event of a communication node failure, resulting in a highly fault-tolerant software system.

### Group Communication
Our program leverages group communication as a means of interaction between clients in the network. When a client wishes to join or publish something, they interact with other clients in the group network, enabling effective communication within the group.

### Peer to Peer Network
Our program utilizes a peer-to-peer network architecture, where each client (node) interacts with other nodes through sockets. This enables clients to update the program's state and participate in leader node selection.

## Frameworks and tools

Netty: Used to construct the communication channel  
Maven: Used to manage dependencies and package modules, and as the application starter  
OKHttp and Spring Web: Used for REST API and REST template, which are used for signup, login, and logout functionality  
MongoDB Atlas: Used as the cloud end database, as it is easy to operate and manage.  

## API Documentation

Please see the JavaDoc in the folder.

## How to Run

To get started with this project, first make sure you have the following software installed:  

Java 8 or later
Apache Maven

