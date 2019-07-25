package exort.associationmanager.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationFilterParams {
	private String keyword;
	private List<String> tags;
	private Integer state;
}
