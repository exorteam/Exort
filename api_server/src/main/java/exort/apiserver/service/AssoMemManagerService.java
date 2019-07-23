package exort.apiserver.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

public interface AssoMemManagerService {

	@Data
	public class Department {
		int associationId;
		int departmentId;
		String name;
		String description;
		int parentId;
	}

	@Data
	@NoArgsConstructor
	public class ApiResponse<T> {
		private T data = null;
		private String error = "";
		private String message = "";

		public ApiResponse(T data) {
			this.data = data;
		}
		public ApiResponse(String error, String message) {
			this.error = error;
			this.message = message;
		}
		public static ApiResponse emptyResponse() {
			return new ApiResponse<>(new HashMap());
		}
	}

	@Data
	public class InitAssociationInfo {
		int userId;
		int associationId;
	}

	@Data
	public class CallbackParam<InfoClass> {
		private Long userId;
		private String event;
		private Application<InfoClass> application;
	}

	@Data
	public class Application<T> {
		private Long id;
		private Long applicantId;
		private String type;
		private T object;
		private List<String> materialIds;
		private Date createdTime;
		private Date handledTime;
		private String state;
	}

	@Data
	public class ApplicationDepartmentInfo {
		private int associationId;
		private int departmentId;
	}

	public ApiResponse<Boolean> adoptApplication(CallbackParam<ApplicationDepartmentInfo> callbackParam);

    public ApiResponse<List<Department>> getDepartmentTree(int associationId);

    public ApiResponse<Department> getSpecDepartmentInfo(int associationId, int departmentId);

    public ApiResponse<Department> createDepartment(int associationId, Department departmentInfo);

    public ApiResponse<Department> deleteDepartment(int associationId, int departmentId);

    public ApiResponse<Department> editDepartment(int associationId, int departmentId, Department departmentInfo);

    public ApiResponse<List<Integer>> getSpecMemberList(int associationId, int departmentId);

    public ApiResponse<Boolean> removeOneFromDepartment(int associationId, int departmentId, int userId);

    public ApiResponse<Boolean> addOneToDepartment(int associationId, int departmentId, int userId);

    public ApiResponse<Boolean> checkUserPermissionInAssociation(int associationId, int userId, String permission);

    public ApiResponse<List<Integer>> getUserAssociation(int userId);

    public ApiResponse<List<Department>> getUserDepartment(int associationId, int userId);

    public ApiResponse<Boolean> deleteOneInAssociation(int associationId, int userId);

    public ApiResponse<Boolean> addOneToAssociation(int associationId, int userId);

    public ApiResponse<List<Integer>> getAssoUserList(int associationId);

    public ApiResponse<Boolean> initDepartment(InitAssociationInfo initAssociationInfo);

}

