package com.zerobase.foodlier.module.recipe.service.quotation;

import com.zerobase.foodlier.common.response.ListResponse;
import com.zerobase.foodlier.module.member.member.domain.model.Member;
import com.zerobase.foodlier.module.member.member.exception.MemberException;
import com.zerobase.foodlier.module.member.member.repository.MemberRepository;
import com.zerobase.foodlier.module.recipe.domain.model.Recipe;
import com.zerobase.foodlier.module.recipe.domain.vo.RecipeDetail;
import com.zerobase.foodlier.module.recipe.domain.vo.RecipeStatistics;
import com.zerobase.foodlier.module.recipe.domain.vo.Summary;
import com.zerobase.foodlier.module.recipe.dto.quotation.QuotationDetailResponse;
import com.zerobase.foodlier.module.recipe.dto.quotation.QuotationDtoRequest;
import com.zerobase.foodlier.module.recipe.dto.quotation.QuotationTopResponse;
import com.zerobase.foodlier.module.recipe.dto.recipe.RecipeDetailDto;
import com.zerobase.foodlier.module.recipe.dto.recipe.RecipeDtoRequest;
import com.zerobase.foodlier.module.recipe.dto.recipe.RecipeIngredientDto;
import com.zerobase.foodlier.module.recipe.exception.quotation.QuotationException;
import com.zerobase.foodlier.module.recipe.repository.RecipeRepository;
import com.zerobase.foodlier.module.request.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.zerobase.foodlier.module.member.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.zerobase.foodlier.module.recipe.exception.quotation.QuotationErrorCode.*;

@Service
@RequiredArgsConstructor
public class QuotationService {

    private final RecipeRepository recipeRepository;
    private final RequestRepository requestRepository;
    private final MemberRepository memberRepository;

    /**
     * 작성자 : 전현서
     * 작성일 : 2023-10-02
     * 견적서를 새로 생성합니다. (이미지 없는 레시피 생성)
     */
    public void createQuotation(Long memberId, QuotationDtoRequest request) {
        Member member = getMember(memberId);

        Recipe quotation = Recipe.builder()
                .summary(Summary.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .build())
                .expectedTime(request.getExpectedTime())
                .recipeStatistics(new RecipeStatistics())
                .difficulty(request.getDifficulty())
                .isPublic(false)
                .member(member)
                .recipeIngredientList(request.getRecipeIngredientDtoList()
                        .stream()
                        .map(RecipeIngredientDto::toEntity)
                        .collect(Collectors.toList()))
                .recipeDetailList(request.getRecipeDetailDtoList()
                        .stream()
                        .map(x -> new RecipeDetail(x, null))
                        .collect(Collectors.toList()))
                .isQuotation(true) //견적서 여부 true
                .build();
        recipeRepository.save(quotation);
    }

    /**
     * 작성자 : 전현서
     * 작성일 : 2023-10-02
     * 본인이 전송 가능한, 견적서 목록을 조회합니다. (냉장고를 부탁해에서 호출되는 목록)
     */
    public ListResponse<QuotationTopResponse> getQuotationListForRefrigerator(Long memberId, Pageable pageable) {
        return ListResponse.from(
                recipeRepository.findQuotationListForRefrigerator(memberId, pageable));
    }

    /**
     * 작성자 : 전현서
     * 작성일 : 2023-10-02
     * 결제까지 완료된 견적서 목록을 조회합니다. (레시피 작성시에, 레시피로 변환할 견적서 조회)
     */
    public ListResponse<QuotationTopResponse> getQuotationListForRecipe(Long memberId, Pageable pageable) {
        return ListResponse.from(
                recipeRepository.findQuotationListForRecipe(memberId, pageable));
    }

    /**
     * 작성자 : 전현서
     * 작성일 : 2023-10-02
     * 본인이 작성한 견적서 상세정보를 반환합니다.
     * <p>
     * 본인 뿐만 아니라, 견적서를 받은 상대방도 볼 수 있는지 확인이 필요함.
     */
    public QuotationDetailResponse getQuotationDetail(Long memberId, Long quotationId) {
        if (!recipeRepository.existsByIdAndMemberForQuotation(memberId, quotationId)) {
            throw new QuotationException(HAS_NOT_QUOTATION_READ_PERMISSION);
        }
        return QuotationDetailResponse.fromEntity(recipeRepository.findById(quotationId)
                .orElseThrow(() -> new QuotationException(QUOTATION_NOT_FOUND)));
    }

    /**
     * 작성자 : 전현서
     * 작성일 : 2023-10-02
     * 견적서에 사진을 담아서, 레시피를 완성시켜, 변환합니다.
     */
    public void convertToRecipe(Long memberId, Long quotationId, RecipeDtoRequest request) {
        Member member = getMember(memberId);
        Recipe quotation = recipeRepository.findByIdAndMemberAndIsQuotationTrue(quotationId, member)
                .orElseThrow(() -> new QuotationException(QUOTATION_NOT_FOUND));

        validateConvertToRecipe(quotation);

        quotation.updateSummary(Summary.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build());
        quotation.updateMainImageUrl(request.getMainImageUrl());
        quotation.updateExpectedTime(request.getExpectedTime());
        quotation.updateDifficulty(request.getDifficulty());
        quotation.updateRecipeDetailList(request.getRecipeDetailDtoList()
                .stream()
                .map(RecipeDetailDto::toEntity)
                .collect(Collectors.toList()));
        quotation.updateRecipeIngredientList(request.getRecipeIngredientDtoList()
                .stream()
                .map(RecipeIngredientDto::toEntity)
                .collect(Collectors.toList()));
        quotation.updatePublic();
        quotation.updateNoQuotation(); //recipe 변환

        recipeRepository.save(quotation);
    }

    /**
     * 작성자 : 전현서
     * 작성일 : 2023-10-02
     * 견적서를 수정합니다.
     */
    public void updateQuotation(Long memberId, Long quotationId, QuotationDtoRequest request) {
        Member member = getMember(memberId);
        Recipe recipe = recipeRepository.findByIdAndMemberAndIsQuotationTrue(quotationId, member)
                .orElseThrow(() -> new QuotationException(QUOTATION_NOT_FOUND));

        recipe.updateSummary(Summary.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build());
        recipe.updateExpectedTime(request.getExpectedTime());
        recipe.updateDifficulty(request.getDifficulty());

        recipe.updateRecipeDetailList(request.getRecipeDetailDtoList()
                .stream()
                .map(x -> new RecipeDetail(x, null))
                .collect(Collectors.toList()));

        recipe.updateRecipeIngredientList(request.getRecipeIngredientDtoList()
                .stream()
                .map(RecipeIngredientDto::toEntity)
                .collect(Collectors.toList()));

        recipeRepository.save(recipe);
    }

    /**
     * 작성자 : 전현서
     * 작성일 : 2023-10-02
     * 본인의 견적서를 삭제합니다. 단, 요청 중인 견적서, 성사된 견적서는 삭제 불가능
     */
    public void deleteQuotation(Long memberId, Long quotationId) {
        validateDeleteQuotation(memberId, quotationId);
        Recipe quotation = recipeRepository.findByIdAndMemberAndIsQuotationTrue(quotationId, getMember(memberId))
                .orElseThrow(() -> new QuotationException(QUOTATION_NOT_FOUND));
        recipeRepository.delete(quotation);
    }

    /**
     * 작성자 : 전현서
     * 작성일 : 2023-10-02
     * 견적서를 보내기 위해서, 견적서 데이터를 검증하고, 이를 반환함.
     */
    public Recipe getQuotationForSend(Long memberId, Long quotationId) {
        Member member = getMember(memberId);
        Recipe quotation = recipeRepository.findByIdAndMemberAndIsQuotationTrue(quotationId, member)
                .orElseThrow(() -> new QuotationException(QUOTATION_NOT_FOUND));

        validateGetQuotationForSend(quotation);

        return quotation;
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }

    //======================== Validates ============================
    private void validateGetQuotationForSend(Recipe quotation) {
        if (requestRepository.existsByRecipe(quotation)) {
            throw new QuotationException(ALREADY_SENT_QUOTATION);
        }
    }

    private void validateDeleteQuotation(Long memberId, Long quotationId) {
        if (recipeRepository.isNotAbleToDeleteForQuotation(memberId, quotationId)) {
            throw new QuotationException(CANNOT_DELETE_IS_LOCKED);
        }
    }

    private void validateConvertToRecipe(Recipe quotation) {
        if (!recipeRepository.isAbleToConvert(quotation)) {
            throw new QuotationException(CANNOT_CONVERT_TO_RECIPE);
        }
    }
}
