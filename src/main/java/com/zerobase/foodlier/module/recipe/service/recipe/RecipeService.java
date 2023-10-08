package com.zerobase.foodlier.module.recipe.service.recipe;

import com.zerobase.foodlier.module.member.member.domain.model.Member;
import com.zerobase.foodlier.module.recipe.domain.model.Recipe;
import com.zerobase.foodlier.module.recipe.dto.recipe.ImageUrlDto;
import com.zerobase.foodlier.module.recipe.dto.recipe.RecipeDtoRequest;
import com.zerobase.foodlier.module.recipe.dto.recipe.RecipeDtoResponse;
import com.zerobase.foodlier.module.recipe.dto.recipe.RecipeDtoTopResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeService {
    void createRecipe(Member member, RecipeDtoRequest recipeDtoRequest);

    void updateRecipe(RecipeDtoRequest recipeDtoRequest, Long id);

    Recipe getRecipe(Long id);

    RecipeDtoResponse getRecipeDetail(Long id);

    void deleteRecipe(Long id);

    List<Recipe> getRecipeByTitle(String recipeTitle, Pageable pageable);

    ImageUrlDto getBeforeImageUrl(Long id);
    List<RecipeDtoTopResponse> getRecipeForHeart(Long memberId);
    List<RecipeDtoTopResponse> getRecipeListByMemberId(Long memberId,
                                                       Long targetMemberId,
                                                       Pageable pageable);

    void plusReviewStar(Long recipeId, int star);
    void updateReviewStar(Long recipeId, int originStar, int newStar);
    void minusReviewStar(Long recipeId, int star);
}