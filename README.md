# chitChat
Yet another Peer-to-Peer instant messaging tool.

## Introduction
chitChat is a peer-to-peer instant messaging tool. It's a terminal application implemented in Java.

## Architecture
**DHT**
chitChat uses a Distributed Hash Table(DHT) to locate peers.

**Networking**
After your client has found other clients, it establishes communication between clients.

**Secure**
The networking protocol is gRPC, so the security has already been handled.

## Now
As of Version 1.0, the application supports a single group and is limited to command-line usage.

## Work to do
1. Implement web-based access, likely using JavaScript.
2. Local saving of message history.
3. Groups, credentials.
4. Browser extension.

## Licensing
GPLv3 applied.


