package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.TitleDAO;
import com.sparta.jpahibernate.dto.TitleDTO;
import com.sparta.jpahibernate.entities.Salary;
import com.sparta.jpahibernate.entities.Title;
import com.sparta.jpahibernate.repositories.TitleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TitleDAOImpl implements TitleDAO {

    @Autowired
    private final TitleRepository titleRepository;

    public TitleDAOImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }


    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(TitleDTO dto) {

    }

    @Override
    public List<TitleDTO> findAll() {
        return null;
    }

    @Override
    public void save(TitleDTO dto) {
        Title title = new Title();
        title.setId(dto.getId());
        title.setEmpNo(dto.getEmpNo());
        title.setToDate(dto.getToDate());
        titleRepository.save(title);
    }
}
