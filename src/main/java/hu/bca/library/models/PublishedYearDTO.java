package hu.bca.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishedYearDTO {

  private String bookName;
  private Integer firstPublishedYear;

}
