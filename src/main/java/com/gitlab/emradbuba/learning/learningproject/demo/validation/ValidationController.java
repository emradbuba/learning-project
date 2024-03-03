package com.gitlab.emradbuba.learning.learningproject.demo.validation;

import com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.CreateWalletResponseDto;
import com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.request.PostCreateNewWalletDto;
import com.gitlab.emradbuba.learning.learningproject.demo.validation.service.Wallet;
import com.gitlab.emradbuba.learning.learningproject.demo.validation.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/demo/validation/wallet")
@RequiredArgsConstructor
@Validated
@Tag(name = "Validation demo", description = "Side controller only for testing the Spring validation and not being" +
        " a part of person CRUD functionality. There is a special error handler for validation related checks when " +
        "application is started with the 'validation' profile")
public class ValidationController {
    private final WalletService walletService;

    @PostMapping("/{contextId}/create")
    @Operation(summary = "Side demo feature! Creates a new wallet with DTO validation. Use with 'validation' spring " +
            "profile active", deprecated = true)
    public ResponseEntity<CreateWalletResponseDto> createNewWallet(
            @UUID @PathVariable("contextId") String contextId,
            @Valid @RequestBody PostCreateNewWalletDto postCreateNewWalletDto) {
        Wallet walletBusinessModel = walletService.saveWalletService(postCreateNewWalletDto);
        CreateWalletResponseDto responseDto = CreateWalletResponseDto.builder()
                .contextId("CTX_" + contextId)
                .walletName(walletBusinessModel.getWalletName())
                .walletSystemId(walletBusinessModel.getSystemId())
                .creditCardSystemId(walletBusinessModel.getCreditCardSystemIds())
                .drivingLicenseSystemId(walletBusinessModel.getDrivingLicenseSystemId())
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
