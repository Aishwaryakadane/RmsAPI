package com.AK.Rms.Repository;

import com.AK.Rms.Model.Post;
import com.AK.Rms.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUser(User user);
}
