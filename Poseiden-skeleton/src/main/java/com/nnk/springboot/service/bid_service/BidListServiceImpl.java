package com.nnk.springboot.service.bid_service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.exception.ResourcesNotFoundException;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BidListServiceImpl: CRUD
 * @author Subhi
 */
@Service
@Transactional
public class BidListServiceImpl implements BidListService {
    private static final Logger logger = LoggerFactory.getLogger(BidListServiceImpl.class);
    private BidListRepository bidListRepository;
    private MapperService mapperService;

    public BidListServiceImpl(BidListRepository bidListRepository, MapperService mapperService) {
        this.bidListRepository = bidListRepository;
        this.mapperService = mapperService;
    }
    /**
     * Get the all BidList from DDB and transfer to BidListDTO
     * @return List of BidListDTO
     */
    @Override
    public List<BidListDTO> getBidList() {
        logger.debug("This getBidList(from BidListServiceImpl) starts here.");
        List<BidListDTO> bidListDTOs;
        List<BidList> bidLists = bidListRepository.findAll();

        if(bidLists.isEmpty()){
            logger.info("BidList is empty in DDB!(from getBidList, BidListServiceImpl).");
            return new ArrayList<>();
        }
        logger.info("BidList successfully loaded from DDB(from getBidList, BidListServiceImpl).");
        bidListDTOs = bidLists.stream().map(bidList ->
                mapperService.fromBidList(bidList)
        ).collect(Collectors.toList());
        return bidListDTOs;
    }

    /**
     * Save a new BidList via BidListDTO
     * @param bidListDTO BidListDTO
     * @return BidListDTO
     */
    @Override
    public BidListDTO saveNewBidList(BidListDTO bidListDTO) {
        logger.debug("This saveNewBidList(from BidListServiceImpl) starts here.");
        BidList bidList = mapperService.fromBidListDTO(bidListDTO);
        BidList savedBidList = bidListRepository.save(bidList);
        logger.info("New BidList successfully saved into DDB(from saveNewBidList, BidListServiceImpl).");
        BidListDTO returnBidListDTO = mapperService.fromBidList(savedBidList);
        return returnBidListDTO;
    }
    /**
     * Find BidList by its Id and return BidListDTO
     * @param id Integer
     * @return BidListDTO
     */
    @Override
    public BidListDTO getBidListById(Integer id) {
        logger.debug("This getBidListById(from BidListServiceImpl) starts here.");
        BidList getBidList = getBidListByBidListId(id);

        logger.info("BidList successfully found by its id: {} (from getBidListById, BidListServiceImpl).", id);
        return mapperService.fromBidList(getBidList);
    }

    /**
     * Update a BidList if its id exists in DDB
     * @param bidListDTO BidListDTO
     */

    @Override
    public void updateBidList(BidListDTO bidListDTO) {
        logger.debug("This updateBidList method(from BidListServiceImpl) starts here.");

      /*
        BidList bidList = getBidListByBidListId(bidListDTO.getBidId());
        bidList.setAccount(bidListDTO.getAccount());
        bidList.setType(bidListDTO.getType());
        bidList.setBidQuantity(bidListDTO.getBidQuantity());*/

        BidList updateBidList = mapperService.fromBidListDTO(bidListDTO);
        updateBidList.setBidListId(bidListDTO.getBidId());

        logger.info("BidList which id: {} successfully updated(from updateBidList, BidListServiceImpl).", bidListDTO.getBidId());
        bidListRepository.save(updateBidList);
    }
    /**
     * Delete a BidList existing in DDB by BidListId
     * @param id id
     */

    @Override
    public void deleteBidListById(Integer id) {
        logger.debug("This deleteBidListById(from BidListServiceImpl starts here.) ");
        BidList bidListById = getBidListByBidListId(id);
        if(bidListById != null) {
            logger.info("BidList which id: {} successfully deleted from DDB(from BidListServiceImpl)", id);
            bidListRepository.deleteById(id);
        }
    }

    /**
     * Get BidList from DDB with its Id and throw ResourceNotFoundException if BidList is not present.
     * @param id Integer
     * @return bidList BidList
     */
    private BidList getBidListByBidListId(Integer id) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(() -> {
            logger.error("This bidId: {} not found!", id);
            throw new ResourcesNotFoundException("This BidList doesn't exist with this id : " + id + " , from getBidListByBidListId, BidListServiceImpl.");
        });
        return bidList;
    }
}
