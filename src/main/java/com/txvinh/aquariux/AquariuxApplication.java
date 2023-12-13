package com.txvinh.aquariux;

import com.txvinh.aquariux.constant.Crypto;
import com.txvinh.aquariux.domain.CryptoWallet;
import com.txvinh.aquariux.domain.User;
import com.txvinh.aquariux.service.CryptoWalletService;
import com.txvinh.aquariux.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@SpringBootApplication
public class AquariuxApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(AquariuxApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService, CryptoWalletService cryptoWalletService) {
        return args -> {
            User user = User.builder()
                    .email("vinhit213@gmail.com")
                    .username("txvinh")
                    .password("FsfcQHFdcU454TB")
                    .phoneNumber("0973123123")
                    .build();
            User newUser = userService.createUser(user);
            CryptoWallet cryptoWallet = CryptoWallet.builder()
                    .amount(BigDecimal.valueOf(50000))
                    .type(Crypto.USDT)
                    .userId(newUser.getId())
                    .build();
            cryptoWalletService.create(cryptoWallet);
        };
    }

}
