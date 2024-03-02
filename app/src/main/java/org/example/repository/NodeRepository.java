package org.example.repository;

import org.example.model.Node;
import org.springframework.data.repository.CrudRepository;

public interface NodeRepository extends CrudRepository<Node, Long> {
}
