package com.jd.wyjm.remote.beans;

import java.io.Serializable;

/**
 * 返回空数据处理
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
public class NullWritable implements Serializable {

    private static NullWritable instance = new NullWritable();

    private NullWritable() {
    }

    public static NullWritable nullWritable() {
        return instance;
    }
}
