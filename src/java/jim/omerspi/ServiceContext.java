/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi;

import jim.omerspi.service.CarRegistrationService;
import jim.omerspi.service.CarRequisitionService;
import jim.omerspi.service.CarRequisitionServiceService;
import jim.omerspi.service.CartypeService;
import jim.omerspi.service.CategoryService;
import jim.omerspi.service.CategoryTypeService;
import jim.omerspi.service.DepartmentService;
import jim.omerspi.service.EmployeeService;
import jim.omerspi.service.ItemService;
import jim.omerspi.service.IteneraryService;
import jim.omerspi.service.MemoService;
import jim.omerspi.service.OfficeAssetRequisitionService;
import jim.omerspi.service.OfficeAssetRequisitionServiceService;
import jim.omerspi.service.PrivilegeService;
import jim.omerspi.service.RequestedItemService;
import jim.omerspi.service.RequisitionResponseService;
import jim.omerspi.service.StationaryRegistrationsService;
import jim.omerspi.service.StationaryRequisitionService;
import jim.omerspi.service.UserService;
import jim.omerspi.service.VendorService;

/**
 *
 * @author JOHN
 */
public class ServiceContext {

    private DepartmentService departmentService;
    private EmployeeService employeeService;
    private CategoryService categoryService;
    private ItemService itemService;
    private StationaryRegistrationsService stationaryRegistrationsService;
    private VendorService vendorService;
    private CarRegistrationService carRegistrationService;
    private MemoService memoService;
    private UserService userService;
    private CarRequisitionService carRequisitionService;
    private CarRequisitionServiceService carRequisitionServiceService;
    private RequisitionResponseService requisitionResponseService;
    private StationaryRequisitionService stationaryRequisitionService;
    private RequestedItemService requestedItemService;
    private IteneraryService iteneraryService;
    private CartypeService carTypeService;
    private PrivilegeService privilegeService;
    private CategoryTypeService categoryTypeService;
    private OfficeAssetRequisitionService officeAssetRequisitionService;
    private OfficeAssetRequisitionServiceService officeAssetRequisitionServiceService;

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public StationaryRegistrationsService getStationaryRegistrationsService() {
        return stationaryRegistrationsService;
    }

    public void setStationaryRegistrationsService(StationaryRegistrationsService stationaryRegistrationsService) {
        this.stationaryRegistrationsService = stationaryRegistrationsService;
    }

    public VendorService getVendorService() {
        return vendorService;
    }

    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    public CarRegistrationService getCarRegistrationService() {
        return carRegistrationService;
    }

    public void setCarRegistrationService(CarRegistrationService carRegistrationService) {
        this.carRegistrationService = carRegistrationService;
    }

    public MemoService getMemoService() {
        return memoService;
    }

    public void setMemoService(MemoService memoService) {
        this.memoService = memoService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public CarRequisitionService getCarRequisitionService() {
        return carRequisitionService;
    }

    public void setCarRequisitionService(CarRequisitionService carRequisitionService) {
        this.carRequisitionService = carRequisitionService;
    }

    public CarRequisitionServiceService getCarRequisitionServiceService() {
        return carRequisitionServiceService;
    }

    public void setCarRequisitionServiceService(CarRequisitionServiceService carRequisitionServiceService) {
        this.carRequisitionServiceService = carRequisitionServiceService;
    }

    public RequisitionResponseService getRequisitionResponseService() {
        return requisitionResponseService;
    }

    public void setRequisitionResponseService(RequisitionResponseService requisitionResponseService) {
        this.requisitionResponseService = requisitionResponseService;
    }

    public StationaryRequisitionService getStationaryRequisitionService() {
        return stationaryRequisitionService;
    }

    public void setStationaryRequisitionService(StationaryRequisitionService stationaryRequisitionService) {
        this.stationaryRequisitionService = stationaryRequisitionService;
    }

    public RequestedItemService getRequestedItemService() {
        return requestedItemService;
    }

    public void setRequestedItemService(RequestedItemService requestedItemService) {
        this.requestedItemService = requestedItemService;
    }

    public IteneraryService getIteneraryService() {
        return iteneraryService;
    }

    public void setIteneraryService(IteneraryService iteneraryService) {
        this.iteneraryService = iteneraryService;
    }

    public CartypeService getCartypeService() {
        return carTypeService;
    }

    public void setCartypeService(CartypeService carTypeService) {
        this.carTypeService = carTypeService;
    }
//

    public PrivilegeService getPrivilegeService() {
        return privilegeService;
    }

    public void setPrivilegeService(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    public CategoryTypeService getCategoryTypeService() {
        return categoryTypeService;
    }

    public void setCategoryTypeService(CategoryTypeService categoryTypeService) {
        this.categoryTypeService = categoryTypeService;
    }

    public OfficeAssetRequisitionService getOfficeAssetRequisitionService() {
        return officeAssetRequisitionService;
    }

    public void setOfficeAssetRequisitionService(OfficeAssetRequisitionService officeAssetRequisitionService) {
        this.officeAssetRequisitionService = officeAssetRequisitionService;
    }

    public OfficeAssetRequisitionServiceService getOfficeAssetRequisitionServiceService() {
        return officeAssetRequisitionServiceService;
    }

    public void setOfficeAssetRequisitionServiceService(OfficeAssetRequisitionServiceService officeAssetRequisitionServiceService) {
        this.officeAssetRequisitionServiceService = officeAssetRequisitionServiceService;
    }
}