package com.example.mybatis;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;
import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureEmbeddedDatabase(provider = ZONKY, type = POSTGRES)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/schema.sql", "/data.sql"})
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findAll_returnsSeededRows() {
        List<User> users = userMapper.findAll();
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getName()).isEqualTo("Alice");
        assertThat(users.get(1).getName()).isEqualTo("Bob");
    }

    @Test
    void insert_generatesId_and_persistsRow() {
        User user = new User();
        user.setName("Carol");
        int inserted = userMapper.insert(user);
        assertThat(inserted).isEqualTo(1);
        assertThat(user.getId()).isNotNull();

        List<User> users = userMapper.findAll();
        assertThat(users).hasSize(3);
        assertThat(users.stream().anyMatch(u -> "Carol".equals(u.getName()))).isTrue();
    }

    @Test
    void findById_returnsSingleRow() {
        User alice = userMapper.findById(1L);
        assertThat(alice).isNotNull();
        assertThat(alice.getName()).isEqualTo("Alice");
    }
}
