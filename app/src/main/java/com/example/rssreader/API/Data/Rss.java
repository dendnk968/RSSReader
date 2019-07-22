package com.example.rssreader.API.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class Rss {
    @Element(name = "channel")
    private Channel channel;
    public Rss(){}
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Rss(Channel channel) {
        this.channel = channel;
    }
}
