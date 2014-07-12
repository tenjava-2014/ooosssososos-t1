package com.hotmail.ooosssososos.Managers.Data;

import org.bukkit.entity.ItemFrame;

public class FrameTimeEntry{
    public ItemFrame frame;
    public long time;
    public FrameTimeEntry(ItemFrame f, long t){
        time = t;
        frame = f;
    }
}