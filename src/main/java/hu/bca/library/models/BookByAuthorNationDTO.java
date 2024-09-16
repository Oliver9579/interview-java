package hu.bca.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookByAuthorNationDTO {

  private Long bookId;
  private String title;
  private Integer publishedYear;

}
