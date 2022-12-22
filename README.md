# EmployeesProjectsWeek5

###### Group : The Superclass Skirmishers
- David Bragadireanu
- Ben Swenson
- JB Lemard-Reid
- Emre Ceyhan
- Yash Jagani
- Ignas Stepura
- Rob Ciuches

## Project Description

###### Purpose : 
- Explore JPA/Hibernate
- Use Spring Boot Testing
- Implement DAO and DTO classes as appropriate

---

###### Requirements : 
- Develop JPA entities and repositories for all 6 tables in the employees sample database in MySQL:
  - departments
  - dept_emp
  - dept_manager
  - employees
  - salaries
  - titles
- Create DAO and DTO classes to abstract the data access layer
- Add methods to enable the following functionality:
  - Find employees by last name`
  - Find all the employees who worked in a named department on a given date
  - What is the average salary for a named department on a given date?
  - Given a job title name, what is the range of salary values within a given year?
  - Provide a summary of the size of each department (number of staff) over a given period (start year to end year)
  - Is there a gender pay gap? If so, quantify it
- Add 3 methods of your own to investigate similar questions
- Configure a Spring Boot application to host the JPA classes`
- Use Spring Boot Test to create a comprehensive suite for your JPA classes and your DAO/DTO classes
- Include the result of your testing in the README.md file for the project on GitHub
- Include code coverage data for all JPA classes, with explanations for any low coverage levels, in the README.md
---

## Packages and Project Structure

:) loading ...

## Implementation

1. sadsa
2. asdas

## Testing and Results

## Code Coverage

## Takeaway and Future Imporvements 
=======
# EmployeesProjectsWeek5

###### Group : The Superclass Skirmishers
- David Bragadireanu
- Ben Swenson
- JB Lemard-Reid
- Emre Ceyhan
- Yash Jagani
- Ignas Stepura
- Rob Ciuches

## Project Description

###### Purpose : 
- Explore JPA/Hibernate
- Use Spring Boot Testing
- Implement DAO and DTO classes as appropriate

---

###### Requirements : 
- Develop JPA entities and repositories for all 6 tables in the employees sample database in MySQL:
  - departments
  - dept_emp
  - dept_manager
  - employees
  - salaries
  - titles
- Create DAO and DTO classes to abstract the data access layer
- Add methods to enable the following functionality:
  - Find employees by last name
  - Find all the employees who worked in a named department on a given date
  - What is the average salary for a named department on a given date?
  - Given a job title name, what is the range of salary values within a given year?
  - Provide a summary of the size of each department (number of staff) over a given period (start year to end year)
  - Is there a gender pay gap? If so, quantify it
- Add 3 methods of your own to investigate similar questions
- Configure a Spring Boot application to host the JPA classes
- Use Spring Boot Test to create a comprehensive suite for your JPA classes and your DAO/DTO classes
- Include the result of your testing in the README.md file for the project on GitHub
- Include code coverage data for all JPA classes, with explanations for any low coverage levels, in the README.md
---

## Packages and Project Structure

:) loading ...

## Implementation

### 1) findByLastName()

    List<Employee> findByLastName(String lastName);

This method takes a string for the last name of an **Employee** we intend to find and returns the result as a list of **Employees**.

### 2) findByDepartmentAndDate()

    @Query(value = "SELECT employees.emp_no, employees.birth_date, employees.first_name, employees.last_name, employees.gender, employees.hire_date, departments.dept_name " +
            "FROM employees " +
            "JOIN dept_emp, departments " +
            "WHERE employees.emp_no = dept_emp.emp_no " +
            "AND departments.dept_name = :department " +
            "AND dept_emp.from_date > :fromDate " +
            "AND dept_emp.to_date < :toDate", nativeQuery = true )
    List<Employee> findByDepartmentAndDate(@Param("department") String department, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate );

This method takes a _@Query_ in its native form and returns a list of **Employees** from said _department_ and between the 
specified date using _"fromDate"_ and _"toDate"_. 

### 3) countNumberOfEmployeesLeftDepartmentByYear()

    @Query(value = "SELECT count(*) FROM employees.employees " +
            "JOIN dept_emp, departments " +
            "WHERE employees.emp_no = dept_emp.emp_no " +
            "AND departments.dept_name = :department " +
            "AND dept_emp.to_date " +
            "BETWEEN :year'-01-01' AND :year'-12-31'", nativeQuery = true )
    int countNumberOfEmployeesLeftDepartmentByYear(@Param("department") String department, @Param("year") String year);

This method takes a _@Query_ and returns a count (as integer) for the number of Employee that have left a **department** in a 
specified _year_.

### 4) findNoOfEmployeesForEachDept() 

    @Query(
            value = "SELECT new com.sparta.jpahibernate.dto.EmpsForDeptsDTO(d.deptName, COUNT(de.empNo)) " +
                    "FROM DeptEmp de JOIN Department d ON de.deptNo = d.id WHERE " +
                    "de.fromDate > :fromDate AND de.toDate < :toDate " +
                    "GROUP BY de.deptNo " +
                    "ORDER BY d.id"
    )
    List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

This method takes a _@Query_ and returns the number of **Employee** in each **department** within a given _date_ 
as a list of DTO's. 

### 5) findAvgSalaryByGender()

    @Query(
            "SELECT new com.sparta.jpahibernate.dto.SalaryForTitlesDTO(t.id.title, e.gender, AVG(s.salary)) " +
            "FROM Employee e LEFT JOIN Salary s ON e.id = s.id.empNo " +
            "LEFT JOIN Title t ON e.id = t.id.empNo WHERE e.gender = :gender " +
            "GROUP BY t.id.title " +
            "ORDER BY t.id.title "
    )
    List<SalaryForTitlesDTO> findAvgSalaryByGender(@Param("gender") String gender);

This method takes a _@Query_ and returns a list of average _salary_ by _gender_ as DTO's.

### 6) Finding Max and Min Salary

    @Query(
            value = "SELECT min(salaries.salary) FROM employees JOIN titles, salaries WHERE " +
                    "employees.emp_no = salaries.emp_no AND employees.emp_no = titles.emp_no AND titles.title = :title AND titles.from_date " +
                    "BETWEEN :year'-01-01' AND :yearEnd'-01-01'",
            nativeQuery = true )
    List<Double> findSalaryByTitleWithinGivenYearMin(@Param("title") String department, @Param("year")
    String year, @Param("yearEnd") String yearEnd);

    @Query(
            value = "SELECT max(salaries.salary) FROM employees JOIN titles, salaries WHERE " +
                    "employees.emp_no = salaries.emp_no AND employees.emp_no = titles.emp_no AND titles.title = :title AND titles.from_date " +
                    "BETWEEN :year'-01-01' AND :yearEnd'-01-01'",
            nativeQuery = true )
    List<Double> findSalaryByTitleWithinGivenYearMax(@Param("title") String department, @Param("year")
    String year, @Param("yearEnd") String yearEnd);

These methods use _@Query_ to find the _minimum_ and _maximum_ range for _salary_ within a _job title_ and _year_ range.

### 7) EmployeeDAO

#### 7.1) Private constructor to keep single instance 

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;

    @Autowired
    public EmployeeDAOImpl(EmployeeRepository employeeRepository,
                           SalaryRepository salaryRepository) {
        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
    }

#### 7.2) count()

    @Override
    public long count() {
        return employeeRepository.count();
    }

This method counts the number of **Employee**

#### 7.3) delete()

    @Override
    public void delete(EmployeeDTO dto) {
        Optional<Employee> optEmp = employeeRepository.findById(dto.getId());
        optEmp.ifPresent(employeeRepository::delete);
    }

This method carries out the _CRUD_ method "_delete_" when called on an **Employee** DAO

#### 7.4) findAll()

    @Override
    public List<EmployeeDTO> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTOs.add(new EmployeeDTO(employee.getId(), employee.getBirthDate(), employee.getFirstName(),
                    employee.getLastName(), employee.getGender(), employee.getHireDate()));
        }
        return employeeDTOs;
    }

This method finds all the Employees when called and returns it as a list 

#### 7.5) save()

    @Override
    public void save(EmployeeDTO dto) {
        Employee emp = new Employee();
        emp.setId(emp.getId());
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setGender(dto.getGender());
        emp.setBirthDate(dto.getBirthDate());
        emp.setHireDate(dto.getHireDate());
        employeeRepository.save(emp);
    }

This method takes a **EmployeeDTO** object and converts/stores it as an **Employee** within the database

#### 7.6) findByLastName()

    public List<EmployeeDTO> findByLastName(String lastName) {
        List<Employee> employees = employeeRepository.findByLastName(lastName);
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for(Employee employee : employees) {
            employeeDTOs.add(new EmployeeDTO(employee.getId(), employee.getBirthDate(), employee.getFirstName(),
                    employee.getLastName(), employee.getGender(), employee.getHireDate()));
        }
        return employeeDTOs;
    }

This takes a _lastName_ as a String and uses it to find **Employees** with said _last name_ and returns it as 
an _EmployeeDTO_ list

#### 7.7) findByDepartmentAndDate()

    public List<EmpsByDeptsDTO> findByDepartmentAndDate(String department, LocalDate fromDate, LocalDate toDate) {
        return employeeRepository.findByDepartmentAndDate(department, fromDate, toDate);
    }

This method returns a list of **Employees** from said **department** within a given _date_ range as a _DTO_

#### 7.8) countNumberOfEmployeesLeftDepartmentByYear()

    public int countNumberOfEmployeesLeftDepartmentByYear(String dept, String year) {
        return employeeRepository.countNumberOfEmployeesLeftDepartmentByYear(dept, year);
    }

This method returns the number of **Employee** that left a department within a _year_

#### 7.9) deleteById()

    @Override
    public void deleteById(Integer id) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if(optEmp.isPresent()){
            employeeRepository.deleteById(id);
        }
    }

This method deletes an **Employee** record by _ID_

#### 7.10) existsById()

    @Override
    public boolean existsById(Integer id) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        return optEmp.isPresent();
    }

This method returns _True_ if the **Employee** we are after exists.

#### 7.11) getPayGapSalary()

    @Override
    public void getPayGapSalary() {
        List<SalaryForTitlesDTO> maleSalary = salaryRepository.findAvgSalaryByGender("M");
        List<SalaryForTitlesDTO> femaleSalary = salaryRepository.findAvgSalaryByGender("F");
        String result;
        double payGap;
        for (int i = 0; i < maleSalary.size(); i++) {
            if (maleSalary.get(i).getAvgSalary() > femaleSalary.get(i).getAvgSalary()) {
                payGap = ((maleSalary.get(i).getAvgSalary() - femaleSalary.get(i).getAvgSalary()) * 100)
                        / femaleSalary.get(i).getAvgSalary();
                result = maleSalary.get(i).getTitle() + " has a pay gap of " +
                        String.format("%.3f", payGap)
                        + "% favouring males";
            } else {
                payGap = ((femaleSalary.get(i).getAvgSalary() - maleSalary.get(i).getAvgSalary()) * 100)
                        / maleSalary.get(i).getAvgSalary();
                result = femaleSalary.get(i).getTitle() + " has a pay gap of " +
                        String.format("%.3f", payGap)
                        + " favouring females";
            }
            System.out.println(result);
        }
    }

This method displays the pay gap between _males_ and _females_ for each **department**

#### 7.12) update()

    @Override
    public void update(Integer id, String[] params) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if (optEmp.isPresent()) {
            Employee emp = optEmp.get();
            emp.setId(Integer.valueOf(params[0]));
            emp.setBirthDate(Date.valueOf(params[1]).toLocalDate());
            emp.setFirstName(params[2]);
            emp.setLastName(params[3]);
            emp.setGender(params[4]);
            emp.setHireDate(Date.valueOf(params[5]).toLocalDate());
            employeeRepository.save(emp);
        }
    }

This method updates an existing **Employee** record 

#### 7.13) findByID()

    @Override
    public Optional<EmployeeDTO> findById(Integer id) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if(optEmp.isPresent()){
            Employee presentEmp = optEmp.get();
            EmployeeDTO emp = new EmployeeDTO(presentEmp.getId(), presentEmp.getBirthDate(),
                    presentEmp.getFirstName(), presentEmp.getLastName(),
                    presentEmp.getGender(), presentEmp.getHireDate());
            return Optional.of(emp);
        }
        return Optional.empty();
    }

This returns a found **Employee** instance as an **EmployeeDTO** only if it exists 

### 8) DepartmentDAO

#### 8.1) Private constructor to keep single instance

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentDAOImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

#### 8.2) count()

    @Override
    public long count() {
        return departmentRepository.count();
    }

This method counts the number of **departments**

#### 8.3) delete()

    @Override
    public void delete(DepartmentDTO dto) {
        Optional<Department> optDept = departmentRepository.findById(dto.getId());
        optDept.ifPresent(departmentRepository::delete);
    }

This method deletes a **department** record using a **department** _DTO_ 

#### 8.4) existsById()

    @Override
    public boolean existsById(String id) {
        Optional<Department> optDept = departmentRepository.findById(id);
        return optDept.isPresent();
    }

This method checks if a **department** exists 

#### 8.5) findAll()

    @Override
    public List<DepartmentDTO> findAll() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();
        for (Department department : departments) {
            departmentDTOs.add(new DepartmentDTO(department.getId(), department.getDeptName()));
        }
        return departmentDTOs;
    }

This method returns a list of all **departments** as _DTO's_

#### 8.6) findById()

    @Override
    public Optional<DepartmentDTO> findById(String id) {
        Optional<Department> optDept = departmentRepository.findById(id);
        if(optDept.isPresent()){
            Department presentDept = optDept.get();
            DepartmentDTO dept = new DepartmentDTO(
                    presentDept.getId(),
                    presentDept.getDeptName());
            return Optional.of(dept);
        }
        return Optional.empty();
    }

This method finds a **department** by _ID_ and then returns it as a **department** _DTO_ only if it exists 

#### 8.7) save()

    @Override
    public void save(DepartmentDTO dto) {
        Department dept = new Department();
        dept.setId(dto.getId());
        dept.setDeptName(dto.getDeptName());
        departmentRepository.save(dept);
    }

This method takes a **Department** _DTO_ and stores it as a record on the database

#### 8.8) update()

    public void update(String id, String[] params) {
        Optional<Department> optDept = departmentRepository.findById(id);
        if (optDept.isPresent()) {
            Department dept = optDept.get();
            dept.setId(params[0]);
            dept.setDeptName(params[1]);
            departmentRepository.save(dept);
        }
    }

This method updates an existing **department** record within the table 

#### 8.9) deleteById()

    @Override
    public void deleteById(String id) {
        Optional<Department> optDept = departmentRepository.findById(id);
        if(optDept.isPresent()){
            departmentRepository.deleteById(id);
        }
    }

This method deletes a **department** if it exists in the table 

#### 8.10) findNoOfEmployeesForEachDept()

    @Override
    public List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(LocalDate fromDate, LocalDate toDate) {
        return departmentRepository.findNoOfEmployeesForEachDept(fromDate, toDate);
    }

This method returns the number of **Employee** in each **department** within a given _date_ range as a _DTO_

#### 8.11) findDepartmentByDeptName()

    @Override
    public DepartmentDTO findDepartmentByDeptName(String deptName) {
        return new DepartmentDTO(departmentRepository.findDepartmentByDeptName(deptName).getId(),
                departmentRepository.findDepartmentByDeptName(deptName).getDeptName());
    }

This method finds a **department** by name and returns it as a **Department** _DTO_

### 9) DTO

```java   
      @Data
      @NoArgsConstructor
      @AllArgsConstructor
      @Component
      public class EmployeeDTO {
      private Integer id;
      private LocalDate birthDate;
      private String firstName;
      private String lastName;
      private String gender;
      private LocalDate hireDate;

    }
```

This is an example of our _DTO_ class implementation

### 10) Entities

```java
@Entity
@Table(name = "departments")
public class Department {
@Id
@Column(name = "dept_no", nullable = false, length = 4)
private String id;

    @Column(name = "dept_name", nullable = false, length = 40)
    private String deptName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
```
This is an example of an entity within our program 

## Testing and Results

### findEmployeeByLastName()

    @Test
    void findEmployeeByLastName() {

        List<Employee> res = empDao.findByLastName("Simmel");
        System.out.println(res);
        Assertions.assertTrue(res.size() >= 1);
    }

The goal of this test is to check if we receive a list of **Employees** after executing the method _findByLastName()_.
In this case we wanted **Employees** with the last name _"Simmel"_. Refer to _Implementation 1)_ to see how _findByLastName()_
Operates.

![lastNameScreenshot.PNG](images%2FlastNameScreenshot.PNG)

As you can see the test has passed since the list is not empty and returns with the desired result. Here we display one
example for testing purposes. 

### findEmployeeByDepartmentAndDate()

    @Test
    void findEmployeeByDepartmentAndDate(){

        LocalDate fromDate = LocalDate.of(1998,10,10);
        LocalDate toDate = LocalDate.of(2020,10,10);

        String department = "Sales";
        List<Employee> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue(res.size() == 8830);
    }

This test uses our _findByDepartmentAndDate()_ (_"Refer to implementation 2)"_) method to see if we return a list of 
**Employee** between a said timeframe. On our backend we have 8830 results, so we used this value to test against our 
method. This _@Test_ has passed as you can see below. 

![findEmployeeByDepartmentAndDateTest.PNG](images%2FfindEmployeeByDepartmentAndDateTest.PNG)

    @Test
    void findEmployeesInDevelopmentByDate()
    {
        LocalDate fromDate = LocalDate.of(1999,10,10);
        LocalDate toDate = LocalDate.of(2020,10,10);

        String department = "Development";
        List<Employee> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue(res.size() == 2513);
    }

We also checked with a different **Department** to double-check functionality 

### countNumberOfEmployeesThatLeftADepartmentByYear()

    @Test
    void countNumberOfEmployeesThatLeftADepartmentByYear(){
        String year = "2000";
        String department = "Sales";
        int res = empDao.countNumberOfEmployeesLeftDepartmentByYear(department, year);
        System.out.println(res);
        Assertions.assertTrue(res == 11224);

    }
This _@Test_ uses our _countNumberOfEmployeesLeftDepartmentByYear()_ (_"refer to implementation 3)_") to see how many
**Employee** have left the **Sales** **department** in the _year 2000_. On our backend we received the count of 11224, 
so we use this value to check against. 

![countNumberOfEmployeesThatLeftADepartmentByYearTest.PNG](images%2FcountNumberOfEmployeesThatLeftADepartmentByYearTest.PNG)

As you can see the test for this has passed as our _@Query_ (_"refer to implementation 3)"_) returns the intended result we desired. 

### findEmployeesByDepartmentByDateYearRange()

    @Test
    void findEmployeesByDepartmentByDateYearRange(){
        List<EmpsForDeptsDTO> testlist = deptDao.findNoOfEmployeesForEachDept(
                LocalDate.of(1995,1,1),
                LocalDate.of(2005,12,31)
        );
        System.out.println(testlist);
    }

This _@Test_ uses our _findNoOfEmployeesForEachDept()_ method (_"refer to Implementation 4)"_) to see if we receive the correct number of 
**Employee** for each **department** within a given _date_. In this case we use the _date_ range of **01/01/1995** and
**31/12/2005**. 

![findEmployeesByDepartmentByDateYearRange.PNG](images%2FfindEmployeesByDepartmentByDateYearRange.PNG)

In the test you can see that our first _DTO_ returns the **department** _Marketing_ with the value of _1963_ **Employee**.

### findPayGapByTitleByGender()

    @Test
    void findPayGapByTitleByGender() {
        List<SalaryForTitlesDTO> maleSalary = salaryRepo.findAvgSalaryByGender("M");
        List<SalaryForTitlesDTO> femaleSalary = salaryRepo.findAvgSalaryByGender("F");
        String result = "";
        double payGap = 0.0;
        for (int i = 0; i < maleSalary.size(); i++) {
            if (maleSalary.get(i).getAvgSalary() > femaleSalary.get(i).getAvgSalary()) {
                payGap = ((maleSalary.get(i).getAvgSalary() - femaleSalary.get(i).getAvgSalary()) * 100) / femaleSalary.get(i).getAvgSalary();
                result = maleSalary.get(i).getTitle() + " has a pay gap of " +
                        String.format("%.3f", payGap)
                        + "% favouring males";
            } else {
                payGap = ((femaleSalary.get(i).getAvgSalary() - maleSalary.get(i).getAvgSalary()) * 100) / maleSalary.get(i).getAvgSalary();
                result = femaleSalary.get(i).getTitle() + " has a pay gap of " +
                        String.format("%.3f", payGap)
                        + " favouring females";
            }
            System.out.println(result);
        }
    }

This _@Test_ displays to us a list of _Males_ and _Females_ from different _job titles_ with their _average salary_.
Refer to _Implementation 5)_.

![findPayGapByTitleByGender.PNG](images%2FfindPayGapByTitleByGender.PNG)

### givenJobTitleFindSalaryRangeWithinGivenYear()

    @Test
    void givenJobTitleFindSalaryRangeWithinGivenYear(){

        String title = "Staff";
        String year = "2000";
        String yearEnd = "2001";

        List<Double> salaryRange = new ArrayList<>();
        salaryRange.add(salaryRepository.findSalaryByTitleWithinGivenYearMin( title, year, yearEnd).get(0));
        salaryRange.add(salaryRepository.findSalaryByTitleWithinGivenYearMax( title, year, yearEnd).get(0));
        System.out.println(salaryRange.toString());
        List<Double> expected = new ArrayList<>(List.of(39186.0, 118492.0));
        Assertions.assertEquals(expected, salaryRange);
    }

This _@Test_ tests the methods in _Implementation 6)_ to see the _minimum_ and _maximum_ range for a **department** _salary_
with a given _year_ range.

![minAndMax.PNG](images%2FminAndMax.PNG)

This _@Test_ has passed and as you can see the range in the console matches the one in our database.



## Code Coverage

## Takeaway and Future Improvements 
