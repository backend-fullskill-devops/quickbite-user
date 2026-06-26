package com.quickbite.user_service.repositories;

import com.quickbite.user_service.models.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {
}
