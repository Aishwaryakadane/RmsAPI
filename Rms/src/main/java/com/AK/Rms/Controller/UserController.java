package com.AK.Rms.Controller;

import com.AK.Rms.Model.Dto.SignInInputDto;
import com.AK.Rms.Model.Dto.SignUpOutputDto;
import com.AK.Rms.Model.User;
import com.AK.Rms.Model.commentDto.AddCommentDto;
import com.AK.Rms.Model.commentDto.GetCommentDto;
import com.AK.Rms.Model.postDto.AddPostDto;
import com.AK.Rms.Model.postDto.GetPostDto;
import com.AK.Rms.Model.postDto.PostUpdateDto;
import com.AK.Rms.Model.recipeDto.GetRecipeDto;
import com.AK.Rms.Model.recipeDto.RecipeDto;
import com.AK.Rms.Service.RecipeService;
import com.AK.Rms.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @Validated
    @RestController
    public class UserController {
        @Autowired
        UserService userService;

        @Autowired
        RecipeService recipeService;

        @PostMapping("user/signup")
        public SignUpOutputDto signUpUser(@RequestBody @Valid User user) {
            return userService.signUpUser(user);
        }

        @PostMapping("user/signIn")
        public String sigInUser(@RequestBody @Valid SignInInputDto signInInput) {
            return userService.signInUser(signInInput);
        }

        @PostMapping("recipe")
        public String addRecipe(@RequestParam @Valid String email, @RequestParam String token, @RequestBody RecipeDto recipeDto) {
            return userService.addRecipe(email, token, recipeDto);
        }

        @PostMapping("post/recipe")
        public String postRecipe(@RequestParam @Valid String email, @RequestParam String token, @RequestBody AddPostDto addPostDto) {
            return userService.postRecipe(email, token, addPostDto);
        }

        @PostMapping("comment/post/{postId}")
        public String postComment(@RequestParam @Valid String email, @RequestParam String token, @RequestBody AddCommentDto addCommentDto, @PathVariable Long postId) {
            return userService.postComment(email, token, addCommentDto, postId);
        }


        @PutMapping("recipe/{recipeId}")
        public String updateRecipe(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long recipeId, @RequestBody RecipeDto recipeDto) {
            return userService.updateRecipe(email, token, recipeId, recipeDto);
        }

        @PutMapping("post/{postId}")
        public String updatePostById(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long postId, @RequestBody PostUpdateDto postUpdateDto) {
            return userService.updatePostById(email, token, postId, postUpdateDto);
        }

        @GetMapping("my/posts")
        public ResponseEntity<List<GetPostDto>> allPost(@RequestParam @Valid String email, @RequestParam String token) {
            return userService.allPost(email, token);
        }

        @DeleteMapping("post/{postId}")
        public String deletePostById(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long postId) {
            return userService.deletePostById(email, token, postId);
        }

        @DeleteMapping("comment/{commentId}")
        public String deleteCommentById(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long commentId) {
            return userService.deleteCommentById(email, token, commentId);
        }

        @GetMapping("comments/post/{postId}")
        public ResponseEntity<List<GetCommentDto>> allCommentsByPostId(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long postId) {
            return userService.allCommentsByPostId(email, token, postId);
        }

        @GetMapping("All/posts")
        public ResponseEntity<List<GetPostDto>> getAllPosts(@RequestParam @Valid String email, @RequestParam String token) {
            return userService.getAllPosts(email, token);
        }

        @DeleteMapping("recipe/{recipeId}")
        public String deleteRecipeById(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long recipeId) {
            return userService.deleteRecipeById(email, token, recipeId);
        }

        @GetMapping("post/{postId}")
        public ResponseEntity<GetPostDto> getPostById(@RequestParam @Valid String email, @RequestParam String token, @PathVariable Long postId) {
            return userService.getPostById(email, token, postId);
        }

        @GetMapping("my/recipes")
        public ResponseEntity<List<GetRecipeDto>>allRecipes(@RequestParam @Valid String email, @RequestParam String token){
            return userService.allRecipes(email,token);
        }

        @DeleteMapping("user/signOut")
        public String sigOutUser(String email, String token) {
            return userService.sigOutUser(email, token);
        }


    }