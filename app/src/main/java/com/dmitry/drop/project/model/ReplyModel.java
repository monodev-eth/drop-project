package com.dmitry.drop.project.model;

/**
 * Created by Laptop on 21/06/2016.
 */
public interface ReplyModel {

    void saveReply(Post post, String author, String annotation, String timeElapsed, String imageFilePath);

    Reply getReply(long replyId);
}
