package pl.coderslab.warsztat3.model;

import java.util.Date;

public class SolutionDTO {
	private int id;
	private String title;
	private int userId;
	private Date submitionDate;
	
	public SolutionDTO(int id, String title, int userId, Date submitionDate) {
		super();
		this.id = id;
		this.title = title;
		this.userId = userId;
		this.submitionDate = submitionDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getSubmitionDate() {
		return submitionDate;
	}
	public void setSubmitionDate(Date submitionDate) {
		this.submitionDate = submitionDate;
	}
	
	
}
