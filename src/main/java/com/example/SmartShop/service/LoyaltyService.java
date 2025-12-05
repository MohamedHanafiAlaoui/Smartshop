package com.example.SmartShop.service;

import com.example.SmartShop.model.entitie.Client;
import com.example.SmartShop.model.enums.CustomerTier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoyaltyService {

    public void updateLoyaltyLevel(Client client) {
        int totalOrders = client.getTotalOrders();
        BigDecimal totalSpent = client.getTotalSpent();

        if (totalOrders == 0) {
            client.setTier(CustomerTier.BASIC);
            return;
        }

        if (totalOrders >= 20 || totalSpent.compareTo(BigDecimal.valueOf(15000)) >= 0) {
            client.setTier(CustomerTier.PLATINUM);
            return;
        }

        if (totalOrders >= 10 || totalSpent.compareTo(BigDecimal.valueOf(5000)) >= 0) {
            client.setTier(CustomerTier.GOLD);
            return;
        }

        if (totalOrders >= 3 || totalSpent.compareTo(BigDecimal.valueOf(1000)) >= 0) {
            client.setTier(CustomerTier.SILVER);
            return;
        }

        client.setTier(CustomerTier.BASIC);
    }

    public BigDecimal getFidelityDiscount(Client client, BigDecimal sousTotal) {
        CustomerTier tier = client.getTier();

        switch (tier) {
            case SILVER:
                if (sousTotal.compareTo(BigDecimal.valueOf(500)) >= 0) {
                    return BigDecimal.valueOf(5);
                }
                break;

            case GOLD:
                if (sousTotal.compareTo(BigDecimal.valueOf(800)) >= 0) {
                    return BigDecimal.valueOf(10);
                }
                break;

            case PLATINUM:
                if (sousTotal.compareTo(BigDecimal.valueOf(1200)) >= 0) {
                    return BigDecimal.valueOf(15);
                }
                break;

            default:
                return BigDecimal.ZERO;
        }

        return BigDecimal.ZERO;
    }

    public BigDecimal calculateDiscountAmount(BigDecimal sousTotal, BigDecimal percent) {
        if (percent == null || percent.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return sousTotal.multiply(percent).divide(BigDecimal.valueOf(100));
    }
}
