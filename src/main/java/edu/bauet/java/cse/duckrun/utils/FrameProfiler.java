package edu.bauet.java.cse.duckrun.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Lightweight frame-time logger used to find out WHY the game stutters.
 *
 * It writes to  ~/duckdash_perf.log  (your user/home folder).
 *
 * Each line is a frame that took longer than the threshold:
 *   gap     = time between this frame and the previous one
 *   prevWork= time the game code itself spent in the previous frame
 *   other   = gap - prevWork  (rendering / GPU / GC / OS)
 *   heap    = used Java heap in MB (a drop vs the last frame suggests garbage collection)
 *
 * Reading it:
 *   prevWork large            -> game code is slow in that frame
 *   prevWork tiny, other large-> rendering/GPU, garbage collection or the OS
 */
public final class FrameProfiler {

    private static final double HITCH_MS = 24.0;       // > ~1.5 frames at 60 fps
    private static final long   SUMMARY_NS = 5_000_000_000L;

    private static PrintWriter out;
    private static long lastNow = 0;
    private static long t0 = 0;
    private static double prevWorkMs = 0;
    private static long lastHeap = 0;
    private static long startNanos = 0;

    // summary window
    private static long winStart = 0;
    private static int  winFrames = 0;
    private static int  winHitches = 0;
    private static double winMaxGap = 0;
    private static double winSumGap = 0;

    private FrameProfiler() { }

    private static void open() {
        if (out != null) return;
        try {
            Path p = Paths.get(System.getProperty("user.home"), "duckdash_perf.log");
            out = new PrintWriter(new FileWriter(p.toFile(), false), true);
            startNanos = System.nanoTime();
            out.println("# DuckDash frame log  " + p);
            out.println("# os=" + System.getProperty("os.name")
                    + "  java=" + System.getProperty("java.version")
                    + "  javafx=" + System.getProperty("javafx.runtime.version")
                    + "  cpus=" + Runtime.getRuntime().availableProcessors()
                    + "  maxHeapMB=" + Runtime.getRuntime().maxMemory() / 1048576);
        } catch (IOException e) {
            out = new PrintWriter(System.out, true);
        }
    }

    private static long usedHeap() {
        Runtime r = Runtime.getRuntime();
        return r.totalMemory() - r.freeMemory();
    }

    /** Call at the very start of each game-loop pulse. */
    public static void begin(long now) {
        open();
        t0 = System.nanoTime();
        if (lastNow != 0) {
            double gapMs = (now - lastNow) / 1_000_000.0;
            long heap = usedHeap();
            winFrames++;
            winSumGap += gapMs;
            if (gapMs > winMaxGap) winMaxGap = gapMs;
            if (gapMs > HITCH_MS) {
                winHitches++;
                double otherMs = gapMs - prevWorkMs;
                boolean heapDropped = heap < lastHeap - 512 * 1024;
                out.printf("t=%6.1fs HITCH gap=%6.1fms prevWork=%6.1fms other=%6.1fms heap=%dMB%s%n",
                        (System.nanoTime() - startNanos) / 1e9, gapMs, prevWorkMs, otherMs,
                        heap / 1048576, heapDropped ? "  (heap dropped: GC likely)" : "");
            }
            lastHeap = heap;
        } else {
            winStart = System.nanoTime();
            lastHeap = usedHeap();
        }
        lastNow = now;

        long nt = System.nanoTime();
        if (nt - winStart >= SUMMARY_NS && winFrames > 0) {
            out.printf("t=%6.1fs summary: avgGap=%5.2fms maxGap=%6.1fms frames=%d hitches=%d%n",
                    (nt - startNanos) / 1e9, winSumGap / winFrames, winMaxGap, winFrames, winHitches);
            winStart = nt; winFrames = 0; winHitches = 0; winMaxGap = 0; winSumGap = 0;
        }
    }

    /** Call at the very end of each game-loop pulse. */
    public static void end() {
        prevWorkMs = (System.nanoTime() - t0) / 1_000_000.0;
    }

    /** Call when the loop is paused/resumed so the pause isn't logged as a hitch. */
    public static void reset() {
        lastNow = 0;
    }
}
