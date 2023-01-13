package com.sparta.jpahibernate.daos;

import com.sparta.jpahibernate.dao.concretes.DepartmentDAOImpl;
import com.sparta.jpahibernate.dto.DepartmentDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@Transactional
public class DepartmentDAOTesting {

    @Autowired
    private DepartmentDAOImpl deptDao;

    @Test
    @DisplayName("long count();")
    void departmentCount() {
    Assertions.assertEquals(9, deptDao.count());
    }

    @Test
    @Rollback
    @DisplayName("void delete(T dto);")
    void departmentDelete() {
        Optional<DepartmentDTO> optDTO = deptDao.findById("d002");
        optDTO.ifPresent(deptDTO -> deptDao.delete(deptDTO));
        Assertions.assertFalse(deptDao.findById("d002").isPresent());
}
    @Test()
    @DisplayName("List<T> findAll();")
    void departmentFindAll() {
        Assertions.assertFalse(deptDao.findAll().isEmpty());
    }

    @Test
    @Rollback
    @DisplayName("void save(T dto);")
    void departmentSave() {
        deptDao.save(new DepartmentDTO(
                "d010", "Test"
        ));
        Assertions.assertEquals(
                deptDao.findDepartmentByDeptName("Test").getDeptName(), "Test");
    }

    @Test
    @Rollback
    @DisplayName("void deleteById(String id);")
    void departmentDeleteById() {
        Optional<DepartmentDTO> optDept = deptDao.findById("d009");
        if(optDept.isEmpty()) Assertions.fail();
        deptDao.deleteById("d009");
        Optional<DepartmentDTO> deptAfterDel = deptDao.findById("d009");
        assertFalse(deptAfterDel.isPresent());
}

    @Test
    @DisplayName("Find department by id")
    void departmentFindById(){
        Optional<DepartmentDTO> optDept = deptDao.findById("d001");
        if(optDept.isPresent()) {
            String result = optDept.get().getDeptName();
            Assertions.assertEquals("Marketing" , result);
        }
}

    @Test
    @DisplayName("boolean existsById(String id);")
    void departmentExistsById(){
        String id = "d009";
        Assertions.assertTrue(deptDao.existsById(id));
    }

    @Test
    @Rollback
    @DisplayName("void update(String id, String[] params);")
    void departmentUpdate(){

        String[] params = {"d001", "TEST"};
        String id = "d001";
        Optional<DepartmentDTO> optDept = deptDao.findById(id);
        if(optDept.isPresent()){
            DepartmentDTO dept = optDept.get();
            deptDao.update(id, params);
        }
        Optional<DepartmentDTO> optDeptAfterUpdate = deptDao.findById(id);
        if(optDeptAfterUpdate.isPresent()){
            DepartmentDTO dept = optDeptAfterUpdate.get();
            Assertions.assertEquals("TEST", dept.getDeptName());
        }
    }

    @Test
    @Rollback
    @DisplayName("List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(LocalDate fromDate, LocalDate toDate);")
    void findNoOfEmpsForEachDeptByDateInterval(){
        LocalDate fromDate = LocalDate.of(1999,1,1);
        LocalDate toDate = LocalDate.of(2000,1,1);
        Assertions.assertFalse(deptDao.findNoOfEmployeesForEachDept(fromDate, toDate).isEmpty());
    }

}
