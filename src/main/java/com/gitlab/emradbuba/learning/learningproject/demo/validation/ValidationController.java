package com.gitlab.emradbuba.learning.learningproject.demo.validation;

import com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.CreateWalletResponseDto;
import com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.request.PostCreateNewWalletDto;
import com.gitlab.emradbuba.learning.learningproject.demo.validation.service.Wallet;
import com.gitlab.emradbuba.learning.learningproject.demo.validation.service.WalletService;
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
public class ValidationController {
    private final WalletService walletService;

    @PostMapping("/{contextId}/create")
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
