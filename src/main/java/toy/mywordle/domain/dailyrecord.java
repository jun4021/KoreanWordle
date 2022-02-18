package toy.mywordle.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class dailyrecord {
    @Id
    public String date;
    public Integer correctanswer=0;
    public Integer fail=0;
    public Integer trycount=0;
    public Integer visit=0;
    public Integer onetrycorrect=0;
    public Integer twotrycorrect=0;
    public Integer threetrycorrect=0;
    public Integer fourtrycorrect=0;
    public Integer fivetrycorrect=0;
    public Integer trystart =0;
    public Integer fourtryrun = 0;

    public Integer getTrystart() {
        return trystart;
    }

    public void setTrystart(Integer trystart) {
        this.trystart = trystart;
    }

    public Integer getFourtryrun() {
        return fourtryrun;
    }

    public void setFourtryrun(Integer fourtryrun) {
        this.fourtryrun = fourtryrun;
    }

    public Integer gettrystart() {
        return trystart;
    }

    public void settrystart(Integer trystart) {
        this.trystart = trystart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(Integer correctanswer) {
        this.correctanswer = correctanswer;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public Integer getTrycount() {
        return trycount;
    }

    public void setTrycount(Integer trycount) {
        this.trycount = trycount;
    }

    public Integer getVisit() {
        return visit;
    }

    public void setVisit(Integer visit) {
        this.visit = visit;
    }

    public Integer getOnetrycorrect() {
        return onetrycorrect;
    }

    public void setOnetrycorrect(Integer onetrycorrect) {
        this.onetrycorrect = onetrycorrect;
    }

    public Integer getTwotrycorrect() {
        return twotrycorrect;
    }

    public void setTwotrycorrect(Integer twotrycorrect) {
        this.twotrycorrect = twotrycorrect;
    }

    public Integer getThreetrycorrect() {
        return threetrycorrect;
    }

    public void setThreetrycorrect(Integer threetrycorrect) {
        this.threetrycorrect = threetrycorrect;
    }

    public Integer getFourtrycorrect() {
        return fourtrycorrect;
    }

    public void setFourtrycorrect(Integer fourtrycorrect) {
        this.fourtrycorrect = fourtrycorrect;
    }

    public Integer getFivetrycorrect() {
        return fivetrycorrect;
    }

    public void setFivetrycorrect(Integer fivetrycorrect) {
        this.fivetrycorrect = fivetrycorrect;
    }
}
