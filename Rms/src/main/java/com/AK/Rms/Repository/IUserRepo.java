package com.AK.Rms.Repository;

import com.AK.Rms.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepo extends JpaRepository<User,Long> {
    User findFirstByUserEmail(String newEmail);
}
