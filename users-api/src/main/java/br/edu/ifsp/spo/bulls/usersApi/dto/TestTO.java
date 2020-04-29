package br.edu.ifsp.spo.bulls.usersApi.dto;

import lombok.Data;

@Data
public class TestTO {
    public TestTO(){

    }
    private String teamName;
    private String university;
    private Long year;
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
    
    
}
