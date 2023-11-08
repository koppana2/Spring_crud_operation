package com.example.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Tutorial;


@Repository
public class TutorialRepositoryImpl implements TutorialRepository{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int save(Tutorial tutorial) {
		logger.info("Save Method from Repository Class!!");
		return jdbcTemplate.update("INSERT INTO tutorials(title,description,published) values(?,?,?)",
				new Object[] {tutorial.getTitle(),tutorial.getDescription(),tutorial.isPublished()});
	}
	
	@Override
	public int update(Tutorial tutorial) {
		logger.info("Update Method from Repository class");
		return jdbcTemplate.update("UPDATE tutorials set title=?,description=?,published=? where id=?  ",
				new Object[] {tutorial.getTitle(),tutorial.getDescription(),tutorial.isPublished(),tutorial.getId()});
	}

	@Override
	public Tutorial findById(Long id) {
		logger.info("FindById Method from Repository class");
		try {
			Tutorial tutorial = jdbcTemplate.queryForObject("SELECT * from tutorials where id=?",
					BeanPropertyRowMapper.newInstance(Tutorial.class),id);
				return tutorial;
			}
		catch(IncorrectResultSizeDataAccessException e){
			return null;
		}
	}

	@Override
	public int deleteById(Long id){
		logger.info("DeleteById Method from Repository class");
		return jdbcTemplate.update("DELETE from tutorials where id=?",id);
	}

	@Override
	public List<Tutorial> findAll() {
		logger.info("FindAll Method from Repository class");
		return jdbcTemplate.query("SELECT * from tutorials", BeanPropertyRowMapper.newInstance(Tutorial.class));
	}

	@Override
	public List<Tutorial> findByTitleContaining(String title) {
		String q="SELECT * from tutorials WHERE title ILIKE '%" + title + "%'";
		return jdbcTemplate.query(q,BeanPropertyRowMapper.newInstance(Tutorial.class));
	}

	@Override
	public int deleteAll() {
		logger.info("DeleteAll Method from Repository class");
		return jdbcTemplate.update("DELETE from tutorials");
	}

	@Override
	public List<Tutorial> findByPublished(boolean published) {
		return jdbcTemplate.query("SELECT * from tutorials where published=?",
				BeanPropertyRowMapper.newInstance(Tutorial.class),published);
	}
}
