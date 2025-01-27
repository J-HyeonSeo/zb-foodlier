package com.zerobase.foodlier.global.recipe.facade;

import com.zerobase.foodlier.common.s3.service.S3Service;
import com.zerobase.foodlier.module.member.member.domain.model.Member;
import com.zerobase.foodlier.module.member.member.service.MemberService;
import com.zerobase.foodlier.module.recipe.domain.model.Recipe;
import com.zerobase.foodlier.module.recipe.dto.recipe.ImageUrlDto;
import com.zerobase.foodlier.module.recipe.dto.recipe.RecipeDtoRequest;
import com.zerobase.foodlier.module.recipe.dto.recipe.RecipeImageResponse;
import com.zerobase.foodlier.module.recipe.exception.recipe.RecipeException;
import com.zerobase.foodlier.module.recipe.service.recipe.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.zerobase.foodlier.module.recipe.exception.recipe.RecipeErrorCode.NO_PERMISSION;

@Component
@RequiredArgsConstructor
public class RecipeFacade {
    private final RecipeService recipeService;
    private final MemberService memberService;
    private final S3Service s3Service;

    /**
     * 2023-09-25
     * 황태원
     * 꿀조합 게시글 등록 시 사진을 s3에 등록 후 url을 return
     */
    public RecipeImageResponse uploadRecipeImage(
            MultipartFile mainImage,
            List<MultipartFile> cookingOrderImageList) {
        return RecipeImageResponse.builder()
                .mainImage(s3Service.getImageUrl(mainImage))
                .cookingOrderImageList(s3Service
                        .getImageUrlList(cookingOrderImageList))
                .build();
    }

    /**
     * 2023-09-26
     * 황태원
     * 꿀조합 게시글 수정 시 사진을 s3에 등록 후 url을 return
     */
    public RecipeImageResponse updateRecipeImage(
            Long id,
            MultipartFile mainImage,
            List<MultipartFile> cookingOrderImageList, Long recipeId) {
        checkPermission(id, recipeId);

        return RecipeImageResponse.builder()
                .mainImage(s3Service.getImageUrl(mainImage))
                .cookingOrderImageList(s3Service
                        .getImageUrlList(cookingOrderImageList))
                .build();
    }

    /**
     * 2023-09-25
     * 황태원
     * 꿀조합 게시글 작성
     */
    public void createRecipe(Long id, RecipeDtoRequest recipeDtoRequest) {
        Member member = memberService.findById(id);

        recipeService.createRecipe(member, recipeDtoRequest);
    }

    /**
     * 2023-09-26
     * 황태원
     * 꿀조합 게시글 수정
     */
    @Transactional
    public void updateRecipe(Long id, RecipeDtoRequest recipeDtoRequest, Long recipeId) {
        checkPermission(id, recipeId);
        ImageUrlDto imageUrlDto = recipeService.getBeforeImageUrl(recipeId);
        recipeService.updateRecipe(recipeDtoRequest, recipeId);
        deleteRecipeImage(imageUrlDto);
    }

    /**
     * 2023-09-26
     * 황태원
     * 꿀조합 게시글 삭제, 삭제 후 s3이미지 삭제
     */
    @Transactional
    public void deleteRecipe(Long id, Long recipeId) {
        checkPermission(id, recipeId);
        ImageUrlDto imageUrlDto = recipeService.getBeforeImageUrl(recipeId);
        recipeService.deleteRecipe(recipeId);
        deleteRecipeImage(imageUrlDto);
    }

    /**
     * 2023-09-26
     * 황태원
     * 꿀조합 게시글 삭제 및 수정 후 s3이미지 삭제
     */
    private void deleteRecipeImage(ImageUrlDto imageUrlDto) {
        s3Service.deleteImage(imageUrlDto.getMainImageUrl());
        imageUrlDto.getCookingOrderImageUrlList().forEach(s3Service::deleteImage);
    }

    /**
     * 2023-09-26
     * 황태원
     * 꿀조합 게시글 수정, 삭제권한 체크
     */
    public void checkPermission(Long id, Long recipeId) {
        Member member = memberService.findById(id);
        Recipe recipe = recipeService.getRecipe(recipeId);

        if (!member.getId().equals(recipe.getMember().getId())) {
            throw new RecipeException(NO_PERMISSION);
        }
    }

}
