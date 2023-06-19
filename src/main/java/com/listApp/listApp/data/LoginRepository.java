package com.listApp.listApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
//    Iterable<Login> findLoginByUserId(Long user_id);

}
