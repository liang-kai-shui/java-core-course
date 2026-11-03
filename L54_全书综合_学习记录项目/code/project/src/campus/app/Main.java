package campus.app;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import campus.model.*;
import campus.io.StudyStore;
import campus.report.StudyReport;
public class Main {
    static StudyBook seed() {
        StudyBook book = new StudyBook(); book.add(new StudyEntry(1, "数组", 30, false)); book.add(new StudyEntry(2, "循环", 20, true)); book.add(new StudyEntry(3, "类", 15, false)); book.setCompleted(1, true); return book;
    }
    static void report(StudyBook book) throws InterruptedException, ExecutionException {
        List<StudyEntry> snapshot = book.snapshot(); ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            StudyReport result = pool.submit(() -> StudyReport.summarize(snapshot)).get();
            System.out.println("entries=" + result.getEntries()); System.out.println("completed-minutes=" + result.getCompletedMinutes()); System.out.println("pending=" + result.getPending());
        } finally { pool.shutdownNow(); if (!pool.awaitTermination(3, TimeUnit.SECONDS)) throw new IllegalStateException("report pool active"); }
    }
    static void checkErrors() throws IOException {
        StudyBook old = seed();
        try { old.add(new StudyEntry(1, "重复", 0, false)); } catch (IllegalArgumentException e) { System.out.println("duplicate-rejected"); }
        try { new StudyEntry(4, "负数", -1, false); } catch (IllegalArgumentException e) { System.out.println("negative-rejected"); }
        try { old.setCompleted(99, true); } catch (NoSuchElementException e) { System.out.println("unknown-rejected"); }
        String[] invalid = {"1\t20\ttrue\t好\n2\tbad\tfalse\t坏\n", "1\t0\tyes\t坏\n", "1\t0\ttrue\t\n", "1\t0\ttrue\tA\n1\t0\tfalse\tB\n", "1\t0\ttrue\n"};
        for (String text : invalid) { try { old = StudyStore.read(new StringReader(text)); throw new AssertionError("invalid accepted"); } catch (IOException expected) { System.out.println(expected.getMessage()); } }
        System.out.println("preserved=" + old.snapshot().size()); System.out.println("empty=" + StudyStore.read(new StringReader("")).snapshot().size());
        List<StudyEntry> snapshot = old.snapshot(); old.setCompleted(3, true); System.out.println("snapshot-old=" + !snapshot.get(2).isCompleted());
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        try {
            if (args.length == 1 && args[0].equals("--check-errors")) { checkErrors(); return; }
            StudyBook book = seed();
            if (args.length == 2 && args[0].equals("--load")) {
                try (Reader in = Files.newBufferedReader(Paths.get(args[1]), StandardCharsets.UTF_8)) { book = StudyStore.read(in); }
            } else if (args.length == 2 && args[0].equals("--new")) {
                try (Writer out = Files.newBufferedWriter(Paths.get(args[1]), StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW)) { StudyStore.write(book, out); }
                System.out.println("saved"); return;
            } else if (args.length != 0) { System.out.println("usage: --load path | --new path | --check-errors"); return; }
            report(book); StringWriter text = new StringWriter(); StudyStore.write(book, text); StudyBook restored = StudyStore.read(new StringReader(text.toString()));
            System.out.println("restored=" + restored.snapshot().size());
        } catch (FileAlreadyExistsException e) { System.out.println("already-exists"); }
        catch (IOException e) { System.out.println("io-error:" + e.getMessage()); }
    }
}
