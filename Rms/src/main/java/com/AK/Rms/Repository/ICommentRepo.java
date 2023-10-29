package com.AK.Rms.Repository;

import com.AK.Rms.Model.Comment;
import com.AK.Rms.Model.Post;
import com.AK.Rms.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByCommenter(User commenter);
}
