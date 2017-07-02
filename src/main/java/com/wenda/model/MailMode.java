package com.wenda.model;

import java.util.Map;

/**
 * Created by 49540 on 2017/6/30.
 */
public class MailMode {
    private String to;
    private String subject;
    private String template;
    private Map<String,Object> model;
    private String text;


    public MailMode set(String key,String value)
    {
        model.put(key,value);
        return this;
    }
    public Object get(String key)
    {
        return model.get(key);
    }
    public String getTo() {
        return to;
    }

    public MailMode setTo(String to) {
        this.to = to;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MailMode setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public MailMode setTemplate(String template) {
        this.template = template;
        return this;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public MailMode setModel(Map<String, Object> model) {
        this.model = model;
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
