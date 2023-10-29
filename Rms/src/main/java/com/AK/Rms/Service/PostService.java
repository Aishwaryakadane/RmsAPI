package com.AK.Rms.Service;
import com.AK.Rms.Model.Post;
import com.AK.Rms.Model.Recipe;
import com.AK.Rms.Model.User;
import com.AK.Rms.Model.UserDto.GetUserDto;
import com.AK.Rms.Model.postDto.AddPostDto;
import com.AK.Rms.Model.postDto.GetPostDto;
import com.AK.Rms.Model.postDto.PostUpdateDto;
import com.AK.Rms.Model.recipeDto.GetRecipeDto;
import com.AK.Rms.Repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    IPostRepo postRepo;

    @Autowired
    RecipeService recipeService;

    public String postRecipe(AddPostDto addPostDto, User user) {
        Post post =new Post();
        Long recipeId = addPostDto.getRecipeId();
        Recipe recipe = recipeService.findById(recipeId);
        if(recipe==null){
            return "add valid recipe";
        }
        User repUser =recipe.getUser();
        if(!repUser.equals(user)){
            return "add valid recipe";
        }
        post.setPostCaption(addPostDto.getPostCaption());
        post.setRecipe(recipe);
        post.setUser(user);
        post.setPostCreationTimestamp(LocalDateTime.now());
        postRepo.save(post);
        return "post uploaded ..";
    }

    public Post findPostById(Long postId) {
        return postRepo.findById(postId).orElse(null);
    }

    public ResponseEntity<List<GetPostDto>> allPost(User user) {
        List<Post>postList =postRepo.findByUser(user);
        List<GetPostDto>postDtoList = new ArrayList<>();
        for (Post post:postList){
            GetPostDto getPostDto =new GetPostDto(post.getPostId(),post.getPostCaption(),post.getPostCreationTimestamp(),
                    post.getPostUpdationTimeStamp(),new GetUserDto(post.getUser().getUserId(),post.getUser().getUserName()),
                    new GetRecipeDto(post.getRecipe().getRecipeId(),post.getRecipe().getRecipeName(),post.getRecipe().getRecipeIngredients(),
                            post.getRecipe().getRecipeInstructions()));
            postDtoList.add(getPostDto);
        }
        return ResponseEntity.ok(postDtoList);
    }

    public String updatePostById(Long postId, PostUpdateDto postUpdateDto, User user) {
        Post post = postRepo.findById(postId).orElse(null);
        if(post==null || !post.getUser().equals(user)){
            return "invalid post id ..";
        }
        Recipe recipe = recipeService.findById(postUpdateDto.getRecipeId());

        if(recipe==null || !recipe.getUser().equals(user)){
            return "invalid recipe Id..";
        }
        post.setPostCaption(postUpdateDto.getPostCaption());
        post.setRecipe(recipe);
        post.setPostUpdationTimeStamp(LocalDateTime.now());
        postRepo.save(post);
        return "post updated..";
    }

    public String deletePostById(User user, Long postId) {
        Post post = postRepo.findById(postId).orElse(null);
        if(post==null || !post.getUser().equals(user)){
            return "invalid post id ..";
        }
        postRepo.deleteById(postId);
        return "post deleted ..";
    }

    public ResponseEntity<List<GetPostDto>> getAllPosts() {
        List<Post>postList =postRepo.findAll();
        List<GetPostDto>postDtoList = new ArrayList<>();
        for (Post post:postList){
            GetPostDto getPostDto =new GetPostDto(post.getPostId(),post.getPostCaption(),post.getPostCreationTimestamp(),
                    post.getPostUpdationTimeStamp(),new GetUserDto(post.getUser().getUserId(),post.getUser().getUserName()),
                    new GetRecipeDto(post.getRecipe().getRecipeId(),post.getRecipe().getRecipeName(),post.getRecipe().getRecipeIngredients(),
                            post.getRecipe().getRecipeInstructions()));
            postDtoList.add(getPostDto);
        }
        return ResponseEntity.ok(postDtoList);
    }

    public ResponseEntity<GetPostDto> getPostById(Long postId) {
        Post post =postRepo.findById(postId).orElse(null);
        if(post==null){
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }
        GetPostDto postDto =new GetPostDto(post.getPostId(),post.getPostCaption(),post.getPostCreationTimestamp(),
                post.getPostUpdationTimeStamp(),new GetUserDto(post.getUser().getUserId(),post.getUser().getUserName()),
                new GetRecipeDto(post.getRecipe().getRecipeId(),post.getRecipe().getRecipeName(),post.getRecipe().getRecipeIngredients(),
                        post.getRecipe().getRecipeInstructions()));
        return ResponseEntity.ok(postDto);
    }

    public void deleteAllPostByUser(User user) {
        List<Post>postList =postRepo.findByUser(user);
        List<Long>postIds =new ArrayList<>();
        for(Post post :postList){
            postIds.add(post.getPostId());
        }
        postRepo.deleteAllById(postIds);
    }


}