package exort.associationmanager.entity;

import java.util.Date;

import lombok.Data;

@Data
public class AssociationApplicationFilterParams {

	private int id;
	private Association asso;
	private int state;

}
