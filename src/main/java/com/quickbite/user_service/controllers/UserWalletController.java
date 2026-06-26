package com.quickbite.user_service.controllers;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.quickbite.user_service.configs.principle.MyUserDetails;
import com.quickbite.user_service.models.dtos.ApiResponse;
import com.quickbite.user_service.models.dtos.UserWalletDto;
import com.quickbite.user_service.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserWalletController {
    private final UserService userService;

    @GetMapping("/wallet")
    public ResponseEntity<ApiResponse<UserWalletDto>> getWallet(@AuthenticationPrincipal MyUserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("Unauthorized");
        }
        return ResponseEntity.ok(ApiResponse.success(userService.getWallet(userDetails.getUser().getId())));
    }

    @PostMapping("/wallet/deposit")
    public ResponseEntity<ApiResponse<UserWalletDto>> deposit(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestParam BigDecimal amount) {
        if (userDetails == null) {
            throw new RuntimeException("Unauthorized");
        }
        return ResponseEntity.ok(ApiResponse.success(userService.depositMoney(userDetails.getUser().getId(), amount)));
    }

    @PostMapping("/wallet/withdraw")
    public ResponseEntity<ApiResponse<UserWalletDto>> withdraw(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestParam BigDecimal amount) {
        if (userDetails == null) {
            throw new RuntimeException("Unauthorized");
        }
        return ResponseEntity.ok(ApiResponse.success(userService.withdrawMoney(userDetails.getUser().getId(), amount)));
    }

    @PostMapping("/{id}/wallet/deduct")
    public ResponseEntity<ApiResponse<Void>> deduct(
            @PathVariable Long id,
            @RequestParam String transactionId,
            @RequestParam BigDecimal amount) {
        userService.deductWallet(id, transactionId, amount);
        return ResponseEntity.ok(ApiResponse.success("Money deducted successfully", null));
    }

    @PostMapping("/{id}/wallet/refund")
    public ResponseEntity<ApiResponse<Void>> refund(
            @PathVariable Long id,
            @RequestParam String transactionId,
            @RequestParam BigDecimal amount) {
        userService.refundWallet(id, transactionId, amount);
        return ResponseEntity.ok(ApiResponse.success("Money refunded successfully", null));
    }
}
