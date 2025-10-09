package com.vicsergeev.SpringHW.repositories;

import com.vicsergeev.SpringHW.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
* Created by Victor 09.10.2025
*/

public interface UserRepository extends JpaRepository<User, Long> {
}
