package br.edu.ifsp.spo.bulls.usersApi.dto;

public class TestTO {
    public TestTO(){

    }
    public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	private String teamName;
    private String university;
    private Long year;
}
