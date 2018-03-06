
<%@include file="/WEB-INF/jsp/header/header.jsp" %>

<h3 align="center">Employee Records</h3>
<c:url var="editImgUrl" value="/images/edit.png" />
<c:url var="deleteImgUrl" value="/images/erase.png"/>
<c:url var="editUrl" value="/employee/edit"/>
<c:url var="deleteUrl" value="/employee/delete"/>
<c:url var="formUrl" value="/employee/form"/>

<form:form modelAttribute="employee" method="GET">
    <p align="center"> <a href="${formUrl}" > New Employee</a></p>

    <table id="employeeList" class="datatable">

        <thead>
            <tr>
                <th>Employee Code</th>  
                <th>First Name</th> 
                <th>Last Name</th> 
                <th>Gender</th> 
                <th>Job Position</th> 
                <th>Department</th> 
                <th>National ID</th> 
                <th>Telephone</th> 
                <th>Email</th>
                <th>Driver</th> 
                <th>State</th> 
                <th>Registered On</th> 
                <th>Disabled On</th>
                <th>Edit</th>
                <th>Delete</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${employeeList}" var="employee">
                <tr>

                    <td>${employee.employeeCode}</td>
                    <td>${employee.firstName}</td>
                    <td>${employee.lastName}</td>
                    <td>${employee.gender}</td>
                    <td>${employee.jobPosition}</td>
                    <td>${employee.department.departmentName}</td>
                    <td>${employee.nationalId}</td>
                    <td>${employee.telephone}</td>
                    <td>${employee.workEmail}</td>
                    <td>${employee.driver}</td>
                    <td>${employee.state}</td>
                    <td>${employee.firstDate}</td>
                    <td>${employee.innactiveDate}</td>


                    <td><a title="Edit" href="${editUrl}?employeeId=${employee.employeeRegistrationId}"><img src="${editImgUrl}"></img></a></td>
                    <td><a title="Delete" href="${deleteUrl}?employeeId=${employee.employeeRegistrationId}"><img src="${deleteImgUrl}"></img></a></td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
</form:form>

<%@include file="/WEB-INF/jsp/footer/footer.jsp" %>