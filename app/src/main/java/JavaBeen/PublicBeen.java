package JavaBeen;

import com.esri.core.geometry.Point;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PublicBeen implements Serializable{
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
    //管理局
    private String Manager_id;
    private String Manager_name;

    public String getManager_id() {
        return Manager_id;
    }

    public void setManager_id(String manager_id) {
        Manager_id = manager_id;
    }

    public String getManager_name() {
        return Manager_name;
    }

    public void setManager_name(String manager_name) {
        Manager_name = manager_name;
    }
    //项目状态信息
    private String project_state;
    private String project_id;
    private String PROBLEMCONTENT;
    private String IMAGESOURCE;
    private String project_TIME;
    private String project_LoginUserList;

    public String getProject_state() {
        return project_state;
    }

    public void setProject_state(String project_state) {
        this.project_state = project_state;
    }

    public String getProject_LoginUserList() {
        return project_LoginUserList;
    }

    public void setProject_LoginUserList(String project_LoginUserList) {
        this.project_LoginUserList = project_LoginUserList;
    }

    public String getProject_TIME() {
        return project_TIME;
    }

    public void setProject_TIME(String project_TIME) {
        this.project_TIME = project_TIME;
    }

    public String getPROBLEMCONTENT() {
        return PROBLEMCONTENT;
    }

    public void setPROBLEMCONTENT(String PROBLEMCONTENT) {
        this.PROBLEMCONTENT = PROBLEMCONTENT;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getIMAGESOURCE() {
        return IMAGESOURCE;
    }

    public void setIMAGESOURCE(String IMAGESOURCE) {
        this.IMAGESOURCE = IMAGESOURCE;
    }
    //负责人
    private String person_ID;
    private String person_Name;
    private String person_Sex;
    private String person_Power;
    private String person_Tel;
    private String person_PROID;
    private String person_GuanlijuList;

    public String getPerson_ID() {
        return person_ID;
    }

    public void setPerson_ID(String person_ID) {
        this.person_ID = person_ID;
    }

    public String getPerson_Name() {
        return person_Name;
    }

    public void setPerson_Name(String person_Name) {
        this.person_Name = person_Name;
    }

    public String getPerson_Sex() {
        return person_Sex;
    }

    public void setPerson_Sex(String person_Sex) {
        this.person_Sex = person_Sex;
    }

    public String getPerson_Power() {
        return person_Power;
    }

    public void setPerson_Power(String person_Power) {
        this.person_Power = person_Power;
    }

    public String getPerson_Tel() {
        return person_Tel;
    }

    public void setPerson_Tel(String person_Tel) {
        this.person_Tel = person_Tel;
    }

    public String getPerson_PROID() {
        return person_PROID;
    }

    public void setPerson_PROID(String person_PROID) {
        this.person_PROID = person_PROID;
    }

    public String getPerson_GuanlijuList() {
        return person_GuanlijuList;
    }

    public void setPerson_GuanlijuList(String person_GuanlijuList) {
        this.person_GuanlijuList = person_GuanlijuList;
    }
    //登录
    private String Login_State;
    private String Login_ID;
    private String Login_Name;
    private String Login_Power;
    private String Login_Sex;
    private String Login_Tel;
    private String Login_GLJNAME;
    private String Login_GLJID;
    private String Login_proID;
    private String Login_proName;

    public String getLogin_proID() {
        return Login_proID;
    }

    public void setLogin_proID(String login_proID) {
        Login_proID = login_proID;
    }

    public String getLogin_proName() {
        return Login_proName;
    }

    public void setLogin_proName(String login_proName) {
        Login_proName = login_proName;
    }

    public String getLogin_State() {
        return Login_State;
    }

    public void setLogin_State(String login_State) {
        Login_State = login_State;
    }

    public String getLogin_ID() {
        return Login_ID;
    }

    public void setLogin_ID(String login_ID) {
        Login_ID = login_ID;
    }

    public String getLogin_Name() {
        return Login_Name;
    }

    public void setLogin_Name(String login_Name) {
        Login_Name = login_Name;
    }

    public String getLogin_Power() {
        return Login_Power;
    }

    public void setLogin_Power(String login_Power) {
        Login_Power = login_Power;
    }

    public String getLogin_Sex() {
        return Login_Sex;
    }

    public void setLogin_Sex(String login_Sex) {
        Login_Sex = login_Sex;
    }

    public String getLogin_Tel() {
        return Login_Tel;
    }

    public void setLogin_Tel(String login_Tel) {
        Login_Tel = login_Tel;
    }

    public String getLogin_GLJNAME() {
        return Login_GLJNAME;
    }

    public void setLogin_GLJNAME(String login_GLJNAME) {
        Login_GLJNAME = login_GLJNAME;
    }

    public String getLogin_GLJID() {
        return Login_GLJID;
    }

    public void setLogin_GLJID(String login_GLJID) {
        Login_GLJID = login_GLJID;
    }
    //
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
