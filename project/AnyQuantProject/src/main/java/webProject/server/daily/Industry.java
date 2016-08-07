package webProject.server.daily;
import java.util.*;

/**
 * Created by G on 16/5/11.
 */
public class Industry {

    String industry_name;
    Date date;
    double open;
    double close;
    double max;
    double min;
    long volumn;
    double updown;
    double pure;
    double total;
    int companySum;
    String leader;
    double leaderPrice;
    double leaderUpdown;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIndustry_name() {
        return industry_name;
    }

    public void setIndustry_name(String industry_name) {
        this.industry_name = industry_name;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public long getVolumn() {
        return volumn;
    }

    public void setVolumn(long volumn) {
        this.volumn = volumn;
    }

    public double getUpdown() {
        return updown;
    }

    public void setUpdown(double updown) {
        this.updown = updown;
    }

    public double getPure() {
        return pure;
    }

    public void setPure(double pure) {
        this.pure = pure;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCompanySum() {
        return companySum;
    }

    public void setCompanySum(int companySum) {
        this.companySum = companySum;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public double getLeaderPrice() {
        return leaderPrice;
    }

    public void setLeaderPrice(double leaderPrice) {
        this.leaderPrice = leaderPrice;
    }

    public double getLeaderUpdown() {
        return leaderUpdown;
    }

    public void setLeaderUpdown(double leaderUpdown) {
        this.leaderUpdown = leaderUpdown;
    }
}
