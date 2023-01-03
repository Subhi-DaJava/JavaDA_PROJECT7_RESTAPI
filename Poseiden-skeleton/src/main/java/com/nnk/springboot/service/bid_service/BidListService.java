package com.nnk.springboot.service.bid_service;

import com.nnk.springboot.dto.BidListDTO;

import java.util.List;
/**
 * BidListService allows to insert the business logic
 * in the BidList business domain.
 * @author Subhi
 */

public interface BidListService {
    List<BidListDTO> getBidList();
    BidListDTO saveNewBidList(BidListDTO bidList);

    BidListDTO getBidListById(Integer id);

    void updateBidList(BidListDTO bidListDTO);

    void deleteBidListById(Integer id);

}
