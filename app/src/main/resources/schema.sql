DROP TABLE IF EXISTS node;
CREATE TABLE node (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    is_active INTEGER NOT NULL
);

DROP TABLE IF EXISTS message;
CREATE TABLE message (
    id INTEGER PRIMARY KEY,
    sender TEXT NOT NULL,
    send_time TIMESTAMP NOT NULL,
    content TEXT NOT NULL,
    receiver TEXT NOT NULL
);
