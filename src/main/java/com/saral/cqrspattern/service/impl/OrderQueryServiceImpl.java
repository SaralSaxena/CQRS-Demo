package com.saral.cqrspattern.service.impl;

import com.saral.cqrspattern.repository.PurchaseOrderSummaryRepository;
import com.saral.cqrspattern.dto.PurchaseOrderSummaryDto;
import com.saral.cqrspattern.entity.PurchaseOrderSummary;
import com.saral.cqrspattern.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    @Autowired
    private PurchaseOrderSummaryRepository purchaseOrderSummaryRepository;

    @Override
    public List<PurchaseOrderSummaryDto> getSaleSummaryGroupByState() {
        return this.purchaseOrderSummaryRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseOrderSummaryDto getSaleSummaryByState(String state) {
        return this.purchaseOrderSummaryRepository.findByState(state)
                        .map(this::entityToDto)
                        .orElseGet(() -> new PurchaseOrderSummaryDto(state, 0));
    }

    @Override
    public double getTotalSale() {
        return this.purchaseOrderSummaryRepository.findAll()
                        .stream()
                        .mapToDouble(PurchaseOrderSummary::getTotalSale)
                        .sum();
    }

    private PurchaseOrderSummaryDto entityToDto(PurchaseOrderSummary purchaseOrderSummary){
        PurchaseOrderSummaryDto dto = new PurchaseOrderSummaryDto();
        dto.setState(purchaseOrderSummary.getState());
        dto.setTotalSale(purchaseOrderSummary.getTotalSale());
        return dto;
    }
}
