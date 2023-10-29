package com.AK.Rms.Model.postDto;


import com.AK.Rms.Model.UserDto.GetUserDto;
import com.AK.Rms.Model.recipeDto.GetRecipeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetPostDto {
    private Long postId;
    private String postCaption;
    private LocalDateTime postCreationTimestamp;
    private LocalDateTime postUpdationTimeStamp;
    private GetUserDto postBy;
    private GetRecipeDto recipe;
}
