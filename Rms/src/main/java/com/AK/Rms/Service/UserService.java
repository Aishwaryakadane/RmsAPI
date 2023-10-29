package com.AK.Rms.Service;

import com.AK.Rms.Model.*;
import com.AK.Rms.Model.Dto.SignInInputDto;
import com.AK.Rms.Model.Dto.SignUpOutputDto;
import com.AK.Rms.Model.commentDto.AddCommentDto;
import com.AK.Rms.Model.commentDto.GetCommentDto;
import com.AK.Rms.Model.postDto.AddPostDto;
import com.AK.Rms.Model.postDto.GetPostDto;
import com.AK.Rms.Model.postDto.PostUpdateDto;
import com.AK.Rms.Model.recipeDto.GetRecipeDto;
import com.AK.Rms.Model.recipeDto.RecipeDto;
import com.AK.Rms.Repository.IRecipeRepo;
import com.AK.Rms.Repository.IUserRepo;
import com.AK.Rms.Service.EmailUtility.EmailHandler;
import com.AK.Rms.Service.PasswordHashing.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RecipeService recipeService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @Autowired
    IRecipeRepo recipeRepo;

    public SignUpOutputDto signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if (newEmail == null) {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutputDto(signUpStatus, signUpStatusMessage);
        }

        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if (existingUser != null) {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutputDto(signUpStatus, signUpStatusMessage);
        }

        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(user.getUserPassword());

            user.setUserRegisteredTimeStamp(LocalDateTime.now());
            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutputDto(signUpStatus, "User registered successfully!!!");
        } catch (Exception e) {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutputDto(signUpStatus, signUpStatusMessage);
        }

    }

    public String signInUser(SignInInputDto signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if (signInEmail == null) {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if (existingUser == null) {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(signInInput.getPassword());
            if (existingUser.getUserPassword().equals(encryptedPassword)) {

                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.createToken(authToken);

                EmailHandler.sendEmail(signInEmail, "Identity Verification !!", authToken.getTokenValue());
                return "Token sent to your email";
            } else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        } catch (Exception e) {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public String addRecipe(String email, String token, RecipeDto recipeDto) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return recipeService.addRecipe(recipeDto,user);
        }
        return "invalid credentials";
    }

    public String postRecipe(String email, String token, AddPostDto addPostDto) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.postRecipe(addPostDto,user);
        }
        return "invalid credentials";
    }

    public String postComment(String email, String token, AddCommentDto addCommentDto, Long postId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            Post post =postService.findPostById(postId);
            if(post==null){
                return "invalid post Id ";
            }
            return commentService.postComment(addCommentDto,user, post);
        }
        return "invalid credentials";
    }


    public String updateRecipe(String email, String token, Long recipeId, RecipeDto recipeDto) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return recipeService.updateRecipe(user,recipeId,recipeDto);
        }
        return "Invalid credentials";
    }

    public ResponseEntity<List<GetPostDto>> allPost(String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.allPost(user);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public String updatePostById(String email, String token, Long postId, PostUpdateDto postUpdateDto) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.updatePostById(postId,postUpdateDto,user);
        }
        return "invalid credentials";
    }

    public String deletePostById(String email, String token, Long postId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.deletePostById(user,postId);
        }
        return "invalid credentials";
    }

    public String deleteCommentById(String email, String token, Long commentId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return commentService.deleteCommentById(user,commentId);
        }
        return "Invalid credentials";
    }

    public ResponseEntity<List<GetCommentDto>> allCommentsByPostId(String email, String token, Long postId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return commentService.allCommentsByPostId(user,postId);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public ResponseEntity<List<GetPostDto>> getAllPosts(String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return postService.getAllPosts();
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public String deleteRecipeById(String email, String token, Long recipeId) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return recipeService.deleteRecipeById(user,recipeId);
        }
        return "Invalid credentials";
    }

    public ResponseEntity<GetPostDto> getPostById(String email, String token, Long postId) {
        if(authenticationService.authenticate(email,token)){

            return postService.getPostById(postId);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public ResponseEntity<List<GetRecipeDto>> allRecipes(String email, String token) {
        if(authenticationService.authenticate(email,token)){
            User user =userRepo.findFirstByUserEmail(email);
            return recipeService.allRecipes(user);
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    public String sigOutUser(String email, String token) {
        if (authenticationService.authenticate(email, token)) {

            User user = userRepo.findFirstByUserEmail(email);
            authenticationService.authTokenRepo.delete(authenticationService.authTokenRepo.findFirstByUser(user));
            return " Signed out successfully";
        } else {
            return "Sign out not allowed for non authenticated user.";
        }
    }

}