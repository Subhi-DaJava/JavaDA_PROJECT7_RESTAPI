package com.nnk.springboot.mapper;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.dto.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MapperService {
    /**
     * Transfer the properties of User form DDB to UserGetDTO sauf Password
     * @param user Use
     * @return UserGetDTO
     */
    public UserGetDTO fromUser (User user) {
        /*     UserGetDTO userGetDTO_Manuel = new UserGetDTO();
        userGetDTO_Manuel.setId(user.getId());
        userGetDTO_Manuel.setFullname(user.getFullname());
        userGetDTO_Manuel.setUsername(user.getUsername());
        userGetDTO_Manuel.setRole(user.getRole());*/

        UserGetDTO userGetDTO = new UserGetDTO();
        BeanUtils.copyProperties (user, userGetDTO); // (source, target)
        return userGetDTO;
    }
    /**
     * Transfer the properties of UserSaveDTO to User sauf Id
     * @param userSaveDTO UserSaveDTO
     * @return user User
     */
    public User fromUserSaveDTO (UserSaveDTO userSaveDTO) {
        User user = new User();
        BeanUtils.copyProperties (userSaveDTO, user); // (source, target)
        return user;
    }
    /**
     * Transfer the properties of BidListDTO to BidList's some properties
     * @param bidListDTO BidListDTO
     * @return bidList BidList
     */
    public BidList fromBidListDTO (BidListDTO bidListDTO) {
        BidList bidList = new BidList();
        BeanUtils.copyProperties (bidListDTO, bidList); // (source, target)
        return bidList;
    }

    public List<BidList> fromBidListDTOs (List<BidListDTO> bidListDTOList) {
        List<BidList> bidLists = bidListDTOList.stream().map(this::fromBidListDTO).collect(Collectors.toList());
        return bidLists;
    }

    public List<BidListDTO> fromBidLists(List<BidList> bidLists) {
        List<BidListDTO> bidListDTOList = bidLists.stream().map(this::fromBidList).collect(Collectors.toList());
        return bidListDTOList;
    }

    /**
     * Transfer the properties of BidList from DDB to BidListDTO's properties
     * @param bidList BidList
     * @return bidListDTO BidListDTO
     */
    public BidListDTO fromBidList (BidList bidList) {
        BidListDTO bidListDTO = new BidListDTO();
        BeanUtils.copyProperties (bidList, bidListDTO);// (source, target)
        bidListDTO.setBidId(bidList.getBidListId());
        return bidListDTO;
    }
    /**
     * Transfer the properties of CurvePointDTO to CurvePoint's some properties
     * @param curvePointDTO CurvePointDTO
     * @return curvePoint CurvePoint
     */
    public CurvePoint fromCurvePointDTO (CurvePointDTO curvePointDTO) {
        CurvePoint curvePoint = new CurvePoint();
        BeanUtils.copyProperties (curvePointDTO, curvePoint); // (source, target)
        return curvePoint;
    }
    /**
     * Transfer the properties of CurvePoint from DDB to CurvePoint's properties
     * @param curvePoint CurvePoint
     * @return curvePointDTO CurvePointDTO
     */
    public CurvePointDTO fromCurvePoint (CurvePoint curvePoint) {
        CurvePointDTO curvePointDTO = new CurvePointDTO();
        BeanUtils.copyProperties(curvePoint, curvePointDTO); // (source, target)
        return curvePointDTO;
    }
    /**
     * Transfer the properties of RatingDTO to Rating's some properties
     * @param ratingDTO RatingDTO
     * @return rating Rating
     */
    public Rating fromRatingDTO (RatingDTO ratingDTO) {
        Rating rating = new Rating();
        BeanUtils.copyProperties (ratingDTO, rating); // (source, target)
        return rating;
    }
    /**
     * Transfer the properties of Rating from DDB to RatingDTO's properties
     * @param rating Rating
     * @return ratingDTO
     */
    public RatingDTO fromRating (Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        BeanUtils.copyProperties(rating, ratingDTO); // (source, target)
        return ratingDTO;
    }
    /**
     * Transfer the properties of RuleNameDTO to RuleName's some properties
     * @param ruleNameDTO RuleNameDTO
     * @return ruleName RuleName
     */
    public RuleName fromRuleNameDTO (RuleNameDTO ruleNameDTO) {
        RuleName ruleName = new RuleName();
        BeanUtils.copyProperties (ruleNameDTO, ruleName); // (source, target)
        return ruleName;
    }
    /**
     * Transfer the properties of RuleName from DDB to RuleNameDTO's properties
     * @param ruleName RuleName
     * @return ruleNameDTO RuleNameDTO
     */
    public RuleNameDTO fromRuleName (RuleName ruleName) {
        RuleNameDTO ruleNameDTO = new RuleNameDTO();
        BeanUtils.copyProperties (ruleName, ruleNameDTO); // (source, target)
        return ruleNameDTO;
    }
    /**
     * Transfer the properties of TradeDTO to Trade's some properties
     * @param tradeDTO TradeDTO
     * @return Trade
     */
    public Trade fromTradeDTO (TradeDTO tradeDTO) {
        Trade trade = new Trade();
        BeanUtils.copyProperties (tradeDTO, trade); // (source, target)
        return trade;
    }
    /**
     * Transfer the properties of Trade from DDB to TradeDTO's properties
     * @param trade Trade
     * @return tradeDTO TradeDTO
     */
    public TradeDTO fromTrade (Trade trade) {
        TradeDTO tradeDTO = new TradeDTO();
        BeanUtils.copyProperties (trade, tradeDTO); // (source, target)
        return tradeDTO;
    }



}
