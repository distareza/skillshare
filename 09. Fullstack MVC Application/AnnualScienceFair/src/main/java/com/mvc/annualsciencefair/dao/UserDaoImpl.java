package com.mvc.annualsciencefair.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.mvc.annualsciencefair.model.Login;
import com.mvc.annualsciencefair.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean register(User user) {
		String sql = "insert into registrations " +
					 "	(student_id, student_name, university_name, project_area, email, password) " + 
					 "values( ?, ?, ?, ?, ?, ? ) ";
		int rowsInserted = jdbcTemplate.update(sql, new Object[] {
				user.getStudentId(),
				user.getStudentName(),
				user.getUniversityName(),
				user.getProjectArea(),
				user.getEmail(),
				Objects.hash(user.getPassword())
		});
		
		return rowsInserted > 0;
	}

	@Override
	public User validateUser(Login login) {
		String sql = "select * from registrations where student_id = ? and password = ?";
		List<User> users = jdbcTemplate.query(sql, 
				new Object[] {login.getStudentId(), Objects.hash(login.getPassword())}, 
				new RowMapper<User>() {
					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setStudentId(rs.getInt("student_id"));
						user.setStudentName(rs.getString("student_name"));
						user.setUniversityName(rs.getString("university_name"));
						user.setProjectArea(rs.getString("project_area"));
						user.setEmail(rs.getString("email"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});
		return users.size() > 0 ? users.get(0) : null;
	}

	@Override
	public boolean doesEmailExists(String email) {
		String sql = "select email from registrations";
		List<String> emails = jdbcTemplate.queryForList(sql, String.class);
		for (String retrievedEmail:emails) {
			if (email.equalsIgnoreCase(retrievedEmail))
				return true;
		}		
		return false;
	}

	@Override
	public boolean doesIdExists(Integer studentId) {
		String sql = "select student_id from registrations";
		List<Integer> studentIds = jdbcTemplate.queryForList(sql, Integer.class);
		for (Integer retrievedId:studentIds) {
			if (studentId.equals(retrievedId))
				return true;
		}		
		return false;
	}

}


