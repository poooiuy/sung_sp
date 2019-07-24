package sung01_solve_server;

public class ReportVo {

	private String insId;
	private int checkCard;
	private int failCard;
	
	public ReportVo() {
	}
	
	public ReportVo(String line) {
		insId = line.split(" ")[0];
		checkCard = Integer.parseInt(line.split(" ")[1]);
		failCard = Integer.parseInt(line.split(" ")[2]);
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public int getCheckCard() {
		return checkCard;
	}

	public void setCheckCard(int nCheckCard) {
		this.checkCard = nCheckCard;
	}

	public int getFailCard() {
		return failCard;
	}

	public void setFailCard(int nFailCard) {
		this.failCard = nFailCard;
	}

	public void increaseCheckCard() {
		this.checkCard++;
	}

	public void increaseFailCard() {
		this.failCard++;
	}

	@Override
	public String toString() {
		return insId + " " + checkCard + " " + failCard;
	}
}