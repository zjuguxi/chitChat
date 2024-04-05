package com.chitchat.persistence.repository;

import com.chitchat.persistence.entity.Node;
import org.springframework.data.repository.CrudRepository;

public interface NodeRepository extends CrudRepository<Node, Long> {
}
