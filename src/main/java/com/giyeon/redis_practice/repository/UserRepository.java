package com.giyeon.redis_practice.repository;

import com.giyeon.redis_practice.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User find(Long id){
        return em.find(User.class, id);
    }

}
