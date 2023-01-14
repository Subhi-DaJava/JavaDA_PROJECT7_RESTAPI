package com.nnk.springboot.service.curve_point_service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceImplTest {
    @InjectMocks
    private CurvePointServiceImpl curvePointService;
    @Mock
    private CurvePointRepository curvePointRepository;
    @Mock
    private MapperService mapperService;

    @Test
    void getCurvePoints() {
        CurvePointDTO curvePointDTO1 = new CurvePointDTO();
        CurvePointDTO curvePointDTO2 = new CurvePointDTO();


        CurvePoint curvePoint1 = new CurvePoint(12, 320D, 1112D);
        CurvePoint curvePoint2 = new CurvePoint(15, 236D, 2023D);

        List<CurvePoint> curvePoints = new ArrayList<>(List.of(curvePoint1, curvePoint2));

        when(mapperService.fromCurvePoint(curvePoint1)).thenReturn(curvePointDTO1);
        when(mapperService.fromCurvePoint(curvePoint2)).thenReturn(curvePointDTO2);

        curvePointDTO1.setValue(curvePoint1.getValue());
        curvePointDTO1.setTerm(curvePoint1.getTerm());
        curvePointDTO1.setCurveId(curvePoint1.getCurveId());

        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePointDTO> curvePointDTOList = curvePointService.getCurvePoints();

        assertThat(curvePointDTOList.size()).isEqualTo(2);
        assertThat(curvePointDTOList.get(0).getValue()).isEqualTo(1112D);
        assertThat(curvePointDTOList).isNotNull();
    }

    @Test
    void getEmptyCurvePoints() {
        List<CurvePoint> curvePoints = new ArrayList<>();
        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePointDTO> curvePointDTOList = curvePointService.getCurvePoints();

        assertThat(curvePointDTOList.size()).isEqualTo(0);
    }

    @Test
    void getCurvePointById() {
        CurvePointDTO curvePointDTO= new CurvePointDTO();
        CurvePoint curvePoint = new CurvePoint(6,12, 320D, 1112D);

        //this mock will map any CurvePoint to CurvePointDTO
        when(mapperService.fromCurvePoint(any())).thenReturn(curvePointDTO);
        curvePointDTO.setCurveid(curvePoint.getId());
        curvePointDTO.setValue(curvePoint.getValue());

        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint));

        CurvePointDTO curvePointDTOGetById = curvePointService.getCurvePointById(curvePoint.getId());
        assertThat(curvePointDTOGetById.getCurveid()).isEqualTo(6);
        assertThat(curvePointDTOGetById.getValue()).isEqualTo(curvePoint.getValue());
    }
    @Test
    void getCurvePointByNotExistingId() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> curvePointService.getCurvePointById(anyInt()));
    }

    @Test
    void saveNewCurvePoint() {
        CurvePointDTO curvePointDTO= new CurvePointDTO();
        CurvePoint curvePoint = new CurvePoint(12, 320D, 1112D);

        //this mock will map any CurvePoint to CurvePointDTO
        when(mapperService.fromCurvePoint(any())).thenReturn(curvePointDTO);

        curvePointDTO.setCurveId(curvePoint.getCurveId());
        curvePointDTO.setTerm(curvePoint.getTerm());
        curvePointDTO.setValue(curvePoint.getValue());

        when(mapperService.fromCurvePointDTO(any())).thenReturn(curvePoint);
        when(curvePointRepository.save(any())).thenReturn(curvePoint);

       CurvePointDTO curvePointDTOSaved = curvePointService.saveNewCurvePoint(curvePointDTO);

        assertThat(curvePointDTOSaved.getCurveId()).isEqualTo(12);
        verify(curvePointRepository, times(1)).save(any());
        verify(mapperService, times(1)).fromCurvePointDTO(any());
        verify(mapperService, times(1)).fromCurvePoint(any());
    }

    @Test
    void updateCurvePoint() {
        CurvePoint curvePoint;
        CurvePointDTO curvePointDTO = new CurvePointDTO();

        curvePoint = new CurvePoint(7,12, 320D, 1112D);

        when(curvePointRepository.save(any())).thenReturn(curvePoint);
        when(mapperService.fromCurvePointDTO(any())).thenReturn(curvePoint);

        curvePointDTO.setCurveid(7);
        curvePointDTO.setCurveId(13);

        curvePointDTO.setValue(2112D);
        curvePoint.setCurveId(13);
        curvePoint.setValue(2112D);

        curvePointService.updateCurvePoint(curvePointDTO);

        assertThat(curvePoint.getCurveId()).isEqualTo(13);
        assertThat(curvePoint.getValue()).isEqualTo(curvePointDTO.getValue());
    }

    @Test
    void deleteCurvePointById() {
        CurvePoint curvePoint = new CurvePoint(8,32, 147D, 6531D);
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint));
        doNothing().when(curvePointRepository).deleteById(anyInt());
        curvePointService.deleteCurvePointById(anyInt());
        verify(curvePointRepository,times(1)).deleteById(anyInt());
        verify(curvePointRepository, times(1)).findById(anyInt());
        assertThat(curvePointService.getCurvePointById(8)).isNull();
    }
}