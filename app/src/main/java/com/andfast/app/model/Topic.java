package com.andfast.app.model;

import java.util.Date;

/**
 * Created by mby on 17-8-8.
 */

public class Topic {
    public String id;
    public String author_id;
    public String tab;
    public String content;
    public String title;
    public Date last_reply_at;
    public boolean good;
    public boolean top;
    public Integer reply_count;
    public Integer visit_count;
    public Date create_at;
    public Author author;

    public class Author{
        public String loginname;
        public String avatar_url;
    }
}
