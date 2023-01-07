package com.nnk.springboot.service.bid_service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidListServiceTest {
    @InjectMocks
    private BidListServiceImpl bidListService;
    @Mock
    private BidListRepository bidListRepository;
    @Mock
    private MapperService mapperService;

    private List<BidListDTO> bidListDTOs;
    private
    @BeforeEach
    void setUp() {
        bidListDTOs = new ArrayList<>();
    }

    @Test
    void getBidList() {
        BidListDTO bidListDTO_1 = new BidListDTO();
        BidListDTO bidListDTO_2 = new BidListDTO();

        BidList bidList1 = new BidList("Account1", "Type1", 2560D);
        BidList bidList2 = new BidList("Account2", "Type2", 2560D);
        List<BidList> bidLists = new ArrayList<>(List.of(bidList1, bidList2));

        when(mapperService.fromBidList(bidList1)).thenReturn(bidListDTO_1);
        when(mapperService.fromBidList(bidList2)).thenReturn(bidListDTO_2);

        bidListDTO_1.setBidId(bidList1.getBidListId());
        bidListDTO_1.setType(bidList1.getType());
        bidListDTO_1.setAccount(bidList1.getAccount());
        bidListDTO_1.setBidQuantity(bidList1.getBidQuantity());

        when(bidListRepository.findAll()).thenReturn(bidLists);

        List<BidListDTO> bidListDTOList = bidListService.getBidList();

        assertThat(bidListDTOList.size()).isEqualTo(2);
        assertThat(bidListDTOList.get(0).getType()).isEqualTo("Type1");
        assertThat(bidListDTOList).isNotNull();
    }

    @Test
    void saveNewBidList() {
        BidListDTO bidListDTO = new BidListDTO();
        BidList bidList = new BidList("Account", "Type", 2560D);

        //this mock will map any BidList to BidListDTO
        when(mapperService.fromBidList(any())).thenReturn(bidListDTO);

        bidListDTO.setAccount("Account");
        bidListDTO.setType("Type");
        bidListDTO.setBidQuantity(2560D);

        when(mapperService.fromBidListDTO(any())).thenReturn(bidList);
        when(bidListRepository.save(any())).thenReturn(bidList);

        BidListDTO bidListDTOSaved = bidListService.saveNewBidList(bidListDTO);

        assertThat(bidListDTOSaved.getType()).isEqualTo("Type");
        verify(bidListRepository, times(1)).save(any());
        verify(mapperService, times(1)).fromBidListDTO(any());

    }

    @Test
    void getBidListById() {
        BidListDTO bidListDTO = new BidListDTO();
        BidList bidList = new BidList(5,"Account", "Type", 2560D);

        when(mapperService.fromBidList(any())).thenReturn(bidListDTO);
        bidListDTO.setBidId(5);
        bidListDTO.setType(bidList.getType());

        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidList));

        BidListDTO bidListDTOById = bidListService.getBidListById(5);
        assertThat(bidListDTOById.getBidId()).isEqualTo(5);
        assertThat(bidListDTOById.getType()).isEqualTo(bidList.getType());

    }

    @Test
    void updateBidList() {
        BidList bidList = new BidList();
        BidListDTO bidListDTO = new BidListDTO();
        when(mapperService.fromBidListDTO(bidListDTO)).thenReturn(bidList);
        bidList = new BidList(5,"Account", "Type", 2560D);

        when(bidListRepository.save(bidList)).thenReturn(bidList);
        when(mapperService.fromBidListDTO(bidListDTO)).thenReturn(bidList);

        bidListDTO.setType("Type Updated");
        bidListDTO.setBidId(5);
        bidListDTO.setAccount("Account_update");
        bidList.setType("Type Updated");
        bidList.setAccount("Account_update");
        //when(bidListRepository.save(bidList)).thenReturn(bidList);

        bidListService.updateBidList(bidListDTO);

        assertThat(bidList.getType()).isEqualTo("Type Updated");
        assertThat(bidList.getAccount()).isEqualTo("Account_update");
    }

    @Test
    void deleteBidListById() {
        BidList bidList = new BidList(4,"Account", "Type", 2560D);
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidList));
        doNothing().when(bidListRepository).deleteById(anyInt());
        bidListService.deleteBidListById(anyInt());
        verify(bidListRepository,times(1)).deleteById(anyInt());
        verify(bidListRepository, times(1)).findById(anyInt());
        assertThat(bidListService.getBidListById(4)).isNull();
    }
}