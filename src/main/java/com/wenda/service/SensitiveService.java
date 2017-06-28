package com.wenda.service;

import javafx.fxml.Initializable;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 49540 on 2017/6/28.
 */
@Service
public class SensitiveService implements InitializingBean {

    /**
     * 默认敏感词替换符
     */
    private static final String DEFAULT_REPLACEMENT = "***";
    //日志
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    @Override
    public void afterPropertiesSet() throws Exception {
        root = new TrieNode();
        BufferedReader reader = null;
        try{
            InputStream inputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("SensitiveWords.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String lineText;
            while((lineText=reader.readLine())!=null)
            {
                logger.debug("linetext",lineText);
                addWord(lineText.trim());
            }
        }
        catch (Exception e)
        {
            logger.error("打开文件失败错误");
        }
        finally {
            if(reader!=null) {
                reader.close();
            }
        }
    }

    /***
     * 前缀树数据结构
     */
    private class TrieNode
    {
        private boolean end = false;

        private Map<Character,TrieNode> subNode = new HashMap<>();

        void addSubNode(Character key,TrieNode node)
        {
            subNode.put(key,node);
        }
        TrieNode getSubNode(Character key)
        {
            return subNode.get(key);
        }

        boolean isKeyWordEnd()
        {
            return end;
        }

        void setKeyWordEnd(boolean end)
        {
            this.end = end;
        }

        int getNodeCount()
        {
            return subNode.size();
        }
    }
    TrieNode root = new TrieNode();

    /***
     * 添加敏感词，构建前缀树
     * @param lineText
     */
    public void addWord(String lineText)
    {
        TrieNode tempNode = root;
        for(int i = 0;i<lineText.length();i++)
        {
            Character c = lineText.charAt(i);
            if(isSymbol(c))
            {
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);
            if(node==null)
            {
                node = new TrieNode();
                tempNode.addSubNode(c,node);
            }
            tempNode = node;
            if(i == lineText.length()-1)
            {
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    /***
     * 过滤敏感词
     * @param text
     * @return
     */
    public String filter(String text)
    {
         if(StringUtils.isBlank(text))
         {
             return text;
         }
         StringBuilder sb = new StringBuilder();
         String replacement = DEFAULT_REPLACEMENT;
         TrieNode tempNode = root;
         int begin = 0;//回滚位置
         int position = 0;//比较位置
         while(position<text.length())
         {
             Character c = text.charAt(position);
             //如果有空格，在敏感词中就略去，在敏感词外添加
            if(isSymbol(c))
            {
                if(tempNode==root)
                {
                    begin++;
                    sb.append(c);
                }
                position++;
                continue;
            }
             tempNode = tempNode.getSubNode(c);
            //无子节点，代表两种情况，一种是进入敏感词阶段但未完全匹配，
             // 一种是还未进入敏感词阶段
             if(tempNode==null)
             {
                 sb.append(text.charAt(begin));
                 position = begin+1;
                 begin = position;
                 tempNode = root;
             }
             //有子节点但未到最终节点，此时只需将比较指针+1
             else if(!tempNode.isKeyWordEnd()){
                position++;
             }
             //有子节点且到最终节点，此时代表进入敏感词且匹配成功
             //将替代词拼接，根节点还原即可
             else{
                sb.append(replacement);
                position = position+1;
                begin = position;
                tempNode = root;
             }
         }
         //如果此时begin还未走到最后，加上begin最后的字符（唯一的情况就是最后一点
        // 跟前缀树部分匹配）
         sb.append(text.substring(begin));
         return sb.toString();
    }

    public boolean isSymbol(Character c)
    {
        int ic = (int)c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public static void main(String[] args)
    {
        SensitiveService sensitiveService = new SensitiveService();
        sensitiveService.addWord("色情");
        System.out.println(sensitiveService.filter("你好，色色情"));
    }

}
