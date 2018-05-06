package com.sam.graduation.design.gdemailserver.model.pojo;

import java.util.Date;

public class TbFriends {
    private Long id;

    private Long usererid;

    private Long useredid;

    private Date focustime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsererid() {
        return usererid;
    }

    public void setUsererid(Long usererid) {
        this.usererid = usererid;
    }

    public Long getUseredid() {
        return useredid;
    }

    public void setUseredid(Long useredid) {
        this.useredid = useredid;
    }

    public Date getFocustime() {
        return focustime;
    }

    public void setFocustime(Date focustime) {
        this.focustime = focustime;
    }
}