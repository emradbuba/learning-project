package com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.request;

import com.gitlab.emradbuba.learning.learningproject.demo.validation.custom.credit.ValidCreditCard;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PostCreateNewWalletDto {

    @NotBlank(message = "Wallet name cannot be blank")
    @Length(max = 20, message = "Wallet name cannot be longer than 20 characters")
    private String walletName;

    @Email // <-- optional, @Email accepts null value
    private String walletNotificationEmail;

    @UUID(message = "Wallet UUID number has to be a valid UUID formatted string")
    private String uuidNumber;

    // CreditCardInfo
    @NotEmpty // <-- in this case, not empty collection
    private List<@ValidCreditCard CreditCardDto> creditCards;

    // DrivingLicenseInfo
    @Valid // <-- propagation required
    private DrivingLicenseDto drivingLicenseDto;
}
