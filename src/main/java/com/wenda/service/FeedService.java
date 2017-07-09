package com.wenda.service;

import com.wenda.dao.FeedDao;
import com.wenda.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 49540 on 2017/7/8.
 */
@Service
public class FeedService {
    @Autowired
    FeedDao feedDao;

    public boolean addFeed(Feed feed)
    {
        return feedDao.addFeed(feed)>0;
    }

    public List<Feed> getFeeds(int offset,int limit)
    {
        return feedDao.selectFeeds(offset, limit);
    }

    public List<Feed> getUserFeeds(int maxId,List<Integer> userIds,int count)
    {
        return feedDao.selectUserFeeds(maxId, userIds, count);
    }

    public Feed getById(int id)
    {
        return feedDao.selectFeedById(id);
    }
}
