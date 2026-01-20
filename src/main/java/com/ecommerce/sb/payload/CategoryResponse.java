package com.ecommerce.sb.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
   private List<CategoryDTO> response;

  private Integer pageNumber;
  private Integer pageSize;
  private Long Totalelements;
  private Integer Totalpages;
  private Boolean lastpage;
}
