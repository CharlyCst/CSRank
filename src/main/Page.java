package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Page {
    private final String url;
    private String title;
    private AtomicInteger nbVisits;
    //private HashMap<String,Page> outNeighbors;
    private ArrayList<Page> outNeighbors;
    private ReentrantLock lock;
    private double CSRank;

    public Page(String url) {
        this.url = url;
        this.title = "";
        lock = new ReentrantLock();
        nbVisits = new AtomicInteger(0);
        outNeighbors = new ArrayList<Page>();
    }

    public void visit() {
        int nb=nbVisits.get();
        while (!nbVisits.compareAndSet(nb, nb+1)){
        	nb=nbVisits.get();
        }
    }

    public boolean add_neighbor(Page p) {
        lock.lock();
        try {
            if (!outNeighbors.contains(p)) {
                outNeighbors.add(p);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void setTitle(String t) {
        title = t;
    }

    public String getTitle() {
        return title;
    }

    public String get_url() {
        return url;
    }

    public int get_nbVisits() {
        lock.lock();
        try {
            return nbVisits.get();
        } finally {
            lock.unlock();
        }
    }

    public int get_nbVisits_unsafe() {
        return nbVisits.get();
    }

    public ArrayList<Page> get_neighbors() {
        return this.outNeighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;
        Page that = (Page) o;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    public double get_CSRank() {
        return CSRank;
    }

    public void set_CSRank(double CSRank) {
        this.CSRank = CSRank;
    }
}
