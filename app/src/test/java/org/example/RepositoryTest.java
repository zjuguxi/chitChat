package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.Node;
import org.example.repository.NodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Random;

@Disabled
@SpringBootTest
public class RepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryTest.class);

    private static final Random random = new Random();

    private static final Long id = random.nextLong();

    @Autowired
    private NodeRepository nodeRepository;

    @Test
    public void testCrud() {
        testInsert();
        testUpdate();
        testDelete();
    }

    private void testInsert() {

        // not existed
        boolean existed = nodeRepository.existsById(id);
        Assertions.assertFalse(existed);

        // insert
        Node node = Node.builder()
                .id(id)
                .name(RandomStringUtils.randomAlphabetic(10))
                .isActive(true)
                .build();
        nodeRepository.save(node);

        // existed
        existed = nodeRepository.existsById(id);
        Assertions.assertTrue(existed);
    }

    private void testUpdate() {

        // test query
        Optional<Node> nodeOptional = nodeRepository.findById(id);
        Assertions.assertTrue(nodeOptional.isPresent());
        Node node = nodeOptional.get();
        logger.info("{}", node);

        // update
        String newName = RandomStringUtils.randomAlphabetic(10);
        node.setName(newName);
        nodeRepository.save(node);

        // test query
        nodeOptional = nodeRepository.findById(id);
        Assertions.assertTrue(nodeOptional.isPresent());
        Assertions.assertEquals(nodeOptional.get().getName(), newName);
        logger.info("{}", nodeOptional.get());
    }

    private void testDelete() {

        // existed
        boolean existed = nodeRepository.existsById(id);
        Assertions.assertTrue(existed);

        // delete
        nodeRepository.deleteById(id);

        // not existed
        existed = nodeRepository.existsById(id);
        Assertions.assertFalse(existed);
    }
}
