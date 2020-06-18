package com.example.apiarymange.Model;

import java.util.List;

public class ListFrames {
    private List<Frame> Frames;

    public ListFrames() {
    }

    public ListFrames(List<Frame> Frames) {
        this.Frames = Frames;
    }

    public List<Frame> getFrames() {
        return Frames;
    }

    public void setFrames(List<Frame> Frames) {
        this.Frames = Frames;
    }
}
