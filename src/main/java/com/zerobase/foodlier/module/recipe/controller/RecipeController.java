package com.zerobase.foodlier.module.recipe.controller;

import com.zerobase.foodlier.common.security.provider.dto.MemberAuthDto;
import com.zerobase.foodlier.global.recipe.facade.RecipeFacade;
import com.zerobase.foodlier.module.recipe.domain.dto.RecipeDtoRequest;
import com.zerobase.foodlier.module.recipe.domain.dto.RecipeDtoResponse;
import com.zerobase.foodlier.module.recipe.domain.dto.RecipeImageResponse;
import com.zerobase.foodlier.module.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeFacade recipeFacade;
    private final RecipeService recipeService;

    @PostMapping("/image")
    public ResponseEntity<RecipeImageResponse> uploadRecipeImage(
            @RequestPart MultipartFile mainImage,
            @RequestPart List<MultipartFile> cookingOrderImageList) {
        return ResponseEntity.ok(recipeFacade.uploadRecipeImage(mainImage, cookingOrderImageList));
    }

    @PutMapping("/image/{recipeId}")
    public ResponseEntity<RecipeImageResponse> updateRecipeImage(
            @AuthenticationPrincipal MemberAuthDto memberAuthDto,
            @RequestPart MultipartFile mainImage,
            @RequestPart List<MultipartFile> cookingOrderImageList,
            @PathVariable(name = "recipeId") Long id) {
        return ResponseEntity.ok(recipeFacade.updateRecipeImage(memberAuthDto.getEmail(),
                mainImage, cookingOrderImageList, id));
    }

    @PostMapping
    public ResponseEntity<String> createRecipe(@AuthenticationPrincipal MemberAuthDto memberAuthDto,
                                               @RequestBody RecipeDtoRequest recipeDto) {
        recipeFacade.createRecipe(memberAuthDto.getEmail(), recipeDto);
        return ResponseEntity.ok("게시글 작성을 성공했습니다.");
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<String> updateRecipe(@AuthenticationPrincipal MemberAuthDto memberAuthDto,
                                               @RequestBody RecipeDtoRequest recipeDto,
                                               @PathVariable(name = "recipeId") Long id) {
        recipeFacade.updateRecipe(memberAuthDto.getEmail(), recipeDto, id);
        return ResponseEntity.ok("게시글 수정을 성공했습니다.");
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeDtoResponse> getRecipe(@PathVariable(name = "recipeId") Long id) {
        return ResponseEntity.ok(recipeService.getRecipeDetail(id));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@AuthenticationPrincipal MemberAuthDto memberAuthDto,
                                               @PathVariable(name = "recipeId") Long id) {
        recipeFacade.deleteRecipe(memberAuthDto.getEmail(), id);
        return ResponseEntity.ok("레시피 삭제를 성공했습니다.");
    }

    @GetMapping("/permission/{recipeId}")
    public ResponseEntity<String> checkPermission(@AuthenticationPrincipal MemberAuthDto memberAuthDto,
                                                  @PathVariable(name = "recipeId") Long id) {
        recipeFacade.checkPermission(memberAuthDto.getEmail(), id);
        return ResponseEntity.ok("레시피 접근 가능합니다.");
    }
}
