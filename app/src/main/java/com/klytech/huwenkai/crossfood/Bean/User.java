package com.klytech.huwenkai.crossfood.Bean;

import java.io.Serializable;

/**
 * Created by huwenkai on 2017-03-13.
 */

public class User implements Serializable {


    /**
     * code : 200
     * data : {"id":"11","rest_id":"10","name":"给对手犯规","account":"gdsfg11","remark":"65","status":"1","time":"1488262350","token":"1f7f7df8f46eb8a44e0f5262f8fa9a0b503e1e52","login":true}
     * msg :
     */

    public int code;
    public DataBean data;
    public String msg;

    public static class DataBean {
        /**
         * id : 11
         * rest_id : 10
         * name : 给对手犯规
         * account : gdsfg11
         * remark : 65
         * status : 1
         * time : 1488262350
         * token : 1f7f7df8f46eb8a44e0f5262f8fa9a0b503e1e52
         * login : true
         */

        public String id;
        public String rest_id;
        public String name;
        public String account;
        public String remark;
        public String status;
        public String time;
        public String token;
        public boolean login;
    }
}
