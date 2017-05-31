package JavaBeen;

import com.esri.core.geometry.Point;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PublicBeen {
    //搜索企业
    private String sortLetters;
    private String companyName;

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    //地图企业位置
    private Point point;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
    //问题详情
    private String Order;
    private String commitTime;
    private String zerenren;
    private String commitType;
    private String commitProblem;
    public String getCommitProblem() {
        return commitProblem;
    }

    public void setCommitProblem(String commitProblem) {
        this.commitProblem = commitProblem;
    }

    public String getCommitType() {
        return commitType;
    }

    public void setCommitType(String commitType) {
        this.commitType = commitType;
    }

    public String getZerenren() {
        return zerenren;
    }

    public void setZerenren(String zerenren) {
        this.zerenren = zerenren;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }



}
