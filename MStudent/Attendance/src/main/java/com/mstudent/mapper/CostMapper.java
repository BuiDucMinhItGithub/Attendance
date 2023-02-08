package com.mstudent.mapper;

import com.mstudent.model.dto.response.Cost.CostResponse;
import com.mstudent.model.entity.Cost;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CostMapper {
  CostMapper INSTANCE = Mappers.getMapper(CostMapper.class);

  CostResponse entityToResponse(Cost cost);

  List<CostResponse> listEntityToResponse(List<Cost> costs);

}
