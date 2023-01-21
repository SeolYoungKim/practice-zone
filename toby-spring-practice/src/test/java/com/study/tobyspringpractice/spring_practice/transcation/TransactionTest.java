package com.study.tobyspringpractice.spring_practice.transcation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class TransactionTest {
    @Autowired
    DataSourceProperties properties;

    @Autowired
    DataSource dataSource;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    TransactionDefinition defaultTxDefinition;

    TransactionDefinition requiresNewTxDefinition = new TransactionDefinition() {
        @Override
        public int getPropagationBehavior() {
            return PROPAGATION_REQUIRES_NEW;
        }
    };

    @DisplayName("트랜잭션 동기화 매니저를 이용하면, 커넥션을 여러번 꺼내와도 같은 커넥션이다.")
    @Test
    void test() {
        TransactionSynchronizationManager.initSynchronization();
        Set<Connection> connections = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            connections.add(DataSourceUtils.getConnection(dataSource));
        }

        assertThat(connections).hasSize(1);
    }

    @DisplayName("새로 생성한 트랜잭션의 isNewTreansaction은 true, isRollbackOnly는 false이다.")
    @Test
    void test1() {
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTxDefinition);
        assertThat(transactionStatus.isNewTransaction()).isTrue();
        assertThat(transactionStatus.isRollbackOnly()).isFalse();
    }

    @DisplayName("트랜잭션을 commit 하면 isCompleted가 false에서 true로 된다.")
    @Test
    void test2() {
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTxDefinition);
        assertThat(transactionStatus.isCompleted()).isFalse();

        transactionManager.commit(transactionStatus);
        assertThat(transactionStatus.isCompleted()).isTrue();
    }

    @DisplayName("트랜잭션을 rollback 하면 isCompleted가 false에서 true로 된다.")
    @Test
    void test3() {
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTxDefinition);
        assertThat(transactionStatus.isCompleted()).isFalse();

        transactionManager.rollback(transactionStatus);
        assertThat(transactionStatus.isCompleted()).isTrue();
    }

    @DisplayName("트랜잭션을 rollbackOnly로 표기하면 commit을 해도 rollback이 된다. (로그 확인)")
    @Test
    void test4() {
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTxDefinition);
        transactionStatus.setRollbackOnly();

        assertThat(transactionStatus.isRollbackOnly()).isTrue();
        transactionManager.commit(transactionStatus);
    }

    @DisplayName("트랜잭션을 연속으로 두개 얻으면 두 번째 트랜잭션은 isNewTransaction이 false다.")
    @Test
    void test5() {
        TransactionStatus tx1 = transactionManager.getTransaction(defaultTxDefinition);
        TransactionStatus tx2 = transactionManager.getTransaction(defaultTxDefinition);

        assertThat(tx1.isNewTransaction()).isTrue();
        assertThat(tx2.isNewTransaction()).isFalse();
    }

    @DisplayName("연속된 트랜잭션을 모두 커밋하면 아무런 문제가 발생하지 않는다.")
    @Test
    void test6() {
        TransactionStatus tx1 = transactionManager.getTransaction(defaultTxDefinition);
        TransactionStatus tx2 = transactionManager.getTransaction(defaultTxDefinition);

        transactionManager.commit(tx2);
        transactionManager.commit(tx1);
    }

    @DisplayName("두 번째 트랜잭션이 롤백되고 첫 번째 트랜잭션을 커밋하면 예외가 발생한다.")
    @Test
    void test7() {
        TransactionStatus tx1 = transactionManager.getTransaction(defaultTxDefinition);
        TransactionStatus tx2 = transactionManager.getTransaction(defaultTxDefinition);

        transactionManager.rollback(tx2);
        assertThatThrownBy(() -> transactionManager.commit(tx1))
                .isInstanceOf(UnexpectedRollbackException.class)
                .hasMessageContaining(
                        "Transaction silently rolled back because it has been marked as rollback-only");
    }

    @DisplayName("두 번째 트랜잭션이 롤백되고 첫 번째 트랜잭션을 롤백하면 문제가 발생하지 않는다.")
    @Test
    void test8() {
        TransactionStatus tx1 = transactionManager.getTransaction(defaultTxDefinition);
        TransactionStatus tx2 = transactionManager.getTransaction(defaultTxDefinition);

        transactionManager.rollback(tx2);
        transactionManager.rollback(tx1);
    }

    @DisplayName("두 번째 트랜잭션이 커밋되고 첫 번째 트랜잭션을 롤백하면 문제가 발생하지 않는다. (최종:롤백)")
    @Test
    void test9() {
        TransactionStatus tx1 = transactionManager.getTransaction(defaultTxDefinition);
        TransactionStatus tx2 = transactionManager.getTransaction(defaultTxDefinition);

        transactionManager.commit(tx2);
        transactionManager.rollback(tx1);
    }

    @DisplayName("트랜잭션 전파 옵션이 RequiresNew인 경우에는 두번쨰로 생성되어도 isNewTransaction 옵션이 true이다.")
    @Test
    void test10() {
        TransactionStatus tx1 = transactionManager.getTransaction(defaultTxDefinition);
        TransactionStatus tx2 = transactionManager.getTransaction(requiresNewTxDefinition);

        assertThat(tx1.isNewTransaction()).isTrue();
        assertThat(tx2.isNewTransaction()).isTrue();
    }
    
    @DisplayName("트랜잭션 전파 옵션이 RequiresNew인 경우에는 두번째 트랜잭션이 롤백되어도 첫번째 트랜잭션이 독립적으로 커밋된다.")
    @Test
    void test11() {
        TransactionStatus tx1 = transactionManager.getTransaction(defaultTxDefinition);
        TransactionStatus tx2 = transactionManager.getTransaction(requiresNewTxDefinition);

        transactionManager.rollback(tx2);
        transactionManager.commit(tx1);
    }
}
