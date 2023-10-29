package com.AK.Rms.Repository;

import com.AK.Rms.Model.Recipe;
import com.AK.Rms.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRecipeRepo extends JpaRepository<Recipe,Long> {
    List<Recipe> findByUser(User user);
}
